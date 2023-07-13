package com.mercury.authservice.controller;

import com.mercury.authservice.bean.Role;
import com.mercury.authservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    RoleService roleService;

    @GetMapping
    public List<Role> getRoles() {

        return roleService.getRoles();
    }

    @PostMapping
    public Role addRole(@RequestBody Role role) {
        System.out.println(role);
        return roleService.saveRole(role);
    }
}
