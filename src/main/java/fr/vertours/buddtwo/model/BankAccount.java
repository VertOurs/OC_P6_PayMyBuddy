package fr.vertours.buddtwo.model;

import javax.persistence.*;

@Entity
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_account_id")
    private Long id;

    @OneToOne(optional = false)
    private User user;

    private String iban;

    private String bic;

    private String customizeName;


    public BankAccount() {
    }

    public BankAccount(User user, String iban, String bic, String customizeName) {
        this.user = user;
        this.iban = iban;
        this.bic = bic;
        this.customizeName = customizeName;
    }

    public BankAccount(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

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
