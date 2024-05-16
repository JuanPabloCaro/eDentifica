package com.project.edentifica.errors;

public class RollBackException extends RuntimeException {
    private String message;

    public RollBackException() {
        this.message = "Error in Transactional";
    }
    public RollBackException(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



}