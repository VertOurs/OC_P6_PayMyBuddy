package fr.vertours.buddtwo.dto;

import java.util.ArrayList;
import java.util.List;

public class ContactDTO {

    private List<FriendDTO> friendDTOS;

    public ContactDTO() {
        friendDTOS = new ArrayList<>();
    }

    public List<FriendDTO> getFriendDTOS() {
        return friendDTOS;
    }

    public void setFriendDTOS(List<FriendDTO> friendDTOS) {
        this.friendDTOS = friendDTOS;
    }
}
