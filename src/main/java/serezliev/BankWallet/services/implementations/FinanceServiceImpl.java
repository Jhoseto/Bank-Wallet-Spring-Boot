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
import java.util.Optional;


@Service
public class FinanceServiceImpl implements FinanceService {

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
    public void deposit(double depositAmount) {
        UserEntity user = userService.getCurrentUser();

        // Update user balance
        double currentBalance = user.getBalance();
        user.setBalance(currentBalance + depositAmount);

        // Add deposit action to history
        String depositAction = "DEPOSIT - " + depositAmount + "  $  on   " + getCurrentDateTimeFormatted();
        user.addActionToHistory(depositAction);
        user.addBalanceHistoryEntry(user.getBalance(),getCurrentDateTimeFormatted());

        userRepository.save(user);
    }


    @Override
    @Transactional
    public void withdrawal(double withdrawalAmount) {
        UserEntity user = userService.getCurrentUser();

        // Update user balance
        double currentBalance = user.getBalance();
        user.setBalance(currentBalance - withdrawalAmount);

        // Add withdrawal action to history
        String withdrawalAction = "WITHDRAWAL - " + withdrawalAmount + "  $  on   " + getCurrentDateTimeFormatted();
        user.addActionToHistory(withdrawalAction);

        // Add balance history entry
        user.addBalanceHistoryEntry(user.getBalance(), getCurrentDateTimeFormatted());


        userRepository.save(user);
    }

    @Override
    @Transactional
    public void sendFunds(String contact, double amount) {
        UserEntity sender = userService.getCurrentUser();
        Optional<UserEntity> findReceiver = userRepository.findByEmail(contact);

        if (findReceiver.isPresent()){
            UserEntity receiver = findReceiver.get();

            String sendAction = "SEND - " + amount + "  $  to --->  "+receiver
                    .getEmail()+"("+receiver.getUsername()+")"+"  on   " + getCurrentDateTimeFormatted();
            sender.addActionToHistory(sendAction);
            sender.addBalanceHistoryEntry(sender.getBalance(), getCurrentDateTimeFormatted());

            String receiveAction = "RECEIVE - " + amount + "  $  from <--- "+sender
                    .getEmail()+"("+sender.getUsername()+")"+"  on   " + getCurrentDateTimeFormatted();
            receiver.addActionToHistory(receiveAction);
            receiver.addBalanceHistoryEntry(receiver.getBalance(), getCurrentDateTimeFormatted());

            userRepository.save(sender);
            userRepository.save(receiver);

        } else {
            throw new RuntimeException("Error from sendFunds operation !");
        }
    }

    public static String getCurrentDateTimeFormatted() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

}
