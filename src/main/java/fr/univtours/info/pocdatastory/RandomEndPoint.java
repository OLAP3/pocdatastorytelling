package fr.univtours.info.pocdatastory;

import fr.univtours.info.model.factual.Insight;
import fr.univtours.info.simpleStory.SimpleCollector;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;


@Controller
public class RandomEndPoint {

    @PostMapping("api/random")
    @ResponseBody
    public String sayHello(@RequestParam(name="msg", required=false) String msg) {
        //System.out.println(msg);

        return msg;
    }

    @PostMapping(value="api/form", consumes = "application/json")
    @ResponseBody
    public String form(@RequestBody FormObject fo) {
        //public String form(@RequestBody String fo) {
        SimpleCollector col=new SimpleCollector(fo.fn);
        try {
            col.run();
        }catch(Exception e){
            e.printStackTrace();
        }
        Iterator<Insight> it= col.fetches().iterator();
        String res=it.next().toString();




       // return fo.fn;
        return res;
//        return "<h2>Bonjour "+fo.fn+"</h2>";
        //return Math.random();
    }

    /*
    static class FormObject {
        String fn;
        String ln;
        FormObject(){

        }
    }*/
}
