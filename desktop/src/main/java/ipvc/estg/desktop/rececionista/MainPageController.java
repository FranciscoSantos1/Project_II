package ipvc.estg.desktop.rececionista;

import bll.SocioBLL;
import database.Database;
import entity.Socio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
import java.math.BigInteger;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public class MainPageController {
    @FXML
    private Button addSocioButton;

    @FXML
    private Button changeButton;

    @FXML
    private Button deactivateButton;

    @FXML
    private Button paymentButton;

    @FXML
    private Button reserveButton;

    @FXML
    private Button ptSessionButton;

    @FXML
    private Button backButton;

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
    }

    @FXML
    void logout(ActionEvent event) {
        try {
            URL resourceUrl = getClass().getResource("/ipvc/estg/desktop/Login/login.fxml");
            if (resourceUrl == null) {
                System.err.println("Arquivo FXML não encontrado.");
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
