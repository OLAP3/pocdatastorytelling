package fr.univtours.info.model.factual;

import fr.univtours.info.model.intentional.*;

import java.util.Collection;

public interface Collector {

    public boolean hasInsight();
    public Collection<Insight> fetches();

    public void run() throws Exception; //collectors can go mad
}
