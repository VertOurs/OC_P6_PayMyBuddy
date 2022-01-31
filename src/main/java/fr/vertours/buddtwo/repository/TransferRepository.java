package fr.vertours.buddtwo.repository;

import fr.vertours.buddtwo.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
