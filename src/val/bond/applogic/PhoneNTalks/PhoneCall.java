package val.bond.applogic.PhoneNTalks;

import val.bond.applogic.Buildings.House;
import val.bond.applogic.Humanlike.Human;
import val.bond.applogic.Interfaces.IPhone;
import val.bond.applogic.Interfaces.ITalky;
import val.bond.applogic.mainpkg.ClientMain;

public class PhoneCall implements ITalky {
    private IPhone phone1;
    private IPhone phone2;
    private Question current_question;
    private Joke current_joke;
    private House house;

    public PhoneCall(IPhone phone1, IPhone phone2, House house){
        if (phone1 != null && phone2 != null){
            this.phone1 = phone1;
            this.phone2 = phone2;
            this.house = house;
            phone1.setCurrent_call(this);
            phone2.setCurrent_call(this);
            if (house.getStation() != null){
                house.getStation().createCall(this);
            }
        }
    }

    @Override
    public Joke getCurrent_joke() {
        return current_joke;
    }

    @Override
    public Question getCurrent_question() {
        return current_question;
    }

    @Override
    public void set_current_quest(Question current_question) {
        this.current_question = current_question;
    }

    @Override
    public void set_current_joke(Joke current_joke) {
        this.current_joke = current_joke;
    }

    public void end(){
        house.getStation().rmCall(this);
        ClientMain.pause("Связь прекращена: " + phone1.getOwner().toString() + " и " + phone2.getOwner().toString());
    }

    @Override
    public void ask(Human hm){
        if (house.getStation().getCalls().contains(this)) {
            set_current_quest(JokeNQuestionMachine.getRandomQuestion());
            ClientMain.pause(hm.toString() + ": \"" + getCurrent_question().getQuestion() + "\"");
        }
        else{
            ClientMain.pause("Извините, но этого звонка больше не существует(((");
        }
    }

    @Override
    public void answer(Human hm){
        if (house.getStation().getCalls().contains(this)) {
            if (getCurrent_question() != null) {
                ClientMain.pause(hm.toString() + ": \"" + getCurrent_question().getAnswer() + "\"");
            } else {
                ClientMain.pause(hm.toString() + ": \"Я не знаю, что тебе сказать(((\"");
            }
        }
        else{
            ClientMain.pause("Извините, но этого звонка больше не существует(((");
        }
    }

    @Override
    public void say_joke(Human hm){
        if (house.getStation().getCalls().contains(this)) {
            Joke joke = JokeNQuestionMachine.get_joke(hm.get_humorlvl());
            set_current_joke(joke);
            ClientMain.pause(hm.toString() + ": \"" + joke.getJoke() + "\"");
        }
        else{
            ClientMain.pause("Извините, но этого звонка больше не существует(((");
        }
    }

    @Override
    public void answ_joke(Human hm) {
        if (house.getStation().getCalls().contains(this)) {
            if (getCurrent_joke() != null) {
                ClientMain.pause(hm.toString() + ": \"" + getCurrent_joke().getAnswer() + "\"");
            } else {
                ClientMain.pause(hm.toString() + ": \"Я не знаю, что тебе сказать(((\"");
            }
        }
        else{
            ClientMain.pause("Извините, но этого звонка больше не существует(((");
        }
    }

    @Override
    public void say(Human hm, String message){
        if (house.getStation().getCalls().contains(this)) {
            ClientMain.pause(hm.toString() + ": " + message);
        }
        else{
            ClientMain.pause("Извините, но этого звонка больше не существует(((");
        }
    }

    public IPhone getPhone1() {
        return phone1;
    }

    public House getHouse() {
        return house;
    }

    public IPhone getPhone2() {
        return phone2;
    }

    @Override
    public int hashCode() {
        int prime = 24;
        int result = 1;
        result = prime * result + phone1.hashCode() + phone2.hashCode() +
                current_joke.hashCode() + current_question.hashCode() +
                house.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("The call between %s and %s", phone1.getOwner(), phone2.getOwner());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PhoneCall other = (PhoneCall) obj;
        if (!phone1.equals(other.phone1))
            return false;
        if (!phone2.equals(other.phone2))
            return false;
        if (!current_question.equals(other.current_question))
            return false;
        if (!current_joke.equals(other.current_joke))
            return false;
        if (!house.equals(other.house))
            return false;
        return true;
    }
}
