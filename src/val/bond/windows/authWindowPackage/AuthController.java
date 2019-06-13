package val.bond.windows.authWindowPackage;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import val.bond.applogic.NetStuff.Net.User;
import val.bond.applogic.mainpkg.ClientMain;
import val.bond.resources.customControlls.ObservableChangeListener;
import val.bond.resources.logic.OldNewLogicConnector;
import val.bond.windows.mainWindow.GeneralWindow;

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
    public TextArea logPasswordTA;
    public TextArea logLoginTA;

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
        String login = logLoginTA.getText();
        String password = logPasswordTA.getText();


        if(login.length() == 0 || ClientMain.findMatches("[\\?\\\\!/\\^\\%\\$\\;\\#\\'\\(\\)\\+\\=\\`\\~]", login).size() != 0){
            Alert loginAlert = new Alert(Alert.AlertType.WARNING);
            loginAlert.setHeaderText("Неправильный ввод логина");
            loginAlert.setContentText("Логин не должен содержать специальных символов!");
        }else
        if (password.length() < 8  || ClientMain.findMatches("[\\?\\\\!/\\^\\%\\$\\;\\#\\'\\(\\)\\+\\=\\`\\~]", password).size() != 0){
            Alert passwordAlert = new Alert(Alert.AlertType.WARNING);
            passwordAlert.setHeaderText("Неверный ввод пароля");
            passwordAlert.setContentText("Пароль должен быть не менее 8-ми символов и не содержать специальных символов!");
        }else{
            User currentUser = new User();
            currentUser.setLogin(login);
            currentUser.setPassword(password);
            OldNewLogicConnector.currentUser = currentUser;

            ObservableChangeListener<String> eChangeListener = (observable, oldValue, newValue) -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Ошибка авторизации!");
                alert.setContentText(newValue);
                alert.show();
                OldNewLogicConnector.errorResponse.clearListeners();
                OldNewLogicConnector.authResponse.clearListeners();
            };
            ObservableChangeListener<String> aChangeListener = (observable, oldValue, newValue) -> {
                try {
                    new GeneralWindow().start(new Stage());
                    Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                    stage.close();
                    OldNewLogicConnector.errorResponse.clearListeners();
                    OldNewLogicConnector.authResponse.clearListeners();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }

            };
            OldNewLogicConnector.errorResponse.addListener(eChangeListener);
            OldNewLogicConnector.authResponse.addListener(aChangeListener);


            OldNewLogicConnector.commandLine.setValue("login {" + currentUser.getLogin() + "} {" + password + "}");

        }
    }
}
