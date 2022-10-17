package javapol102.sdaproject.main;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderMain {
    public static void main(String[] args) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("hasłoMasło"));

        System.out.println(encoder.matches("hasłoMasło","$2a$10$ZubULWlhvsar43O5GGPwHOagovUO9o4gu5awu6fxNPN/xaWcEBGmy"));

        validatePassword();
    }

    public static void validatePassword() {
        String passwd = "";
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        System.out.println(passwd.matches(pattern));
    }


}
