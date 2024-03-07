
# Ansible playbooks that run containerized Java Application on remote hosts

## Prerequisites
Make sure you've downloaded Community.Docker collection for the ansible. It supports 2.11.0 ansible version or newer. Collection can be installed using ansible-galaxy.
Remote machines are running using VMWare Fusion and the images for them are arm64 that's why if your machine doesn't run with Apple silicon chip, then change Vagrantfile and select suitable for you OS images.
Vagrant should be also installed on the host machine.

## Directories

 - **scripts/** - the directory contains all necessary configs, playbooks, files to run hosts with Vagrant and manage them with Ansible. 
 - **dynamic inventory/** - Java app that creates hosts.ini file automatically searching for running Vagrant hosts.  The app can be run with the *script.sh* script.
 - **AnsibleTestApp/** - Spring Boot application that is containerized with Docker on the remote host. With the right port forwarding can be accessed from the host machine/control node on which you are running ansible.
## Scripts directory
As said previously contains the necessary files to manage remote hosts and run the Spring Boot app in a container.
 - **files_to_copy/** - contains all the files that are copied to the remote hosts: .jar, Dockerfile, and external config for the jar file. 
 - **inventory/** - plays the role of the ansible inventory and contains two inventories - hosts.ini and inventory.ini. Both are the same, on was created automatically with the dynamic invenotory app.
 - **playbook_vars/** - folder contains all the vars.yaml files that serve as external sources of all variables that are used in the playbooks.
 - **ansible.cfg** - ansible configuration file
 - **install_docker_playbook.yaml** - a playbook that installs Docker Engine on a remote machine
 - **java_install_playbook.yaml** - a playbook that installs Java 21 on a remote machine
 - **run_app_in_container_playbook.yaml** - a playbook that runs Spring Boot application on a remote machine with the Docker.

## Playbook Description

 - **install_docker_playbook.yaml** - This playbook installs the packages needed for the Docker installation. Adds apt repostiory and then installs Docker Engine. Also creates a group called "docker" and adds there a current "vagrant" user to run docker commands without "sudo" command.
 - **java_install_playbook.yaml** - This playbook downloads ARM64 archive with the Java 21, creates symbolic links for the useful commands and makes it default Java on the remote machine.
 - **run_app_in_container_playbook.yaml** - This playbook creates the "app/" folder on the remote machine, then copies there the Dockerfile, the jar file and the external configuration file.  After that build an image with the corresponding Dockerfile and starts container. 
