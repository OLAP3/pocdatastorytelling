package fr.univtours.info.model.factual;

import fr.univtours.info.model.intentional.*;

public interface Collector {

    public Insight fetches();

    public void run();
}
