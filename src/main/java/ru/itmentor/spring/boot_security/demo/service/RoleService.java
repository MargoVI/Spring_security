package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.entity.Role;
import ru.itmentor.spring.boot_security.demo.entity.User;
import ru.itmentor.spring.boot_security.demo.repository.RoleRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> AllRoles() {
        return roleRepository.findAll();
    }

    public Role findOne(Long id) {
        Optional<Role> foundPerson = roleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    public void saveRole(Role person) {
        roleRepository.save(person);
    }


    public void update(Long id, Role updatedRole) {
        updatedRole.setId(id);
        roleRepository.save(updatedRole);
    }

    public void delete(Long id) {
        roleRepository.deleteById(id);
    }



    //// ????
    public Role loadUserByRoleName(String roleName) throws UsernameNotFoundException {
        Role role = roleRepository.findByRoleName(roleName);

        if (role == null) {
            throw new UsernameNotFoundException("Role not found");
        }

        return role;
    }

    public Set<Role> getSetOfRoles(String[] roleNames) {
        Set<Role> roleSet = new HashSet<>();
        for (String role : roleNames) {
            roleSet.add(loadUserByRoleName(role));
        }
        return roleSet;
    }
}
