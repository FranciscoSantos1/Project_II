package ipvc.estg.desktop.responsavelInstrutores;


import bll.AulaBLL;
import bll.FuncionarioBLL;
import entity.*;
import bll.ModalidadeBLL;
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
import java.time.*;
import java.util.Objects;

public class adicionarAulaController {

    @FXML
    private DatePicker dataDatePicker;

    @FXML
    private ComboBox<String> DuracaoComboBox;

    @FXML
    private ComboBox<String> HoraComboBox;

    @FXML
    private Button addAulaButton;

    @FXML
    private Button detailsButton;

    @FXML
    private TextField localAulaField;

    @FXML
    private ComboBox<String> modalidadeComboBox;

    @FXML
    private TextField nomeAulaField;

    @FXML
    private TextField numeroMinimoAtletasField;

    @FXML
    private TextField vagasField;

    @FXML
    private ComboBox<String> InstrutorComboBox1;

    @FXML
    public void initialize() {
        for (int hour = 7; hour <= 20; hour++) {
            HoraComboBox.getItems().add(String.format("%02d:00h", hour));
        }

        DuracaoComboBox.getItems().add("30m");
        DuracaoComboBox.getItems().add("1h");
        DuracaoComboBox.getItems().add("1h30m");
        DuracaoComboBox.getItems().add("2h");

        try{
            for(Funcionario instrutor : FuncionarioBLL.getAllFuncionarios()){
                if(instrutor.getIdTipofuncionario() == 1){
                    InstrutorComboBox1.getItems().add(instrutor.getNome());
                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
            for(Modalidade modalidade : ModalidadeBLL.getAllModalidades()){
                modalidadeComboBox.getItems().add(modalidade.getModalidade());
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }




    }

    public void createAula() {

        Aula newAula = new Aula();



        String nome = nomeAulaField.getText();
        LocalDate data = dataDatePicker.getValue();
        LocalTime hora = LocalTime.parse(HoraComboBox.getValue().substring(0, 5));
        String local = localAulaField.getText();
        int numeroMinimoAtletas = Integer.parseInt(numeroMinimoAtletasField.getText());
        String selectedDuration = DuracaoComboBox.getValue();
        int vagas = Integer.parseInt(vagasField.getText());
        Instant dataHoraComeco = data.atTime(hora).atZone(ZoneId.systemDefault()).toInstant();
        LocalTime duration = parseDurationToTime(selectedDuration);

        if(!verifyDate(dataHoraComeco)){
            showAlert("Erro", "Data inválida", Alert.AlertType.ERROR);
            return;
        }

        try{
            if (checkEmptyFields(nome, local, numeroMinimoAtletasField.getText(), selectedDuration, vagasField.getText())) {
                showAlert("Erro", "Preencha todos os campos", Alert.AlertType.ERROR);
                return;
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }


        if(Integer.parseInt(numeroMinimoAtletasField.getText()) < 0 || Integer.parseInt(vagasField.getText()) < 0){
            showAlert("Erro", "Número de vagas ou número mínimo de atletas não pode ser negativo", Alert.AlertType.ERROR);
            return;
        }

        if(numeroMinimoAtletas > vagas){
            showAlert("Erro", "Número mínimo de atletas não pode ser maior que o número de vagas", Alert.AlertType.ERROR);
            return;
        }



        // Set the name, location, and minimum number of athletes
        newAula.setNome(nome);
        newAula.setLocalAula(local);
        newAula.setNumMinAtletas(numeroMinimoAtletas);
        newAula.setIdEstadoaula(1);
        newAula.setVagas(vagas);
        newAula.setDataHoraComeco(dataHoraComeco);
        for(Modalidade modalidade : ModalidadeBLL.getAllModalidades()){
            if(modalidade.getModalidade().equals(modalidadeComboBox.getValue())){
                newAula.setIdModalidade(modalidade.getIdModalidade());
            }
        }
        newAula.setDuracao(parseDurationToTime(selectedDuration));
        for(Funcionario instrutor : FuncionarioBLL.getAllFuncionarios()){
            if(instrutor.getNome().equals(InstrutorComboBox1.getValue())){
                newAula.setIdFuncionario(instrutor.getIdFuncionario());
            }
        }



        try {

            AulaBLL.createAula(newAula);

            showAlert("Sucesso", "Registado com sucesso", Alert.AlertType.INFORMATION);

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ipvc/estg/desktop/responsavelInstrutores/responsavelMainPage.fxml")));
            Scene scene = new Scene(root);
            Stage stage = (Stage) addAulaButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("GymMaster - Login");
            stage.show();

        } catch (Exception e) {
            showAlert("Erro", "Erro ao registar", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
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
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ipvc/estg/desktop/responsavelInstrutores/responsavelMainPage.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("GymMaster - Responsável de Instrutores");
        stage.show();
    }


}
