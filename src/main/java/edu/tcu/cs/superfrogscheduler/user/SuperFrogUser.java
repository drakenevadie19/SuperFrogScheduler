package edu.tcu.cs.superfrogscheduler.user;

import edu.tcu.cs.superfrogscheduler.user.user_details.UserDetails;
import jakarta.persistence.*;

import java.io.Serializable;


@Entity
public class SuperFrogUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String email;

    private String password;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "user")
    private UserDetails userDetails;

    private boolean enabled;

    public SuperFrogUser() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetail) {
        this.userDetails = userDetail;
    }
}
