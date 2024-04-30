package ipvc.estg.desktop.rececionista;

import bll.SocioBLL;
import entity.Plano;
import entity.Socio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;


public class SocioDetailsController {

    @FXML
    private Label nameLabel;

    @FXML
    private Button editButton;

    @FXML
    private Button changeButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button backButton;

    @FXML
    private Button saveButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField contactTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField planTextField;

    @FXML
    private TextField idTextField;

    private boolean editing = false;

    private RececionistaMainPageController mainPageController;

    public void initialize(){
        nameTextField.setEditable(false);
        contactTextField.setEditable(false);
        addressTextField.setEditable(false);
        planTextField.setEditable(false);
        saveButton.setVisible(false);

    }

    public void initSocioDetails(int idSocio, String nome, BigInteger contacto, String morada) {
        nameLabel.setText(nome);
        nameTextField.setText(nome);
        contactTextField.setText(contacto.toString());
        addressTextField.setText(morada);
        idTextField.setText(String.valueOf(idSocio));

        //get plano do socio
        Socio socio = SocioBLL.findSocioById(idSocio);
        int plano = socio.getIdPlano();
        Plano planoObj = SocioBLL.findPlanoById(plano);
        //System.out.println("Plano: " + planoObj.getTipo() + " - " + planoObj.getDescricao() + " - " + planoObj.getValor() + "€");

        planTextField.setText(planoObj.getTipo() + " - " + planoObj.getDescricao() + " - " + planoObj.getValor() + "€/mês");
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
    void back(ActionEvent event) {
        try {
            URL resourceUrl = getClass().getResource("/ipvc/estg/desktop/rececionista/rececionistaMainPage.fxml");
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
    public void editDetails() {
        editButton.setVisible(false);
        saveButton.setVisible(true);
        if (!editing) {
            nameTextField.setEditable(true);
            contactTextField.setEditable(true);

            editing = true;
        } else {
            // Save changes
            Socio socio = new Socio();
            socio.setIdSocio(Integer.parseInt(idTextField.getText()));
            socio.setNome(nameTextField.getText());
            socio.setContacto(new BigInteger(contactTextField.getText()));

            //todo: update morada e plano

            SocioBLL.updateSocio(socio);

            nameTextField.setEditable(false);
            contactTextField.setEditable(false);

            alertBox("Sócio atualizado com sucesso!", "Sucesso");

            editing = false;

            editButton.setVisible(true);
            saveButton.setVisible(false);
        }
    }

    private void alertBox(String s, String sucesso) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(sucesso);
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }
}
