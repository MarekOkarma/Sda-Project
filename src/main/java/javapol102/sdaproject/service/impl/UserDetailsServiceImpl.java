package javapol102.sdaproject.service.impl;

import javapol102.sdaproject.model.User;
import javapol102.sdaproject.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userFromDB = userRepository.findByUsername(username);
        if(userFromDB == null){
            throw new UsernameNotFoundException("Username " + username + " not found in database!");
        }
       return org.springframework.security.core.userdetails.User .builder()
                .username(userFromDB.getUsername())
                .password(userFromDB.getPassword())
                .roles(userFromDB.getRoles()
                        .stream()
                        .map(u ->u.getEmail())
                        .toArray(String[]::new))
                .build();
    }
}
