package fr.univtours.info.simpleStory;

import fr.univtours.info.model.factual.Collector;
import fr.univtours.info.model.factual.Insight;

import java.util.ArrayList;
import java.util.Collection;

public class SimpleDescribeCollector implements Collector {

    String describeQuery;
    Collection<Insight> theInsights;


    public SimpleDescribeCollector(String query){
        this.describeQuery=query;
        theInsights = new ArrayList<Insight>();
    }


    @Override
    public boolean hasInsight() {
        return false;
    }

    @Override
    public void fetches(Insight anInsight) {
        theInsights.add(anInsight);
    }

    @Override
    public Collection<Insight> fetches() {
        return theInsights;
    }

    @Override
    public void run() throws Exception {
        // this is run in the callback for now
    }
}
