package fr.univtours.info.pocdatastory;

import fr.univtours.info.model.factual.Insight;
import fr.univtours.info.model.intentional.*;
import fr.univtours.info.simpleStory.SimpleCollector;
import fr.univtours.info.simpleStory.SimpleGoal;
import fr.univtours.info.simpleStory.StoryCreator;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;


@Controller
public class RandomEndPoint {

    StoryCreator creator=null;


    @PostMapping(value="api/goal", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Answer goal(@RequestBody String theGoal) {

        String toReturn="Already a goal. Only one is allowed";
        int code =1;

        if(creator==null){
            code=0;
            creator= new StoryCreator();
            creator.getGoal().addText(theGoal);
            String res=theGoal.toString();
            toReturn=res;
        }

        return new Answer(code,toReturn);

    }


    @PostMapping(value="api/question", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Answer question(@RequestBody String question) {

        String toReturn="No goal created! Please create a goal first.";
        int code=1;

        if(creator!=null){
            code =0;
            String res=creator.newQuestion(question);

            toReturn= res;

        }
        return new Answer(code, toReturn);
    }



    @PostMapping(value="api/query", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Answer query(@RequestBody SqlCollector query) {

        String toReturn="No analytical question created! Please create an analytical question first.";
        int code=1;

        if(creator.getCurrentQuestion()!=null){

            String res=creator.newCollector(query.getQuery());

            if(res.length()>10000){
                toReturn="Query result too long, cannot be rendered";
            }
            else{
                code=0;
                toReturn= res;

            }
        }
        return new Answer(code,toReturn);

    }


    @PostMapping(value="api/result", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Answer result(@RequestBody String theResult) {

        return new Answer(0,theResult);

    }



    @PostMapping(value="api/observation", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Answer observation(@RequestBody String theObservation) {

        creator.newObservation(theObservation);
        return new Answer(0,theObservation);

    }



    @PostMapping(value="api/message", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Answer message(@RequestBody String message) {


        String toReturn="No observation or goal created! Please create a goal or an observation first.";
        int code=1;

        if(creator.getCurrentObservation()!=null || creator.getGoal()!=null){
            code=0;
            String res=creator.newMessage(message);
            toReturn=res;

        }
        return new Answer(code,toReturn);


    }


    @PostMapping(value="api/act", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Answer act(@RequestBody String act) {

        String toReturn="No message created! Please create a message first.";
        int code=1;

        if(creator.getCurrentMessage()!=null){
            code =0;
            String res=creator.newAct(act);
            toReturn=res;

        }
        return new Answer(code,toReturn);


    }



    @PostMapping(value="api/episode", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Answer episode(@RequestBody String episode) {

        String toReturn="No observation created! Please create an observation first.";
        int code =1;

        if(creator.getCurrentObservation()!=null){
            code =0;
            String res=creator.newEpisode(episode);
            toReturn=res;

        }
        return new Answer(code,toReturn);


    }




    @PostMapping(value="api/protagonist", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Answer protagonist(@RequestBody String protagonist) {

        int code=1;
        String toReturn="No observation created! Please create an observation first.";

        if(creator.getCurrentObservation()!=null){
            code=0;
            String res=creator.newProtagonist(protagonist);
            toReturn=res;

        }
        return new Answer(code, toReturn);


    }










    // old stuff below -> trash

    @PostMapping(value="api/form", consumes = "application/json")
    @ResponseBody
    public String form(@RequestBody FormObject fo) {
        //public String form(@RequestBody String fo) {
        SimpleCollector col=new SimpleCollector(fo.getFn());
        try {
            col.run();
        }catch(Exception e){
            e.printStackTrace();
        }
        Iterator<Insight> it= col.fetches().iterator();
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