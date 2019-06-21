package val.bond.applogic.mainpkg;

import javafx.application.Platform;
import javafx.stage.Modality;
import javafx.stage.Stage;
import val.bond.applogic.Buildings.House;
import val.bond.applogic.Buildings.HouseException;
import val.bond.applogic.Buildings.Room;
import val.bond.applogic.Clothes.Costume;
import val.bond.applogic.Enums.BodyType;
import val.bond.applogic.Enums.Color;
import val.bond.applogic.Enums.PropellerModel;
import val.bond.applogic.Enums.RoomType;
import val.bond.applogic.FileSystem.CollectionManager;
import val.bond.applogic.FileSystem.FileManager;
import val.bond.applogic.FileSystem.SomethingWrongException;
import val.bond.applogic.Humanlike.FlyableHuman;
import val.bond.applogic.Humanlike.NormalHuman;
import val.bond.applogic.NetStuff.Net.ShutdownHandler;
import val.bond.applogic.NetStuff.Net.TransferPackage;
import val.bond.applogic.NetStuff.Net.User;
import val.bond.applogic.PhoneNTalks.PhoneCall;
import val.bond.applogic.PhoneNTalks.Talk;
import val.bond.resources.logic.OldNewLogicConnector;
import val.bond.windows.loadingWindow.LoadingWindow;
import val.bond.windows.mainWindow.GeneralWindow;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ClientMain {

    public static final String DEFAULT_CHAR_SET = "UTF-8";

    public class Startingprogramm{
        public void start(HashSet<Costume> costumeList) {
            class Baby extends NormalHuman {
                Baby(){
                    super("Малыш", true, 15,
                            Color.Blonde, true, BodyType.MediumBuild, false, 50);
                }
            }
            ArrayList<Costume> costumes = new ArrayList<Costume>(costumeList);

            Baby baby = new Baby();
            baby.set_costume(costumes.get(0));

            FlyableHuman carlson = new FlyableHuman("Карлсон", true, 20,
                    Color.Blonde, true, BodyType.Strong, true, 80){
            };
            carlson.setPropellerModel(PropellerModel.P420);
            carlson.set_costume(costumes.get(1));

            NormalHuman fBock = new NormalHuman("Фрекен Бок", true, 40,
                    Color.Red, false, BodyType.Plump, true, 50);
            fBock.set_costume(costumes.get(2));

            NormalHuman bosse = new NormalHuman("Боссе", true, 17,
                    Color.White, true, BodyType.Athletic, true, 70);
            bosse.set_costume(costumes.get(3));


            NormalHuman frida = new NormalHuman("Фрида", true, 40,
                    Color.Orange, false, BodyType.MediumBuild, false, 50);
            frida.set_costume(costumes.get(4));

            Room lobby = new Room(RoomType.Lobby, baby, carlson, fBock, frida, bosse);

            Room bossesRoom = new Room(RoomType.BedRoom);

            lobby.add_rooms(new Room(RoomType.Hall), new Room(RoomType.BedRoom), new Room(RoomType.Kitchen)
                    , bossesRoom);

            House house = null;
            try {
                house = new House(lobby);
            } catch (HouseException e) {
                e.printStackTrace();
            }
            if (house != null)
                house.add(baby, carlson, fBock, frida, bosse);
            baby.Goto(house.getLobby().get_rooms().get(0));
            carlson.Goto(house.getLobby().get_rooms().get(2));
            fBock.Goto(house.getLobby().get_rooms().get(0));
            frida.Goto(house.getLobby().get_rooms().get(1));
            bosse.Goto(house.getLobby().get_rooms().get(3));

            fBock.becomeBusy();
            PhoneCall call_1 = (PhoneCall) frida.get_phone().call(baby.get_phone().get_number());

            baby.set_positive_mood();

            if (fBock.is_busy()){
                call_1.say(baby, String.format("%s занята!", fBock.toString()));
            }

            for(int i = 0; i < 5; i++ ){
                call_1.ask(frida);
                call_1.answer(baby);
            }

            call_1.say(baby, "Хватит вопросов, я же сказал, что она занята!");

            baby.get_phone().putDown();

            for (Room r : house.getLobby().get_rooms()){
                baby.Goto(r);
                if(baby.search_here(carlson))
                    break;
            }

            Talk talk = new Talk(house, baby.get_position().getRoom(), baby, carlson);

            for(int i = 0; i < 2; i++){
                talk.say_joke(baby);
                talk.answ_joke(carlson);
                talk.say_joke(carlson);
                talk.answ_joke(baby);
            }
            talk.end();

            carlson.fly();
            carlson.get_position().getHouse().get_people().remove(carlson);


            baby.set_negative_mood();
            baby.Goto(house.getLobby().get_rooms().get(3));

            bosse.Goto(house.getLobby().get_rooms().get(2));
            baby.Goto(house.getLobby().get_rooms().get(2));
            fBock.Goto(house.getLobby().get_rooms().get(2));
            frida.Goto(house.getLobby().get_rooms().get(2));

            frida.drink_alcohol();
            fBock.drink_alcohol();
            baby.drink_alcohol();
            bosse.drink_alcohol();

            ClientMain.pause("Конец!!!");
        }
    }
    public static String line = "";

    public static DatagramSocket clientSocket;
    public static InetAddress IPAddress;
    public static int port = 0;

    public static Scanner scanner = new Scanner(System.in);
    public static FileManager manager;
    public static final boolean[] isConnected = {true};
    public static int previousCmdId = 0;
    public static User user = new User();

    private static boolean isReadyToRead = true;

    private static String programmOutput = "";

    static {
        manager = new FileManager("file.xml");
        try {
            clientSocket = new DatagramSocket();
            IPAddress = InetAddress.getByName(OldNewLogicConnector.ipAddress);
        } catch (SocketException | UnknownHostException e) {
            System.err.println(e.getMessage());
        }
    }

    public Startingprogramm program;

    public ClientMain(){
        program = new Startingprogramm();
    }

    public  static void main(String[] args) throws IOException {
        /*if(args.length == 0){
            System.out.println("Введите адресс и порт в соответствующем порядке!");
            System.exit(0);
        }

        if(args.length == 1){
            System.out.println("Введите порт!");
            System.exit(0);
        }*/
        clientSocket.setSoTimeout(2000);
        Runtime.getRuntime().addShutdownHook(new ShutdownHandler(user, clientSocket, IPAddress, port));
        //System.out.println("Введите команду help для получения полного списка команд.");

        Thread thread = new Thread(ClientMain::readFromServerCmd);
        thread.start();
        OldNewLogicConnector.commandLine.addListener((observable, oldValue, newValue) -> {
            port = OldNewLogicConnector.port;
            try {
                IPAddress = InetAddress.getByName(OldNewLogicConnector.ipAddress);
            } catch (UnknownHostException e) {
                System.err.println(e.getMessage());
            }
            line = newValue;
            while (!isReadyToRead) {
                continue;
            }
            new Thread(()->sendCmd()).start();
            //Придется юзать рефлексию, чтобы по-тихому очистить значение OldNewLogicConnector.commandLine

        });

    }

    public static void sendCmd(){
        try {
            TransferPackage tpkg = null;

            try {

                String loginRegex = "login \\{.+} \\{.+}";

                if (previousCmdId == 6) {
                    byte[] bytes;
                    try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                         ObjectOutputStream dos = new ObjectOutputStream(baos)) {
                        dos.writeObject(CollectionManager.getCollectionFromXML(manager.getXmlFromFile(line)));
                        bytes = baos.toByteArray();
                    }
                    line = "I1A8S1D1F0G0H";
                    tpkg = new TransferPackage(666, line,
                            null, bytes);
                } else {


                    String input = null;
                    ////
                    //line = scanMultiLines(scanner);

                    if (line.matches(loginRegex)) {
                        //new ShutdownHandler(user, clientSocket, IPAddress, port).run();
                        String[] logParts = line.split(" ");
                        user.setLoggedIn(false);
                        user.setLogin(logParts[1].substring(1, logParts[1].length() - 1));
                        user.setPassword(logParts[2].substring(1, logParts[2].length() - 1));
                        if (logParts.length == 4) {
                            user.setEmail(logParts[3].substring(1, logParts[3].length() - 1));
                            tpkg = new TransferPackage(110, "login {" + user.getLogin() + "} {" + user.getUncryptedPassword() + "} {" + user.getEmail() + "}", null);
                        } else {
                            tpkg = new TransferPackage(110, "login {" + user.getLogin() + "} {" + user.getUncryptedPassword() + "}", null);
                        }
                    }
                    input = line.split(" ")[0];
                    ////

                    /// Блок кода разрешающий выполнение упомянутых в блоке комманд если файл не существует !!!!!!
                           /* if (!manager.isDefaultFileExists() && user.isLoggedIn()) {
                                switch (input) {
                                    case "help":
                                    case "change_def_file_path":
                                        continue;
                                    default:
                                        line = "";
                                        System.out.println("Файл с коллекцией не найден!");
                                        break;

                                }
                            }*/
                }

                if (!line.matches(loginRegex)) {
                    if (line.equals("help") | line.split(" ")[0].equals("change_def_file_path")) {
                        tpkg = new TransferPackage(666, line,
                                null, CollectionManager.collectionStringXML.getBytes(ClientMain.DEFAULT_CHAR_SET));
                    } else if (line.trim().equals("load")) {
                        String addbytes = manager.getXmlFromFile();
                        if (addbytes == null) throw new SomethingWrongException("Файл с коллекцией отсутствует!");
                        tpkg = new TransferPackage(666, line,
                                null, addbytes.getBytes(ClientMain.DEFAULT_CHAR_SET));
                    } else
                        tpkg = new TransferPackage(666, line, null);
                    ///.....
                }

            } catch (SomethingWrongException e) {
                System.err.println(e.getMessage());

            } catch (NoSuchElementException e) {
                System.err.println("Завершение работы программы.");
                System.exit(0);
            } catch (FileNotFoundException e) {
                System.err.println("Файл с коллекцией не найден.");

            }
            if (user.isLoggedIn())
                if (line.trim().equals("load"))
                    tpkg = new TransferPackage(666, line,
                            null, manager.getXmlFromFile().getBytes(ClientMain.DEFAULT_CHAR_SET));
                else
                    tpkg = new TransferPackage(666, line, null);


            tpkg.setUser(user);
                /*if(!isConnected[0])
                    tpkg.setId(763);*/
            byte[] sendData = tpkg.getBytes();
            DatagramPacket sendingPkg = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            clientSocket.send(sendingPkg);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }


    public static void readFromServerCmd(){
        boolean isServerShutedDown = false;
        while (true) {
            try {
                //sendCmd()
                if(!isConnected[0]){
                    sendCmd();
                }

                byte[] receiveData = new byte[65536];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                isReadyToRead = true;
                clientSocket.receive(receivePacket);
                isReadyToRead = false;
                if (!isConnected[0])
                    System.out.println();
                isConnected[0] = true;
                Platform.runLater(()->OldNewLogicConnector.isConnected.setValue(true));
                isServerShutedDown = false;
                System.err.println("Босс принял пакет!");
                TransferPackage recievedPkg = TransferPackage.restoreObject(new ByteArrayInputStream(receivePacket.getData()));
                if (recievedPkg != null) {
                    switch (recievedPkg.getId()) {
                        case 11: if(recievedPkg.getId() == 11) Platform.runLater(()-> {
                            try {
                                OldNewLogicConnector.helpResponse.setValue(new String(recievedPkg.getAdditionalData(), ClientMain.DEFAULT_CHAR_SET));
                            } catch (UnsupportedEncodingException e) {
                                System.err.println(e.getMessage());
                            }
                        });
                        case 2: if(recievedPkg.getId() == 2) Platform.runLater(()-> {
                            try {
                                OldNewLogicConnector.showResponse.setValue(new String(recievedPkg.getAdditionalData(), ClientMain.DEFAULT_CHAR_SET));
                                GeneralWindow.notificationOkCmdProperty.setValue(true);
                            } catch (UnsupportedEncodingException e) {
                                System.err.println(e.getMessage());
                            }
                        });
                        case 4: if(recievedPkg.getId() == 4) Platform.runLater(()-> {
                            try {
                                OldNewLogicConnector.loadResponse.setValue(new String(recievedPkg.getAdditionalData(), ClientMain.DEFAULT_CHAR_SET));
                                GeneralWindow.notificationOkCmdProperty.setValue(true);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        });
                        case 5: if(recievedPkg.getId() == 5) Platform.runLater(()-> {
                            try {
                                OldNewLogicConnector.infoResponse.setValue(new String(recievedPkg.getAdditionalData(), ClientMain.DEFAULT_CHAR_SET));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        });
                        case 322:if(recievedPkg.getId() == 322) Platform.runLater(()-> {
                            try {
                                OldNewLogicConnector.showCostumeResponse.setValue(new String(recievedPkg.getAdditionalData(), ClientMain.DEFAULT_CHAR_SET));
                            } catch (UnsupportedEncodingException e) {
                                System.err.println(e.getMessage());
                            }
                        });
                            previousCmdId = recievedPkg.getId();
                            System.out.println(recievedPkg.getCmdData());
                            System.out.println(new String(recievedPkg.getAdditionalData(), ClientMain.DEFAULT_CHAR_SET));
                            break;
                        case 9:
                            previousCmdId = recievedPkg.getId();
                            System.out.println(recievedPkg.getCmdData());
                            System.out.println("Завершение работы программы.");
                            System.exit(0);
                            break;
                        case 7: if(recievedPkg.getId() == 7) Platform.runLater(()->OldNewLogicConnector.addResponse.setValue(recievedPkg.getCmdData()));
                        case 3: if(recievedPkg.getId() == 3) Platform.runLater(()->OldNewLogicConnector.addIfMaxResponse.setValue(recievedPkg.getCmdData()));
                        case 1: if(recievedPkg.getId() == 1) Platform.runLater(() -> OldNewLogicConnector.removeResponse.setValue(recievedPkg.getCmdData()));
                        case 6: if(recievedPkg.getId() == 6) Platform.runLater(()->OldNewLogicConnector.importResponse.setValue(recievedPkg.getCmdData()));
                            previousCmdId = recievedPkg.getId();
                            System.out.println(recievedPkg.getCmdData());
                            GeneralWindow.notificationOkCmdProperty.setValue(true);
                            break;
                        case -1:
                            previousCmdId = recievedPkg.getId();
                            System.out.println("Ошибка: ");
                            System.out.print(recievedPkg.getCmdData());
                            if (recievedPkg.getAdditionalData() != null) {
                                System.out.print(new String(recievedPkg.getAdditionalData(), ClientMain.DEFAULT_CHAR_SET));
                                Platform.runLater(()-> {
                                    try {
                                        OldNewLogicConnector.errorResponse.setValue("Ошибка: \n" + recievedPkg.getCmdData() + "\n" + new String(recievedPkg.getAdditionalData(), ClientMain.DEFAULT_CHAR_SET));
                                    } catch (UnsupportedEncodingException e) {
                                        System.err.println(e.getMessage());
                                    }
                                });

                            }
                            System.out.println();
                            Platform.runLater(()->OldNewLogicConnector.errorResponse.setValue("Ошибка: \n" + recievedPkg.getCmdData()));

                        case 10:
                            previousCmdId = recievedPkg.getId();
                            System.out.println(recievedPkg.getCmdData());
                            String filePath = new String(recievedPkg.getAdditionalData(), ClientMain.DEFAULT_CHAR_SET);
                            manager.setDefaultCollectionFilePath(filePath);
                            Platform.runLater(()->OldNewLogicConnector.changeDefFileResponse.setValue(recievedPkg.getCmdData()));
                            GeneralWindow.notificationOkCmdProperty.setValue(true);
                            break;
                        case 8:
                            previousCmdId = recievedPkg.getId();
                            System.out.println(recievedPkg.getCmdData());
                            programmOutput = "";
                            new ClientMain().program.start(new HashSet<>(recievedPkg.getData().collect(Collectors.toList())));
                            Platform.runLater(()->OldNewLogicConnector.startResponse.setValue(programmOutput));
                            break;
                        case 101:
                            TransferPackage transferPackage = new TransferPackage(666, "login {" + user.getLogin() + "} {" + user.getUncryptedPassword() + "}", null);
                            byte[] bytes = transferPackage.getBytes();
                            clientSocket.send(new DatagramPacket(bytes, bytes.length, IPAddress, port));
                            System.out.println("Соединение с сервером восстановлено!");
                            break;
                        case 110:
                            if(recievedPkg.getAdditionalData()[0] == 1){
                                user.setLoggedIn(true);
                                System.out.println("Вы успешно зарегистрированы!");
                                Platform.runLater(()->OldNewLogicConnector.authResponse.setValue("Ok"));
                            }
                            if(recievedPkg.getAdditionalData()[0] == 2){
                                user.setLoggedIn(true);
                                System.out.println("Вы успешно авторизированы!");
                                Platform.runLater(()->OldNewLogicConnector.authResponse.setValue("Ok"));

                            }

                            break;
                        case 12:
                            manager.writeCollection(CollectionManager.getCollectionFromBytes(recievedPkg.getAdditionalData()));
                            System.out.println(recievedPkg.getCmdData());
                            Platform.runLater(()->OldNewLogicConnector.saveResponse.setValue(recievedPkg.getCmdData()));
                            GeneralWindow.notificationOkCmdProperty.setValue(true);
                        case 9999:
                            isServerShutedDown = true;
                            break;
                    }
                } else {
                    System.out.println("Ничего не пришло!");
                }
                line = "";

            } catch (SocketTimeoutException e) {
                if(isServerShutedDown) {

                    if(OldNewLogicConnector.isConnected.getValue()){

                            Platform.runLater(()-> {
                                try {
                                    Stage stage = new Stage();
                                    stage.initModality(Modality.APPLICATION_MODAL);
                                    new LoadingWindow().start(stage);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            });
                    }
                    Platform.runLater(()->OldNewLogicConnector.isConnected.setValue(false));

                    if (isConnected[0])
                        System.out.println("Соединение с сервером было внезапно разорвано! Попытка соединения");
                    else
                        System.out.print(".");
                    isConnected[0] = false;
                }
            }
            catch (Exception e){
                System.err.println(e.getMessage());
            }
            int k = 0;
        }

    }

    public static ArrayList<String> findMatches(String patterStr, String text){
        Pattern pattern = Pattern.compile(patterStr);
        Matcher matcher = pattern.matcher(text);
        ArrayList<String> collection = new ArrayList<>();
        while(matcher.find()){
            collection.add(text.substring(matcher.start(), matcher.end()));
        }
        return collection;
    }

    public static String scanMultiLines(Scanner scanner){
        StringBuilder input = new StringBuilder();
        String cur = "";
        while(true){
            cur = scanner.nextLine();
            if(cur.matches(".*\\$")){
                input.append(cur.substring(0, cur.length() - 1));
                break;
            }
            else{
                input.append(cur);
            }
        }

        return input.toString();
    }

    public static void pause(String message){
        programmOutput += message + "\n";
    }

    public static int strHashCode(String str){
        int result = 13;
        int prime = 26;
        for (int i = 0; i < str.length(); i++) {
            result = result * prime + (int)str.charAt(i) * (int)Math.pow(prime, i + 1);
        }
        return result;
    }
}




