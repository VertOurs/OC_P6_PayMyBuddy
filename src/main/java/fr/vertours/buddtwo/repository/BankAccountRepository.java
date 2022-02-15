package fr.vertours.buddtwo.repository;

import fr.vertours.buddtwo.model.BankAccount;
import fr.vertours.buddtwo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    Optional<BankAccount> findByUser(User user);
}
