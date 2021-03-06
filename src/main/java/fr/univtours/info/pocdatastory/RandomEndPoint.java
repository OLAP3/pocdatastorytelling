package fr.univtours.info.pocdatastory;

import fr.univtours.info.model.Structural.Episode;
import fr.univtours.info.model.Structural.Plot;
import fr.univtours.info.model.factual.Finding;
import fr.univtours.info.simpleStory.SimpleSQLCollector;
import fr.univtours.info.simpleStory.StoryCreator;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;


@Controller
public class RandomEndPoint {

    StoryCreator creator; //current creator
    ArrayList<StoryCreator> theCreators=new ArrayList<StoryCreator>();


    @PostMapping(value="api/clear", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Answer clear(@RequestBody String clear) {
            creator=new StoryCreator();
            theCreators.add(creator);

        return new Answer(0,"New story created");

    }



    @PostMapping(value="api/goal", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Answer goal(@RequestBody String theGoal) {

        int code=1;
        String toReturn="";

        if(creator==null){
            code=1;
            toReturn="No story created. Please start a new story first.";

        }else{
            if(creator.getGoal()!=null){
                code=1;
                 toReturn="Already a goal. Only one is allowed.";


            }else{
                if(creator.getGoal()==null){
                    code=0;
                    //creator= new StoryCreator();

                    //creator.getGoal().addText(theGoal);
                    String res= creator.newGoal(theGoal);
                    toReturn=res;
                }
            }
        }


        return new Answer(code,toReturn);

    }


    @PostMapping(value="api/question", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Answer question(@RequestBody String question) {

        String toReturn="No goal created! Please create a goal first.";
        int code=1;

        if(creator.getGoal()!=null){
            code =0;
            String res=creator.newQuestion(question);

            toReturn= res;

        }
        return new Answer(code, toReturn);
    }



    @PostMapping(value="api/query", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AnswerViz query(@RequestBody SqlCollector query) {

        String toReturn="No analytical question created! Please create an analytical question first.";
        int code=1;
        String filename=null;

        if(creator.getCurrentQuestion()!=null){


            String res=creator.newSQLCollector(query.getQuery());
            code=0;
            toReturn= res;
            filename= creator.getCurrentFilename();

        }
        return new AnswerViz(code,toReturn, filename);

    }




    @PostMapping(value="api/describeQuery",  produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Answer describeQuery(@RequestBody String query) {

        String toReturn="No analytical question created! Please create an analytical question first.";
        int code=1;

        if(creator.getCurrentQuestion()!=null){

            String res=creator.newDescribeCollector(query);

                code=0;
                toReturn= res;

        }
        return new Answer(code,toReturn);

    }


    @PostMapping(value="api/result", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Answer result(@RequestBody String theResult) {

        return new Answer(0,theResult);
    }



    @PostMapping(value="api/describeInsight", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Answer describeInsight(@RequestBody String base64) {
        // as for SQL result, we must have an analytical question and instantiate a collector
        // meaning that we must encapsulate the describe in a collector

        // change this, pass the base64 string and do the  decoding in the rendering
        try {
            //System.out.println(base64);
            String param=base64;
            int index = base64.indexOf(',');

            if (index != -1) base64 = base64.substring(index + 1);

            byte[] bytes = Base64.getDecoder().decode(new String(base64).getBytes("UTF-8"));

            creator.addDescribeInsight(bytes, param);


        } catch (final Exception e) {
            e.printStackTrace();
        }
        return new Answer(0,"Describe insight added");

    }



    @PostMapping(value="api/describeViz", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Answer describeViz(@RequestBody String base64) {

        return new Answer(0,base64);

    }




    @PostMapping(value="api/observation", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Answer observation(@RequestBody String theMessage) {

        // TODO observation not permitted if no insights!
        creator.newMessage(theMessage);
        return new Answer(0,theMessage);

    }




    @PostMapping(value="api/act", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Answer act(@RequestBody String act) {

        //String toReturn="No message created! Please create a message first.";
        String toReturn="No story created! Please create a story first.";
        int code=1;

        //if(creator.getCurrentMessage()!=null){
        if(creator.getGoal()!=null){
            code =0;
            String res=creator.newAct(act);
            toReturn=res;

        }
        return new Answer(code,toReturn);


    }



    @PostMapping(value="api/episode", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Answer episode(@RequestBody String episode) {

        String toReturn="Please create an act, a message, characters and measures first.";
        int code =1;

        if(creator.getCurrentMessage()!=null && creator.getCurrentAct() !=null
        && creator.getCurrentCharacter()!=null && creator.getCurrentMeasure()!=null){
            code =0;
            String res=creator.newEpisode(episode);
            toReturn=res;

        }
        return new Answer(code,toReturn);


    }



    // now character
    @PostMapping(value="api/protagonist", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Answer protagonist(@RequestBody String protagonist) {

        int code=1;
        String toReturn="No message created! Please create a message first.";

        if(creator.getCurrentMessage()==null){
            return new Answer(code, toReturn);
        }
       else{
           return creator.newCharacter(protagonist);
        }

    }



    // now measure
    // message the new name of observation
    @PostMapping(value="api/message", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Answer message(@RequestBody String message) {

        String toReturn="No message created! Please create a message first.";
        int code=1;

        if(creator.getCurrentMessage()==null){
            return new Answer(code,toReturn);
        }
        else{
            return creator.newMeasure(message);
        }



    }


    @PostMapping(value="api/render", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Answer render(@RequestBody String msg) {

        int code=1;
        String toReturn="No story created! Please create story objects first.";

        if(creator!=null){
            code=0;
            String res=creator.render(msg);
            toReturn=res;

        }
        return new Answer(code, toReturn);


    }



    @PostMapping(value="api/renderDO", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Answer renderDO(@RequestBody String msg) {

        int code=1;
        String toReturn="No story created! Please create story objects first.";

        if(creator!=null){
            code=0;
            String res=creator.renderDO(msg);
            toReturn=res;

        }
        return new Answer(code, toReturn);


    }



    @PostMapping(value="api/renderSQLNB", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Answer renderSQLNB(@RequestBody String msg) {

        int code=1;
        String toReturn="No story created! Please create story objects first.";

        if(creator!=null){
            code=0;
            String res=creator.renderFranchise(msg);
            toReturn=res;

        }
        return new Answer(code, toReturn);


    }


    @PostMapping(value="api/DBsave", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Answer DBsave(@RequestBody String msg) {

        int code=1;
        String toReturn="No story or goal created! Please create story objects first";

        if(creator!=null && creator.getGoal()!=null){
            code=0;
            String res=creator.saveInDB(msg);
            toReturn=res;

        }
        return new Answer(code, toReturn);


    }


    @PostMapping(value="api/DBload", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public NarrativeRecall DBload(@RequestBody String msg) {

        //int code=1;
        //String toReturn="No story found, please try again.";

        NarrativeRecall res;

        creator=new StoryCreator();
        Plot p = creator.loadFromDB(msg);
        if(p!=null){
            creator.setPlot(p);
            res= creator.recallNarrative();

            //code =0;
            //toReturn=p.toString();
        }
        else{
            res=new NarrativeRecall(1, "",
                    0, null,
                    null, null);

        }

        return res ;

        //return new Answer(code, toReturn);


    }




    @PostMapping(value="api/recallAct", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EpisodeRecall recallAct(@RequestBody String msg) {

        //System.out.println(msg);
        EpisodeRecall res= creator.recallAct(Integer.valueOf(msg));

       // if code =1 something went wrong
        return res ;


    }


    @PostMapping(value="api/recallEpisode", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EpisodeRecall recallEpisode(@RequestBody String msg) {

        EpisodeRecall res= creator.recallEpisode(Integer.valueOf(msg));

        // if code =1 something went wrong
        return res ;


    }




    @PostMapping(value="api/recallCharacter", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Answer recallCharacter(@RequestBody String msg) {

        Answer res= creator.recallCharacter(msg);

        // if code =1 something went wrong
        return res ;


    }



    @PostMapping(value="api/recallMeasure", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Answer recallMeasure(@RequestBody String msg) {

        Answer res= creator.recallMeasure(msg);

        // if code =1 something went wrong
        return res ;


    }




    @PostMapping(value="api/modifyEpisode", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Answer modifyEpisode(@RequestBody String msg) {


        String toReturn="Please create an episode first.";
        int code =1;

        if(creator.getCurrentEpisode()!=null){
            code = 0;
            String res=creator.modifyEpisode(msg);
            toReturn="Episode modified.";

        }
        return new Answer(code,toReturn);


    }


    @PostMapping(value="api/modifyAct", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Answer modifyAct(@RequestBody String msg) {


        String toReturn="Please create an act first.";
        int code =1;

        if(creator.getCurrentAct()!=null){
            code = 0;
            String res=creator.modifyAct(msg);
            toReturn="Act modified.";

        }
        return new Answer(code,toReturn);


    }


    @PostMapping(value="api/modifyCharacter", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Answer modifyCharacter(@RequestBody String msg) {


        String toReturn="Please create a character first.";
        int code =1;

        if(creator.getCurrentCharacter()!=null){
            code = 0;
            String res=creator.modifyCharacter(msg);
            toReturn="Character modified.";

        }
        return new Answer(code,toReturn);

    }




    @PostMapping(value="api/modifyMeasure", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Answer modifyMeasure(@RequestBody String msg) {


        String toReturn="Please create a measure first.";
        int code =1;

        if(creator.getCurrentMeasure()!=null){
            code = 0;
            String res=creator.modifyMeasure(msg);
            toReturn="Measure modified.";

        }
        return new Answer(code,toReturn);

    }





    @PostMapping(value="api/modifyMessage", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Answer modifyMessage(@RequestBody String msg) {


        String toReturn="Please create a message first.";
        int code =1;

        if(creator.getCurrentMessage()!=null){
            code = 0;
            String res=creator.modifyMessage(msg);
            toReturn="Message modified.";

        }
        return new Answer(code,toReturn);

    }




















    // old stuff below -> trash

    @PostMapping(value="api/form", consumes = "application/json")
    @ResponseBody
    public String form(@RequestBody FormObject fo) {
        //public String form(@RequestBody String fo) {
        SimpleSQLCollector col=new SimpleSQLCollector(fo.getFn());
        try {
            col.run();
        }catch(Exception e){
            e.printStackTrace();
        }
        Iterator<Finding> it= col.fetches().iterator();
        String res=it.next().toString();

        if(res.length()>1000){
            return("Query result too long, cannot be rendered");
        }
        else{
            return res;

        }
    }

    @PostMapping("api/random")
    @ResponseBody
    public String sayHello(@RequestParam(name="msg", required=false) String msg) {
        //System.out.println(msg);

        return msg;
    }

}
/*
    @PostMapping(value="api/question")
    @ResponseBody
    public String question(@RequestBody String question) {


        String toReturn="No goal created! Please create a goal first.";
        if(creator!=null){
            String res=creator.newQuestion(question);

            toReturn= res;


        }
        return toReturn;

    }
    */