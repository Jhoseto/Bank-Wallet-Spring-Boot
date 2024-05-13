package serezliev.BankWallet.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import serezliev.BankWallet.view.UserRegistrationViewModel;

@Controller
public class UserController {


    @PostMapping("/registration")
    public String addRegistration (UserRegistrationViewModel userRegistrationViewModel,
                                   RedirectAttributes redirectAttributes){

        return "index";
    }

    @PostMapping("/login")
    public String getLogin (UserRegistrationViewModel userRegistrationViewModel,
                                   RedirectAttributes redirectAttributes){

        return "index";
    }
}
