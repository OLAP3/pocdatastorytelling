package fr.univtours.info.simpleStory;

import fr.univtours.info.model.intentional.Goal;
import fr.univtours.info.model.intentional.Protagonist;
import fr.univtours.info.model.structural.Act;
import fr.univtours.info.model.structural.Story;

import java.util.ArrayList;
import java.util.Collection;

public class SimpleStory implements Story {

    private String text;
    private ArrayList<Act> theActs;
    private Goal theGoal;

    public SimpleStory(){

    }



    @Override
    public void addText(String theText) {
        this.text=theText;
    }

    @Override
    public void addAct(Act anAct) {
        this.theActs.add(anAct);
    }

    @Override
    public void has(Goal aGoal) {
        this.theGoal=aGoal;
    }

    @Override
    public Collection<Act> includes() {
        return this.theActs;
    }

    @Override
    public Goal has() {
        return this.theGoal;
    }
}