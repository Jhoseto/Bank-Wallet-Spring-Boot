package serezliev.BankWallet.view;


import javax.validation.constraints.*;

public class UserRegistrationViewModel {

    @NotNull
    @Size(min = 5, max = 20, message = "Невалидно подребителско име! Въведете име с минимум 5 символа.")
    private String username;

    @NotNull
    @Email(message = "Невалиден формат за Емейл!")
    private String email;

    @NotNull
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).+$", message = "Паролата трябва да съдържа поне 6 символа на латиница, една голяма буква и поне две цифри")
    private String regPassword;


    private String confirmPassword;

    @AssertTrue(message = "Грешка в повторението на паролата")
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
