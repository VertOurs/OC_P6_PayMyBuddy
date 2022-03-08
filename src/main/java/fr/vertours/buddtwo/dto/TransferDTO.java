package fr.vertours.buddtwo.dto;

import org.springframework.data.domain.Page;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class TransferDTO {


    private List<FriendDTO> connections = new ArrayList<>();
    @NotBlank(message = "Please select your friend from the list or add a friend")
    private String email;
    @NotNull(message = "please enter the amount you wish to send to your friend")
    @Positive(message = "the number must be positive")
    private BigDecimal amount;
    @NotBlank(message = "Give your Buddy the reason for this transfer")
    private String description;

    private Page<TransactionDTO> transactionDTOS;


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

    public Page<TransactionDTO> getTransactionDTOS() {
        return transactionDTOS;
    }
    public void setTransactionDTOS(Page<TransactionDTO> transactionDTOS) {
        this.transactionDTOS = transactionDTOS;
    }
}
