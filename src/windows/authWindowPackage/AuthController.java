package windows.authWindowPackage;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import windows.mainWindow.GeneralWindow;

public class AuthController {
    @FXML
    public Button loginButton;

    @FXML
    public Button regButton;
    @FXML
    public GridPane MainGridPane;
    @FXML
    public static Label HeadLabel;
    @FXML
    public TilePane loginRegButtonTilePane;
    @FXML
    public VBox regPanel;
    @FXML
    public VBox loginPanel;

    @FXML
    protected void OnLoginRegButtonClick(ActionEvent event){
        Button srcButton = (Button) event.getSource();
        FadeTransition fadeLRBTilePane = new FadeTransition(Duration.millis(1000), loginRegButtonTilePane);
        FadeTransition fadeRPanel;
        if(srcButton.getId().equals("loginButton")){
            fadeRPanel = new FadeTransition(Duration.millis(1000), loginPanel);
        }
        else {
            fadeRPanel = new FadeTransition(Duration.millis(1000), regPanel);
        }

        fadeLRBTilePane.setFromValue(1.0);
        fadeLRBTilePane.setToValue(0.0);

        fadeRPanel.setFromValue(0.0);
        fadeRPanel.setToValue(1.0);

        fadeLRBTilePane.play();
        loginRegButtonTilePane.setDisable(true);
        if(srcButton.getId().equals("loginButton"))
            loginPanel.setDisable(false);
        else
            regPanel.setDisable(false);
        fadeRPanel.play();
    }

    @FXML
    public void onCancelAuthButtonClick(ActionEvent event) {
        Button srcButton = (Button) event.getSource();
        FadeTransition fadeLRBTilePane = new FadeTransition(Duration.millis(1000), loginRegButtonTilePane);
        FadeTransition fadeRPanel;
        if(srcButton.getId().equals("cancelLogButton")){
            fadeRPanel = new FadeTransition(Duration.millis(1000), loginPanel);
        }
        else {
            fadeRPanel = new FadeTransition(Duration.millis(1000), regPanel);
        }

        fadeLRBTilePane.setFromValue(0.0);
        fadeLRBTilePane.setToValue(1.0);

        fadeRPanel.setFromValue(1.0);
        fadeRPanel.setToValue(0.0);


        if(srcButton.getId().equals("cancelLogButton"))
            loginPanel.setDisable(true);
        else
            regPanel.setDisable(true);
        fadeRPanel.play();

        fadeLRBTilePane.play();
        loginRegButtonTilePane.setDisable(false);
    }

    public void onOkLogButtonClick(ActionEvent event) throws Exception {

        new GeneralWindow().start(new Stage());
    }
}
