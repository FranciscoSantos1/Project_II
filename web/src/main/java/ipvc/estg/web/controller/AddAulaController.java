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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.*;
import java.util.List;

@Controller
public class AddAulaController {

    private final AulaBLL aulaBLL;
    private final FuncionarioBLL funcionarioBLL;
    private final ModalidadeBLL modalidadeBLL;

    @Autowired
    public AddAulaController(AulaBLL aulaBLL, FuncionarioBLL funcionarioBLL, ModalidadeBLL modalidadeBLL) {
        this.aulaBLL = aulaBLL;
        this.funcionarioBLL = funcionarioBLL;
        this.modalidadeBLL = modalidadeBLL;
    }

    @GetMapping("/addAula")
    public String showAddAulaForm(Model model) {
        List<Modalidade> modalidades = modalidadeBLL.getAllModalidades();
        model.addAttribute("modalidades", modalidades);
        return "addAula";
    }

    @GetMapping("/getAvailableInstructors")
    @ResponseBody
    public List<Funcionario> getAvailableInstructors(@RequestParam String date, @RequestParam String hour, @RequestParam String duration) {
        System.out.println("Received date: " + date);
        System.out.println("Received duration: " + duration);

        Instant start = Instant.parse(date);
        Duration dur = parseDuration(duration);
        Instant end = start.plus(dur);

        System.out.println("Calculated start: " + start);
        System.out.println("Calculated end: " + end);

        List<Funcionario> availableInstructors = aulaBLL.getAvailableInstructors(start, end);
        System.out.println("Available instructors: " + availableInstructors.size());

        return availableInstructors;
    }

    private Duration parseDuration(String durationStr) {
        switch (durationStr) {
            case "30m":
                return Duration.ofMinutes(30);
            case "1h":
                return Duration.ofHours(1);
            case "1h30m":
                return Duration.ofMinutes(90);
            case "2h":
                return Duration.ofHours(2);
            default:
                throw new IllegalArgumentException("Invalid duration format");
        }
    }

    @PostMapping("/submitAula")
    public String submitAula(@RequestParam String nome,
                             @RequestParam String data,
                             @RequestParam String hora,
                             @RequestParam String modalidade,
                             @RequestParam String local,
                             @RequestParam String duracao,
                             @RequestParam int lugares,
                             @RequestParam int minimo,
                             @RequestParam int instrutor,
                             Model model) {
        try {
            LocalDate localDate = LocalDate.parse(data);
            LocalTime localTime = LocalTime.parse(hora);
            Instant dataHoraComeco = localDate.atTime(localTime).atZone(ZoneId.systemDefault()).toInstant();
            LocalTime durationTime = parseDurationToTime(duracao);
            Instant dataHoraFim = dataHoraComeco.plusSeconds(durationTime.toSecondOfDay());

            Aula newAula = new Aula();
            newAula.setNome(nome);
            newAula.setLocalAula(local);
            newAula.setNumMinAtletas(minimo);
            newAula.setIdEstadoaula(1);
            newAula.setTotalLugares(lugares);
            newAula.setVagas(lugares);
            newAula.setDataHoraComeco(dataHoraComeco);
            newAula.setDataHoraFim(dataHoraFim);
            newAula.setIdModalidade(modalidadeBLL.getModalidadeByName(modalidade).getIdModalidade());
            newAula.setDuracao(durationTime);
            newAula.setIdFuncionario(instrutor);

            aulaBLL.createAula(newAula);

            model.addAttribute("message", "Aula adicionada com sucesso!");
            return "redirect:/home";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao adicionar aula: " + e.getMessage());
            return "addAula";
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
                throw new IllegalArgumentException("Invalid duration format");
        }
    }
}
