package org.melihovs.ansibletestapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class AnsibleController {


    @GetMapping()
    public String welcomeController() {
        return "Ansible is cool!";
    }
}
