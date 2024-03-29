package val.bond.applogic.Interfaces;

import val.bond.applogic.Humanlike.Human;
import val.bond.applogic.PhoneNTalks.Joke;
import val.bond.applogic.PhoneNTalks.JokeNQuestionMachine;
import val.bond.applogic.PhoneNTalks.Question;
import val.bond.applogic.mainpkg.ClientMain;

public interface ITalky {
    default void ask(Human hm){
        set_current_quest(JokeNQuestionMachine.getRandomQuestion());
        ClientMain.pause(hm.toString() + ": \""+ getCurrent_question().getQuestion() + "\"");
    }
    default void answer(Human hm){
        if (getCurrent_question() != null){
            ClientMain.pause(hm.toString() + ": \""+ getCurrent_question().getAnswer() + "\"");
        }
        else{
            ClientMain.pause(hm.toString() + ": \"Я не знаю, что тебе сказать(((\"");
        }
    }
    default void say_joke(Human hm){
        Joke joke = JokeNQuestionMachine.get_joke(hm.get_humorlvl());
        set_current_joke(joke);
        ClientMain.pause(hm.toString() + ": \""+joke.getJoke() + "\"");
    }
    default void answ_joke(Human hm){
        if (getCurrent_joke() != null){
            ClientMain.pause(hm.toString() + ": \""+ getCurrent_joke().getAnswer() + "\"");
        }
        else{
            ClientMain.pause(hm.toString() + ": \"Я не знаю, что тебе сказать(((\"");
        }
    }
    default void say(Human hm, String message){
        ClientMain.pause(hm.toString()+": "+message);
    }
    void set_current_quest(Question current_question);
    void set_current_joke(Joke current_joke);
    Question getCurrent_question();
    Joke getCurrent_joke();
    void end();
}
