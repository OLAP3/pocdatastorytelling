package fr.univtours.info.simpleStory;

import fr.univtours.info.model.factual.Finding;
import fr.univtours.info.model.intentional.AnalyticalQuestion;
import fr.univtours.info.model.intentional.Measure;
import fr.univtours.info.model.intentional.Message;
import fr.univtours.info.model.intentional.Character;

import java.util.ArrayList;
import java.util.Collection;

public class SimpleMessage implements Message {
    String theText;
    Collection<Finding> theFindings;
    Collection<Character> theCharacters;
    Collection<Measure> theMeasures;
    AnalyticalQuestion theAnalyticalQuestion;

    public SimpleMessage(){
        theFindings = new ArrayList<Finding>();
        theCharacters = new ArrayList<Character>();
        theMeasures = new ArrayList<Measure>();

    }
    @Override
    public void addText(String aText) {

        this.theText=aText.substring(1,aText.length()-1).replace("\\n","\n");

    }

    @Override
    public String getText() {
        return theText;
    }

    @Override
    public String toString() { // gives message text + findings
        String result ="Message: " + theText+ "\n";
        for(Finding i : theFindings){
            result = result + i.toString() + "\n";
        }
        return result;
    }

    @Override
    public void poses(AnalyticalQuestion anAnalyticalQuestion) {
        //TODO record that this triggers a new analytical question
    }

    @Override
    public void generates(AnalyticalQuestion anAnalyticalQuestion) {

        theAnalyticalQuestion=anAnalyticalQuestion;
    }

    @Override
    public AnalyticalQuestion generates() {
        return theAnalyticalQuestion;
    }

    @Override
    public void produces(Finding anFinding) {

        theFindings.add(anFinding);

    }

    @Override
    public Collection<Finding> produces() {
        return theFindings;
    }

    @Override
    public void bringsOut(Character aCharacter) {
        theCharacters.add(aCharacter);
    }

    @Override
    public Collection<Character> bringsOut() {
        return theCharacters;
    }

    @Override
    public void includes(Measure aMeasure) {
        theMeasures.add(aMeasure);
    }

    @Override
    public Collection<Measure> includes() {
        return theMeasures;
    }
}
