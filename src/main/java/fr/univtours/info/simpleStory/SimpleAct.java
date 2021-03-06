package fr.univtours.info.simpleStory;

import fr.univtours.info.model.Structural.Act;
import fr.univtours.info.model.Structural.Episode;
import fr.univtours.info.model.intentional.Measure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class SimpleAct implements Act {
    String actText;

    Collection<Episode> theEpisodes;
    Measure theMeasure;

    public SimpleAct(){
        theEpisodes = new ArrayList<Episode>();
    }

    @Override
    public void addText(String theText) {
        this.actText=theText.substring(1,theText.length()-1).replace("\\n","\n");
        //this.actText=theText;
    }

    @Override
    public String getText() {
        return actText;
    }

    @Override
    public String toString() {
        //return "Act: " + actText + "\n" + theMessage.toString();
        return "Act: " + actText ;

    }

    @Override
    public void includes(Episode anEpisode) {
        theEpisodes.add(anEpisode);
    }

    @Override
    public void narrates(Measure aMeasure) {
        this.theMeasure = aMeasure;
        //this.messageText=theMessage.toString();
    }

    @Override
    public Measure narrates() {
        return theMeasure;
    }

    @Override
    public Collection<Episode> includes() {
        return theEpisodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleAct simpleAct = (SimpleAct) o;
        return Objects.equals(actText, simpleAct.actText) &&
                Objects.equals(theEpisodes, simpleAct.theEpisodes) &&
                Objects.equals(theMeasure, simpleAct.theMeasure);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actText, theEpisodes, theMeasure);
    }
}
