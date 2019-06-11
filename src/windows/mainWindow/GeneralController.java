package windows.mainWindow;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class GeneralController {
    @FXML
    public static ImageView productPreviewLoader;
    @FXML
    public TilePane commandTilePane;
    public TilePane collectionTilePane;
    public HBox profileHBox;
    public GridPane generalGridPane;
    public GridPane rightContentGridPane;
    public GridPane profileGridPane;
    public GridPane mainWorkSpaceGridPane;
    public BorderPane editColBorderPane;
    public VBox avatarBox;
    public ToolBar appToolBar;
    public VBox sidePanel;
    public StackPane darkStackPane;
    public HBox settingsSideHBox;
    public HBox mapSideHBox;
    public HBox collectionSideHBox;
    public GridPane mapWorkGridPane;
    public GridPane settingsWorkGridPane;
    public GridPane collectionWorkGridPane;

    private boolean isSidePanelOpen = false;

    private Interpolator exponentialEaseOutInterpolator = new Interpolator() {
        @Override
        protected double curve(double t) {
            return (t == 1.0) ? 1.0 : 1 - Math.pow(2.0, -10 * t);
        }
    };

    public void MenuButtonClick(ActionEvent event) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(300));
        transition.setNode(sidePanel);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(300));
        fadeTransition.setNode(darkStackPane);
        transition.setInterpolator(exponentialEaseOutInterpolator);

        if(!isSidePanelOpen) {
            darkStackPane.setDisable(false);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(0.5);
            transition.setFromX(-350);
            transition.setToX(0);
        }
        else{
            darkStackPane.setDisable(true);
            fadeTransition.setFromValue(0.5);
            fadeTransition.setToValue(0.0);
            transition.setFromX(0);
            transition.setToX(-350);
        }

        fadeTransition.play();
        transition.play();
        isSidePanelOpen = !isSidePanelOpen;
    }

    public void settingsSideHBoxMouseClick(MouseEvent mouseEvent) {
        System.out.println("Hello Settings");

        //Change work panes animation
        GridPane futurePane = settingsWorkGridPane;
        GridPane showsGridPane = null;
        if(!mapWorkGridPane.isDisable())
            showsGridPane = mapWorkGridPane;
        if(!settingsWorkGridPane.isDisable())
            showsGridPane = settingsWorkGridPane;
        if(!collectionWorkGridPane.isDisable())
            showsGridPane = collectionWorkGridPane;

        FadeTransition showsTransition = new FadeTransition(Duration.millis(300), showsGridPane);
        showsTransition.setFromValue(1.0);
        showsTransition.setToValue(0.0);
        showsTransition.play();
        showsGridPane.setDisable(true);
        showsGridPane.setVisible(false);
        showsGridPane.setOpacity(1.0);

        /*double futurePanePos = generalGridPane.getWidth() + 30;
        futurePane.setTranslateX(futurePanePos);*/
        futurePane.setOpacity(0.0);
        futurePane.setVisible(true);
        futurePane.setDisable(false);

        FadeTransition futureTransition = new FadeTransition(Duration.millis(300), futurePane);
        futureTransition.setFromValue(0.0);
        futureTransition.setToValue(1.0);
        futureTransition.play();





        //Settings button click animation
        ScaleTransition transition = new ScaleTransition(Duration.millis(25), settingsSideHBox);
        transition.setToY(0.9);
        transition.setToX(0.9);
        transition.play();
        transition.setOnFinished(event1 -> {
            transition.stop();
            ScaleTransition transition1 = new ScaleTransition(Duration.millis(25), settingsSideHBox);
            transition1.setFromX(0.9);
            transition1.setFromY(0.9);
            transition1.setToY(1.0);
            transition1.setToX(1.0);
            transition1.play();
        });


    }

    public void mapSideHBoxMouseClick(MouseEvent mouseEvent) {
        System.out.println("Hello Map");

        //Change work panes animation
        GridPane futurePane = mapWorkGridPane;
        GridPane showsGridPane = null;
        if(!mapWorkGridPane.isDisable())
            showsGridPane = mapWorkGridPane;
        if(!settingsWorkGridPane.isDisable())
            showsGridPane = settingsWorkGridPane;
        if(!collectionWorkGridPane.isDisable())
            showsGridPane = collectionWorkGridPane;

        FadeTransition showsTransition = new FadeTransition(Duration.millis(300), showsGridPane);
        showsTransition.setFromValue(1.0);
        showsTransition.setToValue(0.0);
        showsTransition.play();
        showsGridPane.setDisable(true);
        showsGridPane.setVisible(false);
        showsGridPane.setOpacity(1.0);

        /*double futurePanePos = generalGridPane.getWidth() + 30;
        futurePane.setTranslateX(futurePanePos);*/
        futurePane.setOpacity(0.0);
        futurePane.setVisible(true);
        futurePane.setDisable(false);

        FadeTransition futureTransition = new FadeTransition(Duration.millis(300), futurePane);
        futureTransition.setFromValue(0.0);
        futureTransition.setToValue(1.0);
        futureTransition.play();





        //Settings button click animation
        ScaleTransition transition = new ScaleTransition(Duration.millis(25), mapSideHBox);
        transition.setToY(0.9);
        transition.setToX(0.9);
        transition.play();
        transition.setOnFinished(event1 -> {
            transition.stop();
            ScaleTransition transition1 = new ScaleTransition(Duration.millis(25), mapSideHBox);
            transition1.setFromX(0.9);
            transition1.setFromY(0.9);
            transition1.setToY(1.0);
            transition1.setToX(1.0);
            transition1.play();
        });
    }

    public void collectionSideHBoxMouseClick(MouseEvent mouseEvent) {
        System.out.println("Hello Col");

        //Change work panes animation
        GridPane futurePane = collectionWorkGridPane;
        GridPane showsGridPane = null;
        if(!mapWorkGridPane.isDisable())
            showsGridPane = mapWorkGridPane;
        if(!settingsWorkGridPane.isDisable())
            showsGridPane = settingsWorkGridPane;
        if(!collectionWorkGridPane.isDisable())
            showsGridPane = collectionWorkGridPane;

        FadeTransition showsTransition = new FadeTransition(Duration.millis(300), showsGridPane);
        showsTransition.setFromValue(1.0);
        showsTransition.setToValue(0.0);
        showsTransition.play();
        showsGridPane.setDisable(true);
        showsGridPane.setVisible(false);
        showsGridPane.setOpacity(1.0);

        /*double futurePanePos = generalGridPane.getWidth() + 30;
        futurePane.setTranslateX(futurePanePos);*/
        futurePane.setOpacity(0.0);
        futurePane.setVisible(true);
        futurePane.setDisable(false);

        FadeTransition futureTransition = new FadeTransition(Duration.millis(300), futurePane);
        futureTransition.setFromValue(0.0);
        futureTransition.setToValue(1.0);
        futureTransition.play();





        //Settings button click animation

        ScaleTransition transition = new ScaleTransition(Duration.millis(25), collectionSideHBox);
        transition.setToY(0.9);
        transition.setToX(0.9);
        transition.play();
        transition.setOnFinished(event1 -> {
            transition.stop();
            ScaleTransition transition1 = new ScaleTransition(Duration.millis(25), collectionSideHBox);
            transition1.setFromX(0.9);
            transition1.setFromY(0.9);
            transition1.setToY(1.0);
            transition1.setToX(1.0);
            transition1.play();
        });


    }

    public void darkStackPaneClick(MouseEvent mouseEvent) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(300));
        transition.setNode(sidePanel);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(300));
        fadeTransition.setNode(darkStackPane);
        transition.setInterpolator(exponentialEaseOutInterpolator);

        if(!isSidePanelOpen) {
            darkStackPane.setDisable(false);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(0.5);
            transition.setFromX(-350);
            transition.setToX(0);
        }
        else{
            darkStackPane.setDisable(true);
            fadeTransition.setFromValue(0.5);
            fadeTransition.setToValue(0.0);
            transition.setFromX(0);
            transition.setToX(-350);
        }

        fadeTransition.play();
        transition.play();
        isSidePanelOpen = !isSidePanelOpen;
    }

}
