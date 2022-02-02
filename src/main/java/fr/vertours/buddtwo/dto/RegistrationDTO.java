package fr.vertours.buddtwo.dto;

import fr.vertours.buddtwo.validation.PasswordConfirmation;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@PasswordConfirmation
public class RegistrationDTO {

    @NotBlank (message = "Please enter your first name.")
    @Size(max=50, message = "First name should be maximum 50 characters.")
    private String firstName;

    @NotBlank (message = "Please enter your last name.")
    @Size(max=50, message = "Last name should be maximum 50 characters")
    private String lastName;

    @NotBlank (message = "Please enter your email.")
    @Email (message = "Please verify validity of your email.")
    private String email;


    @NotBlank(message= "please enter password")
    private String password;

    @NotBlank(message= "please enter password confirmation")
    private String confirmation;


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

    public String getConfirmation() {
        return confirmation;
    }
    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }
}
