package main.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import main.models.INGEvent;
import main.services.CaricaReportService;
import main.services.INGEventDAO;
import main.services.INGEventDAOPostGres;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.List;
import java.util.function.UnaryOperator;

public class INGVReportController implements Initializable {
    private ObservableList<INGEvent> ingEvList;

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

    @FXML
    private Button switchModeBtn;
    private boolean loadModeFromDB = true;

    @FXML
    private TextField locationTfd,minMagTfd,maxMagTfd;

    @FXML
    private HBox filterHBox;

    private INGEventDAO ingEventDAO;

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

        loadBtnBinding();

        ingEventDAO = new INGEventDAOPostGres();
        try {
            ingEvList.addAll(ingEventDAO.listAll(-1));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Errore durante la lettura dei dati", ButtonType.OK).show();
            //e.printStackTrace();
        }
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
                boolean cond3 = ingEvent.getEventLocationName().toLowerCase().contains(strSearched);

                return cond1 || cond2 || cond3;
            });
        });

        resultTableView.setItems(ingEventFilteredList);
    }

    @FXML
    public void loadDataClicked() {
        if(loadModeFromDB)
            ingEvList.clear();

        if(!loadModeFromDB){
            if(startDate.getValue() == null)
                startDate.setValue(LocalDate.of(1970,1,1));
            if(endDate.getValue() == null)
                endDate.setValue(LocalDate.now());
        }

        CaricaReportService caricaReportService = new CaricaReportService(
                loadModeFromDB,
                startDate.getValue(),
                endDate.getValue(),
                limitResult.getText().isEmpty() ? -1 : Integer.parseInt(limitResult.getText()),
                minMagTfd.getText().isEmpty() ? null : Double.parseDouble(minMagTfd.getText()),
                maxMagTfd.getText().isEmpty() ? null : Double.parseDouble(maxMagTfd.getText()),
                locationTfd.getText().isEmpty() ? null : locationTfd.getText(),
                ingEvList
        );

        progressInd.progressProperty().bind(caricaReportService.progressProperty());
        progressInd.visibleProperty().bind(caricaReportService.runningProperty());

        try{
            caricaReportService.start();
        }catch(Exception ex){
            new Alert(Alert.AlertType.ERROR, "Errore: "+ex.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    private void onSwitchMode(Event event) {
        Button btn = (Button) event.getSource();
        if(btn.getText().equals("DB")) {
            loadModeFromDB = false;
            btn.setText("WEB");
            filterHBox.setVisible(false);
        } else {
            loadModeFromDB = true;
            btn.setText("DB");
            filterHBox.setVisible(true);
        }
    }

    private void loadBtnBinding() {
        BooleanBinding emptyTextFieldBinding = limitResult.textProperty().isEmpty();

        BooleanBinding dateBinding = Bindings.createBooleanBinding(() -> {
            if(startDate.getValue() == null || endDate.getValue() == null)
                return false;
            return startDate.getValue().isAfter(endDate.getValue());
                },startDate.valueProperty(),endDate.valueProperty());

        BooleanBinding magnitudeBinding = Bindings.createBooleanBinding(() -> {
            if(minMagTfd.getText().isEmpty() || maxMagTfd.getText().isEmpty())
                return false;

            int minMag = Integer.parseInt(minMagTfd.getText());
            int maxMag = Integer.parseInt(maxMagTfd.getText());

            return minMag > maxMag;
        }, minMagTfd.textProperty(),maxMagTfd.textProperty());
        
        loadDataBtn.disableProperty().bind(Bindings.or(emptyTextFieldBinding,dateBinding).or(magnitudeBinding));

        TextFormatter<String> tfLimitResult = new TextFormatter<>((change) -> {
            if(change.getControlNewText().equals("0")) return null;

            if(change.getText().matches("[0-9]") || change.getText().isEmpty()) {
                return change;
            }
            return null;
        });
        limitResult.setTextFormatter(tfLimitResult);

        UnaryOperator<TextFormatter.Change> magnitude =change -> {
            if(change.getControlNewText().equals("0")) return null;

            if((change.getControlNewText().matches("[0-9]{1,2}") && Integer.parseInt(change.getControlNewText()) <= 10) || change.getControlNewText().isEmpty()) {
                return change;
            }
            return null;
        };

        minMagTfd.setTextFormatter(new TextFormatter<>(magnitude));
        maxMagTfd.setTextFormatter(new TextFormatter<>(magnitude));
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