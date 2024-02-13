package com.example.electronic_numbering.exception;

public class UserBadRequestException extends RuntimeException{
    public UserBadRequestException(String message) {
        super(message);
    }
}
