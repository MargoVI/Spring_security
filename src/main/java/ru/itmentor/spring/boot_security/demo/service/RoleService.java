package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmentor.spring.boot_security.demo.entity.Role;
import ru.itmentor.spring.boot_security.demo.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

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

}
