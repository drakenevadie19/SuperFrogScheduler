package edu.tcu.cs.superfrogscheduler.system.exception;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String message) {
        super("BadRequest: "+message+" :(");
    }

}
