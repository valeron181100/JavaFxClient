package val.bond.applogic.mainpkg;

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

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
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
    private static String line = "";

    public static DatagramSocket clientSocket;
    private static InetAddress IPAddress;
    private static int port = 0;

    private static Scanner scanner = new Scanner(System.in);
    private static FileManager manager;
    private static final boolean[] isConnected = {true};
    private static int previousCmdId = 0;
    private static User user = new User();

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

        OldNewLogicConnector.commandLine.addListener((observable, oldValue, newValue) -> {
            port = OldNewLogicConnector.port;
            try {
                IPAddress = InetAddress.getByName(OldNewLogicConnector.ipAddress);
            } catch (UnknownHostException e) {
                System.err.println(e.getMessage());
            }
            line = newValue;
            commandCycle();
            //Придется юзать рефлексию, чтобы по-тихому очистить значение OldNewLogicConnector.commandLine

        });

    }


    public static String commandCycle(){
        while (true) {
            try {
                TransferPackage tpkg = null;

                    try {

                        String loginRegex = "login \\{.+} \\{.+}";

                        if(previousCmdId == 6) {
                            byte[] bytes;
                            try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                 ObjectOutputStream dos = new ObjectOutputStream(baos)) {
                                dos.writeObject(CollectionManager.getCollectionFromXML(manager.getXmlFromFile(line)));
                                bytes = baos.toByteArray();
                            }
                            line = "I1A8S1D1F0G0H";
                            tpkg = new TransferPackage(666, line,
                                    null, bytes);
                        }
                        else {


                            String input = null;
                            ////
                            //line = scanMultiLines(scanner);

                            if (line.matches(loginRegex)) {
                                //new ShutdownHandler(user, clientSocket, IPAddress, port).run();
                                String[] logParts = line.split(" ");
                                user.setLogin(logParts[1].substring(1, logParts[1].length() - 1));
                                user.setPassword(logParts[2].substring(1, logParts[2].length() - 1));
                                if (logParts.length == 4) {
                                    user.setEmail(logParts[3].substring(1, logParts[3].length() - 1));
                                    tpkg = new TransferPackage(110, "login {" + user.getLogin() + "} {" + user.getUncryptedPassword() + "} {" + user.getEmail() + "}", null);
                                }
                                else{
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

                        if(!line.matches(loginRegex)) {
                            if (line.equals("help") | line.split(" ")[0].equals("change_def_file_path")) {
                                tpkg = new TransferPackage(666, line,
                                        null, CollectionManager.collectionStringXML.getBytes(ClientMain.DEFAULT_CHAR_SET));
                            } else if (line.trim().equals("load")) {
                                String addbytes = manager.getXmlFromFile();
                                if(addbytes == null) throw new SomethingWrongException("Файл с коллекцией отсутствует!");
                                tpkg = new TransferPackage(666, line,
                                        null, addbytes.getBytes(ClientMain.DEFAULT_CHAR_SET));
                            }
                            else
                                tpkg = new TransferPackage(666, line, null);
                            ///.....
                        }

                    } catch (SomethingWrongException e) {
                        System.err.println(e.getMessage());
                        break;
                    }catch (NoSuchElementException e) {
                        System.err.println("Завершение работы программы.");
                        System.exit(0);
                    }catch (FileNotFoundException e){
                        System.err.println("Файл с коллекцией не найден.");
                        break;
                    }
                    if (user.isLoggedIn())
                        if(line.trim().equals("load"))
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

                byte[] receiveData = new byte[65536];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);
                if (!isConnected[0])
                    System.out.println();
                isConnected[0] = true;
                System.err.println("Босс принял пакет!");
                TransferPackage recievedPkg = TransferPackage.restoreObject(new ByteArrayInputStream(receivePacket.getData()));
                if (recievedPkg != null) {
                    switch (recievedPkg.getId()) {
                        case 11: if(recievedPkg.getId() == 11) OldNewLogicConnector.helpResponse.setValue(new String(recievedPkg.getAdditionalData(), ClientMain.DEFAULT_CHAR_SET));
                        case 2: if(recievedPkg.getId() == 2) OldNewLogicConnector.showResponse.setValue(new String(recievedPkg.getAdditionalData(), ClientMain.DEFAULT_CHAR_SET));
                        case 4: if(recievedPkg.getId() == 4) OldNewLogicConnector.loadResponse.setValue(new String(recievedPkg.getAdditionalData(), ClientMain.DEFAULT_CHAR_SET));
                        case 5: if(recievedPkg.getId() == 5) OldNewLogicConnector.infoResponse.setValue(new String(recievedPkg.getAdditionalData(), ClientMain.DEFAULT_CHAR_SET));
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
                        case 7: if(recievedPkg.getId() == 7) OldNewLogicConnector.addResponse.setValue(recievedPkg.getCmdData());
                        case 3: if(recievedPkg.getId() == 3) OldNewLogicConnector.addIfMaxResponse.setValue(recievedPkg.getCmdData());
                        case 1: if(recievedPkg.getId() == 1) OldNewLogicConnector.removeResponse.setValue(recievedPkg.getCmdData());
                        case 601: if(recievedPkg.getId() == 601) OldNewLogicConnector.importResponse.setValue(recievedPkg.getCmdData());
                            previousCmdId = recievedPkg.getId();
                            System.out.println(recievedPkg.getCmdData());
                            break;
                        case 6:
                            previousCmdId = recievedPkg.getId();
                            line = new String(recievedPkg.getAdditionalData(), ClientMain.DEFAULT_CHAR_SET);
                            System.out.println(line);
                            if(new File(new String(recievedPkg.getAdditionalData(), ClientMain.DEFAULT_CHAR_SET)).exists())
                                continue;
                            else
                                break;
                        case -1:
                            previousCmdId = recievedPkg.getId();
                            System.out.println("Ошибка: ");
                            System.out.print(recievedPkg.getCmdData());
                            if (recievedPkg.getAdditionalData() != null) {
                                System.out.print(new String(recievedPkg.getAdditionalData(), ClientMain.DEFAULT_CHAR_SET));
                                OldNewLogicConnector.errorResponse.setValue("Ошибка: \n" + recievedPkg.getCmdData() + "\n" + new String(recievedPkg.getAdditionalData(), ClientMain.DEFAULT_CHAR_SET));
                                return "Ошибка: \n" + recievedPkg.getCmdData() + "\n" + new String(recievedPkg.getAdditionalData(), ClientMain.DEFAULT_CHAR_SET);
                            }
                            System.out.println();
                            OldNewLogicConnector.errorResponse.setValue("Ошибка: \n" + recievedPkg.getCmdData());
                            return "Ошибка: \n" + recievedPkg.getCmdData();
                        case 10:
                            previousCmdId = recievedPkg.getId();
                            System.out.println(recievedPkg.getCmdData());
                            String filePath = new String(recievedPkg.getAdditionalData(), ClientMain.DEFAULT_CHAR_SET);
                            manager.setDefaultCollectionFilePath(filePath);
                            OldNewLogicConnector.changeDefFileResponse.setValue(recievedPkg.getCmdData());
                            break;
                        case 8:
                            previousCmdId = recievedPkg.getId();
                            System.out.println(recievedPkg.getCmdData());
                            new ClientMain().program.start(new HashSet<>(recievedPkg.getData().collect(Collectors.toList())));
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
                                OldNewLogicConnector.authResponse.setValue("Ok");
                                return "Вы успешно зарегистрированы!";
                            }
                            if(recievedPkg.getAdditionalData()[0] == 2){
                                user.setLoggedIn(true);
                                System.out.println("Вы успешно авторизированы!");
                                OldNewLogicConnector.authResponse.setValue("Ok");
                                return "Вы успешно авторизированы!";

                            }

                            break;
                        case 12:
                            manager.writeCollection(CollectionManager.getCollectionFromBytes(recievedPkg.getAdditionalData()));
                            System.out.println(recievedPkg.getCmdData());
                            OldNewLogicConnector.saveResponse.setValue(recievedPkg.getCmdData());
                    }


                } else {
                    System.out.println("Ничего не пришло!");
                }
                line = "";
                break;

            } catch (SocketTimeoutException e) {
                if(isConnected[0])
                    System.out.println("Соединение с сервером было внезапно разорвано! Попытка соединения");
                else
                    System.out.print(".");
                isConnected[0] = false;
            }
            catch (Exception e){
                System.err.println(e.getMessage());
            }
            int k = 0;
        }

        return "";
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
        System.out.println(message);
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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




