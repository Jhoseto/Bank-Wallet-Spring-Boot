package serezliev.BankWallet.services;

import org.springframework.security.core.Authentication;
import serezliev.BankWallet.view.UserRegistrationViewModel;

public interface UserService {


    void addNewUser(UserRegistrationViewModel userRegistrationViewModel);

    Authentication authenticateUser(String email, String password);
}
