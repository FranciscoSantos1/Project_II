package ipvc.estg.desktop.responsavelInstrutores;

import bll.AulaBLL;
import bll.ModalidadeBLL;
import entity.Aula;
import entity.Modalidade;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Callback;


import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public class responsavelMainPageController {

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
    private TableColumn<Aula, Integer> totalLugaresColumn;

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");


    @FXML
    void initialize() {
        dateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(AulaBLL.getDateInicioByIdAula(cellData.getValue().getId())));
        modalidadeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(AulaBLL.getModalidadeNameByIdAula(cellData.getValue().getId())));
        nomeInstrutorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(AulaBLL.getInstrutorNameByIdAula(cellData.getValue().getId())));
        totalLugaresColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getTotalLugares()).asObject());

        // Fetch all Aula objects from the database
        List<Aula> aulas = AulaBLL.getAllAulas();
        ObservableList<Aula> aulasObservableList = FXCollections.observableArrayList(aulas);
        for(Aula a: aulas) {
            System.out.println(a.getDataHoraComeco());
        }
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

    @FXML
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
    }


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

