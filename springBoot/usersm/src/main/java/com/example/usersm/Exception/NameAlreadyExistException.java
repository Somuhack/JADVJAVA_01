package com.example.usersm.Exception;

public class NameAlreadyExistException extends RuntimeException{
    public NameAlreadyExistException(String message){
        super(message);
    }
}