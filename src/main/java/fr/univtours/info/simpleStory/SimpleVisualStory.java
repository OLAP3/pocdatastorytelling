package fr.univtours.info.simpleStory;

import fr.univtours.info.model.discursal.Act;
import fr.univtours.info.model.discursal.Episode;
import fr.univtours.info.model.presentational.*;
import fr.univtours.info.model.discursal.Story;

import java.util.ArrayList;
import java.util.Collection;

public class SimpleVisualStory implements VisualStory {
    Collection<Dashboard> theDashboards;
    Story theStory;
    String theRendering="";

    public SimpleVisualStory(){
        theDashboards = new ArrayList<Dashboard>();
    }

    @Override
    public void print() {
        System.out.println(theRendering);
    }

    @Override
    public void renders(Story aStory) {
        // this is where the visual story creates its component before rendering
        theStory=aStory;

        for(Act act : theStory.includes()){
            Dashboard d = new SimpleDashboard();
            this.contains(d);
            d.renders(act);


            for(Episode ep : act.includes()){
                DashboardComponent dbc = new SimpleDashboardComponent();
                d.contains(dbc);
                dbc.renders(ep);
            }


        }

    }

    @Override
    public void contains(Dashboard aDashboard) {
        theDashboards.add(aDashboard);
    }

    @Override
    public Collection<Dashboard> contains() {

        return theDashboards;
    }

    @Override
    public Story renders() {
        for(Dashboard d : theDashboards){
            d.renders();
            theRendering=theRendering + ((SimpleDashboard) d).getRendering();
        }
        return theStory;
    }
}
