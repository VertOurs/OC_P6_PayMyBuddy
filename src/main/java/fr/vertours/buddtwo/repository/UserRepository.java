package fr.vertours.buddtwo.repository;

import fr.vertours.buddtwo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);
}
