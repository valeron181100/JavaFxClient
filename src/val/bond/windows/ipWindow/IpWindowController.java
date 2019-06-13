package val.bond.windows.ipWindow;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import val.bond.resources.logic.OldNewLogicConnector;
import val.bond.windows.authWindowPackage.AuthWindow;

public class IpWindowController {
    public GridPane MainGridPane;
    public VBox loginPanel;
    public TextArea ipAddressTextArea;
    public TextArea portTextArea;

    public void onOkLogButtonClick(ActionEvent event) {
        String port = portTextArea.getText();
        String ip = ipAddressTextArea.getText();
        if(port.length() == 0 || !port.matches("[0-9]{4}")){
            Alert portAlert = new Alert(Alert.AlertType.WARNING);
            portAlert.setTitle("Port Alert");
            portAlert.setHeaderText("Неверный ввод порта:");
            portAlert.setContentText("Порт пустой или неверный!");
            portAlert.show();
        }
        else {
            if(ip.length() == 0){
                Alert ipAlert = new Alert(Alert.AlertType.WARNING);
                ipAlert.setTitle("IP Alert");
                ipAlert.setHeaderText("Неверный ввод ip адреса:");
                ipAlert.setContentText("Ip адрес не может быть пустым!");
                ipAlert.show();
            }
            else{
                OldNewLogicConnector.port = Integer.parseInt(port);
                OldNewLogicConnector.ipAddress = ip;
                try {
                    Stage currentStage = (Stage)((Button)event.getSource()).getScene().getWindow();
                    currentStage.close();
                    new AuthWindow().start(new Stage());
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    public void onCancelAuthButtonClick(ActionEvent event) {
        System.exit(0);
    }
}
