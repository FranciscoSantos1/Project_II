package ipvc.estg.web.controller;

import bll.FuncionarioBLL;
import bll.AulaBLL;
import bll.ModalidadeBLL;
import bll.SocioBLL;
import entity.Funcionario;
import entity.Aula;
import entity.Modalidade;
import entity.Socio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("funcionario")
public class HomeController {

    private final FuncionarioBLL funcionarioBLL;
    private final AulaBLL aulaBLL;
    private final SocioBLL socioBLL;
    private final ModalidadeBLL modalidadeBLL;

    @Autowired
    public HomeController(FuncionarioBLL funcionarioBLL, AulaBLL aulaBLL, SocioBLL socioBLL, ModalidadeBLL modalidadeBLL) {
        this.funcionarioBLL = funcionarioBLL;
        this.aulaBLL = aulaBLL;
        this.socioBLL = socioBLL;
        this.modalidadeBLL = modalidadeBLL;
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

    @GetMapping("/addAula")
    public String showAddAulaForm(Model model) {
        List<Modalidade> modalidades = modalidadeBLL.getAllModalidades();
        model.addAttribute("modalidades", modalidades);
        return "addAula";
    }

    @GetMapping("/getAvailableInstructors")
    @ResponseBody
    public List<Funcionario> getAvailableInstructors(@RequestParam("date") String date,
                                                     @RequestParam("time") String time,
                                                     @RequestParam("duration") String duration) {
        LocalDate localDate = LocalDate.parse(date);
        LocalTime localTime = LocalTime.parse(time);
        LocalTime durationTime = parseDurationToTime(duration);

        Instant startInstant = localDate.atTime(localTime).atZone(ZoneId.systemDefault()).toInstant();
        Instant endInstant = startInstant.plusSeconds(durationTime.toSecondOfDay());

        return aulaBLL.getAvailableInstructors(startInstant, endInstant);
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
                             @RequestParam String instrutor,
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
            newAula.setTotalLugares(lugares);
            newAula.setVagas(lugares);
            newAula.setDataHoraComeco(dataHoraComeco);
            newAula.setDataHoraFim(dataHoraFim);
            newAula.setIdModalidade(modalidadeBLL.getModalidadeByName(modalidade).getIdModalidade());
            newAula.setDuracao(durationTime);
            newAula.setIdFuncionario(funcionarioBLL.getFuncionarioByName(instrutor).getIdFuncionario());

            aulaBLL.createAula(newAula);

            model.addAttribute("message", "Aula adicionada com sucesso!");
            return "redirect:/addAula";
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
