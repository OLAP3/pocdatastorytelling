package fr.univtours.info.simpleStory;

import fr.univtours.info.model.Structural.Plot;
import fr.univtours.info.model.factual.Collector;
import fr.univtours.info.model.factual.Exploration;
import fr.univtours.info.model.intentional.AnalyticalQuestion;
import fr.univtours.info.model.intentional.Goal;
import fr.univtours.info.model.intentional.Measure;
import fr.univtours.info.model.intentional.Character;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class SimpleGoal implements Goal {
    ArrayList<Exploration> theExplorations;
    String theText;
    Plot thePlot;

    public SimpleGoal(){
        theExplorations=new ArrayList<Exploration>();
    }

    @Override
    public void addText(String aText) {
        this.theText=aText.substring(1,aText.length()-1).replace("\\n","\n");
//        theText=aText;
    }

    public String toString(){
        return theText;
    }

    @Override
    public Plot has() {
        return thePlot;
    }

    @Override
    public void has(Plot thePlot) {
        this.thePlot = thePlot;
    }

    @Override
    public void solves(Exploration anExploration) {

    }

    @Override
    public void poses(AnalyticalQuestion anAnalyticalQuestion) {
        // attaches the collectors to the exploration
        Iterator<Collector> itc = anAnalyticalQuestion.implement().iterator();
        while(itc.hasNext()){
            theExplorations.get(0).tries(itc.next()); //only one exploration in this SimpleGoal
        }
    }

    @Override
    public Collection<Exploration> solves() {
        return theExplorations;
    }

    @Override
    public Collection<AnalyticalQuestion> poses() {
        return null;
    }

    @Override
    public void bringOut(Character aCharacter) {

    }

    @Override
    public void bringOut(Measure aMeasure) {

    }
}
