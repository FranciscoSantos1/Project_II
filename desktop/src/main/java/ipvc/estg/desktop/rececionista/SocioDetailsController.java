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
import javafx.scene.control.*;

import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.List;


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

    @FXML
    private ComboBox<String> planoComboBox;

    private boolean editing = false;

    private RececionistaMainPageController mainPageController;

    public void initialize(){
        nameTextField.setEditable(false);
        contactTextField.setEditable(false);
        addressTextField.setEditable(false);
        planTextField.setEditable(false);
        saveButton.setVisible(false);
        planoComboBox.setVisible(false);
    }

    public void initSocioDetails(int idSocio, String morada) {
        // Fetch the updated Socio from the database
        Socio updatedSocio = SocioBLL.findSocioById(idSocio);

        nameLabel.setText(updatedSocio.getNome());
        nameTextField.setText(updatedSocio.getNome());
        contactTextField.setText(updatedSocio.getContacto().toString());
        addressTextField.setText(morada);
        idTextField.setText(String.valueOf(updatedSocio.getIdSocio()));
        Plano updatedPlano = PlanoBLL.findPlanoById(updatedSocio.getIdPlano());
        planTextField.setText("Plano " + updatedPlano.getIdPlano() + " - " + updatedPlano.getTipo() + " - " + updatedPlano.getDescricao() + " - " + updatedPlano.getValor() + "€/mês");

        // Clear the planoComboBox items
        planoComboBox.getItems().clear();

        List<Plano> planos = PlanoBLL.getAllPlanos();
        for (Plano plano : planos) {
            String descricaoPlano = "Plano " + plano.getIdPlano() + " - " + plano.getTipo() + " - " + plano.getDescricao() + " - " + plano.getValor() + "€/mês";
            planoComboBox.getItems().add(descricaoPlano);
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
    public void editDetails() {
        saveButton.setVisible(true);
        if (!editing) {
            nameTextField.setEditable(true);
            contactTextField.setEditable(true);
            planoComboBox.setVisible(true);
            planoComboBox.setDisable(false);

            editing = true;
        } else {
            Socio socio = new Socio();
            String selectedPlano = planoComboBox.getSelectionModel().getSelectedItem();
            Plano plano = PlanoBLL.findPlanoById(extractIdPlano(selectedPlano));

            if (plano == null) {
                alertBox("Selecione um plano válido antes de salvar!", "Erro");
                return;
            }

            socio.setIdSocio(Integer.parseInt(idTextField.getText()));
            socio.setNome(nameTextField.getText());
            socio.setContacto(new BigInteger(contactTextField.getText()));
            socio.setIdPlano(plano.getIdPlano());

            if (planoComboBox.getSelectionModel().isEmpty()) {
                alertBox("Selecione um plano antes de salvar!", "Erro");
                return;
            }

            SocioBLL.updateSocio(socio);

            nameTextField.setEditable(false);
            contactTextField.setEditable(false);
            planoComboBox.setDisable(true);
            planoComboBox.setVisible(false);

            editing = false;

            alertBox("Sócio atualizado com sucesso!", "Sucesso");
            saveButton.setVisible(false);
        }
    }

    private int extractIdPlano(String descricaoPlano) {
        String[] partes = descricaoPlano.split(" - ");
        return Integer.parseInt(partes[0].split(" ")[1]);
    }

    private void alertBox(String s, String sucesso) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(sucesso);
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }
}
