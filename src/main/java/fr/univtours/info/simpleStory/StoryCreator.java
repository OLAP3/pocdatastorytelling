package fr.univtours.info.simpleStory;

import fr.univtours.info.model.*;
import fr.univtours.info.model.factual.Collector;
import fr.univtours.info.model.presentational.VisualStory;

public class StoryCreator {



    public static void main() throws Exception{
        VisualStory vs=new SimpleVisualStory();
        SimpleCollector col=new SimpleCollector();
        System.out.println(col.sendQuery("Select * from names"));
    }

}
