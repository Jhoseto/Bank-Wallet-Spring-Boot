package serezliev.BankWallet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import serezliev.BankWallet.model.UserEntity;
import serezliev.BankWallet.services.UserService;
import serezliev.BankWallet.view.LoginViewModel;
import serezliev.BankWallet.view.UserRegistrationViewModel;

@Controller
public class MainController {

    private final UserService userService;

    public MainController( UserService userService) {
        this.userService = userService;;
    }

    @GetMapping("/")
    public String getHomePage() {
        return "redirect:/index";
    }


    @GetMapping("/index")
    public String getIndex(Model model) {
        UserEntity user = userService.getCurrentUser();

        model.addAttribute("loginModel", new LoginViewModel());
        model.addAttribute("registrationModel", new UserRegistrationViewModel());
        model.addAttribute("user", user);
        return "index";
    }
}
