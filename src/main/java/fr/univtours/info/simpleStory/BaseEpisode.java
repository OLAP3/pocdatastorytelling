package fr.univtours.info.simpleStory;

import fr.univtours.info.model.Structural.Episode;
import fr.univtours.info.model.intentional.Measure;
import fr.univtours.info.model.intentional.Message;
import fr.univtours.info.model.intentional.Character;

import java.util.ArrayList;
import java.util.Collection;

public abstract class BaseEpisode implements Episode {

    String theText;
    Message theMessage;
    Collection<Character> theCharacters;
    Collection<Measure> theMeasures;

    public BaseEpisode(){
        theCharacters =new ArrayList<Character>();
        theMeasures =new ArrayList<Measure>();

    }
    @Override
    public void narrates(Message anMessage) {

        this.theMessage = anMessage;
    }

    @Override
    public Message narrates() {
        return theMessage;
    }

    @Override
    public void playsIn(Character aCharacter) {

        theCharacters.add(aCharacter);
    }

    @Override
    public Collection<Character> playsIn() {

        return theCharacters;
    }

    public void refersTo(Measure aMeasure){
        theMeasures.add(aMeasure);
    }

    public Collection<Measure> refersTo(){
        return theMeasures;
    }



    @Override
    public void addText(String aText) {
        this.theText=aText.substring(1,aText.length()-1).replace("\\n","\n");
    }
}
