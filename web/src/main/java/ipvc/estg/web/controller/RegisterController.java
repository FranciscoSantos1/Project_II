package ipvc.estg.web.controller;

import bll.FuncionarioBLL;
import entity.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {

    @Autowired
    private FuncionarioBLL funcionarioBLL;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("funcionario", new Funcionario());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Funcionario funcionario, RedirectAttributes redirectAttributes, Model model) {
        if (fieldsAreEmpty(funcionario)) {
            model.addAttribute("registerError", "Preencha todos os campos");
            return "register";
        }

        if (funcionarioBLL.emailExists(funcionario.getEmail())) {
            model.addAttribute("registerError", "Este email já está a ser utilizado. Por favor, insira outro email.");
            return "register";
        }

        try {
            funcionarioBLL.createFuncionario(funcionario);
            redirectAttributes.addFlashAttribute("registerSuccess", "Registado com sucesso");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("registerError", "Erro ao registar");
            return "register";
        }
    }

    private boolean fieldsAreEmpty(Funcionario funcionario) {
        // debug
        System.out.println("Nome: " + funcionario.getNome());
        System.out.println("Email: " + funcionario.getEmail());
        System.out.println("Password: " + funcionario.getPassword());
        System.out.println("Rua: " + funcionario.getRua());
        System.out.println("Nº Porta: " + funcionario.getnPorta());
        System.out.println("Código Postal: " + funcionario.getCodPostal());
        System.out.println("Contacto: " + funcionario.getContacto());
        System.out.println("ID Tipo Funcionário: " + funcionario.getIdTipofuncionario());
        // debug

        return isNullOrEmpty(funcionario.getNome()) || isNullOrEmpty(funcionario.getEmail()) ||
                isNullOrEmpty(funcionario.getPassword()) || isNullOrEmpty(funcionario.getRua()) ||
                isNullOrEmpty(funcionario.getnPorta()) || isNullOrEmpty(funcionario.getCodPostal()) ||
                isNullOrEmpty(funcionario.getContacto()) || funcionario.getIdTipofuncionario() == 0;
    }

    private boolean isNullOrEmpty(String field) {
        return field == null || field.isEmpty();
    }
}
