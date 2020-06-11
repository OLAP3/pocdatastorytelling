package fr.univtours.info.simpleStory;

import fr.univtours.info.model.Structural.Episode;
import fr.univtours.info.model.factual.Collector;
import fr.univtours.info.model.factual.Finding;
import fr.univtours.info.model.presentational.DashboardComponent;

import java.util.Collection;

public class SQLnotebookDashboardComponent implements DashboardComponent {
    Episode theEpisode;

    public void setNarrative(SQLnotebookNarrative narrative) {
        this.narrative = narrative;
    }

    SQLnotebookNarrative narrative;

    @Override
    public void renders(Episode anEpisode) {
        this.theEpisode=  anEpisode;
    }

    @Override
    public Episode render() {
        String theCell="{\\\"rowId\\\":\\\"" +
                narrative.getId()
                +"\\\",\\\"items\\\":[{\\\"query\\\":\\\""
                + narrative.replaceNL(theEpisode.toString())
                +"\\\",\\\"id\\\":\\\""
                + narrative.getId()
                +"\\\",\\\"loading\\\":false,\\\"markdown\\\":true}]}";

        narrative.addCell(theCell);

        Collection<Finding> theFindings=  theEpisode.narrates().produces();
        for(Finding f : theFindings){
            // check if c is indeed a SQL collector
            Collector c =f.fetches();

            if(c.getClass().getName().equals("fr.univtours.info.simpleStory.SimpleSQLCollector")) {

                theCell = "{\\\"rowId\\\":\\\"" +
                        narrative.getId()
                        + "\\\",\\\"items\\\":[{\\\"query\\\":\\\""
                        + narrative.replaceNL(((SimpleSQLCollector) c).sqlQuery)
                        + "\\\",\\\"id\\\":\\\""
                        + narrative.getId()
                        + "\\\",\\\"loading\\\":false}]}";

                narrative.addCell(theCell);
            }
        }


        return theEpisode;
    }
}
