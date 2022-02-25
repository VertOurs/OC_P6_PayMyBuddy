package fr.vertours.buddtwo.dto;

import javax.validation.constraints.NotBlank;

public class AddFriendDTO {

    @NotBlank(message = "please enter the email of your buddy")
    private String friendEmail;


    public String getFriendEmail() {
        return friendEmail;
    }
    public void setFriendEmail(String friendEmail) {
        this.friendEmail = friendEmail;
    }

    @Override
    public String toString() {
        return "AddFriendDTO{" +
                "friendEmail='" + friendEmail + '\'' +
                '}';
    }
}
