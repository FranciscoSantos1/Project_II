package ipvc.estg.web.controller;

import bll.*;
import entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@Controller
@SessionAttributes("funcionario")
public class ReciboController {

    @ModelAttribute("funcionario")
    public Funcionario setUpFuncionario() {
        return new Funcionario();
    }

    @Autowired
    private SocioBLL socioBLL;

    @Autowired
    private ReciboBLL reciboBLL;

    @Autowired
    private TipoPagamentoBLL tipoPagamentoBLL;

    @Autowired
    private EstadoPagamentoBLL estadoPagamentoBLL;

    @Autowired
    private PlanoBLL planoBLL;

    @GetMapping("/emitirRecibo")
    public String emitirReciboForm(@RequestParam("idSocio") int idSocio, Model model) {
        Funcionario funcionario = (Funcionario) model.getAttribute("funcionario");


        Socio socio = socioBLL.getSocioById(idSocio);
        model.addAttribute("socio", socio);

        BigInteger valorRecibo = planoBLL.findPlanoById(socio.getIdPlano()).getValor();
        model.addAttribute("valorRecibo", valorRecibo);

        Recibo recibo = new Recibo();
        recibo.setIdRecibo(reciboBLL.getNextIdRecibo());
        recibo.setDataHoraEmissao(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
        recibo.setIdFuncionario(funcionario.getIdFuncionario());
        model.addAttribute("recibo", recibo);

        List<TipoPagamento> tiposPagamento = tipoPagamentoBLL.readTipoPagamento();
        model.addAttribute("tiposPagamento", tiposPagamento);

        List<EstadoPagamento> estadosPagamento = estadoPagamentoBLL.readEstadoPagamento();
        model.addAttribute("estadosPagamento", estadosPagamento);

        return "emitirRecibo";
    }

    @PostMapping("/emitirRecibo")
    public String emitirReciboSubmit(@RequestParam("idSocio") int idSocio,
                                     @RequestParam("iva") BigInteger iva,
                                     @RequestParam("mes") String mes,
                                     @RequestParam("tipoPagamento") int idTipoPagamento,
                                     @RequestParam("estadoPagamento") int idEstadoPagamento,
                                     @ModelAttribute("funcionario") Funcionario funcionario,
                                     Model model) {
        try {
            Recibo recibo = new Recibo();
            recibo.setIdSocio(idSocio);
            recibo.setValor(planoBLL.findPlanoById(socioBLL.getSocioById(idSocio).getIdPlano()).getValor());
            recibo.setIva(iva);
            recibo.setMes(mes);
            recibo.setIdTipopagamento(idTipoPagamento);
            recibo.setIdEstadopagamento(idEstadoPagamento);
            recibo.setIdFuncionario(funcionario.getIdFuncionario());
            recibo.setDataHoraEmissao(java.sql.Date.valueOf(LocalDate.now()));
            recibo.setIdPlano(socioBLL.getSocioById(idSocio).getIdPlano());

            reciboBLL.createRecibo(recibo);

            model.addAttribute("recibo", recibo);
            return "redirect:/rececionista";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Erro ao criar recibo: " + e.getMessage());
            return "emitirRecibo";
        }
    }
}


