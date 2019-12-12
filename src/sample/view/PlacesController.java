package sample.view;

import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import sample.model.Performance;

public class PlacesController {
    public CheckBox place11;
    public CheckBox place21;
    public CheckBox place12;
    public CheckBox place22;
    public CheckBox place31;
    public CheckBox place32;
    private Stage stage;
    private Performance event;
    private boolean isOk = false;
    private Boolean[] places;


    public void setDialogStage(Stage dialogStage) {
        stage = dialogStage;
    }

    public void setPerson(Performance selectedPerson) {
        event = selectedPerson;
        places = selectedPerson.getPlaces();
        place11.setSelected(places[0]);
        place12.setSelected(places[1]);
        place21.setSelected(places[2]);
        place22.setSelected(places[3]);
        place31.setSelected(places[4]);
        place32.setSelected(places[5]);
    }

    public boolean isOkClicked() {
        return isOk;
    }

    public void backButton(ActionEvent actionEvent) {
        places[0] = place11.isSelected();
        places[1] = place12.isSelected();
        places[2] = place21.isSelected();
        places[3] = place22.isSelected();
        places[4] = place31.isSelected();
        places[5] = place32.isSelected();
        event.setPlaces(places);
        isOk = true;
        stage.close();
    }

    public void setIsAdmin(boolean isAdmin) {
        if (!isAdmin) {
            place11.setDisable(true);
            place12.setDisable(true);
            place21.setDisable(true);
            place22.setDisable(true);
            place31.setDisable(true);
            place32.setDisable(true);
        }
    }
}
