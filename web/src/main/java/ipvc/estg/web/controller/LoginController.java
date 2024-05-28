package ipvc.estg.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";  // Retorna a view 'login.html' que está em src/main/resources/templates
    }

    @GetMapping("/home")
    public String home() {
        return "home";  // Retorna a view 'home.html' que está em src/main/resources/templates
    }
}
