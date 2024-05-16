package serezliev.BankWallet.model;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
public class BalanceHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "balance_amount")
    private Double balanceAmount;

    @Column
    private LocalDateTime dateAndTime;

    @Column
    private String email;


    // Гетъри и сетъри
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(Double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public LocalDateTime  getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(LocalDateTime  dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getEmail() {
        return email;
    }

    public BalanceHistoryEntity setEmail(String email) {
        this.email = email;
        return this;
    }
}
