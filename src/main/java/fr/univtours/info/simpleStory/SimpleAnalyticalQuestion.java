package fr.univtours.info.simpleStory;

import fr.univtours.info.model.factual.Collector;
import fr.univtours.info.model.factual.Insight;
import fr.univtours.info.model.intentional.AnalyticalQuestion;
import fr.univtours.info.model.intentional.Goal;

import java.util.ArrayList;
import java.util.Collection;

public class SimpleAnalyticalQuestion implements AnalyticalQuestion {
    ArrayList<Collector> theCollectors ;
    String query;

    public SimpleAnalyticalQuestion(String sqlQuery){
        this.theCollectors = new ArrayList<Collector>();
        this.query=sqlQuery;
    }

    public Collection<Insight> answer() {
        Collector c = new SimpleCollector(query);
        try {
            c.run();
        }

        catch(Exception e){
            e.printStackTrace();
        }
        return c.fetches();
    }

    @Override
    public Goal posed() {

        return null;
    }

    @Override
    public Collection<Collector> implement() {

        return theCollectors;
    }
}
