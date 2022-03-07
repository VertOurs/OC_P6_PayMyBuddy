package fr.vertours.buddtwo.service;

import fr.vertours.buddtwo.security.MyUserDetails;
import fr.vertours.buddtwo.dto.BankingTransferDTO;

public interface HomeTransferService {
    void creditApplicationAccount(BankingTransferDTO dto, MyUserDetails myUD);

    void debitApplicationAccount(BankingTransferDTO dto, MyUserDetails myUD);
}
