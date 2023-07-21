package com.mercury.authservice.controller;


import com.mercury.authservice.bean.User;
import com.mercury.authservice.http.DataResponse;
import com.mercury.authservice.http.LoginResponse;
import com.mercury.authservice.security.UserDetailsServiceImpl;
import com.mercury.authservice.service.AuthService;
import com.mercury.authservice.vo.AuthRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/register")
    public DataResponse addUser(@RequestBody User user) {
        return authService.saveUser(user);
    }

    @PutMapping("/admin/register")
    public DataResponse updateAdmin(@RequestBody User user) {
        return authService.updateAdmin(user);
    }

    @PostMapping("login")
    public LoginResponse login(@RequestBody AuthRequest authRequest) {
        try{
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),authRequest.getPassword()));
            if(authentication.isAuthenticated()&&authentication.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("Customer"))){
                String token = authService.generateToken(authRequest.getUsername());
                User user = (User)userDetailsService.loadUserByUsername(authRequest.getUsername());
                if (user.getStatus()=="Active"){
                    return new LoginResponse("Login successfully", user, token);
                }else {
                    return new LoginResponse(false,"Your account is not active");
                }
            }else {
                return new LoginResponse(false,"Invalid access");
            }
        }catch (BadCredentialsException e) {
            return new LoginResponse(false, "Invalid username or password");
        }catch (Exception e){
            return new LoginResponse(false, "Login failed");
        }

    }

    @PostMapping("admin/login")
    public LoginResponse adminLogin(@RequestBody AuthRequest authRequest) {
        try{
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),authRequest.getPassword()));
            if(authentication.isAuthenticated()&&
                    authentication.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("Admin")||a.getAuthority().equals("Manager"))){
                String token = authService.generateToken(authRequest.getUsername());
                User user = (User)userDetailsService.loadUserByUsername(authRequest.getUsername());
                if (user.getStatus()=="Active"){
                    return new LoginResponse("Login successfully", user, token);
                }else {
                    return new LoginResponse(false,"Your account is not active");
                }
            }else {
                return new LoginResponse(false,"Invalid access");
            }
        }catch (BadCredentialsException e) {
            return new LoginResponse(false, "Invalid username or password");
        }catch (Exception e){
            return new LoginResponse(false, "Login failed");
        }

    }

    @GetMapping({"/checklogin","/admin/checklogin"})
    public DataResponse<User> checkLogin(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authHeader = request.getHeader("Authorization");
        System.out.println(authHeader);
        if (authHeader != null && authentication.isAuthenticated()) {
            System.out.println(authentication.getPrincipal());
            User user = (User) authentication.getPrincipal();
            return new DataResponse<>("User is authenticated", user );
        } else if (authHeader == null) {
            return new DataResponse<>(false, "Missing Authentication header");
        } else if (!authHeader.startsWith("Bearer ")){
            return new DataResponse<>(false, "Invalid Authentication header");
        } else {
            return new DataResponse<>(false, "User is not authenticated");
        }
    }


    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        authService.validateTokenOnly(token);
        return "Token is valid";


    }

    @PutMapping("/updateUsername")
    public LoginResponse updateUsername(@RequestBody User user) {
        return authService.updateUsername(user);
    }

    @PutMapping("/updatePassword")
    public DataResponse<User> updatePassword(@RequestBody User user) {
        return authService.updatePassword(user);
    }

   }
