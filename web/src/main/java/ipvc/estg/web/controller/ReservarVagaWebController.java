package ipvc.estg.web.controller;

import bll.AulaBLL;
import bll.ModalidadeBLL;
import bll.SocioBLL;
import bll.LinhaAulaBLL;
import entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("funcionario")
public class ReservarVagaWebController {

    @Autowired
    private AulaBLL aulaBLL;

    @Autowired
    private SocioBLL socioBLL;

    @Autowired
    private LinhaAulaBLL linhaAulaBLL;

    @GetMapping("/reservarVaga")
    public String reservarVaga(@RequestParam("idSocio") int idSocio, Model model) {
        List<Aula> aulas = aulaBLL.getAllAulasGrupo();
        Map<Integer, String> modalidades = new HashMap<>();
        for (Aula aula : aulas) {
            modalidades.put(aula.getId(), aulaBLL.getModalidadeNameByIdAula(aula.getId()));
        }

        Socio socio = socioBLL.getSocioById(idSocio);
        model.addAttribute("socio", socio);
        model.addAttribute("aulas", aulas);
        model.addAttribute("modalidades", modalidades);
        model.addAttribute("idSocio", idSocio);
        return "reservarVaga";
    }

    @PostMapping("/reservarVaga")
    public String reservarVagaSubmit(@RequestParam("idAula") int idAula, @RequestParam("idSocio") int idSocio, Model model) {
        Funcionario funcionario = (Funcionario) model.getAttribute("funcionario");
        LinhaAula linhaAula = linhaAulaBLL.getLinhaAulaByIds(idAula, idSocio);
        if (linhaAula == null){
            linhaAula = new LinhaAula();
            linhaAula.setIdAula(idAula);
            linhaAula.setIdSocio(idSocio);
            linhaAula.setIdFuncionario(funcionario.getIdFuncionario());
            linhaAulaBLL.createLinhaAula(linhaAula);
            aulaBLL.RemoverVagaAula(idAula);
            aulaBLL.updateAula(aulaBLL.getAulaById(idAula));
            Socio socio = socioBLL.getSocioById(idSocio);
            List<Aula> aulas = aulaBLL.getAllAulasGrupo();
            model.addAttribute("aulas", aulas);
            model.addAttribute("idSocio", idSocio);
            model.addAttribute("message", "Vaga reservada com sucesso!");
            return "redirect:/rececionista";
        }else {
            model.addAttribute("message", "Só pode reservar uma vaga por aula!");
            return "redirect:/reservarVaga?idSocio=" + idSocio;
        }
    }
}
