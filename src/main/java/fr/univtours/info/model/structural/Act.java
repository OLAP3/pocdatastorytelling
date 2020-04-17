package fr.univtours.info.model.structural;

import fr.univtours.info.model.intentional.*;
import fr.univtours.info.model.presentational.*;

import java.util.Collection;

public interface Act {

    public void addText(String theText);
    public void addEpisode(Episode anEpisode);
    public void narrates(Message aMessage);

    public Collection<Episode> includes(); //getEpisode
}
