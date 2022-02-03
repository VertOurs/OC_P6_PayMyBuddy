package fr.vertours.buddtwo.service;

import fr.vertours.buddtwo.dto.AddBankDTO;
import fr.vertours.buddtwo.model.BankAccount;
import fr.vertours.buddtwo.model.User;
import fr.vertours.buddtwo.repository.BankAccountRepository;
import org.springframework.stereotype.Service;

@Service
public class BankAccountService {

    private BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public BankAccount saveBankAccount(User user, AddBankDTO dto) {
        BankAccount bankA = new BankAccount(user, dto.getIban(),
                dto.getBic(), dto.getCustomizeName());
        return bankAccountRepository.save(bankA);
    }
}
