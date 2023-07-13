package com.mercury.authservice.service;

import com.mercury.authservice.bean.Role;
import com.mercury.authservice.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    RoleDao roleDao;

    public Role saveRole(Role role) {
        return roleDao.save(role);
    }

    public List<Role> getRoles() {
        return roleDao.findAll();
    }
}
