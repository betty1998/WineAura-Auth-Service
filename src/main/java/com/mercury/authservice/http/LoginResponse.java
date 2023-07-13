package com.mercury.authservice.http;

import com.mercury.authservice.bean.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponse extends Response{
    private User user;
    private String token;

    public LoginResponse(boolean success, String message, User user, String token) {
        super(success, message);
        this.user = user;
        this.token = token;
    }

    public LoginResponse(User user, String token) {
        super(true, "");
        this.user = user;
        this.token = token;
    }

    public LoginResponse(boolean success, User user, String token) {
        super(success);
        this.user = user;
        this.token = token;
    }

    public LoginResponse(boolean success, String message) {
        super(success, message);
    }

    public LoginResponse(String message, User user, String token) {
        super(message);
        this.user = user;
        this.token = token;
    }
}
