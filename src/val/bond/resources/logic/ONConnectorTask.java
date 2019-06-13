package val.bond.resources.logic;

import javafx.concurrent.Task;
import val.bond.applogic.FileSystem.CollectionManager;
import val.bond.applogic.NetStuff.Net.TransferPackage;
import val.bond.applogic.mainpkg.ClientMain;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.DatagramPacket;
import java.net.SocketTimeoutException;
import java.util.HashSet;
import java.util.stream.Collectors;

public class ONConnectorTask extends Task<Void> {

    void commandLineSetVal(String newValue){
        OldNewLogicConnector.commandLine.setValue(newValue);
    }
    void addResponseSetVal(String newValue){
        OldNewLogicConnector.addResponse.setValue(newValue);
    }
    void addIfMaxResponseSetVal(String newValue){
        OldNewLogicConnector.addIfMaxResponse.setValue(newValue);
    }
    void loadResponseSetVal(String newValue){
        OldNewLogicConnector.loadResponse.setValue(newValue);
    }
    void importResponseSetVal(String newValue){
        OldNewLogicConnector.commandLine.setValue(newValue);
    }
    void startResponseSetVal(String newValue){
        OldNewLogicConnector.startResponse.setValue(newValue);
    }
    void infoResponseSetVal(String newValue){
        OldNewLogicConnector.infoResponse.setValue(newValue);
    }

    void helpResponseSetVal(String newValue){
        OldNewLogicConnector.helpResponse.setValue(newValue);
    }
    void changeDefFileResponseSetVal(String newValue){
        OldNewLogicConnector.changeDefFileResponse.setValue(newValue);
    }
    void trimToMinResponseSetVal(String newValue){
        OldNewLogicConnector.trimToMinResponse.setValue(newValue);
    }
    void saveResponseSetVal(String newValue){
        OldNewLogicConnector.saveResponse.setValue(newValue);
    }
    void showResponseSetVal(String newValue){
        OldNewLogicConnector.showResponse.setValue(newValue);
    }
    void removeResponseSetVal(String newValue){
        OldNewLogicConnector.removeResponse.setValue(newValue);
    }
    void authResponseSetVal(String newValue){
        OldNewLogicConnector.authResponse.setValue(newValue);
    }
    void errorResponseSetVal(String newValue){
        OldNewLogicConnector.errorResponse.setValue(newValue);
    }
    
    

