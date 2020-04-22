package fr.univtours.info.model.intentional;


import fr.univtours.info.model.factual.Collector;
import fr.univtours.info.model.factual.Insight;

import java.util.Collection;

public interface AnalyticalQuestion {
    public void addText(String aText);
    public String toString(); // replace by getText()?

    public Goal poses(); // getGoal
    public void poses(Goal theGoal);

    public Collection<Collector> implement();
    public void implement(Collector aCollector);


    public void generates(Observation anObservation);
    public Collection<Observation> generates();
}
