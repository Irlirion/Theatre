package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.model.Performance;
import sample.view.*;

import java.io.*;
import java.util.LinkedList;
import java.util.List;


public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private ObservableList<Performance> performancesData = FXCollections.observableArrayList();
    private boolean isAdmin = false;


    public Main() {
        performancesData.add(new Performance("Hans"));
        performancesData.add(new Performance("Hans"));
        performancesData.add(new Performance("Awra"));
        performancesData.add(new Performance("Acadc"));
    }

    public static void main(String[] args) {
        launch(args);
    }


    /**
     * Возвращает данные в виде наблюдаемого списка адресатов.
     *
     * @return
     */
    public ObservableList<Performance> getPerformancesData() {
        return performancesData;
    }

    public void save() {
        ObjectOutputStream oos;
        List<Performance> tempArray = new LinkedList<>(performancesData);
        try {
            oos = new ObjectOutputStream(
                    new FileOutputStream(
                            String.valueOf(getClass().getResource("view/data.txt"))));


            oos.writeObject(tempArray);
            oos.flush();
            oos.close();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    public void load() {
        ObjectInputStream ois;

        try {
            ois = new ObjectInputStream(
                    new FileInputStream(
                            String.valueOf(getClass().getResource("view/data.txt"))));

            performancesData.clear();
            performancesData.addAll((LinkedList<Performance>) ois.readObject());
            ois.close();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.toString());
        }
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Театр");

        // Устанавливаем иконку приложения.
        this.primaryStage.getIcons().add(new Image("file:resources/images/icon.png"));

        initRootLayout();

        showChoiceOverview(null);
        load();
    }

    /**
     * Инициализирует корневой макет и пытается загрузить последний открытый
     * файл с адресатами.
     */
    public void initRootLayout() {
        try {
            // Загружаем корневой макет из fxml файла.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class
                    .getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();
            primaryStage.getIcons().add(new Image("file:resurses/images/icon.png"));
            // Отображаем сцену, содержащую корневой макет.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);


            // Даём контроллеру доступ к главному прилодению.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Показывает в корневом макете сведения об адресатах.
     *
     * @param adminPanelController
     */
    public void showChoiceOverview(AdminPanelController adminPanelController) {
        if (adminPanelController != null) {
            performancesData = adminPanelController.performanceTable.getItems();
        }
        try {
            // Загружаем сведения об адресатах.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/ChoiceOverview.fxml"));
            AnchorPane personOverview = loader.load();

            // Помещаем сведения об адресатах в центр корневого макета.
            rootLayout.setCenter(personOverview);

            // Даём контроллеру доступ к главному приложению.
            ChoiceOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Показывает в корневом макете сведения об адресатах.
     */
    public void showPerformanceOverview() {
        try {
            // Загружаем сведения об адресатах.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/PerformanceOverview.fxml"));
            AnchorPane personOverview = loader.load();

            // Помещаем сведения об адресатах в центр корневого макета.
            rootLayout.setCenter(personOverview);

            // Даём контроллеру доступ к главному приложению.
            PerformanceOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showPersonEditDialog(Performance person) {
        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/AdminPanelDialog.fxml"));
            AnchorPane page = loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            // Устанавливаем иконку приложения.
            dialogStage.getIcons().add(new Image("file:resurses/images/icon.png"));
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаём адресата в контроллер.
            AdminPanelDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Возвращает главную сцену.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void showAdminPanel() {
        if (!isAdmin) {
            isAdmin = showLoginPanel();
        }
        if (isAdmin) {
            try {
                // Загружаем сведения об адресатах.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("view/AdminPanel.fxml"));
                AnchorPane personOverview = loader.load();

                // Помещаем сведения об адресатах в центр корневого макета.
                rootLayout.setCenter(personOverview);

                // Даём контроллеру доступ к главному приложению.
                AdminPanelController controller = loader.getController();
                controller.setMainApp(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean showLoginPanel() {
        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/Login.fxml"));
            AnchorPane page = loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            // Устанавливаем иконку приложения.
            dialogStage.getIcons().add(new Image("file:resurses/images/icon.png"));
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаём адресата в контроллер.
            LoginController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();

            return controller.isPass();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showPlaces(Performance selectedPerson, boolean isAdmin) {
        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/Places.fxml"));
            AnchorPane page = loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            // Устанавливаем иконку приложения.
            dialogStage.getIcons().add(new Image("file:resurses/images/icon.png"));
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаём адресата в контроллер.
            PlacesController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(selectedPerson);
            controller.setIsAdmin(isAdmin);

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
