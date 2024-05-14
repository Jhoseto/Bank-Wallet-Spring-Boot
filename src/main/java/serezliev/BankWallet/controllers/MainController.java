package serezliev.BankWallet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import serezliev.BankWallet.model.UserEntity;
import serezliev.BankWallet.services.UserService;
import serezliev.BankWallet.view.DepositViewModel;
import serezliev.BankWallet.view.LoginViewModel;
import serezliev.BankWallet.view.UserRegistrationViewModel;

import javax.transaction.Transactional;
import java.util.ArrayList;

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
    @Transactional
    public String getIndex(Model model) {
        UserEntity user = userService.getCurrentUser();
        if (user != null && user.getActionHistory() == null) {
            user.addActionToHistory("");
        }

        model.addAttribute("depositModel", new DepositViewModel());
        model.addAttribute("loginModel", new LoginViewModel());
        model.addAttribute("registrationModel", new UserRegistrationViewModel());
        model.addAttribute("user", user);

        return "index";
    }
}
