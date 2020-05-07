package fr.univtours.info.simpleStory;

import fr.univtours.info.model.discursal.Episode;
import fr.univtours.info.model.intentional.Observation;
import fr.univtours.info.model.intentional.Protagonist;

import java.util.ArrayList;
import java.util.Collection;

public abstract class BaseEpisode implements Episode {

    String theText;
    Observation theObservation;
    Collection<Protagonist> theProtagonists;

    public BaseEpisode(){
        theProtagonists=new ArrayList<Protagonist>();

    }
    @Override
    public void narrates(Observation anObservation) {

        this.theObservation=anObservation;
    }

    @Override
    public Observation narrates() {
        return theObservation;
    }

    @Override
    public void playsIn(Protagonist aProtagonist) {

        theProtagonists.add(aProtagonist);
    }

    @Override
    public Collection<Protagonist> playsIn() {

        return theProtagonists;
    }

    @Override
    public void addText(String aText) {
        this.theText=aText.substring(1,aText.length()-1).replace("\\n","\n");
    }
}
