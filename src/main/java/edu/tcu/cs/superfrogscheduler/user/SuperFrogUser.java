package edu.tcu.cs.superfrogscheduler.user;

import edu.tcu.cs.superfrogscheduler.user.security.UserSecurity;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class SuperFrogUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "superFrogUser")
    private UserSecurity userSecurity;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String address;

    @Column(nullable = false, unique = true)
    private String email;

    private Boolean isInternationalStudent;

    public SuperFrogUser() {
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

    public UserSecurity getUserSecurity() {
        return userSecurity;
    }

    public void setUserSecurity(UserSecurity user) {
        this.userSecurity = user;
    }

    public Boolean getInternationalStudent() {
        return this.isInternationalStudent;
    }

    public void setInternationalStudent(Boolean internationalStudent) {
        this.isInternationalStudent = internationalStudent;
    }
}
