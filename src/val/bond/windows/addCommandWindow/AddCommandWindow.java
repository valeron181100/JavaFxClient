package val.bond.windows.addCommandWindow;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import val.bond.applogic.Clothes.*;
import val.bond.applogic.Enums.Color;
import val.bond.applogic.Enums.Material;
import val.bond.resources.customControlls.ToggleSwitch;
import val.bond.resources.logic.OldNewLogicConnector;

public class AddCommandWindow extends Application {

    private boolean isSimpleAddCommand;

    public AddCommandWindow(){
        super();
    }

    public AddCommandWindow(boolean isSimpleAddCommand){
        super();
        this.isSimpleAddCommand = isSimpleAddCommand;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void start(Stage primaryStage) throws Exception {

        class ColorComboBox extends Parent {
            private String costumeFieldName;
            private StringProperty val = new SimpleStringProperty();
            private ComboBox<String> comboBox;
            public ColorComboBox(String costumeFieldName){
                this.costumeFieldName = costumeFieldName;
                ComboBox<String> comboBox = new ComboBox<>();
                ObservableList<String> comboVals = FXCollections.observableArrayList("White", "Black", "Green", "Purple",
                        "Blonde", "Blue", "Red", "Orange", "Gray", "Brown");
                val.setValue("White");
                comboBox.setItems(comboVals);
                comboBox.setOnAction(event -> {
                    System.out.println("OnAaction");
                    val.setValue(comboBox.getSelectionModel().getSelectedItem());
                });
                getChildren().addAll(comboBox);
                comboBox.getSelectionModel().select(0);
            }

            public void addOnSelectedItemListener(EventHandler<ActionEvent> eventHandler){
                comboBox.setOnAction(eventHandler);
            }

            public String getSelectedValue() {
                // String val = comboBox.getSelectionModel().getSelectedItem();
                return val.getValue();
            }

            public String getCostumeFieldName() {
                return costumeFieldName;
            }
        }
        class MaterialComboBox extends Parent {
            private String costumeFieldName;
            private StringProperty val = new SimpleStringProperty();
            private ComboBox<String> comboBox;
            public MaterialComboBox(String costumeFieldName){
                this.costumeFieldName = costumeFieldName;
                ComboBox<String> comboBox = new ComboBox<>();
                ObservableList<String> comboVals = FXCollections.observableArrayList("Leather", "Wool", "Sintetic", "Chlopoc",
                        "Len", "Rubber");
                val.setValue("Leather");
                comboBox.setItems(comboVals);
                comboBox.setOnAction(event -> {
                    System.out.println("OnAaction");
                    val.setValue(comboBox.getSelectionModel().getSelectedItem());
                });
                getChildren().addAll(comboBox);
                comboBox.getSelectionModel().select(0);
            }

            public void addOnSelectedItemListener(EventHandler<ActionEvent> eventHandler){
                comboBox.setOnAction(eventHandler);
            }

            public String getSelectedValue() {
                // String val = comboBox.getSelectionModel().getSelectedItem();
                return val.getValue();
            }

            public String getCostumeFieldName() {
                return costumeFieldName;
            }
        }


        class MyTextArea extends Parent{
            private String costumeFieldName;
            private TextArea textArea;
            public MyTextArea(String costumeFieldName){
                this.costumeFieldName = costumeFieldName;
                textArea = new TextArea();
                textArea.setPrefWidth(300);
                textArea.setFont(new Font("Helvetica Neue", 12));
                textArea.setPadding(new Insets(0,10,0,0));
                textArea.setPrefColumnCount(1);
                textArea.setPrefRowCount(1);
                getChildren().addAll(textArea);
            }

            public String getCostumeFieldName() {
                return costumeFieldName;
            }

            public String getText(){
                return textArea.getText();
            }
        }

        class MyToggler extends Parent{
            private String costumeFieldName;
            private ToggleSwitch toggleSwitch;
            public MyToggler(String costumeFieldName){
                this.costumeFieldName = costumeFieldName;
                toggleSwitch = new ToggleSwitch(25);
                toggleSwitch.setOnHintText("True");
                toggleSwitch.setOnHintText("False");
                getChildren().addAll(toggleSwitch);
            }

            public boolean getValue() {
                return toggleSwitch.switchedOnProperty().getValue();
            }

            public String getCostumeFieldName() {
                return costumeFieldName;
            }
        }

        Pane root = FXMLLoader.load(getClass().getResource("addCommandWindow.fxml"));
        root.getStylesheets().add("val/bond/windows/stylesheet1.css");
        primaryStage.setTitle("Add Window");
        Scene scene = new Scene(root, 480, 340);
        primaryStage.setScene(scene);

        TreeItem<String> costumeTemp = new TreeItem<>("costume");
        TreeItem<String> topClothesTemp = new TreeItem<>("topClothes");
        TreeItem<String> downClothesTemp = new TreeItem<>("downClothes");
        TreeItem<String> underwearTemp = new TreeItem<>("underwear");
        TreeItem<String> hatTemp = new TreeItem<>("hat");
        TreeItem<String> shoesTemp = new TreeItem<>("shoes");

        TreeItem<String> topClothesGrowth_sm = new TreeItem(": growth_sm", new MyTextArea("topClothes_growth_sm"));
        TreeItem<String> topClothesHandSmLength = new TreeItem(": hand_sm_length", new MyTextArea("topClothes_growth_sm"));
        TreeItem<String> topClothesSize = new TreeItem(": size", new MyTextArea("topClothes_size"));
        TreeItem<String> topClothesColor = new TreeItem(": color", new ColorComboBox("topClothes_color"));
        TreeItem<String> topClothesMaterial = new TreeItem(": material", new MaterialComboBox("topClothes_material"));
        TreeItem<String> topClothesIsHood = new TreeItem(": is_hood", new MyToggler("topClothes_growth_sm"));
        TreeItem<String> topClothesName = new TreeItem(": name", new MyTextArea("topClothes_growth_sm"));
        TreeItem<String> topClothesIsForMan = new TreeItem(": is_for_man", new MyToggler("topClothes_growth_sm"));
        topClothesTemp.getChildren().addAll(topClothesGrowth_sm,topClothesSize, topClothesColor, topClothesMaterial,
                topClothesIsHood, topClothesName, topClothesIsForMan, topClothesHandSmLength);


        TreeItem<String> downClothesSize = new TreeItem(": size", new MyTextArea("topClothes_size"));
        TreeItem<String> downClothesColor = new TreeItem(": color", new ColorComboBox("topClothes_color"));
        TreeItem<String> downClothesMaterial = new TreeItem(": material", new MaterialComboBox("topClothes_material"));
        TreeItem<String> downClothesIsHood = new TreeItem(": is_hood", new MyToggler("topClothes_growth_sm"));
        TreeItem<String> downClothesName = new TreeItem(": name", new MyTextArea("topClothes_growth_sm"));
        TreeItem<String> downClothesIsForMan = new TreeItem(": is_for_man", new MyToggler("topClothes_growth_sm"));
        TreeItem<String> downClothesDiametr_sm = new TreeItem(": diametr_leg_sm", new MyTextArea("topClothes_growth_sm"));
        TreeItem<String> downClothesLegSmLength = new TreeItem(": leg_length_sm", new MyTextArea("topClothes_growth_sm"));
        downClothesTemp.getChildren().addAll(downClothesSize, downClothesColor, downClothesMaterial, downClothesIsHood, downClothesName, downClothesIsForMan,
                downClothesDiametr_sm, downClothesLegSmLength);

        TreeItem<String> underwearSize = new TreeItem(": size", new MyTextArea("topClothes_size"));
        TreeItem<String> underwearColor = new TreeItem(": color", new ColorComboBox("topClothes_color"));
        TreeItem<String> underwearMaterial = new TreeItem(": material", new MaterialComboBox("topClothes_material"));
        TreeItem<String> underwearIsHood = new TreeItem(": is_hood", new MyToggler("topClothes_growth_sm"));
        TreeItem<String> underwearName = new TreeItem(": name", new MyTextArea("topClothes_growth_sm"));
        TreeItem<String> underwearIsForMan = new TreeItem(": is_for_man", new MyToggler("topClothes_growth_sm"));
        TreeItem<String> underwearSexLvl = new TreeItem(": sex_lvl", new MyTextArea("topClothes_growth_sm"));
        underwearTemp.getChildren().addAll(underwearSize, underwearColor, underwearMaterial, underwearIsHood, underwearName, underwearIsForMan, underwearSexLvl);



        TreeItem<String> hatSize = new TreeItem(": size", new MyTextArea("topClothes_size"));
        TreeItem<String> hatColor = new TreeItem(": color", new ColorComboBox("topClothes_color"));
        TreeItem<String> hatMaterial = new TreeItem(": material", new MaterialComboBox("topClothes_material"));
        TreeItem<String> hatIsHood = new TreeItem(": is_hood", new MyToggler("topClothes_growth_sm"));
        TreeItem<String> hatName = new TreeItem(": name", new MyTextArea("topClothes_growth_sm"));
        TreeItem<String> hatIsForMan = new TreeItem(": is_for_man", new MyToggler("topClothes_growth_sm"));
        TreeItem<String> hatCylinder = new TreeItem(": cylinder_height_sm", new MyTextArea("topClothes_growth_sm"));
        TreeItem<String> hatVisor = new TreeItem(": visor_length_sm", new MyTextArea("topClothes_growth_sm"));
        hatTemp.getChildren().addAll(hatSize, hatColor, hatMaterial, hatIsHood, hatName, hatIsForMan, hatCylinder, hatVisor);


        TreeItem<String> shoesSize = new TreeItem(": size", new MyTextArea("topClothes_size"));
        TreeItem<String> shoesColor = new TreeItem(": color", new ColorComboBox("topClothes_color"));
        TreeItem<String> shoesMaterial = new TreeItem(": material", new MaterialComboBox("topClothes_material"));
        TreeItem<String> shoesIsHood = new TreeItem(": is_hood", new MyToggler("topClothes_growth_sm"));
        TreeItem<String> shoesName = new TreeItem(": name", new MyTextArea("topClothes_growth_sm"));
        TreeItem<String> shoesIsForMan = new TreeItem(": is_for_man", new MyToggler("topClothes_growth_sm"));
        TreeItem<String> shoesIsShoelaces = new TreeItem(": is_shoelaces", new MyToggler("topClothes_growth_sm"));
        TreeItem<String> shoesOutsoleMaterial = new TreeItem(": outsole_material", new MaterialComboBox("topClothes_material"));
        shoesTemp.getChildren().addAll(shoesSize, shoesColor, shoesMaterial, shoesIsHood, shoesName, shoesIsForMan, shoesIsShoelaces, shoesOutsoleMaterial);


        costumeTemp.getChildren().addAll(topClothesTemp, downClothesTemp, underwearTemp, hatTemp, shoesTemp);

        TreeView<String> treeView = new TreeView<>(costumeTemp);

        Button button = new Button("Готово");
        Button button1 = new Button("Отмена");
        HBox hBox = new HBox(button, button1);
        hBox.setSpacing(20);
        button1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            primaryStage.close();
        });
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                TopClothes topclothes = new TopClothes(Integer.parseInt(((MyTextArea) topClothesSize.getGraphic()).getText()),
                        Color.valueOf(((ColorComboBox) topClothesColor.getGraphic()).getSelectedValue()),
                        Material.valueOf(((MaterialComboBox) topClothesMaterial.getGraphic()).getSelectedValue()),
                        ((MyTextArea) topClothesName.getGraphic()).getText(),
                        ((MyToggler) topClothesIsForMan.getGraphic()).getValue(),
                        Integer.parseInt(((MyTextArea) topClothesHandSmLength.getGraphic()).getText()),
                        ((MyToggler) topClothesIsHood.getGraphic()).getValue(),
                        Integer.parseInt(((MyTextArea) topClothesGrowth_sm.getGraphic()).getText())
                );

