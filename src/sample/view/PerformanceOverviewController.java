package sample.view;

import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.Main;
import sample.model.Performance;

import java.util.function.Predicate;

public class PerformanceOverviewController {
    public TableColumn<Performance, String> namePColumn;
    public TableColumn<Performance, Double> raitingPColumn;
    public TableColumn<Performance, String> dataPColumn;
    public TableView<Performance> concertTable1;
    public TableColumn<Performance, String> nameCColumn1;
    public TableColumn<Performance, Double> raitingCColumn1;
    public TableColumn<Performance, String> dataCColumn1;
    public Label priceCLabel1;
    public Label descCLabel1;
    public Label priceLabel;
    public TableView<Performance> performanceTable;
    FilteredList<Performance> perItems;
    FilteredList<Performance> conItems;
    Predicate<Performance> performances;
    Predicate<Performance> concert;
    @FXML
    private Label actorsLabel;
    @FXML
    private Label descLabel;
    private Main mainApp;

    /**
     * Инициализация класса-контроллера. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
        // Инициализация таблицы адресатов.
        namePColumn.setCellValueFactory(
                cellData -> cellData.getValue().nameProperty());
        raitingPColumn.setCellValueFactory(
                cellData -> cellData.getValue().raitingProperty());
        dataPColumn.setCellValueFactory(
                cellData -> cellData.getValue().dataProperty());
        nameCColumn1.setCellValueFactory(
                cellData -> cellData.getValue().nameProperty());
        raitingCColumn1.setCellValueFactory(
                cellData -> cellData.getValue().raitingProperty());
        dataCColumn1.setCellValueFactory(
                cellData -> cellData.getValue().dataProperty());


        // Слушаем изменения выбора, и при изменении отображаем
        // дополнительную информацию об адресате.
        performanceTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPerformanceDetails(newValue));
        concertTable1.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showConcertDetails(newValue));
    }

    private void showPerformanceDetails(Performance performance) {
        if (performance != null) {
            // Заполняем метки информацией из объекта performance.
            actorsLabel.setText(performance.getActors());
            descLabel.setText(performance.getDesc());
            priceLabel.setText(performance.getPrice());
        } else {
            // Если Person = null, то убираем весь текст.
            actorsLabel.setText("");
            descLabel.setText("");
            priceLabel.setText("");
        }
    }

    private void showConcertDetails(Performance performance) {
        if (performance != null) {
            // Заполняем метки информацией из объекта performance.
            descCLabel1.setText(performance.getDesc());
            priceCLabel1.setText(performance.getPrice());
        } else {
            // Если Person = null, то убираем весь текст.
            descCLabel1.setText("");
            priceCLabel1.setText("");
        }
    }

    public void setMainApp(Main main) {
        this.mainApp = main;

        // Добавление в таблицу данных из наблюдаемого списка
        perItems = new FilteredList<>(mainApp.getPerformancesData());
        conItems = new FilteredList<>(mainApp.getPerformancesData());
        performances = event -> event.getType().equals("Спектакль");
        concert = event -> event.getType().equals("Концерт");
        perItems.setPredicate(performances);
        conItems.setPredicate(concert);
        performanceTable.setItems(perItems);
        concertTable1.setItems(conItems);
    }

    public void perfTabHandle(Event event) {
        showPerformanceDetails(null);
    }

    public void conTabHandle(Event event) {
        showConcertDetails(null);
    }

    public void backButton(ActionEvent actionEvent) {
        mainApp.showChoiceOverview(null);
    }

    public void placesButton(ActionEvent actionEvent) {
        Performance selectedPerson = performanceTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPlaces(selectedPerson, false);
            if (okClicked) {
                showPerformanceDetails(selectedPerson);
            }

        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Ничего не выбрано");
            alert.setHeaderText("Мероприятие не выбрано");
            alert.setContentText("Пожалуйста, выберете мероприятие.");

            alert.showAndWait();
        }
    }

    public void placesCButton(ActionEvent actionEvent) {
        Performance selectedPerson = concertTable1.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPlaces(selectedPerson, false);
            if (okClicked) {
                showConcertDetails(selectedPerson);
            }

        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Ничего не выбрано");
            alert.setHeaderText("Мероприятие не выбрано");
            alert.setContentText("Пожалуйста, выберете мероприятие.");

            alert.showAndWait();
        }
    }
}
