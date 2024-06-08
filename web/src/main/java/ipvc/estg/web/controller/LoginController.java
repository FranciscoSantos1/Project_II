package ipvc.estg.web.controller;

import bll.FuncionarioBLL;
import entity.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("funcionario")
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
                              @ModelAttribute("funcionario") Funcionario funcionario,
                              Model model,
                              RedirectAttributes redirectAttributes) {



        if (funcionarioBLL.verifyLogin(email, password)) {
            System.out.println("Login efetuado com sucesso.");

            Funcionario loggedFuncionario = funcionarioBLL.getFuncionarioByEmail(email);
            if (loggedFuncionario != null) {
                // Armazene o Funcionario no modelo para a sessão
                model.addAttribute("funcionario", loggedFuncionario);
                return "redirect:/home";
            } else {
                redirectAttributes.addFlashAttribute("loginError", "Erro ao obter informações do funcionário. Tente novamente.");
                return "redirect:/login";
            }
        } else {
            redirectAttributes.addFlashAttribute("loginError", "Email ou password incorretos. Tente novamente.");
            return "redirect:/login";
        }
    }
}
