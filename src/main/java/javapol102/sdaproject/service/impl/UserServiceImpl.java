package javapol102.sdaproject.service.impl;

import javapol102.sdaproject.dto.PasswordDto;
import javapol102.sdaproject.dto.UserDto;
import javapol102.sdaproject.exception.EmptyUsernameException;
import javapol102.sdaproject.exception.InvalidPasswordException;
import javapol102.sdaproject.exception.WrongPasswordException;
import javapol102.sdaproject.model.Role;
import javapol102.sdaproject.model.User;
import javapol102.sdaproject.repository.RoleRepository;
import javapol102.sdaproject.repository.UserRepository;
import javapol102.sdaproject.service.UserService;
import javapol102.sdaproject.util.PasswordValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final String INITIAL_PASSWORD = "pass";

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final PasswordValidator passwordValidator;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, PasswordValidator passwordValidator) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.passwordValidator = passwordValidator;
    }

    @Override
    public boolean existsByUsername(String username) throws EmptyUsernameException {
        if (username == null || username.isBlank()) {
            throw new EmptyUsernameException("Username can not be empty!");
        }

        return userRepository.existsByUsername(username);
    }

    @Override
    public void save(User user) {
        user.setRoles(getUserRoles("USER"));
        user.setPassword(encodeRawPassword(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteByUsername(String username) {
        User user = userRepository.findByUsername(username);
        userRepository.deleteById(user.getId());
    }

    @Override
    public void save(UserDto userDto) {
        User user = new User();
        user.setPassword(encodeRawPassword(INITIAL_PASSWORD));
        user.setUsername(userDto.getUsername());
        user.setRoles(getUserRoles(userDto.getRoles().toUpperCase()));
        userRepository.save(user);
    }

    @Override
    public void changePassword(String username, PasswordDto passwordDto) throws WrongPasswordException, InvalidPasswordException {
        //pobierz usera z DB
        User user = userRepository.findByUsername(username);

        //jeśli obecne hasło podane w formularzu jest różne od tego zapisanego dotychczas w DB
        if (!bCryptPasswordEncoder.matches(passwordDto.getActualPassword(), user.getPassword())) {
            throw new WrongPasswordException("Passwords are incorrect");
        }
        //jeśli nowe hasła się nie zgadzają
        if (!passwordDto.getNewPassword().equals(passwordDto.getNewRepeatedPassword())) {
            throw new WrongPasswordException("Passwords are incorrect");
        }

        //jeśli hasło nie spełnia wymogów
        if (!passwordValidator.isPasswordValid(passwordDto.getNewPassword())) {
            throw new InvalidPasswordException("Password must contains A-Z, a-z, @#$%%, minimum 8 characters");
        }

        //zakoduj hasło i zaktualizuj w DB
        user.setPassword(encodeRawPassword(passwordDto.getNewPassword()));
        userRepository.save(user);

    }

    private List<Role> getUserRoles(String roles) {
        String[] roleArray = roles.split(",");

        List<Role> roleList = new ArrayList<>();

        for (String role : roleArray) {
            Role roleFromDB = roleRepository.findByName(role.trim());
            roleList.add(roleFromDB);
        }

        return roleList;
    }

    private String encodeRawPassword(String rawPassword) {
        return bCryptPasswordEncoder.encode(rawPassword);
    }
}
