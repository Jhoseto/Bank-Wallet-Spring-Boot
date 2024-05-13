package serezliev.BankWallet.view;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginViewModel {

    @NotNull (message = "Please input a valid email address !")
    @Email(message = "Invalid Email format !")
    private String email;

    @NotNull (message = "Please enter your password !")
    @Size(min = 6, message = "Invalid password format !")
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
