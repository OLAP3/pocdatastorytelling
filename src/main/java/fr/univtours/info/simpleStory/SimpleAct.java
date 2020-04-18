package fr.univtours.info.simpleStory;

import fr.univtours.info.model.discursal.Act;
import fr.univtours.info.model.discursal.Episode;
import fr.univtours.info.model.intentional.Message;

import java.util.ArrayList;
import java.util.Collection;

public class SimpleAct implements Act {
    String text;
    Collection<Episode> theEpisodes;
    Message theMessage;

    public SimpleAct(){
        theEpisodes = new ArrayList<Episode>();
    }

    @Override
    public void addText(String theText) {
        this.text=theText;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void includes(Episode anEpisode) {
        theEpisodes.add(anEpisode);
    }

    @Override
    public void narrates(Message aMessage) {
        this.theMessage=aMessage;
    }

    @Override
    public Message narrates() {
        return theMessage;
    }

    @Override
    public Collection<Episode> includes() {
        return theEpisodes;
    }
}
