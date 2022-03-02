package fr.vertours.buddtwo.service;

import fr.vertours.buddtwo.configuration.MyUserDetails;
import fr.vertours.buddtwo.dto.TransferDTO;

public interface TransferService {
    TransferDTO findTransferDTO(MyUserDetails myUD);

    void saveTransfer(TransferDTO dto, MyUserDetails myUD);

    void makeTransfer(TransferDTO dto, MyUserDetails myUD);
}
