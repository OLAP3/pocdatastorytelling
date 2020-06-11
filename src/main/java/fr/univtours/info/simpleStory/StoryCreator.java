package fr.univtours.info.simpleStory;

import fr.univtours.info.model.Structural.Act;
import fr.univtours.info.model.Structural.Episode;
import fr.univtours.info.model.factual.Collector;
import fr.univtours.info.model.factual.Exploration;
import fr.univtours.info.model.factual.Finding;
import fr.univtours.info.model.intentional.*;
import fr.univtours.info.model.intentional.Character;
import fr.univtours.info.model.presentational.VisualNarrative;
import fr.univtours.info.model.Structural.Plot;
import fr.univtours.info.pocdatastory.Answer;
import fr.univtours.info.pocdatastory.DBservices;
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

    String currentFilename=null;

    PDDocument thePDF;
    File thePDFfile;

    String franchiseHTMLfile=null;

    DBservices dbs;

    public StoryCreator(){
        dbs=new DBservices();
    }

    public void setPlot(Plot aPlot){
        this.thePlot=aPlot;
    }

    public String getCurrentFilename(){
        return currentFilename;
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

    public Episode getCurrentEpisode() {
        return currentEpisode;
    }

    public void setCurrentEpisode(Episode currentEpisode) {
        this.currentEpisode = currentEpisode;
    }

    public PDDocument getThePDF(){
        return thePDF;
    }

    // TODO maybe trim to remove spaces?
    private Character lookupCharacter(String theText){
        Character characterFound=null;
        Collection<Act> ca=thePlot.includes();
        for(Act a : ca){
            Collection<Episode> ce = a.includes();
            for(Episode e : ce){
                Collection<Character> cc=e.playsIn();
                for(Character c : cc){
                    //System.out.println(text);
                    //System.out.println(c.getText());

                    if(c.getText().compareTo(theText)==0){
                        characterFound=c;
                    }
                }
            }
        }
        return characterFound;
    }


    private Measure lookupMeasure(String theText){
        Measure measureFound=null;
        Collection<Act> ca=thePlot.includes();
        for(Act a : ca){
            Collection<Episode> ce = a.includes();
            for(Episode e : ce){
                Collection<Measure> cc=e.refersTo();
                for(Measure m : cc){
                    //System.out.println(theText);
                    //System.out.println(m.getText());
                    //System.out.println(m.getText().compareTo(theText));

                    if(m.getText().compareTo(theText)==0){
                        measureFound=m;
                    }
                }
            }
        }
        return measureFound;
    }


    public Answer recallCharacter(String text){
        // lookup in all characters the one whose text=text
        String theText=text.substring(1,text.length()-1).replace("\\n","\n");
        int code=1;

        /*
        Character characterFound=null;

        Collection<Act> ca=thePlot.includes();
        for(Act a : ca){
            Collection<Episode> ce = a.includes();
            for(Episode e : ce){
                Collection<Character> cc=e.playsIn();
                for(Character c : cc){
                    //System.out.println(text);
                    //System.out.println(c.getText());

                    if(c.getText().compareTo(theText)==0){
                        characterFound=c;
                    }
                }
            }
        }
         */
        Character characterFound = lookupCharacter(theText);
        if(characterFound!=null) {
            currentCharacter = characterFound;
            code=0;
        }
        Answer res = new Answer(code, theText);
        return res;
    }

    public Answer recallMeasure(String text){
        // lookup in all measures the one whose text=text

        String theText=text.substring(1,text.length()-1).replace("\\n","\n");
        int code=1;

        Measure measureFound=lookupMeasure(theText);
        if(measureFound!=null) {
            currentMeasure = measureFound;
            code=0;
        }
        Answer res = new Answer(code, theText);
        return res;
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


    public String modifyEpisode(String newText){
        this.currentEpisode.addText(newText);
        return newText;
    }

    public String modifyAct(String newText){
        this.currentAct.addText(newText);
        return newText;
    }

    public String modifyCharacter(String newText){
        this.currentCharacter.addText(newText);
        return newText;
    }

    public String modifyMeasure(String newText){
        this.currentMeasure.addText(newText);
        return newText;
    }


    public String modifyMessage(String newText){
        this.currentMessage.addText(newText);
        return newText;
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

        for(Finding f : col){
            f.fetches(c);
        }

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

        for(Finding f : col){
            f.fetches(c);
        }

        Iterator<Finding> it= col.iterator();
        String res="";

        while(it.hasNext()){
            Finding i=it.next();
            //currentObservation.produces(i);
            res=res+i.toString();
            currentFilename=((SimpleSQLFinding) i).getFilename();
        }

        //System.out.println("in story creator: " + res);
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


    public Answer newCharacter(String theCharacter){

        int code=0;

        // if  exist
        Character found=lookupCharacter(theCharacter.substring(1,theCharacter.length()-1).replace("\\n","\n"));
        if(found==null) {

            currentCharacter = new SimpleCharacter();
        }else{
            code=2;
            currentCharacter=found;
        }
        currentCharacter.addText(theCharacter);
        currentMessage.bringsOut(currentCharacter);
        //return theCharacter;
        return new Answer(code,theCharacter);
    }


    public Answer newMeasure(String theMeasure){
        int code=0;

        // if not exist
        Measure found=lookupMeasure(theMeasure.substring(1,theMeasure.length()-1).replace("\\n","\n"));
        if(found==null) {
            currentMeasure  =new SimpleMeasure();
        }else{
            code=2;
            currentMeasure=found;
        }
        currentMeasure.addText(theMeasure);
        currentMessage.includes(currentMeasure);
        //return(theMeasure);
        return new Answer(code,theMeasure);
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
        VisualNarrative vs=new SimpleVisualNarrative();
        vs.renders(thePlot);  // just attach
        vs.renders(); // and then renders
        thePDF=((SimpleVisualNarrative) vs).getThePDF();

        //vs.print(); // and then prints
        return msg;
    }


    // Discourse only visual
    public String renderDO(String msg){
        SimpleDiscourseOnlyVisualNarrative vs=new SimpleDiscourseOnlyVisualNarrative();
        vs.renders(thePlot);  // just attach
        vs.renders(); // and then renders
        thePDF=((SimpleDiscourseOnlyVisualNarrative) vs).getThePDF();

        //vs.print(); // and then prints
        return msg;
    }




    // SQL notebook visual
    public String renderFranchise(String msg){
        SQLnotebookNarrative vs=new SQLnotebookNarrative();
        vs.renders(thePlot);  // just attach
        vs.renders(); // and then renders
        franchiseHTMLfile=((SQLnotebookNarrative) vs).getTheHTML();

        //vs.print(); // and then prints
        return msg;
    }


    public String  saveInDB(String msg){
        thePlot.addText(msg);
        return "Story with id: " + dbs.store(thePlot) + " saved.";
    }


    public Plot loadFromDB(String msg){
        System.out.println(msg);
        String theText=msg.substring(1,msg.length()-1).replace("\\n","\n");
        Plot p = dbs.restore(theText);
        return p;
    }






    // below for testing

    public static void main(String args[]) throws Exception {
      StoryCreator s=new StoryCreator();
      s.renderFranchise("");
    }


}
