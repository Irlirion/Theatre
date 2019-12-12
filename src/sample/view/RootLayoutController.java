package sample.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import sample.Main;

/**
 * Контроллер для корневого макета. Корневой макет предоставляет базовый
 * макет приложения, содержащий строку меню и место, где будут размещены
 * остальные элементы JavaFX.
 *
 * @author Marco Jakob
 */
public class RootLayoutController {

    // Ссылка на главное приложение
    private Main mainApp;

    /**
     * Вызывается главным приложением, чтобы оставить ссылку на самого себя.
     *
     * @param mainApp
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }


    /**
     * Закрывает приложение.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }

    public void openHandle(ActionEvent actionEvent) {
        mainApp.load();
    }

    public void saveHandle(ActionEvent actionEvent) {
        mainApp.save();
    }
}
