package com.example.sms.exception;


public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(){
        super();

    }
    public StudentNotFoundException(String sms){
        super(sms);
    }

}
