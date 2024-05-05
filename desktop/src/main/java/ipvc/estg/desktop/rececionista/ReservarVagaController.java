package ipvc.estg.desktop.rececionista;

import bll.AulaBLL;
import bll.LinhaAulaBLL;
import bll.SocioBLL;
import entity.Aula;
import entity.LinhaAula;
import entity.Socio;
import ipvc.estg.desktop.Login.SessionData;
import javafx.beans.property.SimpleIntegerProperty;
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
import java.util.List;

public class ReservarVagaController {

    @FXML
    private TableColumn<Aula, String> nomeColumn;

    @FXML
    private TableColumn<Aula, String> modalidadeColumn;

    @FXML
    private TableColumn<Aula, String> datahoraColumn;

    @FXML
    private TableColumn<Aula, String> vagasColumn;

    @FXML
    private TableColumn<Aula, String> localColumn;

    @FXML
    private TableColumn<Aula, String> duracaoColumn;

    @FXML
    private TableColumn<Aula, Integer> idColumn;

    @FXML
    private TableView<Aula> aulasTableView;

    @FXML
    private Label nameLabel;


    void initialize(Socio socio) {
        nameLabel.setText(socio.getIdSocio() + " - " + socio.getNome());
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nomeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        modalidadeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(AulaBLL.getModalidadeNameByIdAula(cellData.getValue().getId())));
        datahoraColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDataHoraComeco().toString()));
        vagasColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVagas().toString()));
        localColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocalAula()));
        duracaoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDuracao().toString()));

        List<Aula> aulas = AulaBLL.getAllAulasGrupoSocio(socio.getIdSocio());
        ObservableList<Aula> aulasObservableList = FXCollections.observableArrayList(aulas);
        for(Aula a: aulas) {
            System.out.println(a.getDataHoraComeco());
        }
        aulasTableView.setItems(aulasObservableList);

        aulasTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                int id = newSelection.getId();
                Aula aula = AulaBLL.getAulaById(id);
            }
        });
    }

    @FXML
    void logout(ActionEvent event) {
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
    void back(ActionEvent event) {
        try {
            URL resourceUrl = getClass().getResource("/ipvc/estg/desktop/rececionista/rececionistaMainPage.fxml");
            if (resourceUrl == null) {
                System.err.println("Ficheiro FXML não encontrado.");
                return;
            }
            Parent root = FXMLLoader.load(resourceUrl);
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
    void reservarVaga(ActionEvent event) {
        int idAula = aulasTableView.getSelectionModel().getSelectedItem().getId();
        int idSocio = Integer.parseInt(nameLabel.getText().split(" - ")[0]);

        if (AulaBLL.checkIfSocioIsInAula(idSocio, idAula)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Já está inscrito nesta aula.");
            alert.showAndWait();
            return;
        }

        if (aulasTableView.getSelectionModel().getSelectedItem().getVagas() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erro");
            alert.setHeaderText("Não há vagas disponíveis.");
            alert.showAndWait();
            return;
        }

        try {
            LinhaAula linhaAula = new LinhaAula();
            linhaAula.setIdAula(idAula);
            linhaAula.setIdSocio(SocioBLL.findSocioById(idSocio).getIdSocio());
            linhaAula.setIdFuncionario(SessionData.getInstance().getCurrentUser().getIdFuncionario());
            LinhaAulaBLL.createLinhaAula(linhaAula);

            AulaBLL.RemoverVagaAula(idAula);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Vaga reservada com sucesso.");
            alert.showAndWait();

            URL resourceUrl = getClass().getResource("/ipvc/estg/desktop/rececionista/rececionistaMainPage.fxml");
            if (resourceUrl == null) {
                System.err.println("Ficheiro FXML não encontrado.");
                return;
            }
            Parent root = FXMLLoader.load(resourceUrl);
            Scene mainPage = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(mainPage);
            stage.setTitle("GymMaster - Reservar Vaga");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao reservar vaga.");
            alert.showAndWait();
        }
    }
}
