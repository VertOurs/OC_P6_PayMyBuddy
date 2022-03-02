package fr.vertours.buddtwo.repository;

import fr.vertours.buddtwo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    public Role findByName(String role);
}
