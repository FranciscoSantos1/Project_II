package ipvc.estg.desktop.instrutor;

import bll.AulaBLL;
import bll.LinhaAulaBLL;
import entity.Funcionario;
import entity.Aula;
import entity.LinhaAula;
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
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static bll.AulaBLL.deleteAulaWithLinhaAula;

public class instrutorMainPageController {

    @FXML
    private TableColumn<Aula, String> LocalColumn;

    @FXML
    private Button addSocioButton;

    @FXML
    private TableView<Aula> aulasTableView;

    @FXML
    private TableColumn<Aula, Instant> dateFimColumn1;

    @FXML
    private TableColumn<Aula, Instant> dateInicioColumn;

    @FXML
    private Button deactivateButton;

    @FXML
    private Button detailsButton;

    @FXML
    private Button logoutButton;

    @FXML
    private TableColumn<Aula, String> modalidadeColumn;

    @FXML
    private Label nameLabel;

    @FXML
    private Label nameLabel1;

    @FXML
    private TableColumn<Aula, String> tipoAulaColumn;

    @FXML
    private TableColumn<Aula, String> SocioColumn1;


    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");


    @FXML
    void initialize() {
        dateInicioColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(AulaBLL.getDateInicioByIdAula(cellData.getValue().getId())));
        dateFimColumn1.setCellValueFactory(cellData -> new SimpleObjectProperty<>(AulaBLL.getDateFimByIdAula(cellData.getValue().getId())));
        modalidadeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(AulaBLL.getModalidadeNameByIdAula(cellData.getValue().getId())));
        LocalColumn.setCellValueFactory(cellData -> new SimpleStringProperty(AulaBLL.getLocalByIdAula(cellData.getValue().getId())));
        tipoAulaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(AulaBLL.getTipoAulaByIdAula(cellData.getValue().getId())));
        SocioColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(AulaBLL.getSocioNameByIdAula(cellData.getValue().getId())));
        // Get the logged-in instructor
        Funcionario loggedInInstructor = SessionData.getInstance().getCurrentUser();

        nameLabel.setText(loggedInInstructor.getNome());

        List<Aula> aulas = AulaBLL.getAulasByInstructorId(loggedInInstructor.getIdFuncionario());
        ObservableList<Aula> aulasObservableList = FXCollections.observableArrayList(aulas);
        aulasTableView.setItems(aulasObservableList);
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
    void addAula(ActionEvent event) {
        try {
            URL resourceUrl = getClass().getResource("/ipvc/estg/desktop/instrutor/adicionarAulaIndividual.fxml");
            if (resourceUrl == null) {
                System.err.println("Ficheiro FXML não encontrado.");
                return;
            }
            Parent root = FXMLLoader.load(resourceUrl);
            Scene mainPage = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(mainPage);
            stage.setTitle("GymMaster - Adicionar Aula Individual");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @FXML
    void goToDetails(ActionEvent event) {
        Aula selectedAula = aulasTableView.getSelectionModel().getSelectedItem();
        System.out.println("Selected aula: " + selectedAula);
        if (selectedAula == null) {
            showAlert("Selecionar Aula", "Por favor, selecione uma aula primeiro.", Alert.AlertType.WARNING);
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ipvc/estg/desktop/instrutor/instrutorAulasDetails.fxml"));
            Parent root = loader.load();
            InstrutorAulasDetailsController detailsController = loader.getController();
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


    @FXML
    void eliminateAula(ActionEvent event) {
        Aula selectedAula = aulasTableView.getSelectionModel().getSelectedItem();
        if (selectedAula == null) {
            showAlert("Selecionar Aula", "Por favor, selecione uma aula primeiro.", Alert.AlertType.WARNING);
            return;
        }

        if (selectedAula.getDataHoraComeco().isBefore(Instant.now())) {
            showAlert("Erro", "Não pode eliminar uma aula que já começou.", Alert.AlertType.ERROR);
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Eliminar Aula");
        confirmationAlert.setHeaderText("Tem a certeza que deseja eliminar a aula selecionada?");
        confirmationAlert.setContentText("Esta ação é irreversível!");
        confirmationAlert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    deleteAulaWithLinhaAula(selectedAula.getId());
/*                    AulaBLL.deleteAula(selectedAula);
                    LinhaAulaBLL.deleteAllLinhasAulaByAulaId(selectedAula.getId());*/
                    aulasTableView.getItems().remove(selectedAula);
                    showAlert("Aula Eliminada", "A aula foi eliminada com sucesso.", Alert.AlertType.INFORMATION);
                } catch (Exception e) {
                    showAlert("Erro", "Falha ao eliminar a aula: " + e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        });
    }

}

