package fr.univtours.info.simpleStory;

import fr.univtours.info.model.intentional.Goal;
import fr.univtours.info.model.Structural.Act;
import fr.univtours.info.model.Structural.Story;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

public class SimpleStory implements Story {

    private String text;
    private ArrayList<Act> theActs;
    private Goal theGoal;

    private Connection conn;

    public SimpleStory(){
        theActs=new ArrayList<Act>();
    }



    @Override
    public void addText(String theText) {

        this.text=theText;
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public void includes(Act anAct) {
        this.theActs.add(anAct);
    }


    @Override
    public void has(Goal aGoal) {

        this.theGoal=aGoal;
    }

    @Override
    public Collection<Act> includes() {

        return this.theActs;
    }

    @Override
    public Goal has() {

        return this.theGoal;
    }

    @Override
    public void store() {
        try {
            this.connectToPostgresql();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        //TODO
        // dumps all the story objects in a database:
        // create schema


    }
    void connectToPostgresql() throws Exception{

        FileReader fr=new FileReader(new File("src/main/resources/application.properties"));
        Properties props = new Properties();
        props.load(fr);
        String url=props.getProperty("spring.datasource.url");
        String passwd=props.getProperty("spring.datasource.password");
        String user=props.getProperty("spring.datasource.user");
        url=url +  "?user=" + user + "&password=" + passwd;
        System.out.println(url);

        conn = DriverManager.getConnection(url);
    }
}
