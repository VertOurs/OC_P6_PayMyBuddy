package fr.vertours.buddtwo.service;

import fr.vertours.buddtwo.configuration.MyUserDetails;
import fr.vertours.buddtwo.dto.TransferDTO;
import org.springframework.data.domain.Pageable;

public interface TransferService {

    TransferDTO findTransferDTO(MyUserDetails myUD, Pageable pageable);

    void saveTransfer(TransferDTO dto, MyUserDetails myUD);

    void makeTransfer(TransferDTO dto, MyUserDetails myUD);
}
