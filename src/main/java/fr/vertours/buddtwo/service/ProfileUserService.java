package fr.vertours.buddtwo.service;

import fr.vertours.buddtwo.configuration.MyUserDetails;
import fr.vertours.buddtwo.dto.ChangePasswordDTO;
import fr.vertours.buddtwo.dto.ProfileDTO;

public interface ProfileUserService {

    ProfileDTO findProfileDTO(MyUserDetails myUD);
    void updatePassword(ChangePasswordDTO dto, MyUserDetails myUserDetails);
}
