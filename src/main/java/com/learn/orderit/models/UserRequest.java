package com.learn.orderit.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest {

    @NotNull(message = "First name cannot be null")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    private String lastName;

    @Email(message = "User email should be valid")
    @NotNull(message = "User email cannot be null")
    private String emailId;

    @NotNull(message = "User phone number cannot be null")
    private String phoneNumber;

    @Past(message = "User birthDate cannot be in present/future")
    @NotNull(message = "User birthdate cannot be null")
    private Date birthDate;
}
