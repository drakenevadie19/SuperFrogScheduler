package edu.tcu.cs.superfrogscheduler.system.exception;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(String objName, String id) {
        super("Could not find " + objName + " with Id " + id + " :(");
    }

}
