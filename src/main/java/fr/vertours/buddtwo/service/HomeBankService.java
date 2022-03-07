package fr.vertours.buddtwo.service;

import fr.vertours.buddtwo.security.MyUserDetails;
import fr.vertours.buddtwo.dto.AddBankDTO;


public interface HomeBankService {

    void saveBankAccount(AddBankDTO dto, MyUserDetails mUD);

}
