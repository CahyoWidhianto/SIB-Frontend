package id.co.mii.clientapp.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import id.co.mii.clientapp.models.User;
import id.co.mii.clientapp.models.dto.request.UserRequest;
import id.co.mii.clientapp.services.RoleService;
import id.co.mii.clientapp.services.UserService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    private UserService userService;

    @GetMapping
    public String getAll(Model model, Authentication authentication) {
        String username = authentication.getName();
        model.addAttribute("username", username);
        model.addAttribute("users", userService.getAll());
        model.addAttribute("isActive", "user");
        return "user/index";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable Integer id, Model model, Authentication authentication) {
        String username = authentication.getName();
        model.addAttribute("username", username);
        model.addAttribute("isActive", "user");
        model.addAttribute("user", userService.getById(id));
        return "user/detail";
    }

    @GetMapping("/create")
    public String create(Model model, UserRequest userRequest, Authentication authentication) {
        String username = authentication.getName();
        model.addAttribute("username", username);
        model.addAttribute("isActive", "user");
        return "user/create";
    }

    @PostMapping
    public String created(UserRequest userRequest) {
        userService.create(userRequest);
        return "redirect:/user";
    }

    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable Integer id, User user, Authentication authentication) {
        String username = authentication.getName();
        model.addAttribute("username", username);
        model.addAttribute("isActive", "user");
        model.addAttribute("user", userService.getById(id));
        return "user/edit";
    }

    @PutMapping("/{id}")
    public String updated(@PathVariable Integer id, User user) {
        userService.update(id, user);
        return "redirect:/user";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        userService.delete(id);
        return "redirect:/user";
    }
}
