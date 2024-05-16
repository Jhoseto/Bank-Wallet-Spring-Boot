package serezliev.BankWallet.view;

public class WithdrawalViewModel {

    private double withdrawalAmount;


    public double getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public WithdrawalViewModel setWithdrawalAmount(double withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
        return this;
    }
}