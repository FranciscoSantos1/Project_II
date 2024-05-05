package ipvc.estg.desktop.rececionista;

import bll.AulaBLL;
import bll.SocioBLL;
import entity.Aula;
import entity.Plano;
import entity.Socio;
import ipvc.estg.desktop.Login.SessionData;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.List;
import java.util.Optional;

public class RececionistaMainPageController {
    @FXML
    private Button addSocioButton;

    @FXML
    private Button detailsButton;

    @FXML
    private Button deactivateButton;

    @FXML
    private Button paymentButton;

    @FXML
    private Button reserveButton;

    @FXML
    private Button ptSessionButton;

    @FXML
    private Button logoutButton;

    @FXML
    private TableView<Socio> socioTableView;

    @FXML
    private TableColumn<Socio, Integer> idColumn;

    @FXML
    private TableColumn<Socio, String> nomeColumn;

    @FXML
    private TableColumn<Socio, String> contactoColumn;

    @FXML
    private TableColumn<Socio, String> moradaColumn;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdSocio()).asObject());
        nomeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        contactoColumn.setCellValueFactory(cellData -> {
            BigInteger contacto = cellData.getValue().getContacto();
            return new SimpleStringProperty(contacto != null ? contacto.toString() : "");
        });
        moradaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMorada()));

        List<Object[]> results = SocioBLL.listSocio();
        for (Object[] result : results) {
            Socio socio = new Socio();
            socio.setIdSocio((int) result[0]);
            socio.setNome((String) result[1]);
            socio.setContacto((BigInteger) result[2]);
            socio.setMorada((String) result[3]);
            socioTableView.getItems().add(socio);
        }

        socioTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                int idSocio = newSelection.getIdSocio();
                String nome = newSelection.getNome();
                BigInteger contacto = newSelection.getContacto();
                String morada = newSelection.getMorada();

                //get plano by id
                Socio socio = SocioBLL.findSocioById(idSocio);
                int plano = socio.getIdPlano();
                Plano planoObj = SocioBLL.findPlanoById(plano);
            }
        });
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
    void socioDetails(ActionEvent event) {
        Socio selectedSocio = socioTableView.getSelectionModel().getSelectedItem();
        if (selectedSocio == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aviso");
            alert.setHeaderText("Nenhum sócio selecionado.");
            alert.setContentText("Por favor, selecione um sócio.");
            alert.showAndWait();

            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ipvc/estg/desktop/rececionista/socioDetails.fxml"));
            Parent root = loader.load();
            SocioDetailsController socioDetailsController = loader.getController();
            Socio socio = SocioBLL.findSocioById(selectedSocio.getIdSocio());
            System.out.println("Socio selecionado: " + socio.getIdSocio() + " - " + socio.getNome() + " - " + socio.getContacto() + " - " + socio.getMorada() + " - " + socio.getIdPlano());
            socioDetailsController.initialize(socio);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Detalhes do Sócio");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deactivateSocio(ActionEvent event) {
        Socio selectedSocio = socioTableView.getSelectionModel().getSelectedItem();
        if (selectedSocio == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aviso");
            alert.setHeaderText("Nenhum sócio selecionado.");
            alert.setContentText("Por favor, selecione um sócio.");
            alert.showAndWait();

            return;
        }

        SocioBLL.deactivateSocio(selectedSocio.getIdSocio());
        socioTableView.getItems().remove(selectedSocio);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText("Sócio desativado com sucesso.");
        alert.showAndWait();
    }

    @FXML
    void loadAddSocio(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ipvc/estg/desktop/rececionista/addSocio.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Adicionar Sócio");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void reservePTSession(ActionEvent event) {
        Socio selectedSocio = socioTableView.getSelectionModel().getSelectedItem();
        if (selectedSocio == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aviso");
            alert.setHeaderText("Nenhum sócio selecionado.");
            alert.setContentText("Por favor, selecione um sócio.");
            alert.showAndWait();

            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ipvc/estg/desktop/rececionista/reservarAulaIndividual.fxml"));
            Parent root = loader.load();
            ReservarAulaPTController reservarAulaPTController = loader.getController();
            reservarAulaPTController.initialize(selectedSocio);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Reservar Sessão de PT");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void reserveVaga(ActionEvent event) {
        Socio selectedSocio = socioTableView.getSelectionModel().getSelectedItem();
        if (selectedSocio == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aviso");
            alert.setHeaderText("Nenhum sócio selecionado.");
            alert.setContentText("Por favor, selecione um sócio.");
            alert.showAndWait();

            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ipvc/estg/desktop/rececionista/reservarVaga.fxml"));
            Parent root = loader.load();
            ReservarVagaController reservarVagaController = loader.getController();
            reservarVagaController.initialize(selectedSocio);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Reservar Vaga");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
