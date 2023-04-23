package edu.tcu.cs.superfrogscheduler.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


//public record UserDto(
//
//        String id,
//
//        @NotEmpty(message = "firstName is required")
//        @Size(min = 2, max = 30, message = "firstName must be between 2 and 30 characters")
//        @Pattern(regexp = "[a-zA-Z]+", message = "firstName must contain only letters")
//        String firstName,
//
//        @NotEmpty(message = "lastName is required")
//        @Size(min = 2, max = 30, message = "lastName must be between 2 and 30 characters")
//        @Pattern(regexp = "[a-zA-Z]+", message = "lastName must contain only letters")
//        String lastName,
//
//        @NotEmpty(message = "phoneNumber is required")
//        @Pattern(regexp="^\\(\\d{3}\\)\\s\\d{3}-\\d{4}$", message="Phone number must be in the format (xxx) xxx-xxxx")
//        String phoneNumber,
//
//        @NotEmpty(message = "address is required")
//        @Pattern(regexp="^\\d+\\s+[\\w\\s]+(,\\s+(Suite|Ste|Rm|Room|Floor)\\s*\\d+)?,\\s*[\\w\\s]+,\\s*[A-Z]{2},\\s*\\d{5}(\\-\\d{4})?$",
//                message="Invalid US address format. Example of valid address: 123 Main St, Suite 100 (Optional), Fort Worth, TX, 76109")
//        String address,
//
//        @NotEmpty(message = "email is required")
//        @Email(message = "Invalid email address")
//        String email,
//
//        String roles
//        ) {
//
//}


public class UserDto {
    private String id;

    @NotEmpty(message = "firstName is required")
    @Size(min = 2, max = 30, message = "firstName must be between 2 and 30 characters")
    @Pattern(regexp = "[a-zA-Z]+", message = "firstName must contain only letters")
    private String firstName;

    @NotEmpty(message = "lastName is required")
    @Size(min = 2, max = 30, message = "lastName must be between 2 and 30 characters")
    @Pattern(regexp = "[a-zA-Z]+", message = "lastName must contain only letters")
    private String lastName;

    @NotEmpty(message = "phoneNumber is required")
    @Pattern(regexp = "^\\(\\d{3}\\)\\s\\d{3}-\\d{4}$", message = "Phone number must be in the format (xxx) xxx-xxxx")
    private String phoneNumber;

    @NotEmpty(message = "address is required")
    @Pattern(regexp = "^\\d+\\s+[\\w\\s]+(,\\s+(Suite|Ste|Rm|Room|Floor)\\s*\\d+)?,\\s*[\\w\\s]+,\\s*[A-Z]{2},\\s*\\d{5}(\\-\\d{4})?$",
            message = "Invalid US address format. Example of valid address: 123 Main St, Suite 100 (Optional), Fort Worth, TX, 76109")
    private String address;

    @NotEmpty(message = "email is required")
    @Email(message = "Invalid email address")
    private String email;

    private String roles;

    public UserDto() {

    }

    public UserDto(String id, String firstName, String lastName, String phoneNumber, String address, String email, String roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}