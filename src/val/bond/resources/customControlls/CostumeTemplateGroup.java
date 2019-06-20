package val.bond.resources.customControlls;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.json.JSONArray;
import org.json.JSONObject;
import val.bond.resources.models.CommandClickEvent;
import val.bond.resources.models.CostumeTemplate;

import java.util.ArrayList;

public class CostumeTemplateGroup {

    private ArrayList<CostumeTemplate> templates;
    private ArrayList<EventHandler<MouseEvent>> listeners;

    private CostumeTemplate lastClicked;

    public CostumeTemplateGroup(int size, String serverResponse){
        templates = new ArrayList<>();
        listeners = new ArrayList<>();
        JSONObject response = new JSONObject(serverResponse);
        JSONArray array = response.getJSONArray("array");
        array.forEach(p -> {
            StringBuilder color = new StringBuilder("#");
            for (int i = 0; i < 6; i++) {
                color.append(Integer.toString((int) (Math.random() * 15), 16));
            }
            JSONObject costumeData = (JSONObject)p;
            CostumeTemplate template = new CostumeTemplate(size, size, costumeData.toString());
            template.setColor(Color.web(color.toString()));
            templates.add(template);
        });

        templates.forEach(p -> p.setLabel(p.getCostumeName()));

        templates.forEach(p->{
            p.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                lastClicked = (CostumeTemplate) event.getSource();
            });
        });
    }

    public CostumeTemplate getLastClicked() {
        return lastClicked;
    }

    public ArrayList<CostumeTemplate> getTemplates() {
        return templates;
    }

    public void addGroupListener(CommandClickEvent handler){
        templates.forEach(p -> p.setOnMouseClickedEvent(handler));
    }

}