                DownClothes downClothes = new DownClothes(Integer.parseInt(((MyTextArea) downClothesSize.getGraphic()).getText()),
                        Color.valueOf(((ColorComboBox) downClothesColor.getGraphic()).getSelectedValue()),
                        Material.valueOf(((MaterialComboBox) downClothesMaterial.getGraphic()).getSelectedValue()),
                        ((MyTextArea) downClothesName.getGraphic()).getText(),
                        ((MyToggler) downClothesIsForMan.getGraphic()).getValue(),
                        Integer.parseInt(((MyTextArea) downClothesLegSmLength.getGraphic()).getText()),
                        Integer.parseInt(((MyTextArea) downClothesDiametr_sm.getGraphic()).getText())
                );

                Underwear underwear = new Underwear(Integer.parseInt(((MyTextArea) underwearSize.getGraphic()).getText()),
                        Color.valueOf(((ColorComboBox) underwearColor.getGraphic()).getSelectedValue()),
                        Material.valueOf(((MaterialComboBox) underwearMaterial.getGraphic()).getSelectedValue()),
                        ((MyTextArea) underwearName.getGraphic()).getText(),
                        ((MyToggler) underwearIsForMan.getGraphic()).getValue(),
                        Integer.parseInt(((MyTextArea) underwearSexLvl.getGraphic()).getText()));

