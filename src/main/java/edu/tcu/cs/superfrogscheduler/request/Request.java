package edu.tcu.cs.superfrogscheduler.request;

import edu.tcu.cs.superfrogscheduler.reports.EventType;
import edu.tcu.cs.superfrogscheduler.user.entity.SuperFrogUser;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;


@Entity
public class Request implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    public String address;

    private EventType eventType;

    private LocalDate eventDate; //YYYY-MM-DD

    private LocalTime startTime;

    private LocalTime endTime;

    private Double mileage;

    private String eventTitle;
    private String eventDescription;


    private String customerFirstName;

    private String customerLastName;

    private String customerPhoneNumber;
    private String customerEmail;

    private RequestStatus requestStatus; //ENUM

    //Setting assignedSuperFrog as String broke a RequestService and RequestDto
    private String assignedSuperFrogStudent;

    @ManyToOne
    private SuperFrogUser superFrogUser;









    // Constructors, getters, and setters

    public Request() {
    }



    public Request(String id, EventType eventType, String address, Double mileage, LocalDate eventDate, LocalTime startTime, LocalTime endTime, RequestStatus requestStatus, String assignedSuperFrogStudent) {
        this.id = id;
        this.address = address;
        this.eventType = eventType;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.mileage = mileage;
        this.requestStatus = requestStatus;
        this.assignedSuperFrogStudent = assignedSuperFrogStudent;
    }



    //This is what gets passed

    // Getters and setters

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getAssignedSuperFrogStudent() {

        return assignedSuperFrogStudent;
    }

    public String setAssignedSuperFrogStudent(String assignedSuperFrogStudent) {
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

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Double getMileage() {
        return mileage;
    }

    public Double getMileageOver(Double freeMileage) {
        return this.mileage.compareTo(freeMileage) <= 0 ? 0.0 : this.mileage - freeMileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    public SuperFrogUser getSuperFrogUser() {
        return superFrogUser;
    }

    public void setSuperFrogUser(SuperFrogUser superFrogUser) {
        this.superFrogUser = superFrogUser;
    }

}

