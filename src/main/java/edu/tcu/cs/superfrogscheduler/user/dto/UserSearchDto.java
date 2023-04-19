package edu.tcu.cs.superfrogscheduler.user.dto;

import edu.tcu.cs.superfrogscheduler.system.util.BaseSearchDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class UserSearchDto extends BaseSearchDto {

    private String firstName;

    private String lastName;

    @Pattern(regexp="^\\(\\d{3}\\)\\s\\d{3}-\\d{4}$",
            message="Phone number must be in the format (xxx) xxx-xxxx")
    private String phoneNumber;

    @Email(message = "Invalid email address")
    private String email;


    @NotNull
    @Pattern(regexp = "firstName|lastName|phoneNumber|email",
            message = "sortBy can only be firstName|lastName|phoneNumber|email")
    private String sortBy = "lastName";

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getSortBy() {
        return sortBy;
    }

    @Override
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
