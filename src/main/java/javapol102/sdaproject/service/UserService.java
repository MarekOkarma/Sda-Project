package javapol102.sdaproject.service;

import javapol102.sdaproject.dto.PasswordDto;
import javapol102.sdaproject.dto.UserDto;
import javapol102.sdaproject.exception.EmptyUsernameException;
import javapol102.sdaproject.exception.InvalidPasswordException;
import javapol102.sdaproject.exception.WrongPasswordException;
import javapol102.sdaproject.model.User;


import java.util.List;

public interface UserService {

    boolean existsByUsername(String username) throws EmptyUsernameException;

    void save(User user);

    List<User> findAll();

    void deleteByUsername(String username);

    void save(UserDto userDto);

    void changePassword(String username, PasswordDto passwordDto) throws WrongPasswordException, InvalidPasswordException;

}
