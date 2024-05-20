package serezliev.BankWallet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import serezliev.BankWallet.model.UserEntity;
import serezliev.BankWallet.services.NotificationService;
import serezliev.BankWallet.services.UserService;

@Controller
public class NotificationController {
    private final NotificationService notificationService;
    private final UserService userService;

    public NotificationController(NotificationService notificationService,
                                  UserService userService) {
        this.notificationService = notificationService;
        this.userService = userService;
    }


    @GetMapping("/notifications/markAsRead")
    public String markAsRead() {
        UserEntity user = userService.getCurrentUser();
        if (user != null && user.getNotificationsCount() != 0) {
            notificationService.markNotificationAsRead();
        }
        return "redirect:/index";
    }

}
