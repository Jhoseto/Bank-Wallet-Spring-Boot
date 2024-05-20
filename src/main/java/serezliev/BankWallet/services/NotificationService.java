package serezliev.BankWallet.services;

import serezliev.BankWallet.view.NotificationViewModel;

import java.util.List;

public interface NotificationService {

    List<NotificationViewModel> getNotifications();

    void markNotificationAsRead();
}
