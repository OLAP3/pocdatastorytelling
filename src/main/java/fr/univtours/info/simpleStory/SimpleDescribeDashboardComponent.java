package fr.univtours.info.simpleStory;

import fr.univtours.info.model.discursal.Episode;
import fr.univtours.info.model.presentational.DashboardComponent;

import java.awt.font.GraphicAttribute;

public class SimpleDescribeDashboardComponent implements DashboardComponent {
   Episode theEpisode;
    String theRendering;
    byte[] theGraphic;
    String theStringGraphic;

    @Override
    public void renders(Episode anEpisode) {
        this.theEpisode=  anEpisode;
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
        //theGraphic = theEpisode.getGraphic();
        //theStringGraphic=theEpisode.getStringGraphic();
        return theEpisode;
    }

    public String getRendering(){
        return theRendering;
    }

    public byte[] getGraphic(){
        return theGraphic;
    }

    public String getTheStringGraphic() {
        return theStringGraphic;
    }
}
