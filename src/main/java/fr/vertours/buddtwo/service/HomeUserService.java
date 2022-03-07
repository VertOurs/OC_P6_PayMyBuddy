package fr.vertours.buddtwo.service;

import fr.vertours.buddtwo.security.MyUserDetails;
import fr.vertours.buddtwo.dto.HomeDTO;

public interface HomeUserService {

    HomeDTO findHomeDTOByMyUserDetails(MyUserDetails myUD);

}
