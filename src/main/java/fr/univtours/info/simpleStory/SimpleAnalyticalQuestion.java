package fr.univtours.info.simpleStory;

import fr.univtours.info.model.factual.Collector;
import fr.univtours.info.model.factual.Insight;
import fr.univtours.info.model.intentional.AnalyticalQuestion;
import fr.univtours.info.model.intentional.Goal;
import fr.univtours.info.model.intentional.Observation;

import java.util.ArrayList;
import java.util.Collection;

public class SimpleAnalyticalQuestion implements AnalyticalQuestion {
    ArrayList<Collector> theCollectors ;
    ArrayList<Observation> theObservations ;
    String theText;

    public SimpleAnalyticalQuestion(){
        this.theCollectors = new ArrayList<Collector>();
        this.theObservations = new ArrayList<Observation>();

    }

    public Collection<Insight> answer() {
        Collector c = new SimpleCollector(theText);
        try {
            c.run();
        }

        catch(Exception e){
            e.printStackTrace();
        }
        return c.fetches();
    }

    @Override
    public void generates(Observation anObservation) {
        theObservations.add(anObservation);
    }

    @Override
    public Collection<Observation> generates() {
        return theObservations;
    }

    @Override
    public Goal posed() {

        return null;
    }

    @Override
    public Collection<Collector> implement() {

        return theCollectors;
    }



    @Override
    public void addText(String aText) {
        theText=aText;
    }

    public String toString(){
        return theText;
    }
}