                Hat hat = new Hat(Integer.parseInt(((MyTextArea) hatSize.getGraphic()).getText()),
                        Color.valueOf(((ColorComboBox) hatColor.getGraphic()).getSelectedValue()),
                        Material.valueOf(((MaterialComboBox) hatMaterial.getGraphic()).getSelectedValue()),
                        ((MyTextArea) hatName.getGraphic()).getText(),
                        ((MyToggler) hatIsForMan.getGraphic()).getValue(),
                        Integer.parseInt(((MyTextArea) hatCylinder.getGraphic()).getText()),
                        Integer.parseInt(((MyTextArea) hatVisor.getGraphic()).getText())
                );

                Shoes shoes = new Shoes(Integer.parseInt(((MyTextArea) shoesSize.getGraphic()).getText()),
                        Color.valueOf(((ColorComboBox) shoesColor.getGraphic()).getSelectedValue()),
                        Material.valueOf(((MaterialComboBox) shoesMaterial.getGraphic()).getSelectedValue()),
                        ((MyTextArea) shoesName.getGraphic()).getText(),
                        ((MyToggler) shoesIsForMan.getGraphic()).getValue(),
                        ((MyToggler) shoesIsShoelaces.getGraphic()).getValue(),
                        Material.valueOf(((MaterialComboBox) shoesOutsoleMaterial.getGraphic()).getSelectedValue())
                );

                Costume costume = new Costume(topclothes, downClothes, shoes, hat, underwear);
                if(isSimpleAddCommand)
                    OldNewLogicConnector.commandLine.setValue("add {" + costume.getJson().toString() + "}");
                else
                    OldNewLogicConnector.commandLine.setValue("add_if_max {" + costume.getJson().toString() + "}");
                primaryStage.close();
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Неверный формат!");
                alert.setHeaderText("Введены не верные данные");
                alert.setContentText("Пожалуйста перепроверьте введенные данные!");
                alert.show();
            }
        });

        ((GridPane)root).add(treeView, 0,0);
        ((GridPane)root).add(hBox, 0,1);
        GridPane.setHalignment(hBox, HPos.CENTER);
        primaryStage.show();

    }
}
