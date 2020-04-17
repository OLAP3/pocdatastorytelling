package fr.univtours.info.pocdatastory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RandomEndPoint {

    @PostMapping("api/random")
    @ResponseBody
    public double sayHello(@RequestParam(name="msg", required=false) String msg) {
        System.out.println(msg);
        return Math.random();
    }
}
