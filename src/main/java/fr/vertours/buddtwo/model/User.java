package fr.vertours.buddtwo.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    private String email;

    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user__friend",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "friend_id")})
    private List<User> myFriendList = new ArrayList<>();

    @Column(nullable = false)
    private BigDecimal buddyBalance = BigDecimal.valueOf(0.0);

    @OneToOne(mappedBy = "user", optional = true, cascade = CascadeType.REMOVE)
    private BankAccount bankAccount;

    @ManyToMany()
    @JoinTable(name = "user__roles")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Role> roleList = new ArrayList<>();



    public User() {

    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public User(String firstName, String lastName, String email, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roleList.add(role);
    }

    public boolean hasRole(String roleName) {
        Iterator<Role> iterator = this.roleList.iterator();
        while (iterator.hasNext()) {
            Role role = iterator.next();
            if (role.getName().equals(roleName)) {
                return true;
            }
        }

        return false;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
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
    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", myFriendList=" + myFriendList +
                ", buddyBalance=" + buddyBalance +
                ", bankAccount=" + bankAccount +
                ", roleList=" + roleList +
                '}';
    }
}
