package serezliev.BankWallet.view;

public class DepositViewModel {

    private double amount;

    public double getAmount() {
        return amount;
    }

    public DepositViewModel setAmount(double amount) {
        this.amount = amount;
        return this;
    }
}
