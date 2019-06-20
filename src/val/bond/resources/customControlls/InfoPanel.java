package val.bond.resources.customControlls;

import javafx.animation.FadeTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;



public class InfoPanel extends Parent {
    private StringProperty textContentProperty = new SimpleStringProperty();

    private DoubleProperty textSizeProperty = new SimpleDoubleProperty(0.0);

    private Button button = new Button("×");

    public InfoPanel(int width, String text){
        textContentProperty.setValue(text);

        setDisable(true);
        setOpacity(0.0);

        StackPane pane = new StackPane();
        pane.setMinWidth(width);
        pane.setMaxWidth(width);

        Rectangle background = new Rectangle();
        background.setArcHeight(50);
        background.setArcWidth(50);
        background.setFill(Color.web("#000000a4"));
        background.heightProperty().bind(pane.heightProperty());
        background.widthProperty().bind(pane.widthProperty());

        VBox vbox = new VBox();
        StackPane.setAlignment(vbox, Pos.CENTER);

        Label label = new Label();
        label.setPadding(new Insets(20,20,20,20));
        label.setMaxWidth(width);
        label.setFont(new Font("Helvetica Neue", 15));
        label.setTextFill(Color.WHITE);
        label.setWrapText(true);
        label.textProperty().bind(textContentProperty);
            /*textSizeProperty.bind(pane.widthProperty().add(pane.heightProperty()).divide(10));
            label.styleProperty().bind(Bindings.concat("-fx-font-size: ",textSizeProperty.asString()));*/

        ScrollPane labelScrollPane = new ScrollPane();
        labelScrollPane.setContent(label);
        labelScrollPane.setMaxWidth(width);
        labelScrollPane.setMaxHeight(width / 2);

        button.setAlignment(Pos.CENTER_RIGHT);
        button.getStyleClass().add("close_info_panel_button_style");
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            FadeTransition disappear = new FadeTransition(Duration.millis(1000), this);
            disappear.setFromValue(1.0);
            disappear.setToValue(0.0);
            disappear.play();
            this.setDisable(true);
        });



        vbox.setAlignment(Pos.CENTER_RIGHT);
        vbox.getChildren().addAll(button, labelScrollPane);


        pane.getChildren().addAll(background, vbox);

        getChildren().add(pane);

    }

    public void show(){
        Thread thread = new Thread(()->{
            FadeTransition appear = new FadeTransition(Duration.millis(1000), this);
            appear.setFromValue(0.0);
            appear.setToValue(1.0);
            this.setDisable(false);
            appear.play();
        });

        thread.start();

    }

    public void setTextContent(String text) {
        this.textContentProperty.set(text);
    }

    public String getTextContent() {
        return this.textContentProperty.get();
    }

    public StringProperty getTextContentProperty() {
        return textContentProperty;
    }

    public void addCloseButtonClickEvent(EventHandler<MouseEvent> handler){
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
    }
}
