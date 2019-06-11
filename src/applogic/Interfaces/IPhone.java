package applogic.Interfaces;

import applogic.Humanlike.Human;
import applogic.PhoneNTalks.PhoneCall;
import applogic.PhoneNTalks.PhoneStation;

public interface IPhone {
    public void putDown();
    public ITalky call(long phone_num);
    public long get_number();
    void setCurrent_call(ITalky call);
    PhoneStation getStation();
    void setStation(PhoneStation station);
    Human getOwner();
}
