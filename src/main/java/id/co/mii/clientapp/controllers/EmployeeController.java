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

import id.co.mii.clientapp.models.Employee;
import id.co.mii.clientapp.services.EmployeeService;
import id.co.mii.clientapp.services.UserService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/employee")
@PreAuthorize("hasRole('ADMIN')")
public class EmployeeController {
    private EmployeeService employeeService;
    private UserService userService;

    @GetMapping
    public String getAll(Model model, Authentication authentication) {
        String username = authentication.getName();
        model.addAttribute("username", username);
        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("isActive", "employee");
        return "employee/index";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable Integer id, Model model, Authentication authentication) {
        String username = authentication.getName();
        model.addAttribute("username", username);
        model.addAttribute("employee", employeeService.getById(id));
        model.addAttribute("isActive", "employee");
        return "employee/detail";
    }

    @GetMapping("/create")
    public String create(Model model, Employee employee, Authentication authentication) {
        String username = authentication.getName();
        model.addAttribute("username", username);
        model.addAttribute("isActive", "employee");
        model.addAttribute("users", userService.getAll());
        return "employee/create";
    }

    @PostMapping
    public String created(Employee employee) {
        employeeService.create(employee);
        return "redirect:/employee";
    }

    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable Integer id, Employee employee, Authentication authentication) {
        String username = authentication.getName();
        model.addAttribute("username", username);
        model.addAttribute("employee", employeeService.getById(id));
        return "employee/edit";
    }

    @PutMapping("/{id}")
    public String updated(@PathVariable Integer id, Employee employee) {
        employeeService.update(id, employee);
        return "redirect:/employee";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        employeeService.delete(id);
        return "redirect:/employee";
    }
}
