package fr.univtours.info.model.factual;

import java.util.Collection;

public interface Collector {

    public boolean hasInsight();
    public void fetches(Finding aFinding);
    public Collection<Finding> fetches();

    public void run() throws Exception; //collectors can go mad
}
