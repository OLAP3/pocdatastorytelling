package fr.univtours.info.simpleStory;

import fr.univtours.info.model.discursal.Episode;
import fr.univtours.info.model.intentional.Observation;
import fr.univtours.info.model.intentional.Protagonist;

import java.util.ArrayList;
import java.util.Collection;

public class SimpleEpisode implements Episode {
    String theText;
    Observation theObservation;
    Collection<Protagonist> theProtagonists;

    public SimpleEpisode(){
        theProtagonists=new ArrayList<Protagonist>();
    }
    @Override
    public void addText(String theText) {

    }

    @Override
    public String getText() {
        return theText;
    }

    @Override
    public void narrates(Observation anObservation) {
        this.theObservation=anObservation;
    }

    @Override
    public void playsIn(Protagonist aProtagonist) {
        theProtagonists.add(aProtagonist);
    }

    @Override
    public Collection<Protagonist> playsIn() {
        return theProtagonists;
    }
}
