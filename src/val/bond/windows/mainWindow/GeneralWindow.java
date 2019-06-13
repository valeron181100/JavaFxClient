package val.bond.windows.mainWindow;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import val.bond.resources.customControlls.CostumeTemplateGroup;
import val.bond.resources.customControlls.ToggleSwitch;
import val.bond.resources.loadingResources.ImagePathConst;
import val.bond.resources.logic.OldNewLogicConnector;
import val.bond.resources.models.CommandClickEvent;
import val.bond.resources.models.CommandModel;
import val.bond.resources.models.CostumeTemplate;
import val.bond.resources.personalization.ColorTheme;
import val.bond.windows.addCommandWindow.AddCommandWindow;


public class GeneralWindow extends Application {

    DoubleProperty profileTilePaneWidth = new SimpleDoubleProperty();

    private Label hintSwitcherText;

    protected static Pane root;

    public static Node searchElement(Node root, String id){
        int i = 0;

        if(root.getId() != null && root.getId().equals(id))
            return root;

        if(root instanceof Pane) {
            for (Node node : ((Pane) root).getChildren()) {
                if(node.getId() != null && node.getId().equals(id))
                    return node;
                else
                    if(node instanceof Pane || node instanceof SplitPane || node instanceof ToolBar || node instanceof ScrollPane){
                        int k = 0;
                        Node res =  searchElement(node, id);
                        if(res != null)
                            return res;
                    }
                    else return null;
            }
        }
        else{
            if(root instanceof SplitPane){
                for (Node node : ((SplitPane) root).getItems()) {
                    if(node.getId() != null && node.getId().equals(id))
                        return node;
                    else
                    if(node instanceof Pane || node instanceof SplitPane || node instanceof ToolBar || node instanceof ScrollPane){
                        int k = 0;
                        Node res =  searchElement(node, id);
                        if(res != null)
                            return res;
                    }
                    else return null;
                }
            }
            if(root instanceof ToolBar){
                for (Node node : ((ToolBar) root).getItems()) {
                    if(node.getId() != null && node.getId().equals(id))
                        return node;
                    else
                    if(node instanceof Pane || node instanceof SplitPane || node instanceof ToolBar || node instanceof ScrollPane){
                        int k = 0;
                        Node res =  searchElement(node, id);
                        if(res != null)
                            return res;
                    }
                    else return null;
                }
            }
            if(root instanceof ScrollPane){
                Node node = ((ScrollPane) root).getContent();
                    if(node.getId() != null && node.getId().equals(id))
                        return node;
                    else
                    if(node instanceof Pane || node instanceof SplitPane || node instanceof ToolBar || node instanceof ScrollPane){
                        int k = 0;
                        Node res =  searchElement(node, id);
                        if(res != null)
                            return res;
                    }
                    else return null;
            }
        }
        return null;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = FXMLLoader.load(getClass().getResource("generalWindow.fxml"));
        primaryStage.setTitle("ValectionApp");
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setMinHeight(800);
        primaryStage.setMinWidth(1300);
        primaryStage.setScene(scene);

        final CostumeTemplateGroup[] costumeTemplateGroup = new CostumeTemplateGroup[1];


        GridPane profileGridPane = (GridPane) searchElement(root, "profileGridPane");
        TilePane commandsGrid = (TilePane) searchElement(root, "commandTilePane");
        HBox profileHbox = (HBox) searchElement(root, "profileHBox");
        VBox avatarVbox = (VBox) searchElement(root, "avatarBox");
        VBox sidePanel = (VBox) searchElement(root, "sidePanel");
        GridPane mainWorkSpaceGridPane = (GridPane) searchElement(root, "mainWorkSpaceGridPane");
        TilePane collectionTilePane = (TilePane) searchElement(root, "collectionTilePane");

        profileTilePaneWidth.bind(scene.widthProperty());
        profileHbox.widthProperty().addListener((prop, oval, nval)->{
            if(nval.intValue() <= 263 && profileHbox.getChildren().contains(avatarVbox)){

                profileHbox.getChildren().remove(avatarVbox);
            }
            if(nval.intValue() > 263 && !profileHbox.getChildren().contains(avatarVbox)){
                profileHbox.getChildren().add(0,avatarVbox);
            }
        });

        //Setting up toggle switcher to edit collection panel
        ToggleSwitch switcher = new ToggleSwitch(30);

        switcher.switchedOnProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                costumeTemplateGroup[0].getTemplates().forEach(CostumeTemplate::playEditAnimation);
            }
            else {
                costumeTemplateGroup[0].getTemplates().forEach(CostumeTemplate::stopEditAnimation);
            }
        });

        HBox switcherHbox = new HBox();

        hintSwitcherText = new Label();
        hintSwitcherText.setFont(new Font("Helvetica Neue", 15));
        hintSwitcherText.setPadding(new Insets(10,0,0,0));
        hintSwitcherText.textProperty().bind(switcher.getHintTextProperty());

        switcherHbox.getChildren().addAll(hintSwitcherText, switcher);
        switcherHbox.setMargin(switcher, new Insets(0,10,0,0));

        BorderPane editBorderPane = (BorderPane) searchElement(root, "editColBorderPane");
        editBorderPane.setStyle("-fx-border-color: black");
        switcherHbox.setSpacing(10);
        editBorderPane.setRight(switcherHbox);

        //
        Label widthIndicator = new Label();
        widthIndicator.textProperty().bind(profileTilePaneWidth.asString());

        editBorderPane.setLeft(widthIndicator);

        //Filling commands
        int commandIconSize = 55;
        CommandModel[] commands = new CommandModel[]{
                new CommandModel(commandIconSize,commandIconSize, ImagePathConst.getPlusPng(), "Add"),
                new CommandModel(commandIconSize,commandIconSize, ImagePathConst.getMaxPlusPng(), "Add If Max"),
                new CommandModel(commandIconSize,commandIconSize, ImagePathConst.getLoadPng(), "Load", "load"),
                new CommandModel(commandIconSize,commandIconSize, ImagePathConst.getImportPng(), "Import"),

                new CommandModel(commandIconSize,commandIconSize, ImagePathConst.getStartPng(), "Start", "start"),
                new CommandModel(commandIconSize,commandIconSize, ImagePathConst.getInfoPng(), "Info", "info"),
                new CommandModel(commandIconSize,commandIconSize, ImagePathConst.getHelpPng(), "Help", "help"),
                new CommandModel(commandIconSize,commandIconSize, ImagePathConst.getExitPng(), "Exit", "exit"),

                new CommandModel(commandIconSize,commandIconSize, ImagePathConst.getChangeFilePng(), "ChangeDefFile"),
                new CommandModel(commandIconSize,commandIconSize, ImagePathConst.getTrimToMinPng(), "Trim To Min"),
                new CommandModel(commandIconSize,commandIconSize, ImagePathConst.getSavePng(), "Save", "save"),
                new CommandModel(commandIconSize,commandIconSize, ImagePathConst.getRemovePng(), "Remove")
        };



        for (int i = 0; i < commands.length; i++) {
            commandsGrid.getChildren().add(commands[i]);
        }
        commands[2].setOnMouseClickedEvent(commandClickEvent);
        commands[4].setOnMouseClickedEvent(commandClickEvent);
        commands[5].setOnMouseClickedEvent(commandClickEvent);
        commands[6].setOnMouseClickedEvent(commandClickEvent);
        commands[7].setOnMouseClickedEvent(commandClickEvent);
        commands[9].setOnMouseClickedEvent(commandClickEvent);
        commands[10].setOnMouseClickedEvent(commandClickEvent);

        commands[0].setOnMouseClickedEvent(addCommandClickEvent);
        ///Setting up side panel effects

        GaussianBlur sidePanelBlur = new GaussianBlur();
        sidePanelBlur.setRadius(0);

        sidePanel.translateXProperty().addListener((obv,old,nv)->{
            sidePanelBlur.setRadius(sidePanel.getTranslateX() + 10);
        });

        profileGridPane.setEffect(sidePanelBlur);
        mainWorkSpaceGridPane.setEffect(sidePanelBlur);


        setColorTheme(ColorTheme.WHEAT);

        VBox profileLabelsBox = (VBox) searchElement(root, "profileLabelsBox");
        profileLabelsBox.getChildren().forEach(p->{
            if(p instanceof Label){
                if(p.getId().equals("loginValueLabel"))
                    ((Label) p).setText(OldNewLogicConnector.currentUser.getLogin());
                //TODO: getting email command
            }
        });


        OldNewLogicConnector.showResponse.addListener((observable, oldValue, newValue) -> {
            costumeTemplateGroup[0] = new CostumeTemplateGroup(60, newValue);
            TilePane collectionShowTilePane = (TilePane) searchElement(root, "collectionTilePane");
            collectionTilePane.getChildren().clear();
            costumeTemplateGroup[0].getTemplates().forEach(p -> collectionShowTilePane.getChildren().add(p));
        });

        OldNewLogicConnector.commandLine.setValue("show {} {}");


        primaryStage.show();





        //Image loadingImg = GifLoader.getLoadingImg();


    }


    private CommandClickEvent commandClickEvent = (event -> {
        CommandModel model = (CommandModel)event;
        OldNewLogicConnector.commandLine.setValue(model.getCommandLine());
    });

    private CommandClickEvent addCommandClickEvent = (event -> {
        try {
            new AddCommandWindow().start(new Stage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    });


    public void setColorTheme(ColorTheme theme){

        GridPane generalGridPane = ((GridPane)searchElement(root,"generalGridPane"));
        ToolBar appToolBar = ((ToolBar)searchElement(root,"appToolBar"));
        GridPane profileGridPane = ((GridPane)searchElement(root,"profileGridPane"));
        GridPane settingsWorkGridPane = ((GridPane)searchElement(root,"settingsWorkGridPane"));
        GridPane collectionWorkGridPane = ((GridPane)searchElement(root,"collectionWorkGridPane"));
        VBox sidePanel = ((VBox)searchElement(root,"sidePanel"));
        GridPane mapWorkGridPane = ((GridPane)searchElement(root,"mapWorkGridPane"));
        HBox settingsSideHBox = ((HBox)searchElement(root,"settingsSideHBox"));
        HBox collectionSideHBox = ((HBox)searchElement(root,"collectionSideHBox"));

        generalGridPane.setStyle(generalGridPane.getStyle() + theme.getGeneralGridPaneColor());
        appToolBar.setStyle(appToolBar.getStyle() + theme.getAppToolBarColor());
        profileGridPane.setStyle(profileGridPane.getStyle() + theme.getProfileGridPaneColor());
        settingsWorkGridPane.setStyle(settingsWorkGridPane.getStyle() + theme.getSettingsWorkGridPaneColor());
        collectionWorkGridPane.setStyle(collectionWorkGridPane.getStyle() + theme.getCollectionWorkGridPaneColor());
        sidePanel.setStyle(sidePanel.getStyle() + theme.getSidePanelColor());
        mapWorkGridPane.setStyle(mapWorkGridPane.getStyle() + theme.getMapWorkGridPaneColor());
        settingsSideHBox.setStyle(settingsSideHBox.getStyle() + theme.getSettingsSideHBoxColor());
        collectionSideHBox.setStyle(collectionSideHBox.getStyle() + theme.getCollectionSideHBoxColor());

        //Text styling

        VBox profileLabelsBox = (VBox) searchElement(root, "profileLabelsBox");
        profileLabelsBox.getChildren().forEach(p->{
            if(p instanceof Label)
                ((Label)p).setTextFill(theme.getTextColor());
        });

        hintSwitcherText.setTextFill(theme.getTextColor());
    }

}
