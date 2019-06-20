package val.bond.resources.customControlls;

import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class Notification extends Parent {
    public StringProperty textProperty = new SimpleStringProperty();

    public Notification(int width, int height, String text){
        this.textProperty.setValue(text);
        Rectangle background = new Rectangle(width, height);
        background.setArcWidth(50);
        background.setArcHeight(50);
        background.setFill(Color.web("#0000007e"));

        Label label = new Label();
        label.setFont(new Font("Helvetica Neue", height / 3));
        label.setTextFill(Color.WHITE);
        label.textProperty().bind(textProperty);

        StackPane pane = new StackPane();
        pane.getChildren().addAll(background, label);
        setOpacity(0.0);
        setDisable(true);
        getChildren().add(pane);
    }

    public void show(){
        Thread thread = new Thread(()->{
            FadeTransition appear = new FadeTransition(Duration.millis(1000), this);
            appear.setFromValue(0.0);
            appear.setToValue(1.0);
            this.setDisable(false);
            appear.play();
            appear.setOnFinished(event -> {

                FadeTransition pausing = new FadeTransition(Duration.millis(2000), this);
                pausing.setFromValue(1.0);
                pausing.setToValue(1.0);
                pausing.play();
                pausing.setOnFinished(event1 -> {
                    FadeTransition disappear = new FadeTransition(Duration.millis(1000), this);
                    disappear.setFromValue(1.0);
                    disappear.setToValue(0.0);
                    disappear.play();
                    this.setDisable(true);
                });

            });
        });

        thread.start();

    }

    public String getText() {
        return textProperty.get();
    }

    public void setText(String text) {
        this.textProperty.set(text);
    }

    public StringProperty getTextProperty() {
        return textProperty;
    }
}
