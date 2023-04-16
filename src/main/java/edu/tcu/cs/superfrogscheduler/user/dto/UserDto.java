package edu.tcu.cs.superfrogscheduler.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserDto(

        String id,

        @NotEmpty(message = "firstName is required")
        @Size(min = 2, max = 30, message = "firstName must be between 2 and 30 characters")
        @Pattern(regexp = "[a-zA-Z]+", message = "firstName must contain only letters")
        String firstName,

        @NotEmpty(message = "lastName is required")
        @Size(min = 2, max = 30, message = "lastName must be between 2 and 30 characters")
        @Pattern(regexp = "[a-zA-Z]+", message = "lastName must contain only letters")
        String lastName,

        @NotEmpty(message = "phoneNumber is required")
        @Pattern(regexp="^\\(\\d{3}\\)\\s\\d{3}-\\d{4}$", message="Phone number must be in the format (xxx) xxx-xxxx")
        String phoneNumber,

        @NotEmpty(message = "address is required")
        @Pattern(regexp="^\\d+\\s+[\\w\\s]+(,\\s+(Suite|Ste|Rm|Room|Floor)\\s*\\d+)?,\\s*[\\w\\s]+,\\s*[A-Z]{2},\\s*\\d{5}(\\-\\d{4})?$",
                message="Invalid US address format. Example of valid address: 123 Main St, Suite 100 (Optional), Fort Worth, TX, 76109")
        String address,

        @NotEmpty(message = "email is required")
        @Email(message = "Invalid email address")
        String email,

        String roles
        ) {

}
