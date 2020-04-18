package fr.univtours.info.model.discursal;


import fr.univtours.info.model.intentional.*;

import java.util.Collection;

public interface Episode {

    public void addText(String theText);
    public String getText();
    public void narrates(Observation anObservation); //addObservation
    public void playsIn(Protagonist aProtagonist); //addProtagonist

    public Collection<Protagonist> playsIn(); //getProtagonist
}
