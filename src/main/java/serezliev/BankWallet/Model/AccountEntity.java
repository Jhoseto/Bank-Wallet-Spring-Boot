package serezliev.BankWallet.Model;

import jakarta.persistence.*;
import serezliev.BankWallet.view.AccountHistoryModel;

import java.util.List;

@Entity
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String ownerEmail;

    @Column
    private double balance;

    // One account can have many history records
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<AccountHistoryModel> accountHistory;

    // (One user can have only one account)
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private UserEntity user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<AccountHistoryModel> getAccountHistory() {
        return accountHistory;
    }

    public void setAccountHistory(List<AccountHistoryModel> accountHistory) {
        this.accountHistory = accountHistory;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
