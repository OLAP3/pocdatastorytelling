package fr.univtours.info.pocdatastory;

import fr.univtours.info.model.factual.Insight;
import fr.univtours.info.model.intentional.*;
import fr.univtours.info.simpleStory.SimpleCollector;
import fr.univtours.info.simpleStory.SimpleGoal;
import fr.univtours.info.simpleStory.StoryCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;


@Controller
public class RandomEndPoint {

    StoryCreator creator=null;


    @PostMapping(value="api/goal")
    @ResponseBody
    public String goal(@RequestBody String theGoal) {
        creator= new StoryCreator();
        creator.getGoal().addText(theGoal);
        return theGoal.toString();

    }


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


    @PostMapping(value="api/query", consumes = "application/json")
    @ResponseBody
    public String query(@RequestBody SqlCollector query) {

        String toReturn="No analytical question created! Please create an analytical question first.";


        if(creator.getCurrentQuestion()!=null){

            String res=creator.newCollector(query.getQuery());

            if(res.length()>10000){
                toReturn="Query result too long, cannot be rendered";
            }
            else{
                toReturn= res;

            }
        }
        return toReturn;

    }



    @PostMapping(value="api/result")
    @ResponseBody
    public String result(@RequestBody String theResult) {

        return theResult;

    }



    @PostMapping(value="api/observation")
    @ResponseBody
    public String observation(@RequestBody String theObservation) {

        creator.newObservation(theObservation);

        return theObservation;

    }



    @PostMapping(value="api/message")
    @ResponseBody
    public String message(@RequestBody String message) {


        String toReturn="No observation or goal created! Please create a goal or an observation first.";

        if(creator.getCurrentObservation()!=null || creator.getGoal()!=null){

            String res=creator.newMessage(message);

        }
        return toReturn;


    }


    @PostMapping(value="api/act")
    @ResponseBody
    public String act(@RequestBody String act) {


        String toReturn="No message created! Please create a message first.";

        if(creator.getCurrentMessage()!=null){

            String res=creator.newAct(act);

        }
        return toReturn;


    }



    @PostMapping(value="api/episode")
    @ResponseBody
    public String episode(@RequestBody String episode) {


        String toReturn="No observation created! Please create an observation first.";

        if(creator.getCurrentObservation()!=null){

            String res=creator.newEpisode(episode);

        }
        return toReturn;


    }




    @PostMapping(value="api/protagonist")
    @ResponseBody
    public String protagonist(@RequestBody String protagonist) {


        String toReturn="No observation created! Please create an observation first.";

        if(creator.getCurrentObservation()!=null){

            String res=creator.newEpisode(protagonist);

        }
        return toReturn;


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
