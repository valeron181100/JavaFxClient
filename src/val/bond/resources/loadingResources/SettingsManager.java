package val.bond.resources.loadingResources;

import org.json.JSONObject;

import java.io.*;
import java.util.Scanner;

public class SettingsManager {
    private static String defaultJson = "{\"avatar_path\":\"F:\\IntelliJIdea\\Projects\\JavaFxProject\\src\\val\\bond\\images\\man.png\",\"theme\":\"WHEAT\",\"locale\":\"ru_RU\"}";

    public static String getSetting(String key){
        File file = new File("settings.json");
        if(!file.exists()) {
            try {
                FileWriter writer = new FileWriter(file);
                writer.write(defaultJson);
                writer.close();
                return getSetting(key);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        else{
            try {
                Scanner scanner = new Scanner(file);
                StringBuilder fileContent = new StringBuilder();

                while (scanner.hasNextLine()){
                    fileContent.append(scanner.nextLine());
                }

                JSONObject settingsJson = new JSONObject(fileContent.toString());
                return settingsJson.getString(key);

            } catch (FileNotFoundException e) {
                System.err.println(e.getMessage());
            }

        }
        return null;
    }

    public static void setSetting(String key, String val){
        File file = new File("settings.json");
        if(!file.exists()) {
            try {
                FileWriter writer = new FileWriter(file);
                JSONObject settingsJson = new JSONObject(defaultJson);
                settingsJson.put(key, val);
                writer.write(settingsJson.toString());
                writer.close();
                //getSetting(key);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        else{
            try {
                Scanner scanner = new Scanner(file);
                StringBuilder fileContent = new StringBuilder();

                while (scanner.hasNextLine()){
                    fileContent.append(scanner.nextLine());
                }

                JSONObject settingsJson = new JSONObject(fileContent.toString());
                settingsJson.put(key, val);
                FileWriter writer = new FileWriter(file);
                writer.write(settingsJson.toString());
                writer.close();

            } catch (FileNotFoundException e) {
                System.err.println(e.getMessage());
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }

        }
    }
}
