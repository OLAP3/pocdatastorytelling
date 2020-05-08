package fr.univtours.info.simpleStory;

import fr.univtours.info.model.intentional.Character;
import fr.univtours.info.model.intentional.Measure;

public class SimpleEpisode extends BaseEpisode {


    public SimpleEpisode(){

        super();
    }



    @Override
    public String toString() {

        String episodeProtagonists = "";
        for(Character p : theCharacters){
            episodeProtagonists = episodeProtagonists+ p.toString() + "\n";
        }

        String episodeMeasures = "";
        for(Measure m : theMeasures){
            episodeMeasures = episodeMeasures+ m.toString() + "\n";
        }

        return "Episode: " + theText + "\n" + theMessage.toString() + "\n"
                + episodeProtagonists + episodeMeasures;
    }

}
