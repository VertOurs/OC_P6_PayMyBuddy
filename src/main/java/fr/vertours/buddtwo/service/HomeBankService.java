package fr.vertours.buddtwo.service;

import fr.vertours.buddtwo.configuration.MyUserDetails;
import fr.vertours.buddtwo.dto.AddBankDTO;
import fr.vertours.buddtwo.model.BankAccount;


public interface HomeBankService {

    void saveBankAccount(AddBankDTO dto, MyUserDetails mUD);

}
