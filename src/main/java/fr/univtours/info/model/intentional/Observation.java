package fr.univtours.info.model.intentional;

import fr.univtours.info.model.factual.*;

import java.util.Collection;

public interface Observation {
    public void addText(String aText);
    public String toString();

    public void poses(AnalyticalQuestion anAnalyticalQuestion);
    public void generates(AnalyticalQuestion anAnalyticalQuestion);
    public AnalyticalQuestion generates();

    public void produces(Insight anInsight);
    public Collection<Insight> produces();

    public void bringsOut(Protagonist aProtagonist) ;
    public Collection<Protagonist>  bringsOut();
}
