package ipvc.estg.desktop.responsavelInstrutores;

import bll.AulaBLL;
import bll.ModalidadeBLL;
import entity.Aula;
import entity.Modalidade;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class responsavelMainPageController {

    @FXML
    private Button addSocioButton;

    @FXML
    private TableView<Aula> aulasTableView;

    @FXML
    private TableColumn<Aula, Date> dateColumn;

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
    private TableColumn<Aula, Integer> vagasColumn;

    @FXML
    void initialize() {
        // Setup the columns to display the data
        dateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(convertInstantToDate(cellData.getValue().getDataHoraComeco())));
        modalidadeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(AulaBLL.getModalidadeNameByIdAula(cellData.getValue().getId())));
        nomeInstrutorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(AulaBLL.getInstrutorNameByIdAula(cellData.getValue().getId())));
        vagasColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getVagas()).asObject());

        // Fetch all Aula objects from the database
        List<Aula> aulas = AulaBLL.getAllAulas();
        ObservableList<Aula> aulasObservableList = FXCollections.observableArrayList(aulas);
        aulasTableView.setItems(aulasObservableList);
    }

    private Date convertInstantToDate(Instant instant) {
        return Date.from(instant);
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
}

