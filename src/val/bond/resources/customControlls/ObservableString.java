package val.bond.resources.customControlls;

import java.util.ArrayList;

public class ObservableString {

    private String value;
    private ArrayList<ObservableChangeListener<? super String>> listeners;

    public ObservableString(){
        value = "";
        listeners = new ArrayList<>();
    }

    public ObservableString(String value){
        this.value = value;
        listeners = new ArrayList<>();
    }

    public void clearListeners(){
        listeners.clear();
    }

    public void addListener(ObservableChangeListener<? super String> listener) {
        listeners.add(listener);
    }


    public void removeListener(ObservableChangeListener<? super String> listener) {
        listeners.remove(listener);
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        if(listeners.size() > 0)
            listeners.get(0).changed(this, this.value, value);
        this.value = value;
    }
}
