package serezliev.BankWallet.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public ApplicationSecurityConfiguration(UserDetailsService customUserDetailsService,
                                            PasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(
                        "/css/**",
                        "/js/**",
                        "/images/**").permitAll()
                .antMatchers(
                        "/",
                        "/index",
                        "/registration",
                        "/login")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/index")

                .and()
                .rememberMe() // Remember me functionality
                .key(rememberMeKey()) // Unique key for remember me

                .and()
                .logout()
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/index")

                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "remember-me") // Delete cookies for session and remember me
                .permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                .exceptionHandling()

                .authenticationEntryPoint((request, response, authException) -> {
                    String errorMessage = "Please Sign In, or Sign Up to continue !";
                    String encodedMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);
                    response.sendRedirect("/index?error=" + encodedMessage);
                })
                .and()
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    public String rememberMeKey() {
        return generateKey();
    }

    public static String generateKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] keyBytes = new byte[32];
        secureRandom.nextBytes(keyBytes);
        return Base64.getEncoder().encodeToString(keyBytes);
    }

    @Bean
    public TokenBasedRememberMeServices tokenBasedRememberMeServices() {
        return new TokenBasedRememberMeServices(rememberMeKey(), customUserDetailsService);
    }

}
