package fr.univtours.info.simpleStory;

import fr.univtours.info.model.factual.Collector;
import fr.univtours.info.model.factual.Exploration;

import java.util.ArrayList;
import java.util.Collection;


public class SimpleExploration implements Exploration {

    ArrayList<Collector> theCollectors ;

    public SimpleExploration(){
        this.theCollectors=new ArrayList<Collector>();
    }
    @Override
    public void tries(Collector aCollector) {
        this.theCollectors.add(aCollector);
    }

    @Override
    public Collection<Collector> tries() {
        return theCollectors;
    }
}
