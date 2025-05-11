package mystudentlist;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.deploy.panel.TextFieldProperty;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;

public class MyStudentListViewController implements Initializable {
    @FXML
    private MenuItem saveButton;

    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField codeField;
    @FXML
    private TextField searchField;

    @FXML
    private Button removeButton;
    @FXML
    private TableView<Studente> studentTable;
    @FXML
    private TableColumn<Studente, String> nameClm;
    @FXML
    private TableColumn<Studente, String> surnameClm;
    @FXML
    private TableColumn<Studente, String> codeClm;

    private ObservableList<Studente> studenti;
    
    private StudenteDAO studenteDAO;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        studenteDAO = new StudenteDAOPostGres();

        handleCodeField();
        
        nameClm.setCellValueFactory(new PropertyValueFactory("nome"));
        surnameClm.setCellValueFactory(new PropertyValueFactory("cognome"));
        codeClm.setCellValueFactory(new PropertyValueFactory("matricola"));
        
        nameClm.setCellFactory(TextFieldTableCell.forTableColumn());

        studenti = FXCollections.observableArrayList();
        initItems();

        handleSearchField();
    }

    private void handleCodeField() {
        /* prima implementazione
        codeField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue == null) return ;

            codeField.setText(codeField.getText().replaceAll("[^0-9]",""));
            if(codeField.getText().length() > 10)
                codeField.setText(codeField.getText().substring(0, 10));
        });*/

        TextFormatter<String> tf = new TextFormatter<>(change -> {
            if(codeField.getText().length()>=10)
                return null;

            if(change.getText().matches("[0-9]") || change.getText().isEmpty())
                return change;

            return null;
        });
        codeField.setTextFormatter(tf);
    }

    private void initItems() {
//        studenti.add(new Studente("Mario", "Rossi", "06127001"));
//        studenti.add(new Studente("Ernesto", "Rossi", "06127002"));
//        studenti.add(new Studente("Davide", "Rossi", "06127003"));
        try {
            studenti.addAll(studenteDAO.elencaTutti());
        } catch (Exception e) {e.printStackTrace();}
    }

    private void handleSearchField(){
        FilteredList<Studente> studentsSearched = new FilteredList<>(studenti);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            studentsSearched.setPredicate(studente -> {
                if (newValue == null || newValue.isEmpty())
                    return true;

                String strSearched = newValue.toLowerCase();
                boolean cond1 = studente.getNome().toLowerCase().contains(strSearched);
                boolean cond2 = studente.getCognome().toLowerCase().contains(strSearched);
                boolean cond3 = studente.getMatricola().toLowerCase().contains(strSearched);

                return cond1 || cond2 || cond3;
            });
        });

        studentTable.setItems(studentsSearched);
    }

    @FXML
    private void openFile(ActionEvent event) {
        FileChooser fc = new FileChooser();
    }

    @FXML
    private void saveFile(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File file = fc.showSaveDialog(nameField.getParent().getScene().getWindow());
        
        if(file != null) {
            try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
                for(Studente s : studenti) {
                    pw.append(s.getNome() + ';');
                    pw.append(s.getCognome() + ';');
                    pw.append(s.getMatricola() + '\n');
                }
            } catch (IOException ex) {
                Logger.getLogger(MyStudentListViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void quitApp(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void addStudent() {
        Studente sToAdd = new Studente(nameField.getText(), surnameField.getText(),codeField.getText());
        try {
            if(studenteDAO.inserisci(sToAdd))
                studenti.add(sToAdd);
        } catch (Exception e) {e.printStackTrace();}
    }

    @FXML
    private void removeStudent() {
        Studente sToRemove = studentTable.getSelectionModel().getSelectedItem();
        try {
            studenteDAO.rimuovi(sToRemove);
            studenti.remove(sToRemove);
        } catch (Exception e) {e.printStackTrace();}
    }

    @FXML
    private void updateName(TableColumn.CellEditEvent<Studente, String> event) {
        Studente s = studentTable.getSelectionModel().getSelectedItem();

        try{
            studenteDAO.aggiorna(new Studente(s.getMatricola(),event.getNewValue(),s.getCognome()));
            s.setNome(s.getNome());
        }catch(Exception ex){
            ex.printStackTrace();
            /* un problema della TableVIew è che deve essere refreshata quando si modifica il campo */
            studentTable.refresh();
        }
    }
}
