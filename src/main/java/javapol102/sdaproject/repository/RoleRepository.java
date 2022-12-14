package javapol102.sdaproject.repository;

import javapol102.sdaproject.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role , Integer> {

    Role findByName(String name);

}
