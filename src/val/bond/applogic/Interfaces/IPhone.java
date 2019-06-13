package val.bond.applogic.Interfaces;

import val.bond.applogic.Humanlike.Human;
import val.bond.applogic.PhoneNTalks.PhoneStation;

public interface IPhone {
    public void putDown();
    public ITalky call(long phone_num);
    public long get_number();
    void setCurrent_call(ITalky call);
    PhoneStation getStation();
    void setStation(PhoneStation station);
    Human getOwner();
}
