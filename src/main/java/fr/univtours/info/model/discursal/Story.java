package fr.univtours.info.model.discursal;


import fr.univtours.info.model.intentional.*;

import java.util.Collection;


public interface Story {

    public void addText(String theText);
    public String toString();

    public void includes(Act anAct); //addAct
    public Collection<Act> includes();

    public void has(Goal aGoal); //setGoal
    public Goal has(); //getGoal

    public void store();
}
