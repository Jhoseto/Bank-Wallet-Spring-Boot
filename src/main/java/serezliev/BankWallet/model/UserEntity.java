package serezliev.BankWallet.model;

import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
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

    @ElementCollection
    @CollectionTable(name = "action_history", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "action")
    private List<String> actionHistory;

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

    public List<String> getActionHistory() {
        Hibernate.initialize(actionHistory);
        return actionHistory;
    }

    @Transactional
    public void addActionToHistory(String action) {
        if (actionHistory == null) {
            actionHistory = new ArrayList<>();
        }
        actionHistory.add(action);
    }
}
