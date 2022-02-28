package fr.vertours.buddtwo.dto;


import java.math.BigDecimal;

public class BankingTransferDTO {


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
