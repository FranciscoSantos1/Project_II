package ipvc.estg.web.controller;

import bll.FuncionarioBLL;
import entity.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @Autowired
    public FuncionarioBLL funcionarioBLL;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String verifyLogin(@RequestParam("email") String email,
                              @RequestParam("password") String password,
                              RedirectAttributes redirectAttributes) {
        if (FuncionarioBLL.verifyLogin(email, password)) {
            return "redirect:/home";
        } else {
            redirectAttributes.addFlashAttribute("loginError", "Invalid email or password.");
            return "redirect:/login";
        }
    }
}