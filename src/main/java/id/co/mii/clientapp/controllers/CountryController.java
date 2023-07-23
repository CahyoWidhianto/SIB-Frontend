package id.co.mii.clientapp.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import id.co.mii.clientapp.services.CountryService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/country")
public class CountryController {
    private CountryService countryService;

    @GetMapping
    public String getAll(Model model, Authentication authentication) {
        String username = authentication.getName();
        model.addAttribute("username", username);
        model.addAttribute("countries", countryService.getAll());
        model.addAttribute("isActive", "country");
        return "country/index";
    }

}
