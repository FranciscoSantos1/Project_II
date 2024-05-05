package ipvc.estg.desktop.rececionista;

import bll.PlanoBLL;
import bll.SocioBLL;
import entity.Plano;
import entity.Socio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.List;

public class AddSocioController {
    @FXML
    private Button logoutButton;

    @FXML
    private Button backButton;

    @FXML
    private Button addSocioButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField codPostalTextField;

    @FXML
    private TextField streetTextField;

    @FXML
    private TextField doorNumberTextField;

    @FXML
    private ComboBox<String> planoComboBox;

    @FXML
    void initialize() {
        nameTextField.setEditable(true);
        phoneNumberTextField.setEditable(true);
        codPostalTextField.setEditable(true);
        streetTextField.setEditable(true);
        doorNumberTextField.setEditable(true);

        List<Plano> planos = PlanoBLL.getAllPlanos();
        for (Plano plano : planos) {
            String descricaoPlano = "Plano " + plano.getIdPlano() + " - " + plano.getTipo() + " - " + plano.getDescricao() + " - " + plano.getValor() + "€/mês";
            planoComboBox.getItems().add(descricaoPlano);
        }
    }

    @FXML
    void addSocio() {
        String name = nameTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        String codPostal = codPostalTextField.getText();
        String street = streetTextField.getText();
        String doorNumber = doorNumberTextField.getText();

        if (name.isEmpty() || phoneNumber.isEmpty() || codPostal.isEmpty() || street.isEmpty() || doorNumber.isEmpty()) {
            System.out.println("Preencha todos os campos.");
            return;
        }

        // Verifica se o sócio já existe no banco de dados
        Socio socio = SocioBLL.findSocioByName(name);
        if (socio == null) {
            socio = new Socio();
            socio.setIdSocio(SocioBLL.getNextSocioId());
            socio.setNome(name);
            socio.setContacto(new BigInteger(phoneNumber));
            socio.setCodPostal(codPostal);
            socio.setnPorta(doorNumber);
            socio.setRua(street);
            socio.setAtivo(true);

            // Get plano id
            String plano = planoComboBox.getSelectionModel().getSelectedItem();
            Plano selectedPlano = PlanoBLL.findPlanoById(extractIdPlano(plano));

            if (selectedPlano == null) {
                System.out.println("Plano não encontrado.");
                return;
            }
            socio.setIdPlano(selectedPlano.getIdPlano());
        } else {
            System.out.println("Sócio já existe.");
            return;
        }
        //System.out.println(socio.getIdSocio() + " " + socio.getNome() + " " + socio.getContacto() + " " + socio.getCodPostal() + " " + socio.getRua() + " " + socio.getnPorta() + " " + socio.getIdPlano());
        try{
            SocioBLL.createSocio(socio);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText(null);
            alert.setContentText("Sócio criado com sucesso.");
            alert.showAndWait();

            Parent root = FXMLLoader.load(getClass().getResource("/ipvc/estg/desktop/rececionista/rececionistaMainPage.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) addSocioButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("GymMaster - Rececionista");
            stage.show();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Erro ao criar sócio.");
            alert.showAndWait();
        }
    }



    private int extractIdPlano(String descricaoPlano) {
        String[] partes = descricaoPlano.split(" - ");
        return Integer.parseInt(partes[0].split(" ")[1]);
    }

    @FXML
    public void back(ActionEvent event) {
        try {
            URL resourceUrl = getClass().getResource("/ipvc/estg/desktop/rececionista/rececionistaMainPage.fxml");
            if (resourceUrl == null) {
                System.err.println("Ficheiro FXML não encontrado.");
                return;
            }
            FXMLLoader loader = new FXMLLoader(resourceUrl);
            Parent root = loader.load();
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

}
