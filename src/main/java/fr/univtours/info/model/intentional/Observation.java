package fr.univtours.info.model.intentional;

import fr.univtours.info.model.factual.*;

public interface Observation {
    public void addText(String aText);
    public void generates(AnalyticalQuestion anAnalyticalQuestion);
    public void produces(Insight anInsight);
}
