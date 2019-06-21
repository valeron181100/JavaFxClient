package val.bond.resources.logic;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import val.bond.applogic.NetStuff.Net.User;
import val.bond.resources.customControlls.ObservableString;

public class OldNewLogicConnector {
    public static ObservableString commandLine = new ObservableString();
    public static ObservableString addResponse = new ObservableString();
    public static ObservableString addIfMaxResponse = new ObservableString();
    public static ObservableString loadResponse = new ObservableString();
    public static ObservableString importResponse = new ObservableString();
    public static ObservableString startResponse = new ObservableString();
    public static ObservableString infoResponse = new ObservableString();
    public static ObservableString helpResponse = new ObservableString();
    public static ObservableString changeDefFileResponse = new ObservableString();
    public static ObservableString trimToMinResponse = new ObservableString();
    public static ObservableString saveResponse = new ObservableString();
    public static ObservableString removeResponse = new ObservableString();
    public static ObservableString showResponse = new ObservableString();
    public static ObservableString authResponse = new ObservableString();
    public static ObservableString errorResponse = new ObservableString();
    public static ObservableString showCostumeResponse = new ObservableString();

    public static String infoWindowConnetionStr = "";

    public static BooleanProperty isConnected = new SimpleBooleanProperty(true);


    public static User currentUser = new User();

    public static int port;

    public static String ipAddress;

    public static void restoreDefaults(int port, String ipAddress){
        addResponse = new ObservableString();
        commandLine = new ObservableString();
        addIfMaxResponse = new ObservableString();
        loadResponse = new ObservableString();
        importResponse = new ObservableString();
        startResponse = new ObservableString();
        infoResponse = new ObservableString();
        helpResponse = new ObservableString();
        changeDefFileResponse = new ObservableString();
        trimToMinResponse = new ObservableString();
        saveResponse = new ObservableString();
        removeResponse = new ObservableString();
        showResponse = new ObservableString();
        authResponse = new ObservableString();
        errorResponse = new ObservableString();
        showCostumeResponse = new ObservableString();

        infoWindowConnetionStr = "";

        isConnected = new SimpleBooleanProperty(true);


        currentUser = new User();

        OldNewLogicConnector.port = port;

        OldNewLogicConnector.ipAddress = ipAddress;
    }

}
