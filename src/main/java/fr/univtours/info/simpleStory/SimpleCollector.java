package fr.univtours.info.simpleStory;

import fr.univtours.info.model.factual.Collector;
import fr.univtours.info.model.factual.Insight;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

public class SimpleCollector implements Collector {
    Connection conn;
    String sqlQuery;
    ResultSet resultset;
    Collection<Insight> theInsights;

    public SimpleCollector(String sqlQuery) throws Exception{
        this.connectToPostgresql();
        this.sqlQuery=sqlQuery;
        theInsights=new ArrayList<Insight>();
    }


    void connectToPostgresql() throws Exception{
        String url = "jdbc:postgresql://localhost/marcel";
        Properties props = new Properties();
        props.setProperty("user","marcel");
        props.setProperty("password","");
       // props.setProperty("ssl","true");
        conn = DriverManager.getConnection(url, props);
        //String url = "jdbc:postgresql://localhost/test?user=fred&password=secret&ssl=true";
        //Connection conn = DriverManager.getConnection(url);
    }


    void sendQuery() throws Exception{
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(this.sqlQuery) ;
        this.resultset=rs;
        // now produces insights
        Insight i = new SimpleInsight(resultset);
        theInsights.add(i);
    }

    @Override
    public boolean hasInsight() {
        return true;
    }

    @Override
    public Collection<Insight> fetches() {
        return theInsights ;
    }

    @Override
    public void run() throws Exception{
        this.sendQuery();
    }
}



