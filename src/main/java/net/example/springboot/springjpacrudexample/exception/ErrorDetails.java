package net.example.springboot.springjpacrudexample.exception;

import java.util.Date;

public class ErrorDetails {

    private Date timestamp;
    private String message;
    private String details;


    
    public ErrorDetails(Date timestamp, String message, String details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    
    public String getMessage() {
        return this.message;
    }

    
    public String getDetails() {
        return this.details;
    }

}