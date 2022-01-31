package fr.vertours.buddtwo.repository;

import fr.vertours.buddtwo.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
