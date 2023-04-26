package edu.tcu.cs.superfrogscheduler.system.exception;


public class EntityNotFoundException extends RuntimeException{


    public EntityNotFoundException(String message, String x, String y) {
        super("Could not find "+message+" with "+x+": "+y+" :(");
    }

    public EntityNotFoundException(String requestId) {
        super("Could not find Request with "+requestId+" :(");
    }
}

