package com.mercury.authservice.dao;

import com.mercury.authservice.bean.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role,Integer> {
    Role findByType(String role);
}
