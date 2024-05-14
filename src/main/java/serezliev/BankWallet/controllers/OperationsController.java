package serezliev.BankWallet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import serezliev.BankWallet.services.FinanceService;
import serezliev.BankWallet.view.DepositViewModel;

import javax.validation.Valid;

@Controller
public class OperationsController {

    private final FinanceService financeService;

    public OperationsController(FinanceService financeService) {
        this.financeService = financeService;
    }

    @PostMapping("/deposit")
    public String deposit (@Valid @ModelAttribute("depositModel") DepositViewModel depositViewModel){
        financeService.deposit(depositViewModel.getAmount());

        return "redirect:/index";
    }

}
