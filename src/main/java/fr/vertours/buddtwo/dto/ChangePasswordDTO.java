package fr.vertours.buddtwo.dto;

import fr.vertours.buddtwo.validation.PassConf;

import javax.validation.constraints.NotBlank;

@PassConf
public class ChangePasswordDTO {

    @NotBlank(message = "please enter password")
    private String oldPassword;

    @NotBlank(message = "please enter password")
    private String newPassword;

    @NotBlank(message = "please enter password confirmation")
    private String newPasswordConfirmation;



    public String getOldPassword() {
        return oldPassword;
    }
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordConfirmation() {
        return newPasswordConfirmation;
    }
    public void setNewPasswordConfirmation(String newPasswordConfirmation) {
        this.newPasswordConfirmation = newPasswordConfirmation;
    }
}
