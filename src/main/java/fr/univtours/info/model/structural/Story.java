package fr.univtours.info.model.structural;


import fr.univtours.info.model.intentional.*;
import fr.univtours.info.model.presentational.*;

import java.util.Collection;


public interface Story {

    public void addText(String theText);
    public void includes(Act anAct); //addAct
    public void has(Goal aGoal); //setGoal

    public Collection<Act> includes();
    public Goal has(); //getGoal
}
