package fr.univtours.info.model.discursal;

import fr.univtours.info.model.intentional.*;

import java.util.Collection;

public interface Act {

    public void addText(String theText);
    public void includes(Episode anEpisode); //addEpisode
    public void narrates(Message aMessage);

    public Collection<Episode> includes(); //getEpisode
}
