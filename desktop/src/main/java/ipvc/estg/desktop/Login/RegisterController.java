package ipvc.estg.desktop.Login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import bll.FuncionarioBLL;
import entity.Funcionario;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class RegisterController {

    @FXML
    private TextField CodigoPostalField;
    @FXML
    private TextField DoorNumberField;
    @FXML
    private TextField NIFField;
    @FXML
    private ChoiceBox<String> UserTypeChoiceBox; // Ensure this is populated with the correct types
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
    private Button registerButton2;

    private FuncionarioBLL funcionarioBLL = new FuncionarioBLL();

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
        UserTypeChoiceBox.getItems().addAll("Admin", "Instrutor", "Rececionista", "ResponsavelInstrutores");
        String email = emailField.getText();
        String password = passwordField.getText();
        String fullName = fullNameField.getText();
        String userType = UserTypeChoiceBox.getValue();
        String NIF = NIFField.getText();
        String phoneNumber = phoneNumberField.getText();
        String codigoPostal = CodigoPostalField.getText();
        String address = addressField.getText();
        String doorNumber = DoorNumberField.getText();

        if (!validateInput(email, password, fullName, userType, NIF, phoneNumber, codigoPostal, address, doorNumber)) {

            return;
        }

        try {
            String hashedPassword = hashPassword(password);
            Funcionario newFuncionario = createFuncionario(email, hashedPassword, fullName, userType, NIF, phoneNumber, codigoPostal, address, doorNumber);
            funcionarioBLL.createFuncionario(newFuncionario);
            // Registration success, show confirmation and clear form or go back to login page

        } catch (Exception e) {
            // Handle exception, log it, and show an error message
        }
    }

    private boolean validateInput(String... inputs) {
        for (String input : inputs) {
            if (input == null || input.trim().isEmpty()) {
                return false;
            }
        }
        // Add more complex validation as necessary
        return true;
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        return password; // Replace this with actual password hashing
    }

    private Funcionario createFuncionario(String email, String hashedPassword, String fullName, String userType, String NIF, String phoneNumber, String codigoPostal, String address, String doorNumber) {
        // Create and return a new Funcionario with the given details
        // The details need to be set according to your entity class' design
        Funcionario funcionario = new Funcionario();
        // Set the properties on funcionario here
        return funcionario;
    }
}
