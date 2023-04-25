package edu.tcu.cs.superfrogscheduler.system.exception;

public class ObjectAlreadyExistedException extends RuntimeException{

    public ObjectAlreadyExistedException(String objName, String id) {
        super(objName + " " + id + " is already existed!");
    }

}
