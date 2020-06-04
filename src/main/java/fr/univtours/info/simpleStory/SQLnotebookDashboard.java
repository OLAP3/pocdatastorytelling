package fr.univtours.info.simpleStory;

import fr.univtours.info.model.Structural.Act;
import fr.univtours.info.model.presentational.Dashboard;
import fr.univtours.info.model.presentational.DashboardComponent;

import java.util.ArrayList;
import java.util.Collection;

public class SQLnotebookDashboard implements Dashboard {
    Act theAct;
    Collection<DashboardComponent> theComponents;

    public void setNarrative(SQLnotebookNarrative narrative) {
        this.narrative = narrative;
    }

    SQLnotebookNarrative narrative;

    public SQLnotebookDashboard(){
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
        String theCell=
                "{\\\"rowId\\\":\\\"" +
                        narrative.getId()
                        +"\\\",\\\"items\\\":[{\\\"query\\\":\\\""
                        + narrative.replaceNL(theAct.toString())
                        +"\\\",\\\"id\\\":\\\""
                        + narrative.getId()
                        +"\\\",\\\"loading\\\":false,\\\"markdown\\\":true}]}"
                ;
        narrative.addCell(theCell);
        for(DashboardComponent dc : theComponents){
            ((SQLnotebookDashboardComponent) dc).setNarrative(narrative);
            dc.render();
        }
        return theAct;
    }
}
