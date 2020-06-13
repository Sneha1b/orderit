package com.learn.orderit.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest {

    @NotNull(message = "First name cannot be null")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    private String lastName;

    @NotNull(message = "User email cannot be null")
    private String emailId;

    @NotNull(message = "User phone number cannot be null")
    private String phoneNumber;

    @NotNull(message = "User birthdate cannot be null")
    private long birthDate;

    public boolean validate(){
        if(firstName ==  null || lastName == null || emailId == null || phoneNumber == null)
            return false;
        return true;
    }
}
