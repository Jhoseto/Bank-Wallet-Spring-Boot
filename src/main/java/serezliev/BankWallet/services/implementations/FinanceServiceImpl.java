package serezliev.BankWallet.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import serezliev.BankWallet.model.UserEntity;
import serezliev.BankWallet.repositories.UserRepository;
import serezliev.BankWallet.services.FinanceService;
import serezliev.BankWallet.services.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
public class FinanceServiceImpl implements FinanceService {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public FinanceServiceImpl(UserRepository userRepository,
                              UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }


    @Override
    @Transactional
    public void deposit(double amount) {
        UserEntity user = userService.getCurrentUser();

        // Update user balance
        double currentBalance = user.getBalance();
        user.setBalance(currentBalance + amount);

        // Add deposit action to history
        String depositAction = "DEPOSIT - " + amount + " on " + getCurrentDateTimeFormatted();
        user.addActionToHistory(depositAction);

        userRepository.save(user);
    }


    public static String getCurrentDateTimeFormatted() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
}
