package fr.univtours.info.simpleStory;

import fr.univtours.info.model.factual.Insight;
import fr.univtours.info.model.intentional.AnalyticalQuestion;
import fr.univtours.info.model.intentional.Observation;

import java.util.ArrayList;
import java.util.Collection;

public class SimpleObservation implements Observation {
    String theText;
    Collection<Insight> theInsights;

    public SimpleObservation(){
        theInsights = new ArrayList<Insight>();
    }
    @Override
    public void addText(String aText) {
        this.theText=aText;
    }

    @Override
    public String getTxt() {
        return theText;
    }

    @Override
    public void generates(AnalyticalQuestion anAnalyticalQuestion) {

    }

    @Override
    public void produces(Insight anInsight) {
        theInsights.add(anInsight);
    }

    @Override
    public Collection<Insight> produces() {
        return theInsights;
    }
}
