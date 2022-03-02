package fr.vertours.buddtwo.dto;

import fr.vertours.buddtwo.model.User;

public class FriendDTO {

    private String firstName;
    private String lastName;
    private String email;

    public FriendDTO() {
    }

    public FriendDTO(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public static FriendDTO getFriendDTOByUser(User user) {
        return new FriendDTO(user.getFirstName(), user.getLastName(), user.getEmail());
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

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
