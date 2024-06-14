package ipvc.estg.web.controller;

import bll.FuncionarioBLL;
import bll.AulaBLL;
import bll.SocioBLL;
import entity.Funcionario;
import entity.Aula;
import entity.Socio;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
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
        AulaBLL.checkAulaStatus();
        AulaBLL.removeAulasPassadas();

        int idTipoFuncionario = funcionario.getIdTipofuncionario();
        model.addAttribute("idTipoFuncionario", idTipoFuncionario);

        if (idTipoFuncionario == 1) {
            List<Aula> aulas = aulaBLL.getAulasByInstructorId(funcionario.getIdFuncionario());
            List<String> modalidadeNames = new ArrayList<>();
            List<String> localNames = new ArrayList<>();
            List<String> tipoAulas = new ArrayList<>();
            List<String> alunoNames = new ArrayList<>();
            List<String> estadoAulas = new ArrayList<>();

            for (Aula aula : aulas) {
                modalidadeNames.add(aulaBLL.getModalidadeNameByIdAula(aula.getId()));
                localNames.add(aulaBLL.getLocalByIdAula(aula.getId()));
                tipoAulas.add(aulaBLL.getTipoAulaByIdAula(aula.getId()));
                estadoAulas.add(aulaBLL.getEstadoAulaById(aula.getId()));

                if (aula.getTotalLugares() == 1) {
                    alunoNames.add(aulaBLL.getSocioNameByIdAula(aula.getId()));
                } else {
                    int numAlunos = aulaBLL.getNumAlunosByIdAula(aula.getId());
                    alunoNames.add(numAlunos + " socios inscritos");
                }
            }

            model.addAttribute("aulas", aulas);
            model.addAttribute("modalidadeNames", modalidadeNames);
            model.addAttribute("localNames", localNames);
            model.addAttribute("tipoAulas", tipoAulas);
            model.addAttribute("alunoNames", alunoNames);
            model.addAttribute("estadoAulas", estadoAulas);
            return "instrutor";
        } else if (idTipoFuncionario == 2) {
            List<Aula> aulas = aulaBLL.getAllAulasGrupo();
            List<String> modalidadeNames = new ArrayList<>();
            List<String> instrutorNames = new ArrayList<>();
            List<String> estadoAulas = new ArrayList<>();


            for (Aula aula : aulas) {
                modalidadeNames.add(aulaBLL.getModalidadeNameByIdAula(aula.getId()));
                instrutorNames.add(aulaBLL.getInstrutorNameByIdAula(aula.getId()));
                estadoAulas.add(aulaBLL.getEstadoAulaById(aula.getId()));

            }

            model.addAttribute("aulas", aulas);
            model.addAttribute("modalidadeNames", modalidadeNames);
            model.addAttribute("instrutorNames", instrutorNames);
            model.addAttribute("estadoAulas", estadoAulas);


            return "responsavel";
        } /*else if (idTipoFuncionario == 3) {
            List<Socio> socios = socioBLL.getAllSocios();
            model.addAttribute("socios", socios);
            return "rececionista";
        }*/

        return "home";
    }

    @DeleteMapping("/deleteAula/{id}")
    @ResponseBody
    public String deleteAula(@PathVariable int id) {
        Aula aula = aulaBLL.getAulaById(id);

        if (aula == null) {
            return "Aula não encontrada.";
        }


        if (aula.getDataHoraComeco().isBefore(Instant.now() ) && aula.getDataHoraFim().isAfter(Instant.now())) {
            return "Não pode eliminar uma aula que já começou.";
        }



        aulaBLL.deleteAulaWithLinhaAula(id);
        return "Aula eliminada com sucesso.";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }
}
