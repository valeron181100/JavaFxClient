package resources.customControlls;

import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Parent;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class ToggleSwitch extends Parent {

    private String onHintText = "On";
    private String offHintText = "Off";

    private StringProperty hintTextProperty = new SimpleStringProperty();

    private BooleanProperty switchedOn = new SimpleBooleanProperty(false);


    private TranslateTransition translateAnimation = new TranslateTransition(Duration.seconds(0.25));
    private FillTransition fillAnimation = new FillTransition(Duration.seconds(0.25));
    private ParallelTransition animation = new ParallelTransition(translateAnimation, fillAnimation);

    public BooleanProperty switchedOnProperty() {
        return switchedOn;
    }

    public void setOnHintText(String text){
        onHintText = text;
    }

    public void setOffHintText(String text){
        offHintText = text;
    }

    public StringProperty getHintTextProperty() {
        return hintTextProperty;
    }

    public ToggleSwitch(int size) {

        hintTextProperty.setValue(offHintText);

        Rectangle background = new Rectangle(size * 2, size);
        background.setArcWidth(size);
        background.setArcHeight(size);
        background.setFill(Color.WHITE);
        background.setStroke(Color.LIGHTGRAY);
        background.setStrokeWidth(4);
        InnerShadow backgroundInnerShadow = new InnerShadow();
        backgroundInnerShadow.setRadius(5);
        backgroundInnerShadow.setBlurType(BlurType.GAUSSIAN);
        background.setEffect(backgroundInnerShadow);

        Circle trigger = new Circle(size / 2);
        trigger.setCenterX(size / 2);
        trigger.setCenterY(size / 2);
        trigger.setFill(Color.WHITE);
        trigger.setStroke(Color.LIGHTGRAY);

        DropShadow shadow = new DropShadow();
        shadow.setRadius(2);
        shadow.setBlurType(BlurType.GAUSSIAN);
        trigger.setEffect(shadow);



        translateAnimation.setNode(trigger);
        fillAnimation.setShape(background);

        getChildren().addAll(background,trigger);

        switchedOn.addListener((obs, oldState, newState) -> {
            boolean isOn = newState.booleanValue();
            translateAnimation.setToX(isOn ? size * 2 - size : 0);
            fillAnimation.setFromValue(isOn ? Color.WHITE : Color.LIGHTGREEN);
            fillAnimation.setToValue(isOn ? Color.LIGHTGREEN : Color.WHITE);
            animation.play();
            animation.setOnFinished(event -> hintTextProperty.setValue( isOn ? onHintText : offHintText));
        });

        setOnMouseClicked(event -> {
            switchedOn.set(!switchedOn.get());
        });
    }
}
