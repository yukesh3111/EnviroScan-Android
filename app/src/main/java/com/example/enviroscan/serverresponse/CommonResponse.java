package com.example.enviroscan.serverresponse;

public class CommonResponse {
    String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String status;

    public String getUsername() {
        return username;
    }

    String username;

    public String getSucces() {
        return message;
    }

    public void setSucces(String message) {
        this.message = message;
    }
}
