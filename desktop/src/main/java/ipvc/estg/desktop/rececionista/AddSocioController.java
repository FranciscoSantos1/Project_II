package ipvc.estg.desktop.rececionista;

import bll.PlanoBLL;
import bll.SocioBLL;
import entity.Plano;
import entity.Socio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class AddSocioController {
    @FXML
    private Button logoutButton;

    @FXML
    private Button backButton;

    @FXML
    private Button addSocioButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField contactTextField;

    @FXML
    private TextField codPostalTextField;

    @FXML
    private TextField streetTextField;

    @FXML
    private TextField doorNumberTextField;

    @FXML
    private ComboBox<String> planoComboBox;

    @FXML
    void initialize() {
        nameTextField.setEditable(true);
        contactTextField.setEditable(true);
        codPostalTextField.setEditable(true);
        streetTextField.setEditable(true);
        doorNumberTextField.setEditable(true);

        List<Plano> planos = PlanoBLL.getAllPlanos();
        for (Plano plano : planos) {
            String descricaoPlano = "Plano " + plano.getIdPlano() + " - " + plano.getTipo() + " - " + plano.getDescricao() + " - " + plano.getValor() + "€/mês";
            planoComboBox.getItems().add(descricaoPlano);
        }
    }

    @FXML
    void addSocio() {
        String name = nameTextField.getText();
        String contact = contactTextField.getText();
        String codPostal = codPostalTextField.getText();
        String street = streetTextField.getText();
        String doorNumber = doorNumberTextField.getText();

        if (name.isEmpty() || contact.isEmpty() || codPostal.isEmpty() || street.isEmpty() || doorNumber.isEmpty()) {
            System.out.println("Preencha todos os campos.");
            return;
        }

        SocioBLL.createSocio(new Socio());
    }

    @FXML
    public void back(ActionEvent event) {
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

}
