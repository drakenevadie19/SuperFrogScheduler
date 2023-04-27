package edu.tcu.cs.superfrogscheduler.system.exception;

public class DeactivateUserException extends RuntimeException {

    public DeactivateUserException(String reason) {
        super("Cannot deactivate user: " + reason);
    }

}
