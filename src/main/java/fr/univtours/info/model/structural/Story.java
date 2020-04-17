package fr.univtours.info.model.structural;


import fr.univtours.info.model.intentional.*;
import fr.univtours.info.model.presentational.*;


public interface Story {

    public void addText(String theText);
    public void addAct(Act anAct);
}
