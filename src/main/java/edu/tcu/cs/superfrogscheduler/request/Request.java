package edu.tcu.cs.superfrogscheduler.request;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.time.LocalDate;


@Entity
public class Request implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate eventDate; //YYYY-MM-DD

    private String eventTitle;

    private String customerFirstName;

    private String customerLastName;

    private Integer customerPhoneNumber;
    private String customerEmail;

    private RequestStatus requestStatus; //ENUM

    private String assignedSuperFrogStudent;

    // Constructors, getters, and setters

    public Request() {
    }

//    public Request(Long id, LocalDate eventDate, String eventTitle, String customerFirstName,
//                   String customerLastName, Integer customerPhoneNumber, String customerEmail,
//                   RequestStatus requestStatus, String assignedSuperFrogStudent) {
//        this.id = id;
//        this.eventDate = eventDate;
//        this.eventTitle = eventTitle;
//        this.customerFirstName = customerFirstName;
//        this.customerLastName = customerLastName;
//        this.customerPhoneNumber = customerPhoneNumber;
//        this.customerEmail = customerEmail;
//        this.requestStatus = requestStatus;
//        this.assignedSuperFrogStudent = assignedSuperFrogStudent;
//    }

    //This is what gets passed

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
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

    public String getAssignedSuperFrogStudent() {
        return assignedSuperFrogStudent;
    }

    public void setAssignedSuperFrogStudent(String assignedSuperFrogStudent) {
        this.assignedSuperFrogStudent = assignedSuperFrogStudent;
    }


    public Integer getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }
    public void setCustomerPhoneNumber(Integer customerPhoneNumber) {
        this.customerPhoneNumber= customerPhoneNumber;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
}

