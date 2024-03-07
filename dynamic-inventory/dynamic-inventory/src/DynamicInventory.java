

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DynamicInventory {

    private final static String [] GET_HOSTS = new String[] {"vagrant", "status", "--machine-readable"};

    public static void main(String[] args) throws IOException, InterruptedException {
        List<String> runningHosts = processHosts(executeCommand(GET_HOSTS));
        List<String> ports = new ArrayList<>();
        for (String runningHost : runningHosts) {
            ports.add(getPort(executeCommand(new String[]{"vagrant", "ssh-config", runningHost})));
        }
//        System.out.println(runningHosts);
//        System.out.println(ports);
        File inventory = new File("./inventory/hosts.ini");
        try (FileWriter fw = new FileWriter(inventory)) {
            fw.write("[" + args[0] + "]\n");
            for (int i = 0; i < runningHosts.size(); i++) {
                fw.write(runningHosts.get(i) + " ansible_port=" + ports.get(i) + "\n");
            }
        }

        System.exit(0);
    }


    private static List<String> executeCommand(String [] command) throws IOException, InterruptedException {
        Process p = Runtime.getRuntime().exec(command);
        List<String> hosts = new ArrayList<>();

        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

        while ((line = reader.readLine()) != null) {
            hosts.add(line);
        }

        return hosts;
    }

    private static List<String> processHosts(List<String> hostInfo) {
        List<String> runningHosts = new ArrayList<>();

        for (String s : hostInfo) {
            String[] line = s.split(",");
            if (line[2].equals("state") && line[3].equals("running")) runningHosts.add(line[1]);
        }

        return runningHosts;
    }

    private static String getPort(List<String> hostInfo) {
        for (String s : hostInfo) {
            String[] line = s.split(" ");
            for (int i = 0; i < line.length; i++) {
                if (line[i].equals("Port")) return line[i + 1];
            }
        }

        return null;
    }


}