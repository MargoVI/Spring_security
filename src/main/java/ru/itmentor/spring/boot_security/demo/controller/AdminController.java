package ru.itmentor.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.itmentor.spring.boot_security.demo.entity.Role;
import ru.itmentor.spring.boot_security.demo.entity.User;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.Collections;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String showAll(Model model) {
        model.addAttribute("users", userService.allUsers());
        return "showAll";
    }

    @DeleteMapping("showAll/{id}/delete")
    public String deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/new") // create работает без добавления роли
    public String registration(Model model, Model model1) {
        model.addAttribute("userForm", new User());
        model.addAttribute("roleForm", new Role());
        return "new";
    }
    @PostMapping("/new")
    public String addUser(@ModelAttribute("userForm") @Valid User userForm, @ModelAttribute("userForm") @Valid Role roleForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "new";
        }
        if (roleForm.equals("ROLE_ADMIN")){
            userForm.setRoles(Collections.singleton(new Role(2L, "ROLE_ADMIN")));
        }
        if (!userService.saveUser(userForm)){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "new";
        }

        return "redirect:/admin";
    }


//    @GetMapping("/{id}/edit")
//    public String edit(Model model, @PathVariable("id") Long id) {
//        model.addAttribute("user", userService.findUserById(id));
//        return "edit";
//    }
//
//    @PatchMapping("showAll/{id}")
//    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
//                         @PathVariable("id") Long id) {
//        if (bindingResult.hasErrors())
//            return "edit";
//
//        userService.update(id, user);
//        return "redirect:/admin";
//    }

    @GetMapping("showAll/{id}/edit")  // edit работает без изменения роли
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.findUserById(id));
        return "edit";
    }

    @PatchMapping("showAll/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @PathVariable("id") Long id) {
        if (bindingResult.hasErrors())
            return "edit";

        userService.update(id, user);
        return "redirect:/admin";
    }

}
