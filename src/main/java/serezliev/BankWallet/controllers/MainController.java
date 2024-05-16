package serezliev.BankWallet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import serezliev.BankWallet.model.UserEntity;
import serezliev.BankWallet.services.UserService;
import serezliev.BankWallet.view.*;

import javax.transaction.Transactional;
import java.util.*;

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

        if (user != null){
            List<String> actionHistory = user.getActionHistory();
            if (actionHistory != null) {
                model.addAttribute("historyList", actionHistory);
            }

            List<String> userContactList = user.getContactList();
            if (userContactList != null){
                model.addAttribute("contactList", userContactList);

            }
            model.addAttribute("user", user);
        }

        model.addAttribute("sendFundsModel", new SendFundsViewModel());
        model.addAttribute("addContact", new MyContactViewModel());
        model.addAttribute("deleteContact", new MyContactViewModel());
        model.addAttribute("depositModel", new DepositViewModel());
        model.addAttribute("withdrawalModel", new WithdrawalViewModel());
        model.addAttribute("loginModel", new LoginViewModel());
        model.addAttribute("registrationModel", new UserRegistrationViewModel());

        return "index";
    }
}
