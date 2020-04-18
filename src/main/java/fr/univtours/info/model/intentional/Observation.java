package fr.univtours.info.model.intentional;

import fr.univtours.info.model.factual.*;

import java.util.Collection;

public interface Observation {
    public void addText(String aText);
    public String getTxt();

    public void generates(AnalyticalQuestion anAnalyticalQuestion);

    public void produces(Insight anInsight);
    public Collection<Insight> produces();

    public void bringsOut(Protagonist aProtagonist) ;
    public Collection<Protagonist>  bringsOut();
}
