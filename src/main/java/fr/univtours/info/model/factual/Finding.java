package fr.univtours.info.model.factual;

import fr.univtours.info.model.intentional.*;

public interface Finding {

    public Collector fetches();
    public void fetches(Collector theCollector );
    public String toString();
}
