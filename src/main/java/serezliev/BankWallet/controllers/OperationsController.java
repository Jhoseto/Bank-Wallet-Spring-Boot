package serezliev.BankWallet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import serezliev.BankWallet.services.FinanceService;
import serezliev.BankWallet.services.UserService;
import serezliev.BankWallet.view.DepositViewModel;
import serezliev.BankWallet.view.WithdrawalViewModel;

import javax.validation.Valid;

@Controller
public class OperationsController {

    private final FinanceService financeService;
    private final UserService userService;

    public OperationsController(FinanceService financeService,
                                UserService userService) {
        this.financeService = financeService;
        this.userService = userService;
    }

    @PostMapping("/deposit")
    public String deposit (@Valid @ModelAttribute("depositModel") DepositViewModel depositViewModel){
        financeService.deposit(depositViewModel.getDepositAmount());

        return "redirect:/index";
    }

    @PostMapping("/withdrawal")
    public String withdrawal (@Valid @ModelAttribute("withdrawalModel") WithdrawalViewModel withdrawalViewModel,
                              RedirectAttributes redirectAttributes){

        if (withdrawalViewModel.getWithdrawalAmount() <= userService.getCurrentUser().getBalance()){
            financeService.withdrawal(withdrawalViewModel.getWithdrawalAmount());
        } else {
            redirectAttributes.addFlashAttribute("error", "Not enough funds for withdrawal operation !");
        }

        return "redirect:/index";
    }

}
