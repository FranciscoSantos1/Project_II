package ipvc.estg.desktop.instrutor;


import bll.AulaBLL;
import bll.FuncionarioBLL;
import bll.LinhaAulaBLL;
import bll.ModalidadeBLL;
import entity.*;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class adicionarAulaIndividualController {

    @FXML
    private ComboBox<String> DuracaoComboBox;

    @FXML
    private ComboBox<String> HoraComboBox;

    @FXML
    private ComboBox<String> SociosComboBox1;

    @FXML
    private Button addAulaButton;

    @FXML
    private DatePicker dataDatePicker;

    @FXML
    private Button detailsButton;

    @FXML
    private TextField nomeAulaField;

    @FXML
    public void initialize() {
        setupListeners();
        populateComboBoxes();
    }

    private void setupListeners() {
        dataDatePicker.valueProperty().addListener((ChangeListener<LocalDate>) (observable, oldValue, newValue) -> updateAvailableSocios());
        HoraComboBox.valueProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> updateAvailableSocios());
        DuracaoComboBox.valueProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> updateAvailableSocios());
    }

    @FXML
    private void populateComboBoxes() {
        SociosComboBox1.getItems().setAll(AulaBLL.getAllSocios().stream().map(Socio::getNome).collect(Collectors.toList()));
        HoraComboBox.getItems().setAll(IntStream.range(8, 21).mapToObj(h -> String.format("%02d:00", h)).collect(Collectors.toList()));
        DuracaoComboBox.getItems().setAll(List.of("30m", "1h", "1h30m", "2h"));
    }

    @FXML
    public void updateAvailableSocios() {
        if (dataDatePicker.getValue() != null && HoraComboBox.getValue() != null && DuracaoComboBox.getValue() != null) {
            try {
                LocalTime startTime = LocalTime.parse(HoraComboBox.getValue());
                LocalTime durationTime = parseDurationToTime(DuracaoComboBox.getValue());
                Instant startInstant = dataDatePicker.getValue().atTime(startTime).atZone(ZoneId.systemDefault()).toInstant();
                Instant endInstant = startInstant.plusSeconds(durationTime.toSecondOfDay());

                SociosComboBox1.getItems().clear();

                List<Socio> availableSocios = AulaBLL.getAvailableSocios(startInstant, endInstant);
                for(Socio socio : availableSocios){
                    SociosComboBox1.getItems().add(socio.getNome());
                }


            } catch (Exception e) {
                showAlert("Erro", "Erro ao atualizar sócios disponíveis: " + e.getMessage(), Alert.AlertType.ERROR);
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
            linhaAula.setIdFuncionario(aula.getIdFuncionario());
            linhaAula.setIdAula(aula.getId());
            linhaAula.setIdSocio(findSocioIdByName(SociosComboBox1.getValue()));

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
        Funcionario loggedInInstructor = SessionData.getInstance().getCurrentUser();
        String nomeAula = nomeAulaField.getText();
        LocalTime startTime = LocalTime.parse(HoraComboBox.getValue());
        LocalTime durationTime = parseDurationToTime(DuracaoComboBox.getValue());
        Instant startInstant = dataDatePicker.getValue().atTime(startTime).atZone(ZoneId.systemDefault()).toInstant();

        Aula aula = new Aula();
        aula.setNome(nomeAula);
        aula.setDataHoraComeco(startInstant);
        aula.setDataHoraFim(startInstant.plusSeconds(durationTime.toSecondOfDay()));
        aula.setDuracao(durationTime);
        aula.setIdFuncionario(loggedInInstructor.getIdFuncionario());
        aula.setIdModalidade(11);
        aula.setLocalAula("Sala de musculação");
        aula.setNumMinAtletas(1);
        aula.setTotalLugares(1);
        aula.setVagas(0);
        aula.setIdEstadoaula(1);
        return aula;
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
                && !comboBoxEmpty(HoraComboBox, DuracaoComboBox, SociosComboBox1)
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
}
