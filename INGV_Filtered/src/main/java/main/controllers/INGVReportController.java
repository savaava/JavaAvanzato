package main.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import main.models.INGEvent;
import main.services.CaricaReportService;

import javax.swing.text.DateFormatter;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.List;

public class INGVReportController implements Initializable {
    @FXML
    private TextField searchField;

    @FXML
    private TableView<INGEvent> resultTableView;
    @FXML
    private TableColumn<INGEvent, LocalDateTime> dateClm;
    @FXML
    private TableColumn<INGEvent, Double> magnitudeClm;
    @FXML
    private TableColumn<INGEvent, String> locationClm;

    @FXML
    private DatePicker endDate;
    @FXML
    private DatePicker startDate;
    @FXML
    private TextField limitResult;

    @FXML
    private Button loadDataBtn;
    @FXML
    private ProgressIndicator progressInd;

    private ObservableList<INGEvent> ingEvList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resultTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ingEvList = FXCollections.observableArrayList();

        dateClm.setCellValueFactory(new PropertyValueFactory<>("time"));

        formatTimeClm();

        magnitudeClm.setCellValueFactory(new PropertyValueFactory<>("magnitude"));
        locationClm.setCellValueFactory(new PropertyValueFactory<>("eventLocationName"));

        handleSearchFiltered();

        startDate.setValue(LocalDate.now());
        endDate.setValue(LocalDate.now());

        limitResult.setText("1000");

        setupContextMenu();

        bottonClicked();
    }

    private void formatTimeClm(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        dateClm.setCellFactory(column -> new TableCell<INGEvent, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.format(formatter));
                }
            }
        });
    }

    private void handleSearchFiltered(){
        FilteredList<INGEvent> ingEventFilteredList = new FilteredList<>(ingEvList);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            ingEventFilteredList.setPredicate(ingEvent -> {
                if(newValue==null || newValue.isEmpty())
                    return true;

                String strSearched = newValue.toLowerCase();
                boolean cond1 = ingEvent.getTime().toString().contains(strSearched);
                boolean cond2 = Double.toString(ingEvent.getMagnitude()).equals(strSearched);
                boolean cond3 = ingEvent.getEventLocationName().contains(strSearched);

                return cond1 || cond2 || cond3;
            });
        });

        resultTableView.setItems(ingEventFilteredList);
    }

    @FXML
    public void loadDataClicked() throws InterruptedException {
        CaricaReportService caricaReportService = new CaricaReportService();

        caricaReportService.setIngEvList(ingEvList);
        progressInd.progressProperty().bind(caricaReportService.progressProperty());
        progressInd.visibleProperty().bind(caricaReportService.runningProperty());

        caricaReportService.updateUrl(startDate.getValue(), endDate.getValue(), Integer.parseInt(limitResult.getText()));
        caricaReportService.start();

        //ingEvList.forEach(System.out::println);
    }

    private void bottonClicked() {

        BooleanBinding emptyTextFieldBinding = limitResult.textProperty().isEmpty();

        BooleanBinding dateBinding = Bindings.createBooleanBinding(() -> startDate.getValue().isAfter(endDate.getValue())
                ,startDate.valueProperty(),endDate.valueProperty());
        
        loadDataBtn.disableProperty().bind(Bindings.or(emptyTextFieldBinding,dateBinding));

        TextFormatter<String> tf = new TextFormatter<>((change) -> {
            if(change.getControlNewText().equals("0")) return null;

            if(change.getText().matches("[0-9]") || change.getText().isEmpty()) {
                return change;
            };
            return null;
        });
        limitResult.setTextFormatter(tf);
    }

    private void setupContextMenu(){
        ContextMenu contextMenu = new ContextMenu();
        MenuItem saveItem = new MenuItem("Salva righe");
        saveItem.setOnAction(event -> saveSelectedRows());
        contextMenu.getItems().add(saveItem);
        resultTableView.setContextMenu(contextMenu);
    }

    private void saveSelectedRows(){
        List<INGEvent> selectedRows = resultTableView.getSelectionModel().getSelectedItems();

        if(selectedRows.isEmpty())
            return;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salva righe in CSV");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("File CSV","*.csv")
        );
        File file = fileChooser.showSaveDialog(resultTableView.getScene().getWindow());
        if(file != null)
            writeRows(file, selectedRows);
    }

    private void writeRows(File file, List<INGEvent> selectedRows){
        try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
            pw.println("date|magnitude|location");

            for (INGEvent ingEvent : selectedRows) {
                pw.print(ingEvent.getTime().toLocalDate());
                pw.print("|");
                pw.print(ingEvent.getMagnitude());
                pw.print("|");
                pw.println(ingEvent.getEventLocationName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}