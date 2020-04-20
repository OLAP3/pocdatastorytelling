package fr.univtours.info.model.intentional;


import fr.univtours.info.model.factual.Collector;
import fr.univtours.info.model.factual.Insight;

import java.util.Collection;

public interface AnalyticalQuestion {
    public void addText(String aText);
    public String toString(); // replace by getText()?

    public Goal posed(); // getGoal
    public Collection<Collector> implement();

    public Collection<Insight> answer(); //triggers collector creation

    public void generates(Observation anObservation);
    public Collection<Observation> generates();
}
