package fr.univtours.info.simpleStory;

import fr.univtours.info.model.Structural.Act;
import fr.univtours.info.model.Structural.Episode;
import fr.univtours.info.model.factual.Collector;
import fr.univtours.info.model.factual.Exploration;
import fr.univtours.info.model.factual.Finding;
import fr.univtours.info.model.intentional.*;
import fr.univtours.info.model.intentional.Character;
import fr.univtours.info.model.presentational.VisualStory;
import fr.univtours.info.model.Structural.Plot;
import fr.univtours.info.pocdatastory.EpisodeRecall;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

public class StoryCreator {

    Plot thePlot;
    Goal theGoal;
    Exploration theExploration;

    AnalyticalQuestion currentQuestion;
    Collector currentCollector;
    Message currentMessage;
    Episode currentEpisode;
    Measure currentMeasure;
    Character currentCharacter;
    Act currentAct;
    //ArrayList<Protagonist> currentProtagonists;

    Collection<Finding> currentAnswer;
    String currentGraphicString;
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

    public Message getCurrentMessage() {
        return currentMessage;
    }

    public Measure getCurrentMeasure() {
        return currentMeasure;
    }

    public void setCurrentMeasure(Measure currentMeasure) {
        this.currentMeasure = currentMeasure;
    }

    public Character getCurrentCharacter() {
        return currentCharacter;
    }

    public void setCurrentCharacter(Character currentCharacter) {
        this.currentCharacter = currentCharacter;
    }

    public Act getCurrentAct(){
        return currentAct;
    }

    public PDDocument getThePDF(){
        return thePDF;
    }

    public EpisodeRecall recallAct(Integer nb) {
        Collection<Act> c = thePlot.includes();
        Iterator<Act> it = c.iterator();
        int i = 0;
        Act a = null;
        while (i < nb && it.hasNext()) {
            a = it.next();
            i++;
        }

        //System.out.println("act: " + a.toString());

        currentAct = a;
        // fetch 1st episode of a
        Collection<Episode> ce = a.includes();
        Iterator<Episode> ie = ce.iterator();

        // beware of no episodes!
        Episode e = ie.next();
        if (e != null) {
            currentEpisode = e;
            currentMessage = e.narrates();
            Collection<Character> cc = e.playsIn();
            Iterator<Character> ic = cc.iterator();
            currentCharacter = ic.next();
            Collection<Measure> cm = e.refersTo();
            Iterator<Measure> im = cm.iterator();
            currentMeasure = im.next();
            // see if we must retrieve questions
            return new EpisodeRecall(0, currentEpisode.getText(),
                    currentAct.getText(), currentMessage.getText(),
                    currentMeasure.getText(), currentCharacter.getText());
        } else {
            return new EpisodeRecall(1, "",
                    "", "", "" ,"");
        }
    }

    public EpisodeRecall recallEpisode(Integer nb) {
        //episode lookup

        Collection<Act> c = thePlot.includes();
        Iterator<Act> it = c.iterator();
        int i = 0;
        Act a = null;
        while (i < nb && it.hasNext()) {
            a = it.next();
            i++;
        }

        currentAct = a;
        // fetch 1st episode of a
        Collection<Episode> ce = a.includes();
        Iterator<Episode> ie = ce.iterator();

        // beware of no episodes!
        Episode e = ie.next();
        if (e != null) {
            currentEpisode = e;
            currentMessage = e.narrates();
            Collection<Character> cc = e.playsIn();
            Iterator<Character> ic = cc.iterator();
            currentCharacter = ic.next();
            Collection<Measure> cm = e.refersTo();
            Iterator<Measure> im = cm.iterator();
            currentMeasure = im.next();
            // see if we must retrieve questions
            return new EpisodeRecall(0, currentEpisode.getText(),
                    currentAct.getText(), currentMessage.getText(),
                    currentMeasure.getText(), currentCharacter.getText());
        } else {
            return new EpisodeRecall(1, "",
                    "", "", "" ,"");
        }
    }







    public File getThePDFfile(){
        //return thePDFfile;
        return new File("/Users/marcel/Desktop/test.pdf");
    }

    public String newGoal(String aGoal){
        theGoal = new SimpleGoal();
        theGoal.addText(aGoal);

        thePlot = new SimplePlot();
        thePlot.has(theGoal);
        theGoal.has(thePlot);
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

        Collection<Finding> col =c.fetches();
        currentAnswer = col;

         return query;
    }

