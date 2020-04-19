package fr.univtours.info.model.intentional;


import java.util.Collection;

public interface Message {
    public void addText(String aText);
    public String toString(); // replace by getText()?

    public void bringsOut(Observation anObservation);
    public void bringsOut(Goal aGoal);

    public Collection<Observation> bringsOut(); //getObservation
}
