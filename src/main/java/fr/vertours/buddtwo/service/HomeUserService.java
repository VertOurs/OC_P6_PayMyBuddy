package fr.vertours.buddtwo.service;

import fr.vertours.buddtwo.configuration.MyUserDetails;
import fr.vertours.buddtwo.dto.HomeDTO;

public interface HomeUserService {

    HomeDTO findHomeDTOByMyUserDetails(MyUserDetails myUD);

}
