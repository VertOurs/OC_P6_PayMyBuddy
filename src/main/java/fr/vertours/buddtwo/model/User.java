package fr.vertours.buddtwo.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private String password;

    @ManyToMany
    @JoinTable(name = "user_friend",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "friend_id")})
    private List<User> myFriendList = new ArrayList<>();

    @Column
    private BigDecimal buddyBalance = BigDecimal.valueOf(0.0);

    @OneToOne(mappedBy = "user", optional = true, cascade = CascadeType.REMOVE)
    private BankAccount bankAccount;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roleList = new ArrayList<>();

    public User() {
        Role role = new Role("user");
        roleList.add(role);
    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;

    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public List<User> getMyFriendList() {
        return myFriendList;
    }
    public void setMyFriendList(List<User> myFriendList) {
        this.myFriendList = myFriendList;
    }

    public BigDecimal getBuddyBalance() {
        return buddyBalance;
    }
    public void setBuddyBalance(BigDecimal buddyBalance) {
        this.buddyBalance = buddyBalance;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }
    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public List<Role> getRoleList() {
        return roleList;
    }
    public void setRoleList(List<Role> role) {
        this.roleList = role;
    }
}
