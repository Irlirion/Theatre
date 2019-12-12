package sample.view;

import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.Main;
import sample.model.Performance;

public class AdminPanelController {
    public TableColumn<Performance, String> typeColumn;
    public Label priceLabel;
    @FXML
    public TableView<Performance> performanceTable;
    @FXML
    private TableColumn<Performance, Double> raitingColumn;
    @FXML
    private TableColumn<Performance, String> nameColumn;
    @FXML
    private TableColumn<Performance, String> dataColumn;
    @FXML
    private Label actorsLabel;
    @FXML
    private Label descLabel;
    private Main mainApp;

    @FXML
    private void initialize() {
        // Инициализация таблицы адресатов.
        nameColumn.setCellValueFactory(
                cellData -> cellData.getValue().nameProperty());
        raitingColumn.setCellValueFactory(
                cellData -> cellData.getValue().raitingProperty());
        dataColumn.setCellValueFactory(
                cellData -> cellData.getValue().dataProperty());
        typeColumn.setCellValueFactory(
                cellData -> cellData.getValue().typeProperty());


        // Очистка дополнительной информации об адресате.
        showPerformanceDetails(null);

        // Слушаем изменения выбора, и при изменении отображаем
        // дополнительную информацию об адресате.
        performanceTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPerformanceDetails(newValue));
    }

    public void updateRaiting(String name) {
        FilteredList<Performance> tempArr = performanceTable.getItems().filtered(i -> i.getName().equals(name));
        float raiting;
        float payedPlaces = 0;
        for (Performance per : tempArr) {
            for (Boolean q : per.getPlaces()) {
                if (q) {
                    payedPlaces++;
                }
            }
        }
        raiting = payedPlaces / (tempArr.size() * 6) * 10;
        double scale = Math.pow(10, 1);
        for (Performance per : tempArr) {
            per.setRaiting(Math.round(raiting * scale) / scale);
        }
    }

    /**
     * Вызывается, когда пользователь кликает по кнопке удаления.
     */
    @FXML
    private void handleDeletePerson() {
        int selectedIndex = performanceTable.getSelectionModel().getSelectedIndex();
        Performance performance = performanceTable.getSelectionModel().getSelectedItem();
        if (performance != null) {
            performanceTable.getItems().remove(selectedIndex);
            updateRaiting(performance.getName());
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

    /**
     * Вызывается, когда пользователь кликает по кнопке New...
     * Открывает диалоговое окно с дополнительной информацией нового адресата.
     */
    @FXML
    private void handleNewPerson() {
        Performance tempPerson = new Performance();
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        if (okClicked) {
            mainApp.getPerformancesData().add(tempPerson);
            updateRaiting(tempPerson.getName());
        }
    }

    /**
     * Вызывается, когда пользователь кликает по кнопка Edit...
     * Открывает диалоговое окно для изменения выбранного адресата.
     */
    @FXML
    private void handleEditPerson() {
        Performance selectedPerson = performanceTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showPerformanceDetails(selectedPerson);
                updateRaiting(selectedPerson.getName());
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

    public void setMainApp(Main main) {
        mainApp = main;
        // Добавление в таблицу данных из наблюдаемого списка
        performanceTable.setItems(mainApp.getPerformancesData());
    }

    public void backButton(ActionEvent actionEvent) {
        mainApp.showChoiceOverview(this);
    }

    public void openPlaces(ActionEvent actionEvent) {
        Performance selectedPerson = performanceTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPlaces(selectedPerson, true);
            if (okClicked) {
                showPerformanceDetails(selectedPerson);
                updateRaiting(selectedPerson.getName());
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
