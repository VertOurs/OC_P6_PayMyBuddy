package fr.vertours.buddtwo.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransferDTO {

    private List<FriendDTO> connections = new ArrayList<>();
    private String email;
    private BigDecimal amount;
    private String description;

    private List<TransactionDTO> transactionDTOS = new ArrayList<>();

    public List<FriendDTO> getConnections() {
        return connections;
    }

    public void setConnections(List<FriendDTO> connections) {
        this.connections = connections;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TransactionDTO> getTransactionDTOS() {
        return transactionDTOS;
    }

    public void setTransactionDTOS(List<TransactionDTO> transactionDTOS) {
        this.transactionDTOS = transactionDTOS;
    }
}