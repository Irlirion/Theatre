package sample.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private PasswordField passField;
    @FXML
    private TextField loginField;

    private Stage dialogStage;
    private boolean pass = false;
    private HashMap<String, String> hashMap = new HashMap<>();


    /**
     * Устанавливает сцену для этого окна.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public TextField getLoginField() {
        return loginField;
    }

    public void setLoginField(TextField loginField) {
        this.loginField = loginField;
    }

    public PasswordField getPassField() {
        return passField;
    }

    public void setPassField(PasswordField passField) {
        this.passField = passField;
    }

    @FXML
    private void handleOK() {
        if (isInputValid() && isCorrect()) {
            pass = true;
            dialogStage.close();
        }
    }

    private boolean isCorrect() {
        if (hashMap.getOrDefault(loginField.getText(), "").equals(passField.getText())) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Неправильно введен логин или пароль");
            alert.setContentText("Пожалуйста, попробуйте еще раз.");

            alert.showAndWait();

            return false;
        }

    }

    @FXML
    private void handleClose() {
        dialogStage.close();
    }

    /**
     * Проверяет пользовательский ввод в текстовых полях.
     *
     * @return true, если пользовательский ввод корректен
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (loginField.getText() == null || loginField.getText().length() == 0) {
            errorMessage += "Логин пуст!\n";
        }
        if (passField.getText() == null || passField.getText().length() == 0) {
            errorMessage += "Пароль пуст!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Неправильные поля");
            alert.setHeaderText("Пожалуйста, заполните их правильно");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    public boolean isPass() {
        return pass;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Files.lines(Paths.get(getClass().getResource("logpas.txt").toURI())).forEach(i -> {
                String[] tempStr = i.split(":");
                hashMap.put(tempStr[0], tempStr[1]);
            });
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
