package edu.tcu.cs.superfrogscheduler.system.exception;


public class EntityNotFoundException extends RuntimeException{


    public EntityNotFoundException(String message, String x, long y) {
        super("Could not find "+message+" with "+x+": "+y+" :(");
    }

}

