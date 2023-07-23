package id.co.mii.clientapp.controllers;

import id.co.mii.clientapp.services.RegionService;
import lombok.AllArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/region")
public class RegionController {

    private RegionService regionService;

    @GetMapping
    public String getAll(Model model, Authentication authentication) {
        String username = authentication.getName();
        model.addAttribute("username", username);
        model.addAttribute("regions", regionService.getAll());
        model.addAttribute("isActive", "region");
        return "region/index";
    }

}