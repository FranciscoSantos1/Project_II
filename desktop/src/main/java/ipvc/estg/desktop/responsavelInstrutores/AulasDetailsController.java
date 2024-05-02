package ipvc.estg.desktop.responsavelInstrutores;

import bll.ModalidadeBLL;
import bll.FuncionarioBLL;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import entity.Aula;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class AulasDetailsController {

    @FXML private TextField ModalidadeField;
    @FXML private Button backButton;
    @FXML private TextField dataFimField;
    @FXML private TextField dataInicioField;
    @FXML private TextField funcionarioTextField;
    @FXML private TextField idTextField;
    @FXML private TextField localField;
    @FXML private Button logoutButton;
    @FXML private Label nameLabel;
    @FXML private TextField nameTextField;
    @FXML private TextField numeroMinimoField;
    @FXML private TextField vagasTextField;
    @FXML private TextField totalLugaresTextField1;

    private boolean editing = false;

    private responsavelMainPageController MainPageController;

    public void initialize() {
        idTextField.setEditable(false);
        nameTextField.setEditable(false);
        localField.setEditable(false);
        dataInicioField.setEditable(false);
        dataFimField.setEditable(false);
        numeroMinimoField.setEditable(false);
        vagasTextField.setEditable(false);
        funcionarioTextField.setEditable(false);
        ModalidadeField.setEditable(false);
        totalLugaresTextField1.setEditable(false);
    }

    public void setMainPageController(responsavelMainPageController mainPageController) {
        this.MainPageController = mainPageController;
        loadData();
    }

    private void loadData() {
        Aula selectedAula = MainPageController.getSelectedAula();
        if (selectedAula != null) {
            idTextField.setText(String.valueOf(selectedAula.getId()));
            nameTextField.setText(selectedAula.getNome());
            localField.setText(selectedAula.getLocalAula());
            dataInicioField.setText(selectedAula.getDataHoraComeco().toString());
            dataFimField.setText(selectedAula.getDataHoraFim().toString());
            numeroMinimoField.setText(String.valueOf(selectedAula.getNumMinAtletas()));
            vagasTextField.setText(String.valueOf(selectedAula.getVagas()));
            totalLugaresTextField1.setText(String.valueOf(selectedAula.getTotalLugares()));

            String modalidadeName = ModalidadeBLL.getModalidadeNameById(selectedAula.getIdModalidade());
            String funcionarioName = FuncionarioBLL.getFuncionarioNameById(selectedAula.getIdFuncionario());

            ModalidadeField.setText(modalidadeName);
            funcionarioTextField.setText(funcionarioName);
        }
    }


    @FXML
    void logout(ActionEvent event) throws IOException {
        changeScene(event, "/ipvc/estg/desktop/Login/login.fxml", "GymMaster - Login");
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        changeScene(event, "/ipvc/estg/desktop/responsavelInstrutores/responsavelMainPage.fxml", "GymMaster - Responsável Instrutores");
    }

    private void changeScene(ActionEvent event, String fxmlFile, String title) throws IOException {
        URL resourceUrl = getClass().getResource(fxmlFile);
        if (resourceUrl == null) {
            System.err.println("Ficheiro FXML não encontrado.");
            return;
        }
        Parent root = FXMLLoader.load(resourceUrl);
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }
}
