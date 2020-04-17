package fr.univtours.info.model.intentional;


import fr.univtours.info.model.structural.*;

import java.util.Set;

public interface Message {
    public void addText(String aText);
    public void bringsOut(Observation anObservation);
    public void bringsOut(Goal aGoal);

    public Set<Observation> getObservation();
}
