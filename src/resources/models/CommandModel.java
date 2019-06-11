package resources.models;

import javafx.animation.ScaleTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class CommandModel extends Parent {

    private Rectangle background;

    private StringProperty labelText = new SimpleStringProperty();

    Label label;

    public CommandModel(int width, int height, String imagePath){
        label = new Label();
        init(width, height, imagePath);
    }

    public CommandModel(int width, int height, String imagePath, String hintText){
        label = new Label();
        labelText.setValue(hintText);
        init(width, height, imagePath);
    }

    private void init(int width, int height, String imagePath){
        StackPane stackPane = new StackPane();
        background = new Rectangle(width, height);
        background.setArcHeight(width / 2);
        background.setArcWidth(height / 2);
        Stop[] stops = new Stop[]{
                new Stop(0, Color.web("#9b9ea3")),
                new Stop(0.5, Color.web("#ffffff")),
                new Stop(0.7, Color.web("#9b9ea3"))
        };
        LinearGradient gradient = new LinearGradient(0.0,0.0,1.0,1.0,true, CycleMethod.NO_CYCLE, stops);
        background.setFill(gradient);

        Image image = new Image(imagePath);

        ImageView foregroundImage = new ImageView();
        foregroundImage.setImage(image);
        foregroundImage.setFitHeight(background.getHeight() / 1.25);
        foregroundImage.setFitWidth(background.getWidth() / 1.25);

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.BLACK);
        shadow.setRadius(10);
        shadow.setOffsetX(3);
        shadow.setOffsetY(3);
        shadow.setBlurType(BlurType.GAUSSIAN);

        stackPane.setEffect(shadow);

        stackPane.getChildren().addAll(background, foregroundImage);

        label.setFont(new Font("Helvetica Neue",width/4));
        label.setTextFill(Color.WHITE);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(Pos.CENTER);
        DropShadow labelShadow = new DropShadow();
        labelShadow.setColor(Color.BLACK);
        labelShadow.setRadius(10);
        labelShadow.setSpread(0.5);
        labelShadow.setBlurType(BlurType.GAUSSIAN);
        label.setEffect(labelShadow);
        label.textProperty().bind(labelText);

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.addRow(0, stackPane);
        gridPane.addRow(1, label);
        GridPane.setHalignment(label, HPos.CENTER);

        getChildren().add(gridPane);

        setOnMouseClicked((event -> {
            ScaleTransition transition = new ScaleTransition(Duration.millis(25), this);
            transition.setToY(0.9);
            transition.setToX(0.9);
            transition.play();
            transition.setOnFinished(event1 -> {
                transition.stop();
                ScaleTransition transition1 = new ScaleTransition(Duration.millis(25), this);
                transition1.setFromX(0.9);
                transition1.setFromY(0.9);
                transition1.setToY(1.0);
                transition1.setToX(1.0);
                transition1.play();
            });

        }));
    }

    public void setLabel(String text) {
        labelText.setValue(text);
    }

    public void setWidth(int width){
        background.setWidth(width);
    }

    public void setHeight(int height){
        background.setWidth(height);
    }

    public DoubleProperty widthProperty() {
        return background.widthProperty();
    }

    public DoubleProperty heightProperty() {
        return background.heightProperty();
    }

    public void setOnMouseClickedEvent(EventHandler<? super MouseEvent> event){
        addEventHandler(MouseEvent.MOUSE_CLICKED, event);
    }
}
