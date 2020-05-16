package fr.univtours.info.model.Structural;

import fr.univtours.info.model.intentional.*;

import java.util.Collection;

public interface Act {

    public void addText(String theText);
    public String getText();

    public String toString();

    public void includes(Episode anEpisode); //addEpisode
    public Collection<Episode> includes(); //getEpisode

    public void narrates(Measure aMeasure);
    public Measure narrates();

}
