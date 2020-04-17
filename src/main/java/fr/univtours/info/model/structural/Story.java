package fr.univtours.info.model.structural;


import fr.univtours.info.model.intentional.*;
import fr.univtours.info.model.presentational.*;

import java.util.Collection;


public interface Story {

    public void addText(String theText);
    public void addAct(Act anAct);
    public void has(Goal aGoal);

    public Collection<Act> includes();
    public Goal has();
}
