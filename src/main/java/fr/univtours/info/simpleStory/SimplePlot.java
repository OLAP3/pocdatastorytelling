package fr.univtours.info.simpleStory;

import fr.univtours.info.model.intentional.Goal;
import fr.univtours.info.model.Structural.Act;
import fr.univtours.info.model.Structural.Plot;

import java.io.*;
import java.net.ConnectException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Properties;

public class SimplePlot implements Plot {

    private String text;
    private ArrayList<Act> theActs;
    private Goal theGoal;

    public SimplePlot(){
        theActs=new ArrayList<Act>();
    }


    @Override
    public void addText(String aText) {
        this.text=aText.substring(1,aText.length()-1).replace("\\n","\n");
        //this.text=theText;
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public void includes(Act anAct) {
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

   // for serialization
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimplePlot that = (SimplePlot) o;
        return Objects.equals(text, that.text) &&
                Objects.equals(theActs, that.theActs) &&
                Objects.equals(theGoal, that.theGoal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, theActs, theGoal);
    }
}
