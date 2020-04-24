package fr.univtours.info.simpleStory;

import fr.univtours.info.model.discursal.Act;
import fr.univtours.info.model.discursal.Episode;
import fr.univtours.info.model.factual.Collector;
import fr.univtours.info.model.factual.Exploration;
import fr.univtours.info.model.factual.Insight;
import fr.univtours.info.model.intentional.*;
import fr.univtours.info.model.presentational.Dashboard;
import fr.univtours.info.model.presentational.DashboardComponent;
import fr.univtours.info.model.presentational.VisualStory;
import fr.univtours.info.model.discursal.Story;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class StoryCreator {

    Story theStory;
    Goal theGoal;
    Exploration theExploration;

    AnalyticalQuestion currentQuestion;
    Collector currentCollector; // needed?
    Observation currentObservation;
    Episode currentEpisode;
    Message currentMessage;
    Act currentAct;
    //ArrayList<Protagonist> currentProtagonists;


    Collection<Insight> currentAnswer;

    public StoryCreator(){
        theGoal = new SimpleGoal();
        theStory = new SimpleStory();
        theStory.has(theGoal);
        theGoal.has(theStory);
        theExploration = new SimpleExploration(); // only one exploration
        theGoal.solves(theExploration);
        //currentProtagonists=new ArrayList<Protagonist>();
    }

    public Goal getGoal(){
        return theGoal;
    }

    public AnalyticalQuestion getCurrentQuestion() {
        return currentQuestion;
    }

    public Observation getCurrentObservation() {
        return currentObservation;
    }

    public Message getCurrentMessage(){
        return currentMessage;
    }


    public String newQuestion(String question){
        AnalyticalQuestion anAnalyticalQuestion = new SimpleAnalyticalQuestion();
        theGoal.poses(anAnalyticalQuestion);
        anAnalyticalQuestion.poses(theGoal);
        anAnalyticalQuestion.addText(question);

        currentQuestion=anAnalyticalQuestion;

        return question;
    }

    public String newCollector(String query){

        Collector c = new SimpleCollector(query);
        currentQuestion.implement(c);
        theExploration.tries(c);
        currentCollector=c;

        try {
            c.run();
        }

        catch(Exception e){
            e.printStackTrace();
        }

        Collection<Insight> col =c.fetches();
        currentAnswer = col;

        Iterator<Insight> it= col.iterator();
        String res="";

        while(it.hasNext()){
            Insight i=it.next();
            //currentObservation.produces(i);
            res=res+i.toString();
        }

        return res;
    }


    public String newObservation(String theObservation){
        Observation o = new SimpleObservation();
        o.addText(theObservation);
        currentObservation=o;
        currentQuestion.generates(currentObservation);
        currentObservation.generates(currentQuestion);

        Iterator<Insight> it= currentAnswer.iterator();
        String res="";

        while(it.hasNext()){
            Insight i=it.next();
            currentObservation.produces(i);
            res=res+i.toString();
        }


        return theObservation;
    }


    public String newProtagonist(String theProtagonist){
        Protagonist p = new SimpleProtagonist();
        currentObservation.bringsOut(p);
        p.addText(theProtagonist);
        return theProtagonist;
    }

    public String newAct(String theAct){
        currentAct=new SimpleAct();
        theStory.includes(currentAct);
        currentAct.narrates(currentMessage);
        return(theAct);
    }


    public String newMessage(String theMessage){
        currentMessage=new SimpleMessage();
        if(currentObservation!=null)
            currentMessage.bringsOut(currentObservation);
        else
            theGoal.bringOut(currentMessage);
        currentAct.narrates(currentMessage);
        return(theMessage);
    }



    public String newEpisode(String theEpisode){
        currentEpisode=new SimpleEpisode();
        currentAct.includes(currentEpisode);
        for(Protagonist p : currentObservation.bringsOut()){
            currentEpisode.playsIn(p);
        }

        return(theEpisode);
    }

    public static void main(String args[]) throws Exception{

        Story theStory=new SimpleStory();
        Goal theGoal = new SimpleGoal();
        //String goalText="a straight story";
        //theGoal.addText(goalText);
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
            Collection<Insight> col = ((SimpleAnalyticalQuestion) anAnalyticalQuestion).answer();
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
