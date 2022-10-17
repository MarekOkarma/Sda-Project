package javapol102.sdaproject.util;

import org.springframework.stereotype.Component;

@Component
public class PasswordValidator {

    public boolean isPasswordValid(String password) {
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        return password.matches(pattern);
    }

}
