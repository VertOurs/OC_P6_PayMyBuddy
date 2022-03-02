package fr.vertours.buddtwo.service;

import fr.vertours.buddtwo.model.Role;
import fr.vertours.buddtwo.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl {

    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public Role findUSERRole() {
        return roleRepository.findByName("USER");
    }
}
