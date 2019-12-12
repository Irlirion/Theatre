package sample.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class ChoiceOverviewController implements Initializable {

    public ImageView eventImg;
    public ImageView adminImg;
    private Main mainApp;

    @FXML
    private void handlePerformance() {
        mainApp.showPerformanceOverview();
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void handleAdmin(ActionEvent actionEvent) {
        mainApp.showAdminPanel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Image event = new Image("file:resurses/event.png");
            Image admin = new Image("file:resurses/admin.png");
            eventImg.setImage(event);
            eventImg.setCache(true);
            adminImg.setImage(admin);
            adminImg.setCache(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
