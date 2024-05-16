package serezliev.BankWallet.services;

import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import serezliev.BankWallet.model.BalanceHistoryEntity;
import serezliev.BankWallet.model.UserEntity;
import serezliev.BankWallet.view.UserRegistrationViewModel;

import java.util.Optional;

public interface UserService {


    void addNewUser(UserRegistrationViewModel userRegistrationViewModel);

    Authentication authenticateUser(String email, String password);

    UserEntity getCurrentUser();

    void deleteContact(String contact);

    void addContact(String contact);

    @Transactional(readOnly = true)
    Optional<BalanceHistoryEntity> getBalanceHistoryForCurrentUser();
}
