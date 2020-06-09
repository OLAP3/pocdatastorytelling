package fr.univtours.info.model.intentional;


import fr.univtours.info.model.factual.Collector;

import java.io.Serializable;
import java.util.Collection;

public interface AnalyticalQuestion extends Serializable {
    public void addText(String aText);
    public String toString();

    public Goal poses(); // getGoal
    public void poses(Goal theGoal);

    public void poses(Message aMessage);

    public Collection<Collector> implement();
    public void implement(Collector aCollector);


    public void generates(Message anMessage);
    public Collection<Message> generates();
}
