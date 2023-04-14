package edu.tcu.cs.superfrogscheduler.user.user_details;

import edu.tcu.cs.superfrogscheduler.user.SuperFrogUser;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class UserDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    private SuperFrogUser user;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String address;

    @Column(nullable = false)
    private String email;

    private Integer age;

    private Boolean isInternationalStudent;

    public UserDetails() {
    }

    public String getId() {
        return id;
    }

    public void setId(String userDetailsId) {
        this.id = userDetailsId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public SuperFrogUser getUser() {
        return user;
    }

    public void setUser(SuperFrogUser user) {
        this.user = user;
    }

    public Boolean getInternationalStudent() {
        return this.isInternationalStudent;
    }

    public void setInternationalStudent(Boolean internationalStudent) {
        this.isInternationalStudent = internationalStudent;
    }
}
