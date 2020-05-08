package fr.univtours.info.simpleStory;

import fr.univtours.info.model.factual.Collector;
import fr.univtours.info.model.factual.Finding;
import fr.univtours.info.model.intentional.AnalyticalQuestion;
import fr.univtours.info.model.intentional.Goal;
import fr.univtours.info.model.intentional.Message;

import java.util.ArrayList;
import java.util.Collection;

public class SimpleAnalyticalQuestion implements AnalyticalQuestion {
    ArrayList<Collector> theCollectors ;
    ArrayList<Message> theMessages;
    String theText;
    Goal theGoal;

    public SimpleAnalyticalQuestion(){
        this.theCollectors = new ArrayList<Collector>();
        this.theMessages = new ArrayList<Message>();

    }

    // drop me
    public Collection<Finding> answer() {
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
    public void generates(Message anMessage) {
        theMessages.add(anMessage);
    }

    @Override
    public Collection<Message> generates() {
        return theMessages;
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
