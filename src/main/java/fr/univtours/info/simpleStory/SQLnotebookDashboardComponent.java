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
        String theCell="{\n" +
                "rowId:" + narrative.getId() +",\n" +
                "items:[{query:" + theEpisode.toString()+ ",\n" +
                "id:"+ narrative.getId()+",\n" +
                "loading:false,\n" +
                "markdown:true}]\n" +
                "}";
        narrative.addCell(theCell);

        Collection<Finding> theFindings=  theEpisode.narrates().produces();
        for(Finding f : theFindings){
            Collector c =f.fetches();

             theCell="{\n" +
                    "rowId:" + narrative.getId() +",\n" +
                    "items:[{query:" + ((SimpleSQLCollector) c).sqlQuery+ ",\n" +
                    "id:"+ narrative.getId()+",\n" +
                    "loading:false}]\n" +
                    "}";
            narrative.addCell(theCell);

        }


        return theEpisode;
    }
}
