package sample.model;

import javafx.beans.property.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

public class Performance implements Serializable {
    private StringProperty name;
    private StringProperty actors;
    private Boolean[] places;
    private DoubleProperty raiting;
    private StringProperty data;
    private StringProperty desc;
    private StringProperty price;
    private StringProperty type;

    public Performance() {
        this.name = new SimpleStringProperty(null);
        this.actors = new SimpleStringProperty(null);

        // Какие-то фиктивные начальные данные для удобства тестирования.
        this.places = new Boolean[]{false, false, false, false, false, false};
        this.raiting = new SimpleDoubleProperty(0);
        this.data = new SimpleStringProperty(null);
        this.desc = new SimpleStringProperty(null);
        this.price = new SimpleStringProperty(null);
        this.type = new SimpleStringProperty(null);
    }

    /**
     * Конструктор с некоторыми начальными данными.
     *
     * @param name
     */
    public Performance(String name) {
        this.name = new SimpleStringProperty(name);
        this.actors = new SimpleStringProperty("asd");

        // Какие-то фиктивные начальные данные для удобства тестирования.
        this.places = new Boolean[]{false, false, false, false, false, false};
        this.raiting = new SimpleDoubleProperty(0);
        this.data = new SimpleStringProperty("01.04.2000");
        this.desc = new SimpleStringProperty("Описание спектакля");
        this.price = new SimpleStringProperty("200");
        this.type = new SimpleStringProperty("Концерт");
    }

    private void readObject(ObjectInputStream ois)
            throws IOException, ClassNotFoundException {
        name = new SimpleStringProperty((String) ois.readObject());
        actors = new SimpleStringProperty((String) ois.readObject());
        String string = (String) ois.readObject();
        String[] strings = string.replace("[", "").replace("]", "").split(", ");
        Boolean[] temp = new Boolean[strings.length];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = Boolean.parseBoolean(strings[i]);
        }
        places = temp;
        raiting = new SimpleDoubleProperty((Double) ois.readObject());
        data = new SimpleStringProperty((String) ois.readObject());
        desc = new SimpleStringProperty((String) ois.readObject());
        price = new SimpleStringProperty((String) ois.readObject());
        type = new SimpleStringProperty((String) ois.readObject());
    }

    private void writeObject(ObjectOutputStream oos)
            throws IOException {
        oos.writeObject(name.get());
        oos.writeObject(actors.get());
        oos.writeObject(Arrays.toString(places));
        oos.writeObject(raiting.get());
        oos.writeObject(data.get());
        oos.writeObject(desc.get());
        oos.writeObject(price.get());
        oos.writeObject(type.get());
    }

    public String getPrice() {
        return price.get();
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public StringProperty priceProperty() {
        return price;
    }

    public String getDesc() {
        return desc.get();
    }

    public void setDesc(String desc) {
        this.desc.set(desc);
    }

    public StringProperty descProperty() {
        return desc;
    }

    public String getData() {
        return data.get();
    }

    public void setDate(String date) {
        this.data.set(date);
    }

    public StringProperty dataProperty() {
        return data;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getActors() {
        return actors.get();
    }

    public void setActors(String actors) {
        this.actors.set(actors);
    }

    public StringProperty actorsProperty() {
        return actors;
    }

    public Boolean[] getPlaces() {
        return places;
    }

    public void setPlaces(Boolean[] places) {
        this.places = places;
    }

    public Double getRaiting() {
        return raiting.get();
    }

    public void setRaiting(double raiting) {
        this.raiting.set(raiting);
    }

    public ObjectProperty<Double> raitingProperty() {
        return raiting.asObject();
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public StringProperty typeProperty() {
        return type;
    }
}
