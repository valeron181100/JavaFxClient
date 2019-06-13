package val.bond.resources.customControlls;

public interface ObservableChangeListener<T> {
    void changed(Object observable, T oldValue, T newValue);
}
