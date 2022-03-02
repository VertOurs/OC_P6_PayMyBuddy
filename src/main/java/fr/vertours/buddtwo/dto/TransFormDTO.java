package fr.vertours.buddtwo.dto;

import java.math.BigDecimal;

public class TransFormDTO {

//    private String friendDTO;
    private FriendDTO friendDTO;
    private BigDecimal amount;
    private String description;

    public FriendDTO getFriendDTO() {
        return friendDTO;
    }

    public void setFriendDTO(FriendDTO friendDTO) {
        this.friendDTO = friendDTO;
    }

//    public String getFriendDTO() {
//        return friendDTO;
//    }
//
//    public void setFriendDTO(String friendDTO) {
//        this.friendDTO = friendDTO;
//    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
