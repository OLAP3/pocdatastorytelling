package fr.univtours.info.model.factual;

import fr.univtours.info.model.intentional.*;

import java.util.Collection;

public interface Exploration {

    public void tries(Collector aCollector);
    public Collection<Collector> tries();
}
