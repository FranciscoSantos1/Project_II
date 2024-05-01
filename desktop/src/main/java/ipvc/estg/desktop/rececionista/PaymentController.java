package ipvc.estg.desktop.rececionista;

import bll.FuncionarioBLL;
import bll.PlanoBLL;
import bll.ReciboBLL;
import entity.Funcionario;
import entity.Plano;
import entity.Recibo;
import entity.Socio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class PaymentController {
    @FXML
    private Button logoutButton;

    @FXML
    private Button backButton;

    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelButton;

    @FXML
    private  Label funcionarioLabel;

    @FXML
    private  Label idReciboLabel;

    @FXML
    private  Label dataEmissaoLabel;

    @FXML
    private  Label valorLabel;

    @FXML
    private  Label idSocioLabel;

    @FXML
    private  Label ivaLabel;

    @FXML
    private  Label tipoPagLabel;

    @FXML
    private  Label estadoPagLabel;

    @FXML
    private  Label mesLabel;


    public  void initSocioDetails(Socio socio){
        Recibo recibo = new Recibo();
        recibo.setIdSocio(socio.getIdSocio());
        //TODO: ComboBox para escolher tipo de pagamento e estado de pagamento; adicionar tipos e estados de pagamento à BD
        recibo.setIdTipopagamento(1);
        recibo.setIdEstadopagamento(1);

        recibo.setIdRecibo(ReciboBLL.getNextIdRecibo());
        recibo.setDataEmissao(java.time.LocalDate.now());
        recibo.setIva(new java.math.BigInteger("23"));
        recibo.setIdPlano(socio.getIdPlano());
        recibo.setValor(PlanoBLL.findPlanoById(socio.getIdPlano()).getValor());
        recibo.setMes(java.sql.Date.valueOf(java.time.LocalDate.now()));

        //TODO: Mudar para o funcionário que está logado
        recibo.setIdFuncionario(3);

        funcionarioLabel.setText(String.valueOf(recibo.getIdFuncionario()));
        idReciboLabel.setText(String.valueOf(recibo.getIdRecibo()));
        dataEmissaoLabel.setText(String.valueOf(java.time.LocalDate.now()));
        valorLabel.setText(recibo.getValor() + "€");
        idSocioLabel.setText(String.valueOf(recibo.getIdSocio()));
        ivaLabel.setText(recibo.getIva() + "%");
        tipoPagLabel.setText(String.valueOf(recibo.getIdTipopagamento()));
        estadoPagLabel.setText(String.valueOf(recibo.getIdEstadopagamento()));

        //TODO: Verificar o mês de pagamento corretamente
        mesLabel.setText(String.valueOf(recibo.getMes()));
    }
    @FXML
    void logout(ActionEvent event) {
        try {
            URL resourceUrl = getClass().getResource("/ipvc/estg/desktop/Login/login.fxml");
            if (resourceUrl == null) {
                System.err.println("Ficheiro FXML não encontrado.");
                return;
            }
            Parent root = FXMLLoader.load(resourceUrl);
            Scene mainPage = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(mainPage);
            stage.setTitle("GymMaster - Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void back(ActionEvent event) {
        try {
            URL resourceUrl = getClass().getResource("/ipvc/estg/desktop/rececionista/socioDetails.fxml");
            if (resourceUrl == null) {
                System.err.println("Ficheiro FXML não encontrado.");
                return;
            }
            FXMLLoader loader = new FXMLLoader(resourceUrl);
            Parent root = loader.load();
            Scene mainPage = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(mainPage);
            stage.setTitle("GymMaster - Rececionista");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public static Funcionario getLoggedFuncionario(Funcionario funcionario){
        return funcionario;
    }
}
