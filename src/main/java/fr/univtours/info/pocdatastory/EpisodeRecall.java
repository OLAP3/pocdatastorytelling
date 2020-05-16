package fr.univtours.info.pocdatastory;

import fr.univtours.info.model.Structural.Act;
import fr.univtours.info.model.Structural.Episode;
import fr.univtours.info.model.intentional.Measure;
import fr.univtours.info.model.intentional.Message;
import fr.univtours.info.model.intentional.Character;

public class EpisodeRecall {
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    private int code;

    private String theEpisode;
    private String theAct;
    private String theMessage;

    public String getTheEpisode() {
        return theEpisode;
    }

    public void setTheEpisode(String theEpisode) {
        this.theEpisode = theEpisode;
    }

    public String getTheAct() {
        return theAct;
    }

    public void setTheAct(String theAct) {
        this.theAct = theAct;
    }

    public String getTheMessage() {
        return theMessage;
    }

    public void setTheMessage(String theMessage) {
        this.theMessage = theMessage;
    }

    public String getTheMeasure() {
        return theMeasure;
    }

    public void setTheMeasure(String theMeasure) {
        this.theMeasure = theMeasure;
    }

    public String getTheCharacter() {
        return theCharacter;
    }

    public void setTheCharacter(String theCharacter) {
        this.theCharacter = theCharacter;
    }

    private String theMeasure;
    private String theCharacter;





    public EpisodeRecall(int code, String episode, String act, String message,
                         String measure, String character ){
        this.code=code;
        this.theEpisode=episode;
        this.theAct=act;
        this.theCharacter=character;
        this.theMeasure=measure;
        this.theMessage=message;
    }


}
