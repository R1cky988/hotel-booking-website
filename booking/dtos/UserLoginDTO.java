package com.project.booking.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {

    @NotBlank(message =  "Email cannot be blank")
    private String email;

    @NotBlank(message =  "Password cannot be blank")
    @Size(min = 6, max = 20, message = "Password must between 6 and 20 characters")
    private String password;
}
