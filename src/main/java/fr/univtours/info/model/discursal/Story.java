package fr.univtours.info.model.discursal;


import fr.univtours.info.model.intentional.*;

import java.util.Collection;


public interface Story {

    public void addText(String theText);
    public void includes(Act anAct); //addAct
    public void has(Goal aGoal); //setGoal

    public Collection<Act> includes();
    public Goal has(); //getGoal
}
