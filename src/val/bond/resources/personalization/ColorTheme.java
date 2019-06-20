package val.bond.resources.personalization;

import javafx.scene.paint.Color;

public enum  ColorTheme {
    WHEAT("#faffd6", "#faffd6", "#f6fccc",
            "black","lightBlue", "mediumvioletred",
            "#c3c997", "#8cb8ff", "#8cb8ff", Color.BLACK, "#faffd6");

    private String generalGridPaneColor;
    private String appToolBarColor;
    private String profileGridPaneColor;
    private String settingsWorkGridPaneColor;
    private String collectionWorkGridPaneColor;
    private String sidePanelColor;
    private String mapWorkGridPaneColor;
    private String settingsSideHBoxColor;
    private String collectionSideHBoxColor;
    private Color textColor;
    private String costumeClickPaneColor;

    ColorTheme(String generalGridPaneColor, String appToolBarColor, String profileGridPaneColor,String mapWorkGridPaneColor, String settingsWorkGridPaneColor, String collectionWorkGridPaneColor,
               String sidePanelColor,String settingsSideHBoxColor,String collectionSideHBoxColor, Color textColor, String costumeClickPaneColor){

        this.generalGridPaneColor = generalGridPaneColor;
        this.appToolBarColor = appToolBarColor;
        this.profileGridPaneColor = profileGridPaneColor;
        this.settingsWorkGridPaneColor = settingsWorkGridPaneColor;
        this.collectionWorkGridPaneColor =collectionWorkGridPaneColor;
        this.sidePanelColor = sidePanelColor;
        this.mapWorkGridPaneColor = mapWorkGridPaneColor;
        this.settingsSideHBoxColor = settingsSideHBoxColor;
        this.collectionSideHBoxColor = collectionSideHBoxColor;
        this.textColor = textColor;
        this.costumeClickPaneColor = costumeClickPaneColor;

    }

    public Color getTextColor() {
        return textColor;
    }

    public String getAppToolBarColor() {
        return "-fx-background-color: " + appToolBarColor;
    }

    public String getCollectionSideHBoxColor() {
        return "-fx-background-color: " + collectionSideHBoxColor;
    }

    public String getCollectionWorkGridPaneColor() {
        return "-fx-background-color: " + collectionWorkGridPaneColor;
    }

    public String getGeneralGridPaneColor() {
        return "-fx-background-color: " + generalGridPaneColor;
    }

    public String getMapWorkGridPaneColor() {
        return "-fx-background-color: " + mapWorkGridPaneColor;
    }

    public String getProfileGridPaneColor() {
        return "-fx-background-color: " + profileGridPaneColor;
    }

    public String getSettingsSideHBoxColor() {
        return "-fx-background-color: " + settingsSideHBoxColor;
    }

    public String getSettingsWorkGridPaneColor() {
        return "-fx-background-color: " + settingsWorkGridPaneColor;
    }

    public String getSidePanelColor() {
        return "-fx-background-color: " + sidePanelColor;
    }

    public String getCostumeClickPaneColor() {
        return "-fx-background-color: " + costumeClickPaneColor;
    }
}
