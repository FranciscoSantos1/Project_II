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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
            return "instrutor";
        } else if (idTipoFuncionario == 2) {
            List<Aula> aulas = aulaBLL.getAllAulasGrupo();
            List<String> modalidadeNames = new ArrayList<>();
            List<String> instrutorNames = new ArrayList<>();

            for (Aula aula : aulas) {
                modalidadeNames.add(aulaBLL.getModalidadeNameByIdAula(aula.getId()));
                instrutorNames.add(aulaBLL.getInstrutorNameByIdAula(aula.getId()));
            }

            model.addAttribute("aulas", aulas);
            model.addAttribute("modalidadeNames", modalidadeNames);
            model.addAttribute("instrutorNames", instrutorNames);
            return "responsavel";
        } else if (idTipoFuncionario == 3) {
            List<Socio> socios = socioBLL.getAllSociosWeb();
            model.addAttribute("socios", socios);
            return "rececionista";
        }

        return "home";
    }
}
