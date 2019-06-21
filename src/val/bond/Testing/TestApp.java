package val.bond.Testing;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import val.bond.internalization.Props;

import java.util.Properties;


public class TestApp extends Application {




    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = FXMLLoader.load(getClass().getResource("test.fxml"));
        primaryStage.setTitle("TestApp");
        root.getStylesheets().add("val/bond/windows/stylesheet1.css");
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setMinHeight(800);
        primaryStage.setMinWidth(1300);
        primaryStage.setScene(scene);

        Props.loadProps("hu_HU");

        Properties properties = Props.getProps();



        Label label = new Label(properties.getProperty("login"));

        root.getChildren().add(label);


        int k = 0;



        /*TableView<SimpleClothes> costumeTableView = new TableView<>();
        costumeTableView.setItems(FXCollections.observableArrayList(SimpleClothes.devideCostumeOnSimple(costume)));

        TableColumn<SimpleClothes, String> clothesPartNameCol = new TableColumn<>();
        clothesPartNameCol.setCellValueFactory(new PropertyValueFactory<>("clothesPartName"));

        TableColumn<SimpleClothes, Integer> sizeCol = new TableColumn<>("size");
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));

        TableColumn<SimpleClothes, Color> colorCol = new TableColumn<>("color");
        colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));

        TableColumn<SimpleClothes, Boolean> isForManCol = new TableColumn<>("isForMan");
        isForManCol.setCellValueFactory(new PropertyValueFactory<>("isForMan"));

        TableColumn<SimpleClothes, Material> materialCol = new TableColumn<>("material");
        materialCol.setCellValueFactory(new PropertyValueFactory<>("material"));

        TableColumn<SimpleClothes, String> nameCol = new TableColumn<>("name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<SimpleClothes, Integer> growthSmCol = new TableColumn<>("growthSm");
        growthSmCol.setCellValueFactory(new PropertyValueFactory<>("growthSm"));

        TableColumn<SimpleClothes, Integer> handSmLengthCol = new TableColumn<>("handSmLength");
        handSmLengthCol.setCellValueFactory(new PropertyValueFactory<>("handSmLength"));

        TableColumn<SimpleClothes, Boolean> isHoodCol = new TableColumn<>("isHood");
        isHoodCol.setCellValueFactory(new PropertyValueFactory<>("isHood"));

        TableColumn<SimpleClothes, Integer> legLengthSmCol = new TableColumn<>("legLengthSm");
        legLengthSmCol.setCellValueFactory(new PropertyValueFactory<>("legLengthSm"));

        TableColumn<SimpleClothes, Integer> diametrLegSmCol = new TableColumn<>("diametrLegSm");
        diametrLegSmCol.setCellValueFactory(new PropertyValueFactory<>("diametrLegSm"));

        TableColumn<SimpleClothes, Integer> sexLvlCol = new TableColumn<>("sexLvl");
        sexLvlCol.setCellValueFactory(new PropertyValueFactory<>("sexLvl"));

        TableColumn<SimpleClothes, Integer> cylinderHeightSmCol = new TableColumn<>("cylinderHeightSm");
        cylinderHeightSmCol.setCellValueFactory(new PropertyValueFactory<>("cylinderHeightSm"));

        TableColumn<SimpleClothes, Integer> visorHeightSmCol = new TableColumn<>("visorHeightSm");
        visorHeightSmCol.setCellValueFactory(new PropertyValueFactory<>("visorHeightSm"));

        TableColumn<SimpleClothes, Boolean> isShoelacesCol = new TableColumn<>("isShoelaces");
        isShoelacesCol.setCellValueFactory(new PropertyValueFactory<>("isShoelaces"));

        TableColumn<SimpleClothes, Material> outsoleMaterialCol = new TableColumn<>("outsoleMaterial");
        outsoleMaterialCol.setCellValueFactory(new PropertyValueFactory<>("outsoleMaterial"));


        costumeTableView.getColumns().addAll(clothesPartNameCol, sizeCol, colorCol, isForManCol, materialCol, nameCol, growthSmCol,
                handSmLengthCol, isHoodCol, legLengthSmCol, diametrLegSmCol, sexLvlCol, cylinderHeightSmCol, visorHeightSmCol,
                isShoelacesCol, outsoleMaterialCol);*/
        //root.getChildren().add();
        primaryStage.show();

    }



}
