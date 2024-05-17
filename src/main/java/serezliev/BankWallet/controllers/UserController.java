package serezliev.BankWallet.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import serezliev.BankWallet.model.UserEntity;
import serezliev.BankWallet.repositories.UserRepository;
import serezliev.BankWallet.services.UserService;
import serezliev.BankWallet.view.LoginViewModel;
import serezliev.BankWallet.view.MyContactViewModel;
import serezliev.BankWallet.view.UserRegistrationViewModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final TokenBasedRememberMeServices rememberMeServices;

    public UserController(UserService userService,
                          UserRepository userRepository,
                          TokenBasedRememberMeServices rememberMeServices) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.rememberMeServices = rememberMeServices;
    }


    @PostMapping("/registration")
    public String addRegistration (@Valid @ModelAttribute ("registrationModel") UserRegistrationViewModel userRegistrationViewModel,
                                   Model model,
                                   RedirectAttributes redirectAttributes,
                                   BindingResult bindingResult){

        StringBuilder errorMessages = new StringBuilder();
        if (bindingResult.hasErrors()) {

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessages.append(error.getDefaultMessage()).append("\n");
            }

            if (!userRegistrationViewModel.isPasswordsMatch()) {
                errorMessages.append("The passwords did not matched !. ");
            }
            redirectAttributes.addFlashAttribute("error", errorMessages.toString());
            return "redirect:/index";

        } else {
            Optional<UserEntity> existingUserByEmail = userRepository.findByEmail(userRegistrationViewModel.getEmail());
            Optional<UserEntity> existingUserByUsername = userRepository.findByUsername(userRegistrationViewModel.getUsername());

            if (existingUserByEmail.isPresent()) {
                errorMessages.append("User with this Email address already exist ! Choice another one...");
                redirectAttributes.addFlashAttribute("error", errorMessages.toString());

                return "redirect:/index";
            }
            if (existingUserByUsername.isPresent()) {
                errorMessages.append("User with this username already exist ! Choice another one...");
                redirectAttributes.addFlashAttribute("error", errorMessages.toString());

                return "redirect:/index";
            }

            userService.addNewUser(userRegistrationViewModel);

            redirectAttributes.addFlashAttribute("successMessage",
                    "Registration Success !\nPlease login in to your account");

        }
        return "redirect:/index";
    }


    @PostMapping("/login")
    public String getLogin (LoginViewModel loginViewModel,
                            RedirectAttributes redirectAttributes,
                            BindingResult bindingResult,
                            HttpServletResponse response,
                            HttpServletRequest request){
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessages.append(error.getDefaultMessage()).append("\n");
            }
            redirectAttributes.addFlashAttribute("error", errorMessages.toString());

            return "redirect:/index";

        } else {
            Optional<UserEntity> userOptional = userRepository.findByEmail(loginViewModel.getEmail());

            if (userOptional.isPresent()) {
                UserEntity user = userOptional.get();

                    Authentication authentication = userService.authenticateUser(loginViewModel.getEmail(), loginViewModel.getPassword());

                    if (authentication != null) {
                        // User is autenticate

                        rememberMeServices.loginSuccess(request, response, authentication);
                    } else {
                        // Wrong password
                        redirectAttributes.addFlashAttribute("error", "Wrong password !");
                        return "redirect:/index";
                    }
                    return "redirect:index";
            } else {
                // User not found in DB
                redirectAttributes.addFlashAttribute("error", "User with email -> "+ loginViewModel.getEmail() +" is not registered !");
                return "redirect:/index";
            }
        }
    }

    @PostMapping("/deleteContact")
    public String deleteContact (@RequestParam("selectedContact") String selectedContact,
                                 RedirectAttributes redirectAttributes){

        if (userService.getCurrentUser().getContactList().contains(selectedContact)){
            userService.deleteContact(selectedContact);
            redirectAttributes.addFlashAttribute("successMessage"," Successfully deleted "+selectedContact+" from your Contact List !");
        } else {
            redirectAttributes.addFlashAttribute("error"," Oops! Something went wrong while submitting the form.");
        }

        return "redirect:/index";
    }

    @PostMapping("/addContact")
    public String addContact(@Valid @ModelAttribute("addContact") MyContactViewModel contact,
                             RedirectAttributes redirectAttributes) {
        Optional<UserEntity> user = userRepository.findByEmail(contact.getContactEmail());

        if (user.isPresent()){
            if (!userService.getCurrentUser().getContactList().contains(contact.getContactEmail())){
                if (userService.getCurrentUser().getEmail().equals(contact.getContactEmail())){
                    redirectAttributes.addFlashAttribute("error", "Error for "+contact.getContactEmail()+" You cannot add your own email !");
                    return "redirect:/index";
                }

                userService.addContact(contact.getContactEmail());
                redirectAttributes.addFlashAttribute("successMessage", contact.getContactEmail()+" Successfully added to your Contact List !");
            } else {
                redirectAttributes.addFlashAttribute("error", contact.getContactEmail()+" is already added in your Contact List !");
                return "redirect:/index";
            }


        } else {
            redirectAttributes.addFlashAttribute("error", "The contact you tried to add is not found in our system. " +
                    "Please add a contact that is registered in our database.");
        }
        return "redirect:/index";
    }
}
