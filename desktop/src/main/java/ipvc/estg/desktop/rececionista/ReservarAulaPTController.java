package ipvc.estg.desktop.rececionista;

import bll.AulaBLL;
import bll.FuncionarioBLL;
import bll.LinhaAulaBLL;
import bll.SocioBLL;
import entity.Aula;
import entity.Funcionario;
import entity.LinhaAula;
import entity.Socio;
import ipvc.estg.desktop.Login.SessionData;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReservarAulaPTController {

    @FXML
    private ComboBox<String> DuracaoComboBox;

    @FXML
    private ComboBox<String> HoraComboBox;

    @FXML
    private ComboBox<String> instrutorComboBox;

    @FXML
    private Button addAulaButton;

    @FXML
    private DatePicker dataDatePicker;

    @FXML
    private Button detailsButton;

    @FXML
    private TextField nomeAulaField;

    @FXML
    private Button logoutButton;

    @FXML
    private Label nomeSocioLabel;


    @FXML
    public void initialize(Socio socio) {
        setupListeners();
        populateComboBoxes();
        nomeSocioLabel.setText(socio.getNome());
    }

    private void setupListeners() {
        dataDatePicker.valueProperty().addListener((ChangeListener<LocalDate>) (observable, oldValue, newValue) -> updateAvaiableInstrutores());
        HoraComboBox.valueProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> updateAvaiableInstrutores());
        DuracaoComboBox.valueProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> updateAvaiableInstrutores());
    }

    @FXML
    private void populateComboBoxes() {
        instrutorComboBox.getItems().setAll(FuncionarioBLL.getAllInstrutores().stream().map(Funcionario::getNome).collect(Collectors.toList()));
        HoraComboBox.getItems().setAll(IntStream.range(8, 21).mapToObj(h -> String.format("%02d:00", h)).collect(Collectors.toList()));
        DuracaoComboBox.getItems().setAll(List.of("30m", "1h", "1h30m", "2h"));
    }

    @FXML
    public void updateAvaiableInstrutores() {
        if (dataDatePicker.getValue() != null && HoraComboBox.getValue() != null && DuracaoComboBox.getValue() != null) {
            try {
                LocalTime startTime = LocalTime.parse(HoraComboBox.getValue());
                LocalTime durationTime = parseDurationToTime(DuracaoComboBox.getValue());
                Instant startInstant = dataDatePicker.getValue().atTime(startTime).atZone(ZoneId.systemDefault()).toInstant();
                Instant endInstant = startInstant.plusSeconds(durationTime.toSecondOfDay());

                instrutorComboBox.getItems().clear();

                List<Funcionario> availableInstructors = AulaBLL.getAvailableInstructors(startInstant, endInstant);
                for (Funcionario funcionario : availableInstructors) {
                    instrutorComboBox.getItems().add(funcionario.getNome());
                }
            }catch (Exception e){
                showAlert("Erro", "Erro ao atualizar instrutores disponíveis: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    public void createAulaIndividual() {
        if (!validateInput()) {
            showAlert("Erro", "Por favor preencha todos os campos obrigatórios.", Alert.AlertType.ERROR);
            return;
        }

        try {
            Aula aula = createAulaFromInput();
            AulaBLL.createAula(aula);

            LinhaAula linhaAula = new LinhaAula();
            linhaAula.setIdFuncionario(findInstrutorIdByName(instrutorComboBox.getValue()));
            linhaAula.setIdAula(aula.getId());
            linhaAula.setIdSocio(SocioBLL.findSocioById(Integer.parseInt(nomeSocioLabel.getText().split(" - ")[0])).getIdSocio());

            LinhaAulaBLL.createLinhaAula(linhaAula);

            showAlert("Sucesso", "Aula individual e linha aula criadas com sucesso.", Alert.AlertType.INFORMATION);

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ipvc/estg/desktop/instrutor/instrutorMainPage.fxml")));
            Scene scene = new Scene(root);
            Stage stage = (Stage) addAulaButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Página Principal");
            stage.show();
        } catch (Exception e) {
            showAlert("Erro", "Erro ao criar aula individual: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private Aula createAulaFromInput() {
        //TODO: Verificar o erro que dá ao criar a aula
        Funcionario currentUser = SessionData.getInstance().getCurrentUser();
        String nomeAula = nomeAulaField.getText();
        LocalTime startTime = LocalTime.parse(HoraComboBox.getValue());
        LocalTime durationTime = parseDurationToTime(DuracaoComboBox.getValue());
        Instant startInstant = dataDatePicker.getValue().atTime(startTime).atZone(ZoneId.systemDefault()).toInstant();

        Aula aula = new Aula();
        aula.setNome(nomeAula);
        aula.setDataHoraComeco(startInstant);
        aula.setDataHoraFim(startInstant.plusSeconds(durationTime.toSecondOfDay()));
        aula.setDuracao(durationTime);
        aula.setIdFuncionario(findInstrutorIdByName(instrutorComboBox.getValue()));
        aula.setIdModalidade(11);
        aula.setLocalAula("SALA MUSCULAÇÃO");
        aula.setNumMinAtletas(1);
        aula.setTotalLugares(1);
        aula.setVagas(0);
        aula.setIdEstadoaula(1);
        return aula;
    }

    private int findInstrutorIdByName(String name) {
        return AulaBLL.getAllInstrutores().stream()
                .filter(s -> s.getNome().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Instrutor não encontrado"))
                .getIdFuncionario();
    }

    private int findSocioIdByName(String name) {
        return AulaBLL.getAllSocios().stream()
                .filter(s -> s.getNome().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Sócio não encontrado"))
                .getIdSocio();
    }

    private boolean validateInput() {
        return !checkEmptyFields(nomeAulaField.getText())
                && !comboBoxEmpty(HoraComboBox, DuracaoComboBox, instrutorComboBox)
                && !dataEmpty(dataDatePicker);
    }

    private LocalTime parseDurationToTime(String durationStr) {
        if (durationStr.equals("30m")) {
            return LocalTime.of(0, 30);
        } else if (durationStr.equals("1h")) {
            return LocalTime.of(1, 0);
        } else if (durationStr.equals("1h30m")) {
            return LocalTime.of(1, 30);
        } else if (durationStr.equals("2h")) {
            return LocalTime.of(2, 0);
        }
        throw new IllegalArgumentException("Invalid duration format");
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean verifyDate(Instant date){
        LocalDate today = LocalDate.now();
        if(date.isBefore(today.atStartOfDay(ZoneId.systemDefault()).toInstant())){
            return false;
        }
        return true;
    }

    private boolean checkEmptyFields(String... fields) {
        for (String field : fields) {
            if (field == null || field.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private boolean comboBoxEmpty(ComboBox<String>... comboBoxes){
        for(ComboBox<String> comboBox : comboBoxes){
            if(comboBox.getValue() == null){
                return true;
            }
        }
        return false;
    }

    private boolean dataEmpty(DatePicker... datePickers){
        for(DatePicker datePicker : datePickers){
            if(datePicker.getValue() == null){
                return true;
            }
        }
        return false;
    }

    public void goBack(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ipvc/estg/desktop/instrutor/instrutorMainPage.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Página Principal");
        stage.show();
    }

    public void logout(ActionEvent event) throws IOException {
        SessionData.getInstance().setCurrentUser(null);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Tem a certeza que quer fechar a aplicação?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try{
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ipvc/estg/desktop/Login/login.fxml")));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Login");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
