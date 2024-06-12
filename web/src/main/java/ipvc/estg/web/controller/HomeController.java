package ipvc.estg.web.controller;

import bll.FuncionarioBLL;
import bll.AulaBLL;
import bll.SocioBLL;
import entity.Funcionario;
import entity.Aula;
import entity.Socio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;

@Controller
@SessionAttributes("funcionario")
public class HomeController {

    private final FuncionarioBLL funcionarioBLL;
    private final AulaBLL aulaBLL;
    private final SocioBLL socioBLL;

    @Autowired
    public HomeController(FuncionarioBLL funcionarioBLL, AulaBLL aulaBLL, SocioBLL socioBLL) {
        this.funcionarioBLL = funcionarioBLL;
        this.aulaBLL = aulaBLL;
        this.socioBLL = socioBLL;
    }

    @ModelAttribute("funcionario")
    public Funcionario setUpFuncionario() {
        return new Funcionario();
    }

    @GetMapping("/home")
    public String home(@ModelAttribute("funcionario") Funcionario funcionario, Model model) {
        if (funcionario == null || funcionario.getIdFuncionario() == 0) {
            return "redirect:/login";
        }

        int idTipoFuncionario = funcionario.getIdTipofuncionario();
        model.addAttribute("idTipoFuncionario", idTipoFuncionario);

        if (idTipoFuncionario == 1) {
            List<Socio> socios = socioBLL.getAllSociosWeb();
            model.addAttribute("socios", socios);
            return "rececionista";
        } else if (idTipoFuncionario == 2) {
            List<Aula> aulas = aulaBLL.getAllAulasGrupo();
            model.addAttribute("aulas", aulas);
            return "responsavel";
        } else if (idTipoFuncionario == 3) {
            List<Aula> aulas = aulaBLL.getAulasByInstructorId(funcionario.getIdFuncionario());
            model.addAttribute("aulas", aulas);
            return "instrutor";
        }

        return "home";
    }
}
