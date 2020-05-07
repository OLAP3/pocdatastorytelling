package fr.univtours.info.simpleStory;

import com.fasterxml.jackson.databind.ser.Serializers;
import fr.univtours.info.model.discursal.Episode;
import fr.univtours.info.model.intentional.Observation;
import fr.univtours.info.model.intentional.Protagonist;

import java.util.Collection;

public class SimpleGraphicEpisode extends BaseEpisode {
    byte[] graphic;


    public byte[] getGraphic() {
        return graphic;
    }

    public void setGraphic(byte[] graphic) {
        this.graphic = graphic;
    }

    public SimpleGraphicEpisode(){
        super();
    }



    @Override
    public String toString() {

        String episodeProtagonists = "";
        for(Protagonist p : theProtagonists){
            episodeProtagonists = episodeProtagonists+ p.toString() + "\n";
        }
        String result= "Episode: " + theText + "\n" + episodeProtagonists;

        result = result + new String(graphic) + "\n"; // changeMe

        return result;
    }
}
