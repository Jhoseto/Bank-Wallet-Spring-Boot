package serezliev.BankWallet.services.implementations;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import serezliev.BankWallet.model.BalanceHistoryEntity;
import serezliev.BankWallet.model.UserEntity;
import serezliev.BankWallet.repositories.UserRepository;
import serezliev.BankWallet.services.UserService;
import serezliev.BankWallet.view.UserRegistrationViewModel;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;

    public UserServiceImpl(PasswordEncoder passwordEncoder,
                           UserRepository userRepository,
                           UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void addNewUser(UserRegistrationViewModel userRegistrationViewModel) {
        UserEntity newUser = new UserEntity();

        newUser.setUsername(userRegistrationViewModel.getUsername())
                .setPassword(passwordEncoder.encode(userRegistrationViewModel.getRegPassword()))
                .setEmail(userRegistrationViewModel.getEmail())
                .setBalance(0);

        userRepository.save(newUser);
    }

    public Authentication authenticateUser(String email, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        if (userDetails != null && passwordEncoder.matches(password, userDetails.getPassword())) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return authentication;
        }
        return null;
    }

    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Optional<UserEntity> userOptional = userRepository.findByUsername(username);
            if (userOptional.isPresent()) {
                return userOptional.get();
            } else {
                Optional<UserEntity> userByEmailOptional = userRepository.findByEmail(username);
                return userByEmailOptional.orElse(null);
            }
        }
        return null;
    }


    @Override
    @Transactional
    public void deleteContact(String contact){
        UserEntity user = getCurrentUser();
        user.getContactList().remove(contact);
        userRepository.save(user);
    }


    @Override
    @Transactional
    public void addContact(String contact){
        UserEntity user = getCurrentUser();
        user.addNewContact(contact);
        userRepository.save(user);
    }


    @Override
    @Transactional
    public List<BalanceHistoryEntity> getBalanceHistoryForCurrentUser() {
        UserEntity currentUser = getCurrentUser();
        if (currentUser != null) {
            List<BalanceHistoryEntity> balanceHistory =currentUser.getBalanceHistory().stream().toList();

            return balanceHistory;
        }
        return null;
    }

}
