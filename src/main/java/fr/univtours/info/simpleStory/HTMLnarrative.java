package fr.univtours.info.simpleStory;

import fr.univtours.info.model.Structural.Plot;
import fr.univtours.info.model.presentational.Dashboard;
import fr.univtours.info.model.presentational.VisualNarrative;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;

public abstract class HTMLnarrative implements VisualNarrative {
    Collection<Dashboard> theDashboards;
    Plot thePlot;
    String theStringRendering = "";
    String HTMLfile = "/Users/marcel/Documents/RECHERCHE/STUDENTS/Faten/pocdatastory/public/html/sql-data-narrative.html";
    File f;
    FileWriter fw;


    public String getTheHTML() {
        return HTMLfile;
    }

    public HTMLnarrative() {
        theDashboards = new ArrayList<Dashboard>();
    }

    @Override
    public String toString() {
        return theStringRendering;
    }

    @Override
    public void contains(Dashboard aDashboard) {
        theDashboards.add(aDashboard);
    }

    @Override
    public Collection<Dashboard> contains() {
        return theDashboards;
    }

}