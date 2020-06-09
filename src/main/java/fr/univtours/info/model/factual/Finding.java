package fr.univtours.info.model.factual;

import fr.univtours.info.model.intentional.*;

import java.io.Serializable;

public interface Finding extends Serializable {

    public Collector fetches();
    public void fetches(Collector theCollector );
    public String toString();
}
