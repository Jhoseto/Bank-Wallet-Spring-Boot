package serezliev.BankWallet.view;

public class DepositViewModel {

    private double depositAmount;


    public DepositViewModel setDepositAmount(double depositAmount) {
        this.depositAmount = depositAmount;
        return this;
    }

    public double getDepositAmount() {
        return depositAmount;
    }

}