    @Override
    protected Void call() throws Exception {
        boolean isServerShutedDown = false;
        while (true) {
            try {
                //sendCmd()
                if(!ClientMain.isConnected[0]){
                    ClientMain.sendCmd();
                }

                byte[] receiveData = new byte[65536];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                ClientMain.clientSocket.receive(receivePacket);
                if (!ClientMain.isConnected[0])
                    System.out.println();
                ClientMain.isConnected[0] = true;
                System.err.println("Босс принял пакет!");
                TransferPackage recievedPkg = TransferPackage.restoreObject(new ByteArrayInputStream(receivePacket.getData()));
                if (recievedPkg != null) {
                    switch (recievedPkg.getId()) {
                        case 11: if(recievedPkg.getId() == 11) helpResponseSetVal(new String(recievedPkg.getAdditionalData(), ClientMain.DEFAULT_CHAR_SET));
                        case 2: if(recievedPkg.getId() == 2) showResponseSetVal(new String(recievedPkg.getAdditionalData(), ClientMain.DEFAULT_CHAR_SET));
                        case 4: if(recievedPkg.getId() == 4) loadResponseSetVal(new String(recievedPkg.getAdditionalData(), ClientMain.DEFAULT_CHAR_SET));
                        case 5: if(recievedPkg.getId() == 5) infoResponseSetVal(new String(recievedPkg.getAdditionalData(), ClientMain.DEFAULT_CHAR_SET));
                            ClientMain.previousCmdId = recievedPkg.getId();
                            System.out.println(recievedPkg.getCmdData());
                            System.out.println(new String(recievedPkg.getAdditionalData(), ClientMain.DEFAULT_CHAR_SET));
                            break;
                        case 9:
                            ClientMain.previousCmdId = recievedPkg.getId();
                            System.out.println(recievedPkg.getCmdData());
                            System.out.println("Завершение работы программы.");
                            System.exit(0);
                            break;
                        case 7: if(recievedPkg.getId() == 7) addResponseSetVal(recievedPkg.getCmdData());
                        case 3: if(recievedPkg.getId() == 3) addIfMaxResponseSetVal(recievedPkg.getCmdData());
                        case 1: if(recievedPkg.getId() == 1) removeResponseSetVal(recievedPkg.getCmdData());
                        case 601: if(recievedPkg.getId() == 601) importResponseSetVal(recievedPkg.getCmdData());
                            ClientMain.previousCmdId = recievedPkg.getId();
                            System.out.println(recievedPkg.getCmdData());
                            break;
                        case 6:
                            ClientMain.previousCmdId = recievedPkg.getId();
                            ClientMain.line = new String(recievedPkg.getAdditionalData(), ClientMain.DEFAULT_CHAR_SET);
                            System.out.println(ClientMain.line);
                            if(new File(new String(recievedPkg.getAdditionalData(), ClientMain.DEFAULT_CHAR_SET)).exists())
                                continue;
                            else
                                break;
                        case -1:
                            ClientMain.previousCmdId = recievedPkg.getId();
                            System.out.println("Ошибка: ");
                            System.out.print(recievedPkg.getCmdData());
                            if (recievedPkg.getAdditionalData() != null) {
                                System.out.print(new String(recievedPkg.getAdditionalData(), ClientMain.DEFAULT_CHAR_SET));
                                errorResponseSetVal("Ошибка: \n" + recievedPkg.getCmdData() + "\n" + new String(recievedPkg.getAdditionalData(), ClientMain.DEFAULT_CHAR_SET));
                            }
                            System.out.println();
                            errorResponseSetVal("Ошибка: \n" + recievedPkg.getCmdData());
                           
                        case 10:
                            ClientMain.previousCmdId = recievedPkg.getId();
                            System.out.println(recievedPkg.getCmdData());
                            String filePath = new String(recievedPkg.getAdditionalData(), ClientMain.DEFAULT_CHAR_SET);
                            ClientMain.manager.setDefaultCollectionFilePath(filePath);
                            changeDefFileResponseSetVal(recievedPkg.getCmdData());
                            break;
                        case 8:
                            ClientMain.previousCmdId = recievedPkg.getId();
                            System.out.println(recievedPkg.getCmdData());
                            new ClientMain().program.start(new HashSet<>(recievedPkg.getData().collect(Collectors.toList())));
                            break;
                        case 101:
                            TransferPackage transferPackage = new TransferPackage(666, "login {" + ClientMain.user.getLogin() + "} {" + ClientMain.user.getUncryptedPassword() + "}", null);
                            byte[] bytes = transferPackage.getBytes();
                            ClientMain.clientSocket.send(new DatagramPacket(bytes, bytes.length, ClientMain.IPAddress, ClientMain.port));
                            System.out.println("Соединение с сервером восстановлено!");
                            break;
                        case 110:
                            if(recievedPkg.getAdditionalData()[0] == 1){
                                ClientMain.user.setLoggedIn(true);
                                System.out.println("Вы успешно зарегистрированы!");
                                authResponseSetVal("Ok");
                                
                            }
                            if(recievedPkg.getAdditionalData()[0] == 2){
                                ClientMain.user.setLoggedIn(true);
                                System.out.println("Вы успешно авторизированы!");
                                authResponseSetVal("Ok");
                                

                            }

                            break;
                        case 12:
                            ClientMain.manager.writeCollection(CollectionManager.getCollectionFromBytes(recievedPkg.getAdditionalData()));
                            System.out.println(recievedPkg.getCmdData());
                            saveResponseSetVal(recievedPkg.getCmdData());
                        case 9999:
                            isServerShutedDown = true;
                            break;
                    }


                } else {
                    System.out.println("Ничего не пришло!");
                }
                ClientMain.line = "";
                break;

            } catch (SocketTimeoutException e) {
                if(isServerShutedDown) {
                    if (ClientMain.isConnected[0])
                        System.out.println("Соединение с сервером было внезапно разорвано! Попытка соединения");
                    else
                        System.out.print(".");
                    ClientMain.isConnected[0] = false;
                }
            }
            catch (Exception e){
                System.err.println(e.getMessage());
            }
            int k = 0;
        }

        return null;
    }



}