    public void addDescribeInsight(byte[] describeResult, String base64){
        currentCollector.fetches(new SimpleDescribeFinding(describeResult, base64));
        currentGraphic=describeResult;
        currentGraphicString=base64;
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

        Collection<Finding> col =c.fetches();
        currentAnswer = col;

        Iterator<Finding> it= col.iterator();
        String res="";

        while(it.hasNext()){
            Finding i=it.next();
            //currentObservation.produces(i);
            res=res+i.toString();
        }

        return res;
    }


    public String newMessage(String theMessage){

        Message o = new SimpleMessage();
        o.addText(theMessage);
        currentMessage =o;
        currentQuestion.generates(currentMessage);
        currentMessage.generates(currentQuestion);

        Iterator<Finding> it= currentAnswer.iterator();
        String res="";

        while(it.hasNext()){
            Finding i=it.next();
            currentMessage.produces(i);
            res=res+i.toString();
        }


        return theMessage;
    }


    public String newCharacter(String theCharacter){
        currentCharacter = new SimpleCharacter();
        currentCharacter.addText(theCharacter);
        currentMessage.bringsOut(currentCharacter);
        return theCharacter;
    }


    public String newMeasure(String theMeasure){
        currentMeasure  =new SimpleMeasure();
        currentMeasure.addText(theMeasure);
        currentMessage.includes(currentMeasure);
        return(theMeasure);
    }


    public String newAct(String theAct){
        currentAct=new SimpleAct();
        thePlot.includes(currentAct);
        currentAct.addText(theAct);
        return(theAct);
    }


    public String newEpisode(String theEpisode){
        currentEpisode=new SimpleEpisode();
        currentEpisode.narrates(currentMessage);
        currentEpisode.addText(theEpisode);

        currentAct.includes(currentEpisode);

        // attaches current message characters
        for(Character p : currentMessage.bringsOut()){
            currentEpisode.playsIn(p);
        }
        // attaches current message measures
        for(Measure m : currentMessage.includes()){
            currentEpisode.refersTo(m);
        }

        // validation of episode resets message, character, measure and episode
        currentEpisode=null;
        currentMessage=null;
        currentCharacter=null;
        currentMeasure=null;
        return(theEpisode);
    }




    public String render(String msg){
        VisualStory vs=new SimpleVisualStory();
        vs.renders(thePlot);  // just attach
        vs.renders(); // and then renders
        thePDF=((SimpleVisualStory) vs).getThePDF();

        //vs.print(); // and then prints
        return msg;
    }






    public static void main(String args[]) throws Exception{

        // I was only for testing purpose, please drop me

        Plot thePlot =new SimplePlot();
        Goal theGoal = new SimpleGoal();
        //String goalText="a straight story";
        //theGoal.addText(goalText);
        thePlot.has(theGoal);

        Act currentAct =null;
        Measure currentMeasure =null;

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
            Collection<Finding> col = ((SimpleAnalyticalQuestion) anAnalyticalQuestion).answer();
            theGoal.poses(anAnalyticalQuestion);

            // show insights and ask if worthy
            boolean isWorthy=true; //Fake
            if(isWorthy){
                // ask if new act
                boolean newAct=true; //Fake
                if(newAct){
                    currentAct=new SimpleAct();
                    thePlot.includes(currentAct);
                    currentMeasure = new SimpleMeasure();

                    // ask for text
                }

                Message currentMessage = new SimpleMessage();
                anAnalyticalQuestion.generates(currentMessage);
                currentMessage.generates(anAnalyticalQuestion);

                //currentMessage.bringsOut(currentObservation);
                //currentObservation.addText(anAnalyticalQuestion.toString());

                for(Finding i : col){
                    // may ask the author if insight is accepted or not
                    currentMessage.produces(i);

                }



                Episode currentEpisode= new SimpleEpisode();
                currentEpisode.narrates(currentMessage);
                currentAct.includes(currentEpisode);
               // currentMeasure.bringsOut(currentMessage);
                currentAct.narrates(currentMeasure);

                // how many protagonists
                int nbProtagonists = 1; // fake
                for(int i=0;i<nbProtagonists;i++){
                    Character aCharacter = new SimpleCharacter();
                    currentEpisode.playsIn(aCharacter);
                    currentMessage.bringsOut(aCharacter);
                }


            }

            // ask more analytical questions? else stop
            stop=true; //fake
        }

        // ask to complement the story, acts, episodes with texts
        //theStory.addText("ask the author");

        VisualStory vs=new SimpleVisualStory();
        vs.renders(thePlot);  // just attach
        vs.renders(); // and then renders
        vs.toString(); // and then prints
    }


}
