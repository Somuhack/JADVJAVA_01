package com.example.usersm.Exception;

public class NameNotFoundException extends RuntimeException{
    public NameNotFoundException(String message){
        super(message);
    }
}