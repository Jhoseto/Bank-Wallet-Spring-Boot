package serezliev.BankWallet.model;

import javax.persistence.*;

@Entity
public class AccountHistoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_account_id", nullable = false)
    private UserEntity userAccount;

    @Column(nullable = false)
    private String action;

    @Column(nullable = false)
    private double money;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserEntity userAccount) {
        this.userAccount = userAccount;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
