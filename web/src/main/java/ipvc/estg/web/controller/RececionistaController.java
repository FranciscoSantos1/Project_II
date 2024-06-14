package ipvc.estg.web.controller;

import bll.PlanoBLL;
import bll.SocioBLL;
import entity.Plano;
import entity.Socio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigInteger;
import java.util.List;

@Controller
public class RececionistaController {

    @GetMapping("/rececionista")
    public String rececionista(Model model) {
        List<Socio> socios = SocioBLL.getAllSocios();
        model.addAttribute("socios", socios);
        return "rececionista";
    }

    @GetMapping("/desativarSocio")
    public String deactivateSocio(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {
        try {
            SocioBLL.deactivateSocio(id);
            redirectAttributes.addFlashAttribute("message", "Sócio desativado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao desativar o sócio!");
        }
        return "redirect:/rececionista";
    }

    @GetMapping("/addSocio")
    public String addSocioForm(Model model) {
        List<Plano> planos = PlanoBLL.getAllPlanos();
        model.addAttribute("planos", planos);
        return "addSocio";
    }

    @PostMapping("/addSocio")
    public String addSocio(@RequestParam String nome, @RequestParam BigInteger contacto, @RequestParam String rua,
                           @RequestParam String nPorta, @RequestParam String codPostal, @RequestParam int idPlano,
                           RedirectAttributes redirectAttributes) {
        try {
            Socio socio = new Socio();
            socio.setNome(nome);
            socio.setContacto(contacto);
            socio.setRua(rua);
            socio.setnPorta(nPorta);
            socio.setCodPostal(codPostal);
            socio.setIdPlano(idPlano);
            socio.setAtivo(true);

            SocioBLL.createSocio(socio);
            redirectAttributes.addFlashAttribute("message", "Sócio adicionado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao adicionar o sócio!");
        }
        return "redirect:/rececionista";
    }
}
