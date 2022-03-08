package fr.vertours.buddtwo.dto;


import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class BankingTransferDTO {

    @Positive(message = "the number must be positive")
    private BigDecimal amount;

    public BankingTransferDTO() {
    }


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
