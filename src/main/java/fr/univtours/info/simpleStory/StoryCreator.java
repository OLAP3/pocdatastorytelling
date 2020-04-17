package fr.univtours.info.simpleStory;

import fr.univtours.info.model.*;
import fr.univtours.info.model.factual.Collector;
import fr.univtours.info.model.factual.Insight;
import fr.univtours.info.model.presentational.VisualStory;

import java.util.Iterator;

public class StoryCreator {



    public static void main(String args[]) throws Exception{
        VisualStory vs=new SimpleVisualStory();
        SimpleCollector col=new SimpleCollector("Select * from names");
        col.run();
        Iterator<Insight> it= col.fetches().iterator();
        System.out.println(it.next());

        /* logic:

        new visual story, story, goal, exploration
        iterate on analytical question
        new collector

        while not finished
            new act y/n?
            new collector
            inspect insight
            if ok new observation, message, protagonist, episode
        end
        then rendering: new dashboardComp, dashboard and final print

         */
    }

}
