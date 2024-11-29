package com.project.booking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {

    @NotBlank(message = "First name cannot be blank")
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank(message =  "Last name cannot be blank")
    @JsonProperty("last_name")
    private String lastName;

    @NotBlank(message =  "Email cannot be blank")
    private String email;

    @NotBlank(message =  "Password cannot be blank")
    @Size(min = 6, max = 20, message = "Password must between 6 and 20 characters")
    private String password;
}
