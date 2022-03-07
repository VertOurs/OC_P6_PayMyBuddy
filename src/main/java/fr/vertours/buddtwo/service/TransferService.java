package fr.vertours.buddtwo.service;

import fr.vertours.buddtwo.security.MyUserDetails;
import fr.vertours.buddtwo.dto.TransferDTO;
import org.springframework.data.domain.Pageable;

public interface TransferService {

    TransferDTO findTransferDTO(MyUserDetails myUD, Pageable pageable);


    void makeTransfer(TransferDTO dto, MyUserDetails myUD);
}
