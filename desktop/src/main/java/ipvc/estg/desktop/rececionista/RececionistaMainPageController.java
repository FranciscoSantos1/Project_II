package ipvc.estg.desktop.rececionista;

import bll.SocioBLL;
import entity.Plano;
import entity.Socio;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.List;

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
                System.out.println("Sócio " + idSocio + " - " + nome + " - " + contacto + " - " + morada + " - " + planoObj.getIdPlano() + " - " + planoObj.getTipo() + " - " + planoObj.getDescricao() + " - " + planoObj.getValor() + "€/mês");
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
            SocioDetailsController detailsController = loader.getController();
            detailsController.initSocioDetails(selectedSocio);
            System.out.println(selectedSocio.getIdSocio() + " " + selectedSocio.getNome() + " " + selectedSocio.getContacto() + " " + selectedSocio.getMorada() + " " + selectedSocio.getIdPlano());
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Detalhes do Sócio");
            stage.setOnHidden(e -> refreshSocioTable());
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

    public void refreshSocioTable() {
        socioTableView.getItems().clear();
        List<Object[]> results = SocioBLL.listSocio();
        for (Object[] result : results) {
            Socio socio = new Socio();
            socio.setIdSocio((int) result[0]);
            socio.setNome((String) result[1]);
            socio.setContacto((BigInteger) result[2]);
            socio.setMorada((String) result[3]);
            socioTableView.getItems().add(socio);
        }
    }

}
