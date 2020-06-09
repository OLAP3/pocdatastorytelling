package fr.univtours.info.model.factual;

import fr.univtours.info.model.intentional.*;

import java.io.Serializable;
import java.util.Collection;

public interface Exploration extends Serializable {

    public void tries(Collector aCollector);
    public Collection<Collector> tries();
}
