package serezliev.BankWallet.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import serezliev.BankWallet.model.UserEntity;
import serezliev.BankWallet.repositories.UserRepository;
import serezliev.BankWallet.services.NotificationService;
import serezliev.BankWallet.services.UserService;
import serezliev.BankWallet.view.NotificationViewModel;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public NotificationServiceImpl(UserService userService,
                                   UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public List<NotificationViewModel> getNotifications() {
        List<String> user = userService.getCurrentUser().getActionHistory();
        if (userService.getCurrentUser().getActionHistory() != null){
            List<NotificationViewModel> notifications = new ArrayList<>();
            for (String currentString : user) {
                NotificationViewModel currentNotification = new NotificationViewModel();
                String[] message = currentString.split(" ");

                if (message[0].equals("RECEIVE")){
                    currentNotification.setHeader( "Receive Money");
                    currentNotification.setMessage(currentString);
                    notifications.add(0, currentNotification);
                }
            }
            return notifications;
        }
        return  null;
    }

    @Override
    public void markNotificationAsRead() {
        UserEntity user = userService.getCurrentUser();
        user.setNotificationsCount(0);
        userRepository.save(user);

    }
}
