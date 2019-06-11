package resources.loadingResources;

import javafx.scene.image.Image;

public class GifLoader {
    private static Image loadingImg;
    private static final String loadingGifPath = "file:F:\\IntelliJIdea\\Projects\\JavaFxProject\\images\\loading.gif";

    public GifLoader(){
        loadingImg = new Image(loadingGifPath);
    }

    public static Image getLoadingImg() {
        return loadingImg;
    }
}
