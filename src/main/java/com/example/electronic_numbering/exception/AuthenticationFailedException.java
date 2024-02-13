package com.example.electronic_numbering.exception;


public class AuthenticationFailedException extends RuntimeException {

    public AuthenticationFailedException(String message){
        super(message);
    }
 }
