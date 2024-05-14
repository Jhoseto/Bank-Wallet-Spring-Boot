package serezliev.BankWallet.services.implementations;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import serezliev.BankWallet.model.UserEntity;
import serezliev.BankWallet.repositories.UserRepository;

import java.util.Collections;

/**
 * Implementation of {@link UserDetailsService} responsible for loading user details by email.
 */
@Service
public class CustomUserDetailsImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Constructs a new CustomUserDetailsImpl instance with the UserRepository dependency.
     *
     * @param userRepository the repository for accessing user data
     */
    public CustomUserDetailsImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * Loads a user's details by email address from the database.
     *
     * @param email the email address of the user to load
     * @return UserDetails representing the loaded user details
     * @throws UsernameNotFoundException if the user with the specified email address is not found
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with this email address not found: " + email));
        return mapToUserDetails(userEntity);
    }

    /**
     * Maps a UserEntity to UserDetails containing user details and authorities.
     *
     * @param userEntity the user entity to map to UserDetails
     * @return UserDetails representing the mapped user details
     * @throws IllegalStateException if the user does not have any roles assigned
     */
    private static UserDetails mapToUserDetails(UserEntity userEntity) {

        return new User(userEntity.getEmail(), userEntity.getPassword(), Collections.singleton(new SimpleGrantedAuthority("User")));
    }

}
