package ipvc.estg.desktop.Login;

import entity.TipoFuncionario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import bll.FuncionarioBLL;
import entity.Funcionario;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class RegisterController{

    @FXML
    private TextField CodigoPostalField;
    @FXML
    private TextField DoorNumberField;

    @FXML
    private ChoiceBox<String> UserTypeChoiceBox;
    @FXML
    private TextField addressField;
    @FXML
    private Button backButton;
    @FXML
    private TextField emailField;
    @FXML
    private TextField fullNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private Button registerButton;

    public void initialize() {
        UserTypeChoiceBox.getItems().addAll("Instrutor", "Responsavel Instrutores", "Rececionista");
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("GymMaster - Login");
        stage.show();
    }

    @FXML
    void register(ActionEvent event) {
        String fullName = fullNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String address = addressField.getText();
        String doorNumber = DoorNumberField.getText();
        String postalCode = CodigoPostalField.getText();
        String phoneNumber = phoneNumberField.getText();
        String userType = UserTypeChoiceBox.getValue();

        if (fieldsAreEmpty(fullName, email, password, address, doorNumber, postalCode, phoneNumber, userType)) {
            showAlert("Erro", "Preencha todos os campos", Alert.AlertType.ERROR);
            return;
        }

        Funcionario funcionario = new Funcionario();

        funcionario.setNome(fullName);
        if(FuncionarioBLL.emailExists(email)){
            showAlert("Erro", "Este email já está a ser utilizado. Por favor, insira outro email.", Alert.AlertType.ERROR);
            return;
        } else {
            funcionario.setEmail(email);
        }
        funcionario.setEmail(email);
        funcionario.setPassword(password);
        funcionario.setIdTipofuncionario(UserTypeChoiceBox.getSelectionModel().getSelectedIndex() + 1);
        funcionario.setRua(address);
        funcionario.setnPorta(doorNumber);
        funcionario.setCodPostal(postalCode);
        funcionario.setContacto(phoneNumber);




        try {
            FuncionarioBLL.createFuncionario(funcionario);
            showAlert("Sucesso", "Registado com sucesso", Alert.AlertType.INFORMATION);

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
            Scene scene = new Scene(root);
            Stage stage = (Stage) registerButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("GymMaster - Login");
            stage.show();

        } catch (Exception e) {
            showAlert("Erro", "Erro ao registar", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean fieldsAreEmpty(String... fields) {
        for (String field : fields) {
            if (field == null || field.isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
