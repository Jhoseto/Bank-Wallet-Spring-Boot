package serezliev.BankWallet.view;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginViewModel {

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 6)
    private String password;


    public String getEmail() {
        return email;
    }

    public LoginViewModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginViewModel setPassword(String password) {
        this.password = password;
        return this;
    }
}
