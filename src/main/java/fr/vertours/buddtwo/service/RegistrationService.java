package fr.vertours.buddtwo.service;

import fr.vertours.buddtwo.dto.RegistrationDTO;

public interface RegistrationService {

    void saveUserByRegistrationDTO(RegistrationDTO regDTO);
}
