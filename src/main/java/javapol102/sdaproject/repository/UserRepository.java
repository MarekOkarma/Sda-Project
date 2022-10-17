package javapol102.sdaproject.repository;

import javapol102.sdaproject.model.Role;
import javapol102.sdaproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    User findByEmail(String email);

    boolean existsByUsername(String username);

}
