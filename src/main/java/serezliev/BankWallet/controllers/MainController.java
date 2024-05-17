package serezliev.BankWallet.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import serezliev.BankWallet.model.BalanceHistoryEntity;
import serezliev.BankWallet.model.UserEntity;
import serezliev.BankWallet.repositories.BalanceHistoryRepository;
import serezliev.BankWallet.services.MapperForBalanceHistory;
import serezliev.BankWallet.services.UserService;
import serezliev.BankWallet.view.*;

import javax.transaction.Transactional;
import java.util.*;

@Controller
public class MainController {

    private final UserService userService;
    private final BalanceHistoryRepository balanceHistoryRepository;

    public MainController(UserService userService,
                          BalanceHistoryRepository balanceHistoryRepository) {
        this.userService = userService;
        this.balanceHistoryRepository = balanceHistoryRepository;
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


    @Transactional
    @GetMapping("/balanceHistory")
    public ResponseEntity<List<BalanceHistoryViewModel>> getBalanceHistory() {
        UserEntity currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            List<BalanceHistoryEntity> balanceHistory = userService.getBalanceHistoryForCurrentUser();
            List<BalanceHistoryViewModel> balanceHistoryDTOs = MapperForBalanceHistory.mapToDTOList(balanceHistory);
            return ResponseEntity.ok(balanceHistoryDTOs);
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/currencyRates")
    public Object getCurrencyRates() {
        String apiUrl = "https://api.exchangeratesapi.io/latest";
        RestTemplate restTemplate = new RestTemplate();
        Object response = restTemplate.getForObject(apiUrl, Object.class);
        return response;
    }
}
