package val.bond.windows.loadingWindow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import val.bond.resources.loadingResources.GifLoader;
import val.bond.resources.logic.OldNewLogicConnector;


public class LoadingWindow extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = FXMLLoader.load(getClass().getResource("loadingWindow.fxml"));
        primaryStage.setTitle("Loading Window");
        root.getStylesheets().add("val/bond/windows/stylesheet1.css");
        Scene scene = new Scene(root, 700, 400);
        primaryStage.setScene(scene);

        ((ImageView)root.getChildren().get(0)).setImage(GifLoader.getLoadingImg());

        OldNewLogicConnector.isConnected.addListener(((observable, oldValue, newValue) -> {
            if(newValue){
                primaryStage.close();
            }
        }));

        primaryStage.show();
    }
}
