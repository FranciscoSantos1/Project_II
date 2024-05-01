package ipvc.estg.desktop.instrutor;

import bll.AulaBLL;
import entity.Funcionario;
import entity.Aula;
import ipvc.estg.desktop.Login.SessionData;
import ipvc.estg.desktop.responsavelInstrutores.AulasDetailsController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
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

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;

public class instrutorMainPageController {

    @FXML
    private Button addSocioButton;

    @FXML
    private TableView<Aula> aulasTableView;

    @FXML
    private TableColumn<Aula, Instant> dateColumn;

    @FXML
    private Button deactivateButton;

    @FXML
    private Button detailsButton;

    @FXML
    private Button logoutButton;

    @FXML
    private TableColumn<Aula, String> modalidadeColumn;

    @FXML
    private TableColumn<Aula, String> nomeInstrutorColumn;

    @FXML
    private Button ptSessionButton;

    @FXML
    private Button reserveButton;

    @FXML
    private Label nameLabel;

    @FXML
    private TableColumn<Aula, Integer> vagasColumn;

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");


    @FXML
    void initialize() {
        dateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(AulaBLL.getDateByIdAula(cellData.getValue().getId())));
        modalidadeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(AulaBLL.getModalidadeNameByIdAula(cellData.getValue().getId())));
        nomeInstrutorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(AulaBLL.getInstrutorNameByIdAula(cellData.getValue().getId())));
        vagasColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getVagas()).asObject());

        // Get the logged-in instructor
        Funcionario loggedInInstructor = SessionData.getInstance().getCurrentUser();

        nameLabel.setText(loggedInInstructor.getNome());

        List<Aula> aulas = AulaBLL.getAulasByInstructorId(loggedInInstructor.getIdFuncionario());
        ObservableList<Aula> aulasObservableList = FXCollections.observableArrayList(aulas);
        aulasTableView.setItems(aulasObservableList);
    }


    @FXML
    void logout(ActionEvent event){
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
    void addAula(ActionEvent event) {
        try {
            URL resourceUrl = getClass().getResource("/ipvc/estg/desktop/responsavelInstrutores/adicionarAula.fxml");
            if (resourceUrl == null) {
                System.err.println("Ficheiro FXML não encontrado.");
                return;
            }
            Parent root = FXMLLoader.load(resourceUrl);
            Scene mainPage = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(mainPage);
            stage.setTitle("GymMaster - Adicionar Aula");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

/*    @FXML
    void goToDetails(ActionEvent event) {
        Aula selectedAula = aulasTableView.getSelectionModel().getSelectedItem();
        if (selectedAula == null) {
            System.out.println("Nenhuma aula selecionado.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ipvc/estg/desktop/responsavelInstrutores/aulasDetails.fxml"));
            Parent root = loader.load();
            AulasDetailsController detailsController = loader.getController();
            detailsController.setMainPageController(this);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Detalhes da Aula");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


    @FXML
    void eliminateAula(ActionEvent event) {
        Aula selectedAula = aulasTableView.getSelectionModel().getSelectedItem();

        if (selectedAula.getDataHoraComeco().isBefore(Instant.now())) {
            showAlert("Erro", "Não pode eliminar uma aula que já começou.", Alert.AlertType.ERROR);
            return;
        }
        if (selectedAula == null) {
            showAlert("Selecionar Aula", "Por favor, selecione uma aula primeiro.", Alert.AlertType.WARNING);
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Eliminar Aula");
        confirmationAlert.setHeaderText("Tem a certeza que deseja eliminar a aula selecionada?");
        confirmationAlert.setContentText("Esta ação é irreversível!");

        confirmationAlert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);


        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                AulaBLL.deleteAula(selectedAula);
                aulasTableView.getItems().remove(selectedAula);
                showAlert("Aula Eliminada", "A aula foi eliminada com sucesso.", Alert.AlertType.INFORMATION);

            }
        });

    }

    public Aula getSelectedAula(){
        return aulasTableView.getSelectionModel().getSelectedItem();
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}

