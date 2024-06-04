package ipvc.estg.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import bll.FuncionarioBLL;

@Controller
public class LoginController {

    private final FuncionarioBLL funcionarioBLL;

    @Autowired
    public LoginController(FuncionarioBLL funcionarioBLL) {
        this.funcionarioBLL = funcionarioBLL;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String verifyLogin(@RequestParam String email, @RequestParam String password, Model model) {
        boolean isValid = funcionarioBLL.verifyLogin(email, password);
        if (isValid) {
            return "home";
        } else {
            model.addAttribute("loginError", "Invalid email or password");
            return "login";
        }
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
