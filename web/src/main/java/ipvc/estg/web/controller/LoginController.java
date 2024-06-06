package ipvc.estg.web.controller;

import bll.FuncionarioBLL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @Autowired
    private FuncionarioBLL funcionarioBLL;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String verifyLogin(@RequestParam("email") String email,
                              @RequestParam("password") String password,
                              RedirectAttributes redirectAttributes) {
        if (funcionarioBLL.verifyLogin(email, password)) {
            System.out.println("Login efetuado com sucesso.");
            return "redirect:/home";
        } else {
            redirectAttributes.addFlashAttribute("loginError", "Email ou password incorretos. Tente novamente.");
            return "redirect:/login";
        }
    }
}
