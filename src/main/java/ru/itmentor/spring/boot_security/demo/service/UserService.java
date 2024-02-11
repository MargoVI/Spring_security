package ru.itmentor.spring.boot_security.demo.service;

import ru.itmentor.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserService {
    User findUserById(Long userId);
    List<User> allUsers();
    boolean saveUser(User user);
    void deleteUser(Long id);
    void update(Long id, User updatedUser);
}
