package fr.univtours.info.simpleStory;

import fr.univtours.info.model.discursal.Act;
import fr.univtours.info.model.discursal.Episode;
import fr.univtours.info.model.factual.Exploration;
import fr.univtours.info.model.factual.Insight;
import fr.univtours.info.model.intentional.*;
import fr.univtours.info.model.presentational.Dashboard;
import fr.univtours.info.model.presentational.DashboardComponent;
import fr.univtours.info.model.presentational.VisualStory;
import fr.univtours.info.model.discursal.Story;

import java.util.Collection;

public class StoryCreator {



    public static void main(String args[]) throws Exception{

        Story theStory=new SimpleStory();
        Goal theGoal = new SimpleGoal();
        String goalText="a straight story";
        theGoal.addText(goalText);
        theStory.has(theGoal);
        Act currentAct =null;
        Message currentMessage=null;

        Exploration theExploration = new SimpleExploration(); // only one exploration
        theGoal.solves(theExploration);

        boolean stop=false;

        while(!stop){
            // ask SQL query
            String query="select sum(lo_revenue), d_year, p_brand \n" +
                    "from lineorder, date, part, supplier \n" +
                    " where lo_orderdate = d_datekey \n" +
                    " and lo_partkey = p_partkey \n" +
                    " and lo_suppkey = s_suppkey \n" +
                    " and p_category = 'MFGR#12' \n" +
                    " and s_region = 'AMERICA' \n" +
                    " group by d_year, p_brand \n" +
                    " order by d_year, p_brand;\n"; //fake
            AnalyticalQuestion anAnalyticalQuestion = new SimpleAnalyticalQuestion();
            anAnalyticalQuestion.addText(query);
            Collection<Insight> col = anAnalyticalQuestion.answer();
            theGoal.poses(anAnalyticalQuestion);

            // show insights and ask if worthy
            boolean isWorthy=true; //Fake
            if(isWorthy){
                // ask if new act
                boolean newAct=true; //Fake
                if(newAct){
                    currentAct=new SimpleAct();
                    theStory.includes(currentAct);
                    currentMessage = new SimpleMessage();

                    // ask for text
                }

                Observation currentObservation = new SimpleObservation();
                anAnalyticalQuestion.generates(currentObservation);
                currentObservation.generates(anAnalyticalQuestion);

                //currentMessage.bringsOut(currentObservation);
                //currentObservation.addText(anAnalyticalQuestion.toString());

                for(Insight i : col){
                    // may ask the author if insight is accepted or not
                    currentObservation.produces(i);

                }



                Episode currentEpisode= new SimpleEpisode();
                currentEpisode.narrates(currentObservation);
                currentAct.includes(currentEpisode);
                currentMessage.bringsOut(currentObservation);
                currentAct.narrates(currentMessage);

                // how many protagonists
                int nbProtagonists = 1; // fake
                for(int i=0;i<nbProtagonists;i++){
                    Protagonist aProtagonist = new SimpleProtagonist();
                    currentEpisode.playsIn(aProtagonist);
                    currentObservation.bringsOut(aProtagonist);
                }


            }

            // ask more analytical questions? else stop
            stop=true; //fake
        }

        // ask to complement the story, acts, episodes with texts
        //theStory.addText("ask the author");

        VisualStory vs=new SimpleVisualStory();
        vs.renders(theStory);  // just attach
        vs.renders(); // and then renders
        vs.print(); // and then prints
    }

}
