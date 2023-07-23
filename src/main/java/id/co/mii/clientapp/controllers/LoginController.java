package id.co.mii.clientapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import id.co.mii.clientapp.models.dto.request.LoginRequest;
import id.co.mii.clientapp.services.LoginService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class LoginController {

    private LoginService loginService;

    @GetMapping("/login")
    public String loginPage(LoginRequest loginRequest) {
        // model.addAttribute("loginRequest", new LoginRequest());
        return "auth/login";
    }

    @GetMapping("/login2")
    public String loginPage2(LoginRequest loginRequest) {
        // model.addAttribute("loginRequest", new LoginRequest());
        return "auth/login2";
    }

    @PostMapping("/login2")
    public String login(LoginRequest loginRequest) {
        if (!loginService.login(loginRequest)) {
            return "redirect:/login2?error=true";
        }
        return "redirect:/region";
    }

}
