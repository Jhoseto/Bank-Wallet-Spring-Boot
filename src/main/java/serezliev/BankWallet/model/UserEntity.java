package serezliev.BankWallet.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String username;

    @Column(nullable = false)
    private String email;

    @Column
    private String password;

    @Column
    private double balance;

    // One user can have many history records
    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL)
    private List<AccountHistoryModel> accountHistory;


    public Long getId() {
        return id;
    }

    public UserEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public double getBalance() {
        return balance;
    }

    public UserEntity setBalance(double balance) {
        this.balance = balance;
        return this;
    }

    public List<AccountHistoryModel> getAccountHistory() {
        return accountHistory;
    }

    public UserEntity setAccountHistory(List<AccountHistoryModel> accountHistory) {
        this.accountHistory = accountHistory;
        return this;
    }
}
