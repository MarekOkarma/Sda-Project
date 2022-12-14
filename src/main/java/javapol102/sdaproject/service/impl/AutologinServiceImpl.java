package javapol102.sdaproject.service.impl;

import javapol102.sdaproject.service.AutologinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class AutologinServiceImpl implements AutologinService {

    private final UserDetailsService userDetailsService;

    public AutologinServiceImpl(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Override
    public void autoLogin(String username) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        //zaloguj użytkownika
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        //jeśli udało się zalogować to ustaw tego użytkownika jako zalogowanego
        if (token.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(token);
            log.info("Successfully autologin user with username: " + username);
        }
    }
}
