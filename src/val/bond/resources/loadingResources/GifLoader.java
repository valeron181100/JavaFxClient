package val.bond.resources.loadingResources;

import javafx.scene.image.Image;

public class GifLoader {
    private static Image loadingImg;
    private static final String loadingGifPath = "@../../val/bond/images/loading.gif";

    public GifLoader(){
        loadingImg = new Image(loadingGifPath);
    }

    public static Image getLoadingImg() {
        return loadingImg;
    }
}
