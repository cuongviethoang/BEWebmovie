package com.example.web.movie.webmovie.exception;

import java.util.Date;

public class ErrorMessage {
    private int statusCode;
    private Date timestamp;
    private String message;
    private String desc;

    public ErrorMessage(int statusCode, Date timestamp, String message, String desc) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.message = message;
        this.desc = desc;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
