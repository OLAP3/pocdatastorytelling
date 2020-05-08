package fr.univtours.info.simpleStory;

import fr.univtours.info.model.Structural.Episode;
import fr.univtours.info.model.presentational.DashboardComponent;

public class SimpleDashboardComponent implements DashboardComponent {
    Episode theEpisode;
    String theRendering;

    @Override
    public void renders(Episode anEpisode) {
        this.theEpisode=anEpisode;
    }

    @Override
    public Episode render() {
        /*
        Observation theObservation=theEpisode.narrates();

        Iterator<Insight> iti= theObservation.produces().iterator();
        Insight theInsight=iti.next(); //only 1 insight
        String stringInsight=theInsight.toString();

        theRendering=stringInsight;

         */
        theRendering = theEpisode.toString();
        return theEpisode;
    }

    public String getRendering(){
        return theRendering;
    }
}
