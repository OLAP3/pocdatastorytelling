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
    Goal theGoal;

    public SimpleAnalyticalQuestion(){
        this.theCollectors = new ArrayList<Collector>();
        this.theObservations = new ArrayList<Observation>();

    }

    // drop me
    public Collection<Insight> answer() {
        Collector c = new SimpleSQLCollector(theText);
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
    public Goal poses() {
        return theGoal;
    }

    @Override
    public void poses(Goal theGoal) {
        this.theGoal=theGoal;
    }

    @Override
    public Collection<Collector> implement() {
        return theCollectors;
    }

    @Override
    public void implement(Collector aCollector) {
        theCollectors.add(aCollector);
    }


    @Override
    public void addText(String aText) {
        this.theText=aText.substring(1,aText.length()-1).replace("\\n","\n");
//        theText=aText;
    }

    public String toString(){
        return theText;
    }
}
