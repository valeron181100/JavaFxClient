package val.bond.resources.customControlls;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class IntegerInput extends Parent {

    StringProperty val = new SimpleStringProperty();

    public IntegerInput(){

        Button plus = new Button("+");
        plus.setMaxSize(25, 25);

        Button minus = new Button("-");
        minus.setMaxSize(25, 25);

        Label label = new Label();
        label.setAlignment(Pos.CENTER);
        label.setFont(new Font("Helvetica Neue", 16));
        label.textProperty().bind(val);

        val.setValue("0");

        plus.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            val.setValue(String.valueOf(Integer.parseInt(val.getValue()) + 1));
        });

        minus.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            String value = val.getValue();
            val.setValue(String.valueOf(Integer.parseInt(value) - 1));
        });

        VBox buttonsVbox = new VBox();
        buttonsVbox.getChildren().addAll(plus,minus);
        GridPane gridPane = new GridPane();

        gridPane.addColumn(0, label);
        gridPane.addColumn(1, buttonsVbox);
        GridPane.setMargin(label, new Insets(0,10,0,0));

        getChildren().addAll(gridPane);

    }

    public String getVal() {
        return val.get();
    }
}
