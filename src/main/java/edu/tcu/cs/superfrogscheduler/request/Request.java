package edu.tcu.cs.superfrogscheduler.request;

import edu.tcu.cs.superfrogscheduler.user.entity.SuperFrogUser;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;


@Entity
public class Request implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private LocalDate eventDate; //YYYY-MM-DD

    private String eventTitle;
    private String eventDescription;


    private String customerFirstName;

    private String customerLastName;

    private String customerPhoneNumber;
    private String customerEmail;

    private RequestStatus requestStatus; //ENUM

    @ManyToOne
    private SuperFrogUser assignedSuperFrogStudent;

    @ManyToOne
    private SuperFrogUser superFrogUser;









    // Constructors, getters, and setters

    public Request() {
    }


    //This is what gets passed

    // Getters and setters

    public String getId() {
        return id;
    }

    public String setId(String id) {
        return this.id = id;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }


    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public SuperFrogUser getAssignedSuperFrogStudent() {

        return assignedSuperFrogStudent;
    }

    public SuperFrogUser setAssignedSuperFrogStudent(SuperFrogUser assignedSuperFrogStudent) {
        return this.assignedSuperFrogStudent = assignedSuperFrogStudent;
    }


    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }
    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber= customerPhoneNumber;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public SuperFrogUser getSuperFrogUser() {
        return superFrogUser;
    }

    public void setSuperFrogUser(SuperFrogUser superFrogUser) {
        this.superFrogUser = superFrogUser;
    }

}

