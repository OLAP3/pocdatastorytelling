package fr.univtours.info.simpleStory;

import fr.univtours.info.model.discursal.Episode;
import fr.univtours.info.model.factual.Insight;
import fr.univtours.info.model.intentional.Observation;
import fr.univtours.info.model.presentational.DashboardComponent;

import java.util.Iterator;

public class SimpleDashboardComponent implements DashboardComponent {
    Episode theEpisode;
    String theRendering;

    @Override
    public void renders(Episode anEpisode) {
        this.theEpisode=anEpisode;
    }

    @Override
    public Episode render() {
        Observation theObservation=theEpisode.narrates();
        Iterator<Insight> iti= theObservation.produces().iterator();
        Insight theInsight=iti.next(); //only 1 insight
        String stringInsight=theInsight.toString();

        theRendering=stringInsight;
        return theEpisode;
    }

    public String getRendering(){
        return theRendering;
    }
}
