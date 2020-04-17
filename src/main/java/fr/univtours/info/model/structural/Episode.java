package fr.univtours.info.model.structural;


import fr.univtours.info.model.intentional.*;
import fr.univtours.info.model.presentational.*;

import java.util.Collection;

public interface Episode {

    public void addText(String theText);
    public void narrates(Observation anObservation); //addObservation
    public void playsIn(Protagonist aProtagonist); //addProtagonist

    public Collection<Protagonist> playsIn(); //getProtagonist
}
