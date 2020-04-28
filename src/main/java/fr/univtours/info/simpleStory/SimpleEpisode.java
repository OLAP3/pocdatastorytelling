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
    public void addText(String aText) {
        this.theText=aText.substring(1,aText.length()-1).replace("\\n","\n");
    }

    @Override
    public String toString() {

        String episodeProtagonists = "";
        for(Protagonist p : theProtagonists){
            episodeProtagonists = episodeProtagonists+ p.toString() + "\n";
        }
        return "Episode: " + theText + "\n" +theObservation.toString() + "\n"
                + episodeProtagonists;
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
}
