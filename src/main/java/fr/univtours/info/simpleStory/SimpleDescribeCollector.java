package fr.univtours.info.simpleStory;

import fr.univtours.info.model.factual.Collector;
import fr.univtours.info.model.factual.Finding;

import java.util.ArrayList;
import java.util.Collection;

public class SimpleDescribeCollector implements Collector {

    String describeQuery;
    Collection<Finding> theFindings;


    public SimpleDescribeCollector(String query){
        this.describeQuery=query;
        theFindings = new ArrayList<Finding>();
    }


    @Override
    public boolean hasInsight() {
        return false;
    }

    @Override
    public void fetches(Finding anFinding) {
        theFindings.add(anFinding);
    }

    @Override
    public Collection<Finding> fetches() {
        return theFindings;
    }

    @Override
    public void run() throws Exception {
        // this is run in the callback for now
    }
}
