package fr.vertours.buddtwo.service;

import fr.vertours.buddtwo.dto.ContactDTO;
import fr.vertours.buddtwo.security.MyUserDetails;

public interface ContactUserService {

    ContactDTO findContactDTO(MyUserDetails myUD);
    void addFriendByEmail(String username, String friendEmail);
    void delFriendByEmail(String username, String friendEmail);
}
