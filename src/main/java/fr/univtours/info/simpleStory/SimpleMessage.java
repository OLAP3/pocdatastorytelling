package fr.univtours.info.simpleStory;

import fr.univtours.info.model.intentional.Goal;
import fr.univtours.info.model.intentional.Message;
import fr.univtours.info.model.intentional.Observation;

import java.util.Collection;
import java.util.Set;

public class SimpleMessage implements Message {
    String theText;

    @Override
    public void addText(String aText) {
        this.theText=aText;
    }

    @Override
    public String getText() {
        return theText;
    }

    @Override
    public void bringsOut(Observation anObservation) {

    }

    @Override
    public void bringsOut(Goal aGoal) {

    }

    @Override
    public Collection<Observation> getObservation() {
        return null;
    }
}
