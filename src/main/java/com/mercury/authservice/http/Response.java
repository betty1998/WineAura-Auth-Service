package com.mercury.authservice.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private boolean success;
    private String message;

    public Response(boolean success) {
        this.success = success;
        this.message = "";
    }
    public Response(String message) {
        this.success = true;
        this.message = message;
    }

}
