package ipvc.estg.desktop.rececionista;

import bll.PlanoBLL;
import bll.SocioBLL;
import entity.Plano;
import entity.Socio;
import ipvc.estg.desktop.Login.SessionData;
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
import java.util.Optional;


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
    private Button paymentButton;

    @FXML
    private Button saveButton;

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
    private TextField planTextField;

    @FXML
    private TextField idTextField;

    @FXML
    private ComboBox<String> planoComboBox;

    private boolean editing = false;

    //TODO: Get updated socio details
    public void initialize(Socio socio) {
        nameTextField.setEditable(false);
        contactTextField.setEditable(false);
        codPostalTextField.setEditable(false);
        streetTextField.setEditable(false);
        doorNumberTextField.setEditable(false);
        planTextField.setEditable(false);
        saveButton.setVisible(false);
        planoComboBox.setVisible(false);

        Socio socio1 = SocioBLL.findSocioById(socio.getIdSocio());
        System.out.println("ID Sócio: " + socio.getIdSocio() + " - " + socio.getIdPlano() + " - " + socio.getNome());
        System.out.println("ID Sócio1: " + socio1.getIdSocio() + " - " + socio1.getIdPlano() + " - " + socio1.getNome());

        nameLabel.setText(socio.getNome());
        nameTextField.setText(socio.getNome());
        contactTextField.setText(socio.getContacto().toString());
        codPostalTextField.setText(socio.getCodPostal());
        streetTextField.setText(socio.getRua());
        doorNumberTextField.setText(socio.getnPorta());
        idTextField.setText(String.valueOf(socio.getIdSocio()));
        Plano updatedPlano = PlanoBLL.findPlanoById(socio.getIdPlano());
        planTextField.setText("Plano " + updatedPlano.getIdPlano() + " - " + updatedPlano.getTipo() + " - " + updatedPlano.getDescricao() + " - " + updatedPlano.getValor() + "€/mês");

        // Clear the planoComboBox items
        //planoComboBox.getItems().clear();

        List<Plano> planos = PlanoBLL.getAllPlanos();
        for (Plano plano : planos) {
            String descricaoPlano = "Plano " + plano.getIdPlano() + " - " + plano.getTipo() + " - " + plano.getDescricao() + " - " + plano.getValor() + "€/mês";
            planoComboBox.getItems().add(descricaoPlano);
        }
    }


    @FXML
    void logout(ActionEvent event){
        SessionData.getInstance().setCurrentUser(null);

        SessionData.getInstance().setCurrentUser(null);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Tem a certeza que quer sair a aplicação?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
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
            codPostalTextField.setEditable(true);
            streetTextField.setEditable(true);
            doorNumberTextField.setEditable(true);

            editing = true;
        } else {
            Socio socio = new Socio();
            String selectedPlano = planoComboBox.getSelectionModel().getSelectedItem();
            Plano plano = PlanoBLL.findPlanoById(extractIdPlano(selectedPlano));
            System.out.println("Plano selecionado: " + plano.getIdPlano() + " - " + plano.getTipo() + " - " + plano.getDescricao() + " - " + plano.getValor() + "€/mês");

            if (plano == null) {
                alertBox("Selecione um plano válido antes de salvar!", "Erro");
                return;
            }

            socio.setIdSocio(Integer.parseInt(idTextField.getText()));
            socio.setNome(nameTextField.getText());
            socio.setContacto(new BigInteger(contactTextField.getText()));
            socio.setIdPlano(plano.getIdPlano());
            socio.setCodPostal(codPostalTextField.getText());
            socio.setRua(streetTextField.getText());
            socio.setnPorta(doorNumberTextField.getText());

            if (planoComboBox.getSelectionModel().isEmpty()) {
                alertBox("Selecione um plano antes de salvar!", "Erro");
                return;
            }

            try {
                SocioBLL.updateSocio(socio);
                nameTextField.setEditable(false);
                contactTextField.setEditable(false);
                planoComboBox.setDisable(true);
                planoComboBox.setVisible(false);
                codPostalTextField.setEditable(false);
                streetTextField.setEditable(false);
                doorNumberTextField.setEditable(false);
                editing = false;

                alertBox("Sócio atualizado com sucesso!", "Sucesso");
                saveButton.setVisible(false);

                Parent root = FXMLLoader.load(getClass().getResource("/ipvc/estg/desktop/rececionista/rececionistaMainPage.fxml"));
                Scene mainPage = new Scene(root);
                Stage stage = (Stage) saveButton.getScene().getWindow();
                stage.setScene(mainPage);
                stage.setTitle("GymMaster - Rececionista");
                stage.show();
            } catch (Exception e) {
                alertBox("Erro ao atualizar sócio!", "Erro");
                return;
            }
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

    @FXML
    void payment(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ipvc/estg/desktop/rececionista/payment.fxml"));
            Parent root = loader.load();

            Socio socio = SocioBLL.findSocioById(Integer.parseInt(idTextField.getText()));
            PaymentController paymentController = loader.getController();
            paymentController.initSocioDetails(socio);
            System.out.println("ID Sócio: " + socio.getIdSocio());

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Pagamento");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
