package serezliev.BankWallet.model;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class BalanceHistoryEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "balance_amount")
    private Double balanceAmount;

    @Column
    private Instant dateAndTime;

    public BalanceHistoryEntry() {
    }


    public Long getId() {
        return id;
    }

    public BalanceHistoryEntry setId(Long id) {
        this.id = id;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public BalanceHistoryEntry setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public Double getBalanceAmount() {
        return balanceAmount;
    }

    public BalanceHistoryEntry setBalanceAmount(Double balanceAmount) {
        this.balanceAmount = balanceAmount;
        return this;
    }

    public Instant getDateAndTime() {
        return dateAndTime;
    }

    public BalanceHistoryEntry setDateAndTime(Instant dateAndTime) {
        this.dateAndTime = dateAndTime;
        return this;
    }
}
