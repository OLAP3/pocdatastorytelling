package fr.univtours.info.model.factual;

import java.io.Serializable;
import java.util.Collection;

public interface Collector extends Serializable {

    public boolean hasInsight();
    public void fetches(Finding aFinding);
    public Collection<Finding> fetches();

    public void run() throws Exception; //collectors can go mad
}
