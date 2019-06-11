package windows.ipWindow;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import resources.loadingResources.GifLoader;

public class IpWindow extends Application {

    private DoubleProperty headerFontSize = new SimpleDoubleProperty(30);

    private DoubleProperty buttonFontSize = new SimpleDoubleProperty(16);

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = FXMLLoader.load(getClass().getResource("ipWindow.fxml"));
        root.getStylesheets().add("windows/stylesheet1.css");
        primaryStage.setTitle("IPWindow");
        Scene scene = new Scene(root, 480, 340);
        primaryStage.setScene(scene);

        headerFontSize.bind(scene.widthProperty().add(scene.heightProperty()).divide(30));
        Label label = (Label) ((HBox)root.getChildren().get(1)).getChildren().get(0);
        label.styleProperty().bind(Bindings.concat("-fx-font-size: ",headerFontSize.asString()));

        GifLoader gifLoader = new GifLoader();

        buttonFontSize.bind(scene.widthProperty().add(scene.heightProperty()).divide(70));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
