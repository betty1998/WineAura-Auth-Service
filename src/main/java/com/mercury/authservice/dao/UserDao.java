package com.mercury.authservice.dao;

import com.mercury.authservice.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User,Integer> {
    public User findByUsername(String username);

    boolean existsByUsername(String username);

    List<User> findAllByStatus(String unactivated);
}
