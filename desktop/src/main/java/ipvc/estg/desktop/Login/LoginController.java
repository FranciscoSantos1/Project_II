package ipvc.estg.desktop.Login;

import bll.FuncionarioBLL;
import entity.Funcionario;
import ipvc.estg.desktop.rececionista.PaymentController;
import jakarta.persistence.EntityManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;


public class LoginController {

    @FXML
    private Button loginButton;

    @FXML
    private Button newAccount;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registerButton;

    @FXML
    private TextField usernameField;

    @FXML
    void exit(ActionEvent event) {

    }
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void loginButtonOnAction(ActionEvent event) {
        String mail = usernameField.getText();
        String password = passwordField.getText();

        if (FuncionarioBLL.verifyLogin(mail, password)) {
            Funcionario funcionario = FuncionarioBLL.getFuncionarioByEmail(mail);
            if(funcionario.getIdTipofuncionario() == 3){
                try {
                    URL resourceUrl = getClass().getResource("/ipvc/estg/desktop/rececionista/rececionistaMainPage.fxml");
                    if (resourceUrl == null) {
                        System.err.println("Arquivo FXML não encontrado.");
                        return;
                    }

                    PaymentController.getLoggedFuncionario(funcionario);

                    Parent root = FXMLLoader.load(resourceUrl);
                    Scene mainPage = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(mainPage);
                    stage.setTitle("GymMaster - Página Principal - Rececionista");
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(funcionario.getIdTipofuncionario() == 2){
                try {
                    URL resourceUrl = getClass().getResource("/ipvc/estg/desktop/responsavelInstrutores/responsavelMainPage.fxml");
                    if (resourceUrl == null) {
                        System.err.println("Arquivo FXML não encontrado.");
                        return;
                    }

                    Parent root = FXMLLoader.load(resourceUrl);
                    Scene mainPage = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(mainPage);
                    stage.setTitle("GymMaster - Página Principal - Responsavel Instrutores");
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(funcionario.getIdTipofuncionario() == 1){
                try {
                    SessionData sessionData = SessionData.getInstance();
                    sessionData.setCurrentUser(funcionario);
                    URL resourceUrl = getClass().getResource("/ipvc/estg/desktop/instrutor/instrutorMainPage.fxml");
                    if (resourceUrl == null) {
                        System.err.println("Arquivo FXML não encontrado.");
                        return;
                    }

                    Parent root = FXMLLoader.load(resourceUrl);
                    Scene mainPage = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(mainPage);
                    stage.setTitle("GymMaster - Página Principal - Instrutor");
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                showAlert("Erro", "Tipo de funcionário não reconhecido", Alert.AlertType.ERROR);
                return;
            }
        } else {
            showAlert("Erro", "Credenciais inválidas", Alert.AlertType.ERROR);
            return;
        }
    }



    @FXML
    void register(javafx.event.ActionEvent ActionEvent) {
        try {
            Parent root;
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("register.fxml")));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) ActionEvent.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("GymMaster - Registar");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
