package sample.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.model.Performance;

public class AdminPanelDialogController {
    public TextField typeField;
    public TextArea descField;
    public TextField priceField;
    public TextField dateField;
    public TextArea actorsField;
    public TextField eventNameField;

    private Stage dialogStage;
    private Performance person;
    private boolean okClicked = false;

    /**
     * Инициализирует класс-контроллер. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Устанавливает сцену для этого окна.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Задаёт адресата, информацию о котором будем менять.
     *
     * @param person
     */
    public void setPerson(Performance person) {
        this.person = person;

        typeField.setText(person.getType());
        descField.setText(person.getDesc());
        priceField.setText(person.getPrice());
        dateField.setText(person.getData());
        actorsField.setText(person.getActors());
        eventNameField.setText(person.getName());
    }

    /**
     * Returns true, если пользователь кликнул OK, в другом случае false.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Вызывается, когда пользователь кликнул по кнопке OK.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            person.setType(typeField.getText());
            person.setDesc(descField.getText());
            person.setPrice(priceField.getText());
            person.setDate(dateField.getText());
            person.setActors(actorsField.getText());
            person.setName(eventNameField.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Вызывается, когда пользователь кликнул по кнопке Cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Проверяет пользовательский ввод в текстовых полях.
     *
     * @return true, если пользовательский ввод корректен
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (typeField.getText() == null || typeField.getText().length() == 0) {
            errorMessage += "Не введен тип мероприятия!\n";
        }
        if (descField.getText() == null || descField.getText().length() == 0) {
            errorMessage += "Не введено описание мероприятия!\n";
        }
        if (priceField.getText() == null || priceField.getText().length() == 0) {
            errorMessage += "Не введена цена билета!\n";
        }

        if (dateField.getText() == null || dateField.getText().length() == 0) {
            errorMessage += "Не введена дата мероприятия!\n";
        }

        if (actorsField.getText() == null || actorsField.getText().length() == 0) {
            errorMessage += "Не введены актеры!\n";
        }

        if (eventNameField.getText() == null || eventNameField.getText().length() == 0) {
            errorMessage += "Не введено название мероприятия!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Неверные поля");
            alert.setHeaderText("Пожалуйста, заполните поля правильно");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
