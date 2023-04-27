package edu.tcu.cs.superfrogscheduler.user.security;

import edu.tcu.cs.superfrogscheduler.user.entity.SuperFrogUser;
import jakarta.persistence.*;

import java.io.Serializable;


@Entity
public class UserSecurity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

//    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "userSecurity")
    @OneToOne
    private SuperFrogUser superFrogUser;

    private boolean enabled;

    private String roles;

    public UserSecurity() {
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

    public SuperFrogUser getUser() {
        return superFrogUser;
    }

    public void setUser(SuperFrogUser userDetail) {
        this.superFrogUser = userDetail;
    }

    public static UserSecurity createUserSecurity(SuperFrogUser superFrogUser) {
        UserSecurity userSecurity = new UserSecurity();
        userSecurity.setEmail(superFrogUser.getEmail());
        userSecurity.setUser(superFrogUser);
        userSecurity.setEnabled(true);

        superFrogUser.setUserSecurity(userSecurity);

        return userSecurity;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
