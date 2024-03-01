package com.example.webassignment.event_management.pojo;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordChangePojo {
    private String email;
    @NotEmpty(message = "Old Password can't be empty")
    private String oldPassword;
    @NotEmpty(message = "New Password can't be empty")
    private String newPassword;
    @NotEmpty(message = "Repeat Password can't be empty")
    private String repeatPassword;

}
