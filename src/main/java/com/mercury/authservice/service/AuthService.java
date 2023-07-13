package com.mercury.authservice.service;
import com.mercury.authservice.bean.User;
import com.mercury.authservice.dao.RoleDao;
import com.mercury.authservice.dao.UserDao;
import com.mercury.authservice.http.DataResponse;
import com.mercury.authservice.http.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;


    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateTokenOnly(String token) {
        jwtService.validateTokenOnly(token);
    }

    public DataResponse saveUser(User user) {
        boolean exists = userDao.existsByUsername(user.getUsername());
        if (exists) {
            return new DataResponse<>(false,"Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleDao.findByType("CUSTOMER"));
        user.setStatus("Active");
        userDao.save(user);
        return new DataResponse<>("Register successfully",user);
    }

    public LoginResponse updateUsername(User user){
        try {
            User oldUser = userDao.findById(user.getId())
                    .orElseThrow(() -> new Exception("Not found user by user id "+user.getId()));
            oldUser.setUsername(user.getUsername());
            userDao.save(oldUser);
            String token = generateToken(oldUser.getUsername());
            return new LoginResponse("update username successfully",oldUser,token);
        } catch (Exception e) {
            e.printStackTrace();
            return new LoginResponse(false, e.getMessage());
        }
    }

    public DataResponse<User> updatePassword(User user) {
        try{
            User oldUser = userDao.findById(user.getId())
                    .orElseThrow(() -> new Exception("Not found user by user id "+user.getId()));
            oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
            userDao.save(oldUser);
            return new DataResponse<>(oldUser);
        } catch (Exception e) {
            e.printStackTrace();
            return new DataResponse<>(false, e.getMessage());
        }
    }



    public DataResponse updateAdmin(User user) {
        try {
            User oldUser = userDao.findById(user.getId())
                    .orElseThrow(() -> new Exception("Not found user by user id "+user.getId()));
            oldUser.setUsername(user.getUsername());
            oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
            oldUser.setStatus("Active");
            oldUser.setRole(user.getRole());
            userDao.save(oldUser);
            return new DataResponse<>(oldUser);
        } catch (Exception e) {
            e.printStackTrace();
            return new DataResponse<>(false, e.getMessage());
        }
    }
}
