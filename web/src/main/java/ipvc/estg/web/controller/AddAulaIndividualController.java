package ipvc.estg.web.controller;

import bll.AulaBLL;
import bll.FuncionarioBLL;
import bll.LinhaAulaBLL;
import bll.SocioBLL;
import entity.Aula;
import entity.Funcionario;
import entity.LinhaAula;
import entity.Socio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.List;

@Controller
@SessionAttributes("funcionario")
public class AddAulaIndividualController {

    private final AulaBLL aulaBLL;
    private final SocioBLL socioBLL;

    @Autowired
    public AddAulaIndividualController(AulaBLL aulaBLL, SocioBLL socioBLL) {
        this.aulaBLL = aulaBLL;
        this.socioBLL = socioBLL;
    }

    @GetMapping("/addAulaIndividual")
    public String showAddAulaIndividualForm(Model model) {
        List<Socio> socios = socioBLL.getAllSociosWeb();
        model.addAttribute("socios", socios);
        return "addAulaIndividual";
    }

    @PostMapping("/submitAulaIndividual")
    public String submitAulaIndividual(@RequestParam String nome,
                                       @RequestParam String data,
                                       @RequestParam String hora,
                                       @RequestParam String duracao,
                                       @RequestParam int socioId,
                                       Model model) {
        try {
            LocalDate localDate = LocalDate.parse(data);
            LocalTime localTime = LocalTime.parse(hora);
            Instant dataHoraComeco = localDate.atTime(localTime).atZone(ZoneId.systemDefault()).toInstant();
            LocalTime durationTime = parseDurationToTime(duracao);
            Instant dataHoraFim = dataHoraComeco.plusSeconds(durationTime.toSecondOfDay());

            Funcionario funcionario = (Funcionario) model.getAttribute("funcionario");


            Aula newAula = new Aula();
            newAula.setNome(nome);
            newAula.setLocalAula("SALA MUSCULAÇÃO");
            newAula.setNumMinAtletas(1);
            newAula.setIdEstadoaula(1);
            newAula.setTotalLugares(1);
            newAula.setVagas(0);
            newAula.setDataHoraComeco(dataHoraComeco);
            newAula.setDataHoraFim(dataHoraFim);
            newAula.setIdModalidade(11);
            newAula.setDuracao(durationTime);
            newAula.setIdFuncionario(funcionario.getIdFuncionario());


            aulaBLL.createAula(newAula);



            LinhaAula linhaAula = new LinhaAula();
            linhaAula.setIdAula(newAula.getId());
            linhaAula.setIdFuncionario(funcionario.getIdFuncionario());
            linhaAula.setIdSocio(socioId);

            LinhaAulaBLL.createLinhaAula(linhaAula);


            System.out.println("After creating linha aula");

            model.addAttribute("message", "Aula individual adicionada com sucesso!");
            return "redirect:/home";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao adicionar aula individual: " + e.getMessage());
            return "addAulaIndividual";
        }
    }

    private LocalTime parseDurationToTime(String durationStr) {
        switch (durationStr) {
            case "30m":
                return LocalTime.of(0, 30);
            case "1h":
                return LocalTime.of(1, 0);
            case "1h30m":
                return LocalTime.of(1, 30);
            case "2h":
                return LocalTime.of(2, 0);
            default:
                throw new IllegalArgumentException("Formato de duração inválido");
        }
    }
}
