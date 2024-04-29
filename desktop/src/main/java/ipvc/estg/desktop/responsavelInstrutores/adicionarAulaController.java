package ipvc.estg.desktop.responsavelInstrutores;


import bll.AulaBLL;
import bll.FuncionarioBLL;
import entity.*;
import bll.ModalidadeBLL;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.*;

public class adicionarAulaController {

    @FXML
    private DatePicker dataDatePicker;

    @FXML
    private ComboBox<String> DuracaoComboBox;

    @FXML
    private ComboBox<String> HoraComboBox;

    @FXML
    private Button addSocioButton;

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

        for(Modalidade modalidade : ModalidadeBLL.getAllModalidades()){
            modalidadeComboBox.getItems().add(modalidade.getModalidade());
        }

        for(Funcionario instrutor : FuncionarioBLL.getAllFuncionarios()){
            if(instrutor.getIdTipofuncionario() == 2){
                InstrutorComboBox1.getItems().add(instrutor.getNome());
            }
        }
    }

    public void createAula() {
        Aula newAula = new Aula();

        // Assuming you have methods to get these values from input fields or other parts of the UI
        String nome = nomeAulaField.getText();
        LocalDate data = dataDatePicker.getValue();
        LocalTime hora = LocalTime.parse(HoraComboBox.getValue().substring(0, 5)); // Parses the hour, removing the 'h'
        String local = localAulaField.getText();
        int numeroMinimoAtletas = Integer.parseInt(numeroMinimoAtletasField.getText());
        String selectedDuration = DuracaoComboBox.getValue();
        int vagas = Integer.parseInt(vagasField.getText());
        Instant dataHoraComeco = data.atTime(hora).atZone(ZoneId.systemDefault()).toInstant();
        LocalTime duration = parseDurationToTime(selectedDuration);




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
        AulaBLL.createAula(newAula);
        AulaBLL.readAula();
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

}
