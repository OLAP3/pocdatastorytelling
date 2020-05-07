package fr.univtours.info.simpleStory;

import fr.univtours.info.model.discursal.Act;
import fr.univtours.info.model.discursal.Episode;
import fr.univtours.info.model.factual.Collector;
import fr.univtours.info.model.factual.Exploration;
import fr.univtours.info.model.factual.Insight;
import fr.univtours.info.model.intentional.*;
import fr.univtours.info.model.presentational.VisualStory;
import fr.univtours.info.model.discursal.Story;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

public class StoryCreator {

    Story theStory;
    Goal theGoal;
    Exploration theExploration;

    AnalyticalQuestion currentQuestion;
    Collector currentCollector;
    Observation currentObservation;
    Episode currentEpisode;
    Message currentMessage;
    Act currentAct;
    //ArrayList<Protagonist> currentProtagonists;

    Collection<Insight> currentAnswer;

    byte[] currentGraphic;
    boolean currentInsightIsGraphic=false;

    PDDocument thePDF;
    File thePDFfile;

    public StoryCreator(){

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

    public Act getCurrentAct(){
        return currentAct;
    }

    public PDDocument getThePDF(){
        return thePDF;
    }

    public File getThePDFfile(){
        //return thePDFfile;
        return new File("/Users/marcel/Desktop/test.pdf");
    }

    public String newGoal(String aGoal){
        theGoal = new SimpleGoal();
        theGoal.addText(aGoal);

        theStory = new SimpleStory();
        theStory.has(theGoal);
        theGoal.has(theStory);
        theExploration = new SimpleExploration(); // only one exploration
        theGoal.solves(theExploration);
        //currentProtagonists=new ArrayList<Protagonist>();
        return aGoal;
    }


    public String newQuestion(String question){
        AnalyticalQuestion anAnalyticalQuestion = new SimpleAnalyticalQuestion();
        theGoal.poses(anAnalyticalQuestion);
        anAnalyticalQuestion.poses(theGoal);
        anAnalyticalQuestion.addText(question);

        currentQuestion=anAnalyticalQuestion;

        return question;
    }



    public String newDescribeCollector(String query) {

        Collector c = new SimpleDescribeCollector(query);
        currentQuestion.implement(c);
        theExploration.tries(c);
        currentCollector=c;


        Collection<Insight> col =c.fetches();
        currentAnswer = col;
        /*
        Iterator<Insight> it= col.iterator();
        String res="";

        while(it.hasNext()){
            Insight i=it.next();
            //currentObservation.produces(i);
            res=res+i.toString();
        }

        return res;
        */
         return query;

    }

    public void addDescribeInsight(byte[] describeResult){
        currentCollector.fetches(new SimpleDescribeInsight(describeResult));
        currentGraphic=describeResult;
        currentInsightIsGraphic=true;
    }


    public String newSQLCollector(String query){

        Collector c = new SimpleSQLCollector(query);
        currentQuestion.implement(c);
        theExploration.tries(c);
        currentCollector=c;

        try {
            c.run();
        }

        catch(Exception e){
            e.printStackTrace();
        }

        currentInsightIsGraphic=false;

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

        //currentAct.narrates(currentMessage);

        currentAct.addText(theAct);

        return(theAct);
    }


    public String newMessage(String theMessage){
        currentMessage=new SimpleMessage();
        currentMessage.addText(theMessage);
        if(currentObservation!=null)
            currentMessage.bringsOut(currentObservation);
        else
            theGoal.bringOut(currentMessage);

        return(theMessage);
    }



    public String newEpisode(String theEpisode){
        if(currentInsightIsGraphic){
            currentEpisode=new SimpleGraphicEpisode();
            ((SimpleGraphicEpisode) currentEpisode).setGraphic(currentGraphic);
        }else{
            currentEpisode=new SimpleEpisode();
        }

        currentAct.includes(currentEpisode);
        for(Protagonist p : currentObservation.bringsOut()){
            currentEpisode.playsIn(p);
        }
        currentEpisode.narrates(currentObservation);
        currentEpisode.addText(theEpisode);

        return(theEpisode);
    }




    public String render(String msg){
        VisualStory vs=new SimpleVisualStory();
        vs.renders(theStory);  // just attach
        vs.renders(); // and then renders
        thePDF=((SimpleVisualStory) vs).getThePDF();

        //vs.print(); // and then prints
        return msg;
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
