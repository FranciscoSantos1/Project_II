package ipvc.estg.web.controller;

import bll.AulaBLL;
import bll.FuncionarioBLL;
import bll.ModalidadeBLL;
import entity.Aula;
import entity.Funcionario;
import entity.Modalidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@Controller
public class DetalhesAulaController {

    @Autowired
    private AulaBLL aulaBLL;

    @Autowired
    private ModalidadeBLL modalidadeBLL;

    @Autowired
    private FuncionarioBLL funcionarioBLL;

    @GetMapping("/detalhesAula")
    public String getDetalhesAula(@RequestParam("id") int id, Model model) {
        Aula aula = aulaBLL.getAulaById(id);
        if (aula == null) {
            return "redirect:/home";
        }

        Modalidade modalidade = modalidadeBLL.getModalidadeById(aula.getIdModalidade());
        Funcionario instrutor = funcionarioBLL.getFuncionarioById(aula.getIdFuncionario());

        model.addAttribute("aula", aula);
        model.addAttribute("modalidadeName", modalidade.getModalidade());
        model.addAttribute("instrutorName", instrutor.getNome());

        return "detalhesAula";
    }

}
