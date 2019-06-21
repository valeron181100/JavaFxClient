package val.bond.windows.authWindowPackage;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import val.bond.applogic.mainpkg.ClientMain;
import val.bond.internalization.Props;
import val.bond.resources.loadingResources.GifLoader;
import val.bond.resources.loadingResources.SettingsManager;

public class AuthWindow extends Application {

    private DoubleProperty headerFontSize = new SimpleDoubleProperty(30);

    private DoubleProperty buttonFontSize = new SimpleDoubleProperty(16);

    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane root = FXMLLoader.load(getClass().getResource("authWindowPackage.fxml"));
        root.getStylesheets().add("val/bond/windows/stylesheet1.css");
        primaryStage.setTitle("AuthWindow");
        Scene scene = new Scene(root, 480, 340);
        primaryStage.setScene(scene);
        ClientMain.main(new String[]{});

        setLocale(root);

        //Binding header font size
        headerFontSize.bind(scene.widthProperty().add(scene.heightProperty()).divide(30));
        Label label = (Label) ((HBox)root.getChildren().get(1)).getChildren().get(0);
        label.styleProperty().bind(Bindings.concat("-fx-font-size: ",headerFontSize.asString()));

        GifLoader gifLoader = new GifLoader();

        buttonFontSize.bind(scene.widthProperty().add(scene.heightProperty()).divide(70));
        ((TilePane)root.getChildren().get(2)).getChildren().forEach(p->p.styleProperty().bind(Bindings.concat(
                "-fx-font-size: ",buttonFontSize.asString()
        )));

        primaryStage.show();

        int k = 0;
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void setLocale(Pane root){
        Props.loadProps(SettingsManager.getSetting("locale"));

        Label headerLabel = (Label)root.getScene().lookup("#HeadLabel");
        Button loginButton = (Button)root.getScene().lookup("#loginButton");
        Button regButton = (Button)root.getScene().lookup("#regButton");
        Button okRegButton = (Button)root.getScene().lookup("#okRegButton");
        Button cancelRegButton = (Button)root.getScene().lookup("#cancelRegButton");
        Button okLogButton = (Button)root.getScene().lookup("#okLogButton");
        Button cancelLogButton = (Button)root.getScene().lookup("#cancelLogButton");
        TextArea regPasswordTA = (TextArea)root.getScene().lookup("#regPasswordTA");
        TextArea regLoginTA = (TextArea)root.getScene().lookup("#regLoginTA");
        TextArea logPasswordTA = (TextArea)root.getScene().lookup("#logPasswordTA");
        TextArea logLoginTA = (TextArea)root.getScene().lookup("#logLoginTA");

        headerLabel.setText(Props.getProps().getProperty("authWindow.header_text"));
        loginButton.setText(Props.getProps().getProperty("authWindow.login_text"));
        regButton.setText(Props.getProps().getProperty("authWindow.reg_text"));
        okRegButton.setText(Props.getProps().getProperty("ok_text"));
        cancelRegButton.setText(Props.getProps().getProperty("cancel_text"));
        okLogButton.setText(Props.getProps().getProperty("ok_text"));
        cancelLogButton.setText(Props.getProps().getProperty("cancel_text"));
        regPasswordTA.setPromptText(Props.getProps().getProperty("authWindow.password"));
        regLoginTA.setPromptText(Props.getProps().getProperty("login"));
        logPasswordTA.setPromptText(Props.getProps().getProperty("authWindow.password"));
        logLoginTA.setPromptText(Props.getProps().getProperty("login"));
    }
}
