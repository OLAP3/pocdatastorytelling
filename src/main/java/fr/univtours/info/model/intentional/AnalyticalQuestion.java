package fr.univtours.info.model.intentional;


import fr.univtours.info.model.factual.Collector;
import fr.univtours.info.model.factual.Insight;

import java.util.Collection;

public interface AnalyticalQuestion {

    public Goal posed(); // getGoal
    public Collection<Collector> implement();

    public Collection<Insight> answer(); //triggers collector creation
}
