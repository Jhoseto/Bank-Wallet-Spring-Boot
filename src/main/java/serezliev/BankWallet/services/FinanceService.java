package serezliev.BankWallet.services;

public interface FinanceService {

    void deposit(double amount);

    void withdrawal(double amount);

    void sendFunds(String contact, double amount);
}
