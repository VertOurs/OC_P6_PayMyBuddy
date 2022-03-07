package fr.vertours.buddtwo.dto;

public class HomeDTO {

    private String firstName;
    private String lastName;
    private String balance;
    private String bankName;
    private boolean hasBankAccount;

    public HomeDTO() {
    }

    public HomeDTO(String firstName, String lastName, String balance, String bankName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
        this.bankName = bankName;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBalance() {
        return balance;
    }
    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBankName() {
        return bankName;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public boolean isHasBankAccount() {
        return bankName != null;
    }


}
