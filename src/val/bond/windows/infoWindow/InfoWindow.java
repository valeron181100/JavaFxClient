package val.bond.windows.infoWindow;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import val.bond.resources.logic.OldNewLogicConnector;


public class InfoWindow extends Application {

    StringProperty textContentProperty = new SimpleStringProperty();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = FXMLLoader.load(getClass().getResource("info_window.fxml"));
        primaryStage.setTitle("Info Window");
        root.getStylesheets().add("val/bond/windows/stylesheet1.css");
        Scene scene = new Scene(root, 700, 400);
        primaryStage.setScene(scene);





        ((Label)((ScrollPane)root.getChildren().get(0)).getContent()).setText(OldNewLogicConnector.infoWindowConnetionStr);


        primaryStage.show();
    }
}
