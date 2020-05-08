package fr.univtours.info.simpleStory;

import fr.univtours.info.model.Structural.Act;
import fr.univtours.info.model.presentational.Dashboard;
import fr.univtours.info.model.presentational.DashboardComponent;

import java.util.ArrayList;
import java.util.Collection;

public class SimpleDashboard implements Dashboard {
    Act theAct;
    Collection<DashboardComponent> theComponents;
    String theRendering="";

    public SimpleDashboard(){
        theComponents=new ArrayList<DashboardComponent>();
    }

    @Override
    public void renders(Act anAct) {
        this.theAct=anAct;
    }

    @Override
    public void contains(DashboardComponent aDashboardComponent) {
        theComponents.add(aDashboardComponent);
    }

    @Override
    public Collection<DashboardComponent> contains() {
        return theComponents;
    }

    @Override
    public Act renders() {
        theRendering = theRendering + theAct.toString() + "\n";
        for(DashboardComponent dc : theComponents){
            dc.render();
            theRendering=theRendering+ ((SimpleDashboardComponent) dc).getRendering();
        }
        return theAct;
    }

    public String getRendering(){
        return theRendering;
    }
}
