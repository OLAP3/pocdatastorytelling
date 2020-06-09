package fr.univtours.info.model.Structural;


import fr.univtours.info.model.intentional.*;

import java.io.Serializable;
import java.util.Collection;


public interface Plot extends Serializable {

    public void addText(String theText);
    public String toString();

    public void includes(Act anAct); //addAct
    public Collection<Act> includes();

    public void has(Goal aGoal); //setGoal
    public Goal has(); //getGoal

    public String store();
}
