package val.bond.windows.mainWindow;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONObject;
import val.bond.applogic.Clothes.Costume;
import val.bond.applogic.Enums.Color;
import val.bond.applogic.Enums.Material;
import val.bond.applogic.FileSystem.FileManager;
import val.bond.resources.customControlls.*;
import val.bond.resources.loadingResources.ImagePathConst;
import val.bond.resources.logic.OldNewLogicConnector;
import val.bond.resources.models.CommandClickEvent;
import val.bond.resources.models.CommandModel;
import val.bond.resources.models.CostumeTemplate;
import val.bond.resources.models.SimpleClothes;
import val.bond.resources.personalization.ColorTheme;
import val.bond.windows.addCommandWindow.AddCommandWindow;
import val.bond.windows.infoWindow.InfoWindow;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class GeneralWindow extends Application {

    public static BooleanProperty notificationOkCmdProperty = new SimpleBooleanProperty(false);

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
        root.getStylesheets().add("val/bond/windows/stylesheet1.css");
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
        VBox costumeClickPane = (VBox) searchElement(root, "costumeClickPane");
        StackPane darkStackPane = (StackPane) searchElement(root, "darkStackPane");
        BorderPane notificationBorderPane = (BorderPane) searchElement(root, "notificationBorderPane");
        Button menuButton = (Button) searchElement(root, "menuButton");


        GaussianBlur sidePanelBlur = new GaussianBlur();

        notificationOkCmdProperty.addListener(((observable, oldValue, newValue) -> {
            if(newValue){
                Notification notification = new Notification(200,50, "Комманда выполнена!");
                GridPane pane = new GridPane();
                pane.setAlignment(Pos.CENTER);
                pane.addRow(0, notification);
                GridPane.setHalignment(notification, HPos.CENTER);
                GridPane.setValignment(notification, VPos.CENTER);
                GridPane.setMargin(notification, new Insets(0,0,20,0));
                Platform.runLater(()->notificationBorderPane.setBottom(pane));
                notification.show();
                notificationOkCmdProperty.setValue(false);
            }
        }));

        costumeClickPane.setAlignment(Pos.CENTER);
        costumeClickPane.setSpacing(10);

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
        ArrayList<Node> costumeClickPaneNodes = new ArrayList<>();
        switcher.switchedOnProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                costumeTemplateGroup[0].getTemplates().forEach(CostumeTemplate::playEditAnimation);
                costumeClickPane.setAlignment(Pos.CENTER_LEFT);
                costumeClickPaneNodes.clear();
                costumeClickPaneNodes.addAll(costumeClickPane.getChildren());
                costumeClickPane.getChildren().clear();
                costumeClickPane.setMaxHeight(180);
                costumeClickPane.setPrefHeight(180);
                costumeClickPane.setMaxWidth(500);
                costumeClickPane.setPrefWidth(500);
                costumeClickPane.setMinHeight(180);
                costumeClickPane.setMinWidth(500);
                //Creating edit panel
                HBox editHBox = new HBox();

                ComboBox<String> costumeFieldCB = new ComboBox<>(FXCollections.observableArrayList("down_clothes",
                        "hats", "shoes", "top_clothes", "underwear"));

                ComboBox<String> clothesFieldCB = new ComboBox<>();
                clothesFieldCB.setOpacity(0.0);
                clothesFieldCB.setDisable(true);

                ArrayList<String> clothes_fields = new ArrayList<>(Arrays.asList("size", "color", "material", "name", "is_for_man"));
                ArrayList<String> topClothes_fields = new ArrayList<>(clothes_fields);
                topClothes_fields.addAll(Arrays.asList("hand_sm_length", "is_hood", "growth_sm"));

                ArrayList<String> downClothes_fields = new ArrayList<>(clothes_fields);
                downClothes_fields.addAll(Arrays.asList("leg_length_sm", "diametr_leg_sm"));

                ArrayList<String> underwear_fields = new ArrayList<>(clothes_fields);
                underwear_fields.addAll(Arrays.asList("sex_lvl"));

                ArrayList<String> hat_fields = new ArrayList<>(clothes_fields);
                hat_fields.addAll(Arrays.asList("cylinder_height_sm", "visor_length_sm"));

                ArrayList<String> shoes_fields = new ArrayList<>(clothes_fields);
                shoes_fields.addAll(Arrays.asList("is_shoelaces", "outsole_material"));

                costumeFieldCB.getSelectionModel().selectedItemProperty().addListener(((observable1, oldValue1, newValue1) -> {
                    if(clothesFieldCB.isDisable()) {
                        FadeTransition clothesFieldCBTransition = new FadeTransition(Duration.millis(150), clothesFieldCB);
                        clothesFieldCBTransition.setFromValue(0.0);
                        clothesFieldCBTransition.setToValue(1.0);
                        clothesFieldCBTransition.play();
                        clothesFieldCB.setDisable(false);
                    }
                    switch(newValue1){
                        case "down_clothes": clothesFieldCB.setItems(FXCollections.observableArrayList(downClothes_fields)); break;
                        case "top_clothes": clothesFieldCB.setItems(FXCollections.observableArrayList(topClothes_fields)); break;
                        case "hats": clothesFieldCB.setItems(FXCollections.observableArrayList(hat_fields)); break;
                        case "shoes": clothesFieldCB.setItems(FXCollections.observableArrayList(shoes_fields)); break;
                        case "underwear": clothesFieldCB.setItems(FXCollections.observableArrayList(underwear_fields)); break;
                    }
                }));
                editHBox.setSpacing(20);
                editHBox.getChildren().addAll(costumeFieldCB, clothesFieldCB);

                IntegerInput integerInput = new IntegerInput();
                integerInput.setDisable(true);
                integerInput.setOpacity(0.0);

                TextArea textArea = new TextArea();
                textArea.setPrefWidth(150);
                textArea.setFont(new Font("Helvetica Neue", 12));
                textArea.setPrefColumnCount(1);
                textArea.setPrefRowCount(1);
                textArea.setDisable(true);
                textArea.setOpacity(0.0);

                ToggleSwitch boolInput = new ToggleSwitch(25);
                boolInput.setDisable(true);
                boolInput.setOpacity(0.0);

                ComboBox<Material> materialComboBox = new ComboBox<>(FXCollections.observableArrayList(Material.values()));
                materialComboBox.setDisable(true);
                materialComboBox.setOpacity(0.0);

                ComboBox<Color> colorComboBox = new ComboBox<>(FXCollections.observableArrayList(Color.values()));
                colorComboBox.setDisable(true);
                colorComboBox.setOpacity(0.0);

                int appearingDuration = 300;

                clothesFieldCB.getSelectionModel().selectedItemProperty().addListener(((observable1, oldValue1, newValue1) -> {
                    switch (newValue1){
                        case "size": case "hand_sm_length": case "growth_sm": case "leg_length_sm": case "diametr_leg_sm":
                        case "sex_lvl": case "cylinder_height_sm": case "visor_length_sm":
                            if(editHBox.getChildren().size() > 2){
                                if(!(editHBox.getChildren().get(2) instanceof IntegerInput)) {
                                    FadeTransition dissappearTrans = new FadeTransition(Duration.millis(appearingDuration), editHBox.getChildren().get(2));
                                    dissappearTrans.setFromValue(1.0);
                                    dissappearTrans.setToValue(0.0);
                                    dissappearTrans.play();
                                    editHBox.getChildren().get(2).setDisable(true);
                                    editHBox.getChildren().remove(2);

                                    FadeTransition appearTrans = new FadeTransition(Duration.millis(appearingDuration), integerInput);
                                    appearTrans.setFromValue(0.0);
                                    appearTrans.setToValue(1.0);
                                    appearTrans.play();
                                    integerInput.setDisable(false);
                                    editHBox.getChildren().add(integerInput);
                                }
                            }
                            else{
                                if(editHBox.getChildren().size() == 2){
                                    FadeTransition appearTrans = new FadeTransition(Duration.millis(appearingDuration), integerInput);
                                    appearTrans.setFromValue(0.0);
                                    appearTrans.setToValue(1.0);
                                    appearTrans.play();
                                    integerInput.setDisable(false);
                                    editHBox.getChildren().add(integerInput);
                                }
                            }
                            break;
                        case "name":
                            if(editHBox.getChildren().size() > 2){
                                if(!(editHBox.getChildren().get(2) instanceof TextArea)) {
                                    FadeTransition dissappearTrans = new FadeTransition(Duration.millis(appearingDuration), editHBox.getChildren().get(2));
                                    dissappearTrans.setFromValue(1.0);
                                    dissappearTrans.setToValue(0.0);
                                    dissappearTrans.play();
                                    editHBox.getChildren().get(2).setDisable(true);
                                    editHBox.getChildren().remove(2);

                                    FadeTransition appearTrans = new FadeTransition(Duration.millis(appearingDuration), textArea);
                                    appearTrans.setFromValue(0.0);
                                    appearTrans.setToValue(1.0);
                                    appearTrans.play();
                                    textArea.setDisable(false);
                                    editHBox.getChildren().add(textArea);
                                }
                            }
                            else{
                                if(editHBox.getChildren().size() == 2){
                                    FadeTransition appearTrans = new FadeTransition(Duration.millis(appearingDuration), textArea);
                                    appearTrans.setFromValue(0.0);
                                    appearTrans.setToValue(1.0);
                                    appearTrans.play();
                                    textArea.setDisable(false);
                                    editHBox.getChildren().add(textArea);
                                }
                            }
                            break;
                        case "is_for_man": case "is_hood": case "is_shoelaces":
                            if(editHBox.getChildren().size() > 2){
                                if(!(editHBox.getChildren().get(2) instanceof ToggleSwitch)) {
                                    FadeTransition dissappearTrans = new FadeTransition(Duration.millis(appearingDuration), editHBox.getChildren().get(2));
                                    dissappearTrans.setFromValue(1.0);
                                    dissappearTrans.setToValue(0.0);
                                    dissappearTrans.play();
                                    editHBox.getChildren().get(2).setDisable(true);
                                    editHBox.getChildren().remove(2);

                                    FadeTransition appearTrans = new FadeTransition(Duration.millis(appearingDuration), boolInput);
                                    appearTrans.setFromValue(0.0);
                                    appearTrans.setToValue(1.0);
                                    appearTrans.play();
                                    boolInput.setDisable(false);
                                    editHBox.getChildren().add(boolInput);
                                }
                            }
                            else{
                                if(editHBox.getChildren().size() == 2){
                                    FadeTransition appearTrans = new FadeTransition(Duration.millis(appearingDuration), boolInput);
                                    appearTrans.setFromValue(0.0);
                                    appearTrans.setToValue(1.0);
                                    appearTrans.play();
                                    boolInput.setDisable(false);
                                    editHBox.getChildren().add(boolInput);
                                }
                            }
                            break;
                        case "material": case "outsole_material":
                            if(editHBox.getChildren().size() > 2){
                                if(!(editHBox.getChildren().get(2) instanceof ComboBox &&
                                        ((ComboBox)editHBox.getChildren().get(2)).getItems().get(0).getClass() == Material.class)) {
                                    FadeTransition dissappearTrans = new FadeTransition(Duration.millis(appearingDuration), editHBox.getChildren().get(2));
                                    dissappearTrans.setFromValue(1.0);
                                    dissappearTrans.setToValue(0.0);
                                    dissappearTrans.play();
                                    editHBox.getChildren().get(2).setDisable(true);
                                    editHBox.getChildren().remove(2);

                                    FadeTransition appearTrans = new FadeTransition(Duration.millis(appearingDuration), materialComboBox);
                                    appearTrans.setFromValue(0.0);
                                    appearTrans.setToValue(1.0);
                                    appearTrans.play();
                                    materialComboBox.setDisable(false);
                                    editHBox.getChildren().add(materialComboBox);
                                }
                            }
                            else{
                                if(editHBox.getChildren().size() == 2){
                                    FadeTransition appearTrans = new FadeTransition(Duration.millis(appearingDuration), materialComboBox);
                                    appearTrans.setFromValue(0.0);
                                    appearTrans.setToValue(1.0);
                                    appearTrans.play();
                                    materialComboBox.setDisable(false);
                                    editHBox.getChildren().add(materialComboBox);
                                }
                            }
                            materialComboBox.getSelectionModel().select(0);
                            break;
                        case "color":
                            if(editHBox.getChildren().size() > 2){
                                if(!(editHBox.getChildren().get(2) instanceof ComboBox &&
                                        ((ComboBox)editHBox.getChildren().get(2)).getItems().get(0).getClass() == Color.class)) {
                                    FadeTransition dissappearTrans = new FadeTransition(Duration.millis(appearingDuration), editHBox.getChildren().get(2));
                                    dissappearTrans.setFromValue(1.0);
                                    dissappearTrans.setToValue(0.0);
                                    dissappearTrans.play();
                                    editHBox.getChildren().get(2).setDisable(true);
                                    editHBox.getChildren().remove(2);

                                    FadeTransition appearTrans = new FadeTransition(Duration.millis(appearingDuration), colorComboBox);
                                    appearTrans.setFromValue(0.0);
                                    appearTrans.setToValue(1.0);
                                    appearTrans.play();
                                    colorComboBox.setDisable(false);
                                    editHBox.getChildren().add(colorComboBox);
                                }
                            }
                            else{
                                if(editHBox.getChildren().size() == 2){
                                    FadeTransition appearTrans = new FadeTransition(Duration.millis(appearingDuration), colorComboBox);
                                    appearTrans.setFromValue(0.0);
                                    appearTrans.setToValue(1.0);
                                    appearTrans.play();
                                    colorComboBox.setDisable(false);
                                    editHBox.getChildren().add(colorComboBox);
                                }
                            }
                            colorComboBox.getSelectionModel().select(0);
                            break;
                    }
                }));

                editHBox.setAlignment(Pos.CENTER_LEFT);

                Label headLabel = new Label("Что хотите поменять?");
                headLabel.setFont(new Font("Helvetica Neue", 20));
                DropShadow headerTextShadow = new DropShadow();
                headerTextShadow.setSpread(0.3);
                headerTextShadow.setBlurType(BlurType.GAUSSIAN);
                headLabel.setPadding(new Insets(0,10,0,0));
                headLabel.setEffect(headerTextShadow);
                headLabel.setAlignment(Pos.CENTER_LEFT);
                headLabel.setTextAlignment(TextAlignment.LEFT);
                headLabel.setTextFill(javafx.scene.paint.Color.WHITE);

                HBox editButtonsHBox = new HBox();

                editButtonsHBox.setAlignment(Pos.CENTER_LEFT);
                editButtonsHBox.setSpacing(30);

                Button okEditButton = new Button("Готово");
                Button cancelEditButton = new Button("Отмена");

                okEditButton.getStyleClass().add("macos_button_style");
                cancelEditButton.getStyleClass().add("macos_button_style");

                cancelEditButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    menuButton.setDisable(false);
                    costumeTemplateGroup[0].getLastClicked().playDissappearClickPaneAnim();
                    new Thread(()->{
                        for (double i = sidePanelBlur.getRadius(); i > 0.0; i--) {
                            double finalI = i;
                            Platform.runLater(()->sidePanelBlur.setRadius(finalI));
                        }
                    }).start();
                    FadeTransition fadeTransition = new FadeTransition(Duration.millis(150), darkStackPane);
                    fadeTransition.setFromValue(0.5);
                    fadeTransition.setToValue(0.0);
                    fadeTransition.play();
                });

                okEditButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if(editHBox.getChildren().size() == 3) {
                        if (costumeFieldCB.getSelectionModel().getSelectedItem().length() != 0 &&
                                clothesFieldCB.getSelectionModel().getSelectedItem().length() != 0) {
                            String thrirdParam = "";
                            if (!integerInput.isDisable()) {
                                thrirdParam = integerInput.getVal();
                            }
                            if (!textArea.isDisable()) {
                                thrirdParam = textArea.getText();
                            }
                            if (!boolInput.isDisable()) {
                                thrirdParam = boolInput.switchedOnProperty().getValue().toString();
                            }
                            if (!materialComboBox.isDisable()) {
                                thrirdParam = materialComboBox.getSelectionModel().getSelectedItem().toString();
                            }
                            if (!colorComboBox.isDisable()) {
                                thrirdParam = colorComboBox.getSelectionModel().getSelectedItem().toString();
                            }

                            String query = "edit {" + costumeTemplateGroup[0].getLastClicked().getCostumeId() + "} {" +
                                    costumeFieldCB.getSelectionModel().getSelectedItem() + "." +
                                    clothesFieldCB.getSelectionModel().getSelectedItem() + "} {" +
                                    thrirdParam + "}";
                            OldNewLogicConnector.commandLine.setValue(query);
                            menuButton.setDisable(false);
                            costumeTemplateGroup[0].getLastClicked().playDissappearClickPaneAnim();
                            new Thread(()->{
                                for (double i = sidePanelBlur.getRadius(); i > 0.0; i--) {
                                    double finalI = i;
                                    Platform.runLater(()->sidePanelBlur.setRadius(finalI));
                                }
                            }).start();
                            FadeTransition fadeTransition = new FadeTransition(Duration.millis(150), darkStackPane);
                            fadeTransition.setFromValue(0.5);
                            fadeTransition.setToValue(0.0);
                            fadeTransition.play();
                        }
                    }else{
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Неверные данные!");
                        alert.setContentText("Вы ввели не все данные!");
                        alert.show();
                    }
                });

                editButtonsHBox.getChildren().addAll(okEditButton, cancelEditButton);

                costumeClickPane.getChildren().addAll(headLabel, editHBox, editButtonsHBox);

            }
            else {
                costumeTemplateGroup[0].getTemplates().forEach(CostumeTemplate::stopEditAnimation);
                costumeClickPane.setAlignment(Pos.CENTER);
                costumeClickPane.getChildren().clear();
                costumeClickPane.getChildren().addAll(costumeClickPaneNodes);
                costumeClickPane.setMaxHeight(360);
                costumeClickPane.setPrefHeight(360);
                costumeClickPane.setMaxWidth(1200);
                costumeClickPane.setPrefWidth(1200);
                costumeClickPane.setMinHeight(360);
                costumeClickPane.setMinWidth(1200);
            }
        });
        HBox switcherHbox = new HBox();

        hintSwitcherText = new Label();
        hintSwitcherText.setFont(new Font("Helvetica Neue", 15));
        hintSwitcherText.setPadding(new Insets(10,0,0,0));
        hintSwitcherText.textProperty().bind(switcher.getHintTextProperty());

        switcherHbox.getChildren().addAll(hintSwitcherText, switcher);
        switcherHbox.setMargin(switcher, new Insets(0,10,0,0));

        //Setting up user filter combobox
        ComboBox<String> usersFilterCB = new ComboBox<>();

        usersFilterCB.getStyleClass().add("macos_button_style");

        usersFilterCB.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue.equals("-")){
                OldNewLogicConnector.commandLine.setValue("show {} {}");
            }else{
                OldNewLogicConnector.commandLine.setValue("show {" + newValue + "} {}");
            }
        }));

        BorderPane editBorderPane = (BorderPane) searchElement(root, "editColBorderPane");
        editBorderPane.setStyle("-fx-border-color: black");
        switcherHbox.setSpacing(10);
        editBorderPane.setRight(switcherHbox);
        editBorderPane.setLeft(usersFilterCB);


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
                new CommandModel(commandIconSize,commandIconSize, ImagePathConst.getTrimToMinPng(), "Trim To Min", "trimToMin"),
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
        commands[8].setOnMouseClickedEvent(changeDefFileClickEvent);
        commands[0].setOnMouseClickedEvent(addCommandClickEvent);
        commands[1].setOnMouseClickedEvent(addIfMaxCommandClickEvent);
        commands[11].setOnMouseClickedEvent(event ->{
            switcher.switchedOnProperty().setValue(true);
        });
        commands[3].setOnMouseClickedEvent(importIconClickEvent);

        ///Setting up side panel effects

        //GaussianBlur sidePanelBlur = new GaussianBlur();
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

        // Creating table of costume content
        TableView<SimpleClothes> costumeTableView = new TableView<>();
        {
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
                    isShoelacesCol, outsoleMaterialCol);

        }

        //costumeTableView filtering

        HBox filterHBox = new HBox();
        costumeClickPane.getChildren().add(filterHBox);
        ComboBox<String> lComboBox = new ComboBox<>();
        lComboBox.setItems(FXCollections.observableArrayList("none", "size","color", "isForMan", "material", "name",
                "legLengthSm", "diametrLegSm", "growthSm", "handSmLength", "isHood", "sexLvl", "cylinderHeightSm", "visorHeightSm",
                "isShoelaces", "outsoleMaterial"));
        lComboBox.getSelectionModel().select(0);

        ComboBox<String> signComboBox = new ComboBox<>();
        signComboBox.setItems(FXCollections.observableArrayList("=", ">", "≤", "<", "≥", "≠"));
        signComboBox.getSelectionModel().select(0);

        TextArea rArea = new TextArea();
        rArea.setPrefRowCount(1);
        rArea.setPromptText("Своё значение");

        filterHBox.setSpacing(10);

        filterHBox.setAlignment(Pos.CENTER_LEFT);

        filterHBox.getChildren().addAll(lComboBox, signComboBox, rArea);


        ArrayList<SimpleClothes> unfilteredTableViewList = new ArrayList<>();

        ChangeListener<String> filterListener = ((observable, oldValue, newValue) -> {
            System.out.println("Вызван фильтр");
            try {
                if (!lComboBox.getSelectionModel().getSelectedItem().equals("none") && rArea.getText().length() != 0) {
                    String logicExp =lComboBox.getSelectionModel().getSelectedItem() + signComboBox.getSelectionModel().getSelectedItem() +
                            rArea.getText();
                    ArrayList<SimpleClothes> allClothes = new ArrayList<>(unfilteredTableViewList);
                    List<SimpleClothes> collect = unfilteredTableViewList.stream().filter(p -> {
                        int k = 0;
                        try {
                            Field field = SimpleClothes.class.getDeclaredField(lComboBox.getSelectionModel().getSelectedItem());
                            field.setAccessible(true);
                            Object lVal = field.get(p);
                            field.setAccessible(false);

                            String rVal = rArea.getText();
                            switch (signComboBox.getSelectionModel().getSelectedItem()) {
                                case "=":
                                    if (lVal instanceof String) return ((String) lVal).equals(rVal);
                                    if (lVal instanceof Integer) return ((Integer) lVal).equals(Integer.parseInt(rVal));
                                    if (lVal instanceof Boolean)
                                        return ((Boolean) lVal).equals(Boolean.valueOf(rVal));
                                    if (lVal instanceof Color) return ((Color) lVal).equals(Color.valueOf(rVal));
                                    if (lVal instanceof Material)
                                        return ((Material) lVal).equals(Material.valueOf(rVal));

                                case ">":
                                    if (lVal instanceof String)
                                        return ((String) lVal).length() > ((String) rVal).length();
                                    if (lVal instanceof Integer) return ((Integer) lVal) > (Integer.parseInt(rVal));
                                    if (lVal instanceof Boolean)
                                        return ((Boolean) lVal) ? (Boolean) lVal : Boolean.valueOf(rVal);
                                    if (lVal instanceof Color)
                                        return ((Color) lVal).toString().length() > Color.valueOf(rVal).toString().length();
                                    if (lVal instanceof Material)
                                        return ((Material) lVal).toString().length() > Material.valueOf(rVal).toString().length();

                                case "<":
                                    if (lVal instanceof String)
                                        return ((String) lVal).length() < ((String) rVal).length();
                                    if (lVal instanceof Integer) return ((Integer) lVal) < (Integer.parseInt(rVal));
                                    if (lVal instanceof Boolean)
                                        return ((Boolean) lVal) ? Boolean.valueOf(rVal) : (Boolean) (lVal);
                                    if (lVal instanceof Color)
                                        return ((Color) lVal).toString().length() < Color.valueOf(rVal).toString().length();
                                    if (lVal instanceof Material)
                                        return ((Material) lVal).toString().length() < Material.valueOf(rVal).toString().length();

                                case "≤":
                                    if (lVal instanceof String)
                                        return ((String) lVal).length() <= ((String) rVal).length();
                                    if (lVal instanceof Integer) return ((Integer) lVal) <= (Integer.parseInt(rVal));
                                    if (lVal instanceof Boolean)
                                        return ((Boolean) lVal) ? Boolean.valueOf(rVal) : (Boolean) (lVal);
                                    if (lVal instanceof Color)
                                        return ((Color) lVal).toString().length() <= Color.valueOf(rVal).toString().length();
                                    if (lVal instanceof Material)
                                        return ((Material) lVal).toString().length() <= Material.valueOf(rVal).toString().length();

                                case "≥":
                                    if (lVal instanceof String)
                                        return ((String) lVal).length() >= ((String) rVal).length();
                                    if (lVal instanceof Integer) return ((Integer) lVal) >= (Integer.parseInt(rVal));
                                    if (lVal instanceof Boolean)
                                        return ((Boolean) lVal) ? (Boolean) lVal : Boolean.valueOf(rVal);
                                    if (lVal instanceof Color)
                                        return ((Color) lVal).toString().length() >= Color.valueOf(rVal).toString().length();
                                    if (lVal instanceof Material)
                                        return ((Material) lVal).toString().length() >= Material.valueOf(rVal).toString().length();

                                case "≠":
                                    if (lVal instanceof String) return !((String) lVal).equals(rVal);
                                    if (lVal instanceof Integer)
                                        return !((Integer) lVal).equals(Integer.parseInt(rVal));
                                    if (lVal instanceof Boolean) return !((Boolean) lVal).equals((Boolean) lVal);
                                    if (lVal instanceof Color) return !((Color) lVal).equals(Color.valueOf(rVal));
                                    if (lVal instanceof Material)
                                        return !((Material) lVal).equals(Material.valueOf(rVal));
                                    
                                    default: return false;
                            }

                        } catch (NoSuchFieldException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }).collect(Collectors.toList());
                    costumeTableView.getItems().clear();
                    costumeTableView.setItems(FXCollections.observableArrayList(collect));
                }
                if(lComboBox.getSelectionModel().getSelectedItem().equals("none") || rArea.getText().length() == 0){
                    costumeTableView.getItems().clear();
                    costumeTableView.setItems(FXCollections.observableArrayList(unfilteredTableViewList));
                }
            }catch (Exception e) {}
        });

        lComboBox.getSelectionModel().selectedItemProperty().addListener(filterListener);
        signComboBox.getSelectionModel().selectedItemProperty().addListener(filterListener);
        rArea.textProperty().addListener(filterListener);

        //Responses processing

        OldNewLogicConnector.showResponse.addListener((observable, oldValue, newValue) -> {
            costumeTemplateGroup[0] = new CostumeTemplateGroup(60, newValue);
            TilePane collectionShowTilePane = (TilePane) searchElement(root, "collectionTilePane");
            collectionTilePane.getChildren().clear();

            Button button = new Button("Close");
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                menuButton.setDisable(false);
                costumeTemplateGroup[0].getLastClicked().playDissappearClickPaneAnim();
                new Thread(()->{
                    for (double i = sidePanelBlur.getRadius(); i > 0.0; i--) {
                        double finalI = i;
                        Platform.runLater(()->sidePanelBlur.setRadius(finalI));
                    }
                }).start();
                FadeTransition fadeTransition = new FadeTransition(Duration.millis(150), darkStackPane);
                fadeTransition.setFromValue(0.5);
                fadeTransition.setToValue(0.0);
                fadeTransition.play();
            });
            button.setAlignment(Pos.CENTER);
            button.getStyleClass().add("macos_button_style");
            button.setPrefWidth(500);
            if(costumeClickPane.getChildren().size() == 1)
                costumeClickPane.getChildren().addAll(costumeTableView,button);
            if(usersFilterCB.getItems().size() == 0) {
                ArrayList<String> usersLogins = new ArrayList<>();
                usersLogins.add("-");
                costumeTemplateGroup[0].getTemplates().forEach(p -> usersLogins.add(p.getOwnerUser()));
                usersFilterCB.setItems(FXCollections.observableArrayList(usersLogins.stream().distinct().collect(Collectors.toList())));
            }
            costumeTemplateGroup[0].getTemplates().forEach(p->p.setClickPane(360,1200,costumeClickPane));
            costumeTemplateGroup[0].getTemplates().forEach(p -> collectionShowTilePane.getChildren().add(p));
            costumeTemplateGroup[0].getTemplates().forEach(p -> {
                p.setOnMouseClickedEvent(((event)->{
                    OldNewLogicConnector.commandLine.setValue("show {} {" + ((CostumeTemplate)event).getCostumeId() + "}");
                }));
            });
            costumeTemplateGroup[0].getTemplates().forEach(p -> p.addRemoveIconClickEvent(removeIconClickEvent));
        });

        OldNewLogicConnector.showCostumeResponse.addListener(((observable, oldValue, newValue) -> {
            menuButton.setDisable(true);
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(150), darkStackPane);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(0.5);
            fadeTransition.play();
            new Thread(()->{
                for (double i = sidePanelBlur.getRadius(); i < 10.0; i++) {
                    double finalI = i;
                    Platform.runLater(()->sidePanelBlur.setRadius(finalI));
                }
            }).start();
            Costume costume = new Costume(new JSONObject(newValue).getJSONObject("costumeObject"));
            costumeTableView.setItems(FXCollections.observableArrayList(SimpleClothes.devideCostumeOnSimple(costume)));
            unfilteredTableViewList.clear();
            unfilteredTableViewList.addAll(costumeTableView.getItems());
        }));

        OldNewLogicConnector.commandLine.setValue("show {} {}");

        ObservableChangeListener<String> infoChangeListener = ((observable, oldValue, newValue) -> {

            //TODO: Opening info window
            OldNewLogicConnector.infoWindowConnetionStr = newValue;
            InfoWindow infoWindow = new InfoWindow();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            try {
                infoWindow.start(stage);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

        });

        OldNewLogicConnector.startResponse.addListener(infoChangeListener);
        OldNewLogicConnector.helpResponse.addListener(infoChangeListener);
        OldNewLogicConnector.infoResponse.addListener(infoChangeListener);


        primaryStage.show();


        //Image loadingImg = GifLoader.getLoadingImg();


    }


    private CommandClickEvent commandClickEvent = (event -> {
        CommandModel model = (CommandModel)event;
        OldNewLogicConnector.commandLine.setValue(model.getCommandLine());
    });

    private CommandClickEvent addCommandClickEvent = (event -> {
        try {
            new AddCommandWindow(true).start(new Stage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    });

    private CommandClickEvent addIfMaxCommandClickEvent = (event -> {
        try {
            new AddCommandWindow(false).start(new Stage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    });

    private CommandClickEvent removeIconClickEvent = (event -> {
        CostumeTemplate template = (CostumeTemplate) event;
        OldNewLogicConnector.commandLine.setValue("remove {" + template.getCostumeId() + "}");
    });

    private CommandClickEvent importIconClickEvent = (event -> {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
        if(file != null) {
            FileManager manager = new FileManager(file.getPath());
            String xml = manager.getXmlFromFile();
            if (xml.length() != 0)
                OldNewLogicConnector.commandLine.setValue("import {" + xml + "}");
            else {
                System.out.println("В файле не найдена коллекция!");
            }
        }
        else{
            System.out.println("Файл не выбран!");
        }

    });

    private CommandClickEvent changeDefFileClickEvent = (event -> {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
        if(file != null)
            OldNewLogicConnector.commandLine.setValue("change_def_file_path {" + file.getPath() + "}");
        else{
            System.out.println("Файл не выбран!");
        }
    });



    public void setColorTheme(ColorTheme theme){

        GridPane generalGridPane = ((GridPane)searchElement(root,"generalGridPane"));
        ToolBar appToolBar = ((ToolBar)searchElement(root,"appToolBar"));
        GridPane profileGridPane = ((GridPane)searchElement(root,"profileGridPane"));
        GridPane settingsWorkGridPane = ((GridPane)searchElement(root,"settingsWorkGridPane"));
        GridPane collectionWorkGridPane = ((GridPane)searchElement(root,"collectionWorkGridPane"));
        VBox sidePanel = ((VBox)searchElement(root,"sidePanel"));
        VBox costumeClickPane = ((VBox)searchElement(root,"costumeClickPane"));
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
        costumeClickPane.setStyle(costumeClickPane.getStyle() + theme.getCostumeClickPaneColor());

        //Text styling

        VBox profileLabelsBox = (VBox) searchElement(root, "profileLabelsBox");
        profileLabelsBox.getChildren().forEach(p->{
            if(p instanceof Label)
                ((Label)p).setTextFill(theme.getTextColor());
        });

        hintSwitcherText.setTextFill(theme.getTextColor());
    }

}
