package fr.vertours.buddtwo.dto;


import javax.validation.constraints.NotBlank;

public class AddBankDTO {

    @NotBlank(message = "please enter your IBAN ")
    private String iban;

    @NotBlank(message = "please enter your BIC")
    private String bic;

    @NotBlank(message = "please enter a name for your Bank account")
    private String customizeName;


    public String getIban() {
        return iban;
    }
    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBic() {
        return bic;
    }
    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getCustomizeName() {
        return customizeName;
    }
    public void setCustomizeName(String customizeName) {
        this.customizeName = customizeName;
    }
}
