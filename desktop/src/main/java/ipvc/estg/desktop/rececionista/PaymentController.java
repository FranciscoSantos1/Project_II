package ipvc.estg.desktop.rececionista;

import bll.*;
import database.Database;
import entity.*;
import ipvc.estg.desktop.Login.SessionData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.hibernate.internal.SessionOwnerBehavior;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.Date;
import java.util.List;

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
    private  Label idPlanoLabel;

    @FXML
    private ComboBox<String> tipoPagamentoCB;

    @FXML
    private ComboBox<String> estadoPagamentoCB;

    @FXML
    private ComboBox<String> mesComboBox;


    public void initSocioDetails(Socio socio){
        List<TipoPagamento> tipo = TipoPagamentoBLL.readTipoPagamento();
        for (TipoPagamento t : tipo) {
            tipoPagamentoCB.getItems().add(t.getIdTipopagamento() + " - " + t.getTipo());
        }

        List<EstadoPagamento> estado = EstadoPagamentoBLL.readEstadoPagamento();
        for (EstadoPagamento e : estado) {
            estadoPagamentoCB.getItems().add(e.getIdEstadopagamento() + " - " + e.getEstado());
        }
        mesComboBox.getItems().addAll("Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho",
                "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro");
        funcionarioLabel.setText(SessionData.getInstance().getCurrentUser().getNome());
        idSocioLabel.setText(String.valueOf(socio.getIdSocio()));
        dataEmissaoLabel.setText(String.valueOf(java.sql.Date.valueOf(java.time.LocalDate.now())));
        ivaLabel.setText("23");
        valorLabel.setText(String.valueOf(PlanoBLL.findPlanoById(socio.getIdPlano()).getValor()));
        idReciboLabel.setText(String.valueOf(ReciboBLL.getNextIdRecibo()));
        idPlanoLabel.setText(String.valueOf(socio.getIdPlano()));
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
            SocioDetailsController detailsController = loader.getController();
            detailsController.initialize(SocioBLL.findSocioById(Integer.parseInt(idSocioLabel.getText())));
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
    public void saveRecibo(ActionEvent event) {
        Recibo recibo = new Recibo();
        Funcionario funcionario = SessionData.getInstance().getCurrentUser();
        recibo.setDataHoraEmissao(java.sql.Date.valueOf(java.time.LocalDate.now()));
        recibo.setIdFuncionario(funcionario.getIdFuncionario());
        recibo.setIdRecibo(Integer.parseInt(idReciboLabel.getText()));
        recibo.setIdSocio(Integer.parseInt(idSocioLabel.getText()));
        recibo.setIdPlano(Integer.parseInt(idPlanoLabel.getText()));
        recibo.setDataEmissao(java.sql.Date.valueOf(java.time.LocalDate.now()));
        recibo.setIva(BigInteger.valueOf(Integer.parseInt(ivaLabel.getText())));
        recibo.setValor(BigInteger.valueOf(Integer.parseInt(valorLabel.getText())));
        recibo.setMes(mesComboBox.getValue());
        recibo.setIdTipopagamento(Integer.parseInt(tipoPagamentoCB.getValue().split(" - ")[0]));
        recibo.setIdEstadopagamento(Integer.parseInt(estadoPagamentoCB.getValue().split(" - ")[0]));

        try{
            ReciboBLL.createRecibo(recibo);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Recibo criado");
            alert.setHeaderText("Recibo criado com sucesso");
            alert.setContentText("Recibo criado com sucesso");
            alert.showAndWait();
            Parent root = FXMLLoader.load(getClass().getResource("/ipvc/estg/desktop/rececionista/rececionistaMainPage.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) confirmButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("GymMaster - Rececionista");
            stage.show();

        } catch (Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao criar recibo");
            alert.setContentText("Erro ao criar recibo");
            alert.showAndWait();
        }
    }

    @FXML
    public void cancel(ActionEvent event) {
        try {
            URL resourceUrl = getClass().getResource("/ipvc/estg/desktop/rececionista/socioDetails.fxml");
            if (resourceUrl == null) {
                System.err.println("Ficheiro FXML não encontrado.");
                return;
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aviso");
            alert.setHeaderText("Operação cancelada");
            alert.setContentText("Operação cancelada");
            alert.showAndWait();
            FXMLLoader loader = new FXMLLoader(resourceUrl);
            Parent root = loader.load();
            SocioDetailsController detailsController = loader.getController();
            detailsController.initialize(SocioBLL.findSocioById(Integer.parseInt(idSocioLabel.getText())));
            Scene mainPage = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(mainPage);
            stage.setTitle("GymMaster - Rececionista");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
