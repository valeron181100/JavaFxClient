package val.bond.resources.models;

import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.PopupWindow;
import javafx.util.Duration;
import org.json.JSONObject;
import val.bond.resources.loadingResources.ImagePathConst;

import java.util.ArrayList;

public class CostumeTemplate extends CommandModel{
    private String costumeName;
    private int costumeId;
    private String ownerUser;
    private RotateTransition imageRotateTransition;
    private ImageView removeImageView;
    private ArrayList<CommandClickEvent> removeIconClickEvents;

    private Pane clickGridPane;

    private boolean isClickPaneConnected = false;
    private boolean isClickPaneVisible = false;

    private GridPane generalGridPane;
    private int rotateSign;
    public CostumeTemplate(int width, int height, String costumeDataJson) {
        super(width, height, ImagePathConst.getCrownPng());

        JSONObject costumeData = new JSONObject(costumeDataJson);
        costumeName = costumeData.getString("costumeName");
        costumeId = costumeData.getInt("costumeId");
        ownerUser = costumeData.getString("costumeLogin");
        generalGridPane = getGridPane();
        removeIconClickEvents = new ArrayList<>();
        imageRotateTransition = new RotateTransition(Duration.millis(150), generalGridPane);
        rotateSign = ((int) (Math.random() * 100)) > 50 ? 1 : -1;
        imageRotateTransition.setFromAngle(0);
        imageRotateTransition.setToAngle(2 * rotateSign);

        Tooltip tooltip = new Tooltip("costumeId: " + costumeId + "\nowner: " + ownerUser);
        tooltip.setFont(new Font("Helvetica Neue",14));
        tooltip.setAnchorLocation(PopupWindow.AnchorLocation.WINDOW_BOTTOM_LEFT);
        Tooltip.install(generalGridPane, tooltip);

        getChildren().clear();
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(generalGridPane);
        Image removeImage = new Image(ImagePathConst.getRemoveCostumeIconPng());
        removeImageView = new ImageView(removeImage);
        removeImageView.setFitWidth(20);
        removeImageView.setFitHeight(20);
        removeImageView.setScaleX(0);
        removeImageView.setScaleY(0);
        StackPane.setAlignment(removeImageView, Pos.TOP_RIGHT);
        stackPane.getChildren().add(removeImageView);
        getChildren().add(stackPane);
        setOnMouseClickedEvent(event->{
            if(isClickPaneConnected){
                FadeTransition fadeTransition = new FadeTransition(Duration.millis(150), clickGridPane);
                fadeTransition.setFromValue(0.0);
                fadeTransition.setToValue(1.0);
                fadeTransition.play();
                clickGridPane.setDisable(false);
                isClickPaneVisible = true;
            }
        });
        removeImageView.setOnMouseClicked(event -> {
            removeIconClickEvents.forEach(p -> p.clicked(this));
            ScaleTransition transition = new ScaleTransition(Duration.millis(25), removeImageView);
            transition.setToY(0.9);
            transition.setToX(0.9);
            transition.play();
            transition.setOnFinished(event1 -> {
                transition.stop();
                ScaleTransition transition1 = new ScaleTransition(Duration.millis(25), removeImageView);
                transition1.setFromX(0.9);
                transition1.setFromY(0.9);
                transition1.setToY(1.0);
                transition1.setToX(1.0);
                transition1.play();
            });
        });
    }

    public void playEditAnimation(){
        imageRotateTransition.setOnFinished((event) -> {
            imageRotateTransition.setFromAngle(2 * rotateSign);
            imageRotateTransition.setToAngle(2 * (- rotateSign));
            imageRotateTransition.setCycleCount(Timeline.INDEFINITE);
            imageRotateTransition.setAutoReverse(true);
            imageRotateTransition.play();
        });
        imageRotateTransition.play();
        ScaleTransition rmIconTransition = new ScaleTransition(Duration.millis(100), removeImageView);
        rmIconTransition.setFromX(0);
        rmIconTransition.setFromY(0);
        rmIconTransition.setToX(1);
        rmIconTransition.setToY(1);
        rmIconTransition.play();
    }

    public void stopEditAnimation(){
        ScaleTransition rmIconTransition = new ScaleTransition(Duration.millis(100), removeImageView);
        rmIconTransition.setFromX(1);
        rmIconTransition.setFromY(1);
        rmIconTransition.setToX(0);
        rmIconTransition.setToY(0);
        rmIconTransition.play();
        imageRotateTransition.stop();
        RotateTransition rt1 = new RotateTransition(Duration.millis(150), generalGridPane);
        rt1.setFromAngle(generalGridPane.getRotate());
        rt1.setToAngle(0);
        rt1.play();
    }

    public String getCostumeName() {
        return costumeName;
    }

    public int getCostumeId() {
        return costumeId;
    }

    public String getOwnerUser() {
        return ownerUser;
    }

    public void setColor(Paint color){
        getBackground().setFill(color);
    }

    public void addRemoveIconClickEvent(CommandClickEvent event){
        removeIconClickEvents.add(event);
    }


    public void setClickPane(int height, int width, Pane clickPane) {
        clickGridPane = clickPane;
        clickGridPane.setMaxHeight(height);
        clickGridPane.setPrefHeight(height);
        clickGridPane.setMaxWidth(width);
        clickGridPane.setPrefWidth(width);
        clickGridPane.setMinHeight(height);
        clickGridPane.setMinWidth(width);
        clickGridPane.setOpacity(0);
        clickGridPane.setDisable(true);
        isClickPaneVisible = false;
        isClickPaneConnected = true;
    }

    public void playDissappearClickPaneAnim(){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(150), clickGridPane);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.play();
        clickGridPane.setDisable(true);
        isClickPaneVisible = true;
    }
}
