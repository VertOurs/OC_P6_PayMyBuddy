package fr.vertours.buddtwo.service;

import fr.vertours.buddtwo.security.MyUserDetails;
import fr.vertours.buddtwo.dto.AddBankDTO;
import fr.vertours.buddtwo.model.BankAccount;
import fr.vertours.buddtwo.repository.BankAccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankAccountServiceImpl implements HomeBankService {

    private final BankAccountRepository bankAccountRepository;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public void saveBankAccount(AddBankDTO dto, MyUserDetails mUD) {
        Optional<BankAccount> isAccountExist = bankAccountRepository.findByUser(mUD.getUser());
        BankAccount bankAccount;
        if(isAccountExist.isPresent()) {
            bankAccount = isAccountExist.get();
        } else {
            bankAccount = new BankAccount(mUD.getUser());
        }
        saveBankAccountInDB(bankAccount, dto);
    }

    private void saveBankAccountInDB(BankAccount bankAccount, AddBankDTO dto) {
        bankAccount.setIban(dto.getIban());
        bankAccount.setBic(dto.getBic());
        bankAccount.setCustomizeName(dto.getCustomizeName());
        bankAccountRepository.save(bankAccount);
    }


}
