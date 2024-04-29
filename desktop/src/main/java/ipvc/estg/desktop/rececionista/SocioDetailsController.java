package ipvc.estg.desktop.rececionista;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import ipvc.estg.desktop.rececionista.RececionistaMainPageController;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;


public class SocioDetailsController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label name1Label;

    @FXML
    private Label contactLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label idLabel;

    @FXML
    private Label planLabel;

    @FXML
    private Button editButton;

    @FXML
    private Button changeButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button backButton;

    private RececionistaMainPageController mainPageController;

    public void initSocioDetails(int idSocio, String nome, BigInteger contacto, String morada, Integer plano) {
        nameLabel.setText("" + nome);
        name1Label.setText("" + nome);
        contactLabel.setText("" + contacto);
        //todo
        //addressLabel.setText("" + morada);
        idLabel.setText("" + idSocio);
        planLabel.setText("" + plano);

    }

    // Setter para o controlador da página principal
    public void setMainPageController(RececionistaMainPageController mainPageController) {
        this.mainPageController = mainPageController;
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


}
