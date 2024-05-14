package serezliev.BankWallet.view;


import javax.validation.constraints.*;

public class UserRegistrationViewModel {

    @NotNull
    @Size(min = 5, max = 20, message = "Invalid username ! Please enter a username with minimum 5, and maximum 20 symbols...")
    private String username;

    @NotNull
    @Email(message = "Invalid Email format !")
    private String email;

    @NotNull
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).+$", message = "Password must contain at least 6 Latin characters, one capital letter and at least two numbers")
    private String regPassword;


    private String confirmPassword;

    @AssertTrue(message = "Password repeat error !")
    public boolean isPasswordsMatch() {
        if (!regPassword.equals(confirmPassword)) {
            confirmPassword = null;
            return false;
        }
        return true;
    }

    public String getUsername() {
        return username;
    }

    public UserRegistrationViewModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegistrationViewModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getRegPassword() {
        return regPassword;
    }

    public UserRegistrationViewModel setRegPassword(String regPassword) {
        this.regPassword = regPassword;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegistrationViewModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
