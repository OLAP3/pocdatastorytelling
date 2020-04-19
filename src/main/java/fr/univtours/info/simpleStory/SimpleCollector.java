package fr.univtours.info.simpleStory;

import fr.univtours.info.model.factual.Collector;
import fr.univtours.info.model.factual.Insight;

import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

public class SimpleCollector implements Collector {
    Connection conn;
    String sqlQuery;
    ResultSet resultset;
    Collection<Insight> theInsights;

    public SimpleCollector(String sqlQuery) {
        try {
            this.connectToPostgresql();
            this.sqlQuery = sqlQuery;
            theInsights = new ArrayList<Insight>();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    void connectToPostgresql() throws Exception{

        /*
        String url = "jdbc:postgresql://localhost/marcel";
        Properties props = new Properties();
        props.setProperty("user","marcel");
        props.setProperty("password","");
       // props.setProperty("ssl","true");
        conn = DriverManager.getConnection(url, props);
        //String url = "jdbc:postgresql://localhost/test?user=fred&password=secret&ssl=true";
        //Connection conn = DriverManager.getConnection(url);
*/

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


    void sendQuery() throws Exception{
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(this.sqlQuery) ;
        this.resultset=rs;
        // now produces insights
        Insight i = new SimpleInsight(resultset);
        this.fetches(i);
    }

    @Override
    public boolean hasInsight() {
        return true;
    }

    @Override
    public void fetches(Insight anInsight) {
        theInsights.add(anInsight);
    }

    @Override
    public Collection<Insight> fetches() {
        return theInsights ;
    }

    @Override
    public void run() throws Exception{
        //throw(new SQLException(sqlQuery));
        this.sendQuery();
    }
}



