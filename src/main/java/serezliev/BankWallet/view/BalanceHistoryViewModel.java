package serezliev.BankWallet.view;

import serezliev.BankWallet.model.BalanceHistoryEntity;

public class BalanceHistoryViewModel {
    private Long id;
    private String dateAndTime;
    private double balanceAmount;

    public BalanceHistoryViewModel(Long id, String dateAndTime, double balanceAmount) {
        this.id = id;
        this.dateAndTime = dateAndTime;
        this.balanceAmount = balanceAmount;
    }
    public BalanceHistoryViewModel() {

    }

    public Long getId() {
        return id;
    }

    public BalanceHistoryViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public BalanceHistoryViewModel setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
        return this;
    }

    public double getBalanceAmount() {
        return balanceAmount;
    }

    public BalanceHistoryViewModel setBalanceAmount(double balanceAmount) {
        this.balanceAmount = balanceAmount;
        return this;
    }
}
