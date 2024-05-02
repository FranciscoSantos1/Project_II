package ipvc.estg.desktop.instrutor;

import bll.AulaBLL;
import bll.FuncionarioBLL;
import bll.LinhaAulaBLL;
import bll.ModalidadeBLL;
import entity.Aula;
import entity.Socio;
import ipvc.estg.desktop.Login.SessionData;
import ipvc.estg.desktop.responsavelInstrutores.responsavelMainPageController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

public class InstrutorAulasDetailsController {

    @FXML private TableColumn<Socio, String> SocioNomeColumn;
    @FXML private TextField TotalLudaresField1;
    @FXML private Button backButton;
    @FXML private TextField dataFimField;
    @FXML private TextField dataInicioField;
    @FXML private TextField idTextField;
    @FXML private TextField vagasTextField;
    @FXML private Button logoutButton;
    @FXML private Label nameLabel;
    @FXML private TextField nameTextField;
    @FXML private Button removeButton;
    @FXML private TableView<Socio> sociosTableView;
    @FXML private TableColumn<Socio, String> SocioIDColumn1;


    private boolean editing = false;

    private instrutorMainPageController MainPageController;

    public void initialize() {
        idTextField.setEditable(false);
        nameTextField.setEditable(false);
        dataInicioField.setEditable(false);
        dataFimField.setEditable(false);
        TotalLudaresField1.setEditable(false);
        vagasTextField.setEditable(false);


    }

    public void setMainPageController(instrutorMainPageController mainPageController) {
        this.MainPageController = mainPageController;
        loadData();
    }

    private void loadData() {
        Aula selectedAula = MainPageController.getSelectedAula();
        if (selectedAula != null) {
            idTextField.setText(String.valueOf(selectedAula.getId()));
            nameTextField.setText(selectedAula.getNome());
            dataInicioField.setText(selectedAula.getDataHoraComeco().toString());
            dataFimField.setText(selectedAula.getDataHoraFim().toString());
            vagasTextField.setText(String.valueOf(selectedAula.getVagas()));
            TotalLudaresField1.setText(String.valueOf(selectedAula.getTotalLugares()));

            List<Socio> socios = AulaBLL.getAllSociosFromAula(selectedAula.getId());
            ObservableList<Socio> sociosObservableList = FXCollections.observableArrayList(socios);



            SocioNomeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
            SocioIDColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getIdSocio())));

            sociosTableView.setItems(sociosObservableList);


        }
    }

    @FXML
    void removeSocio(ActionEvent event) {
    Socio selectedSocio = getSelectedSocio();
    if (selectedSocio == null) {
        showAlert("Selecionar Socio", "Por favor, selecione um socio primeiro.", Alert.AlertType.WARNING);
        return;
    }

    Aula selectedAula = MainPageController.getSelectedAula();
    if (selectedAula == null) {
        showAlert("Selecionar Aula", "Por favor, selecione uma aula primeiro.", Alert.AlertType.WARNING);
        return;
    }

    List<Socio> socios = AulaBLL.getAllSociosFromAula(selectedAula.getId());
    if (socios.size() <= 1) {
        showAlert("Erro", "Não pode remover o último socio de uma aula.", Alert.AlertType.ERROR);
        return;
    }

    // Create a confirmation dialog
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Remover Socio");
    alert.setHeaderText("Tem a certeza que deseja remover o socio selecionado?");
    alert.setContentText("Esta ação é irreversível!");

    // Show the dialog and wait for user response
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
        // If OK button is pressed, remove the socio
        try {
            LinhaAulaBLL.removeSocioFromAula(selectedAula.getId(), selectedSocio.getIdSocio());
            // Refresh the table view
            loadData();
        } catch (Exception e) {
            showAlert("Erro", "Falha ao remover o socio: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    @FXML
    void logout(ActionEvent event) throws IOException{
        SessionData.getInstance().setCurrentUser(null);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Tem a certeza que quer fechar a aplicação?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            changeScene(event, "/ipvc/estg/desktop/Login/login.fxml", "GymMaster - Login");

        }
    }


    @FXML
    void back(ActionEvent event) throws IOException {
        changeScene(event, "/ipvc/estg/desktop/instrutor/instrutorMainPage.fxml", "GymMaster - Instrutor");
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

    public Socio getSelectedSocio(){
        return sociosTableView.getSelectionModel().getSelectedItem();
    }
}
