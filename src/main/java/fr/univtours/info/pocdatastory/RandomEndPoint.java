package fr.univtours.info.pocdatastory;

import fr.univtours.info.model.factual.Insight;
import fr.univtours.info.simpleStory.SimpleCollector;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Iterator;


@Controller
public class RandomEndPoint {

    @PostMapping("api/random")
    @ResponseBody
    public String sayHello(@RequestParam(name="msg", required=false) String msg) {
        SimpleCollector col=new SimpleCollector("Select * from names");
        try {
            col.run();
        }catch(Exception e){
            e.printStackTrace();
        }
        Iterator<Insight> it= col.fetches().iterator();
        String res=it.next().toString();
        //System.out.println(it.next());

        System.out.println(msg);
        return res;
        //return Math.random();
    }

    @PostMapping("api/form")
    @ResponseBody
    public String form(@RequestBody FormObject fo) {
        return "<h2>Bonjour "+fo.fn+"</h2>";
    }

    static class FormObject {
        String fn;
        String ln;
    }
}
