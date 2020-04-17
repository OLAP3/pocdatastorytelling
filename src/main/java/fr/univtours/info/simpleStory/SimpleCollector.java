package fr.univtours.info.simpleStory;

import fr.univtours.info.model.factual.Collector;
import fr.univtours.info.model.factual.Insight;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class SimpleCollector implements Collector {
    Connection conn;
    public SimpleCollector() throws Exception{
        this.connectToPostgresql();
    }


    void connectToPostgresql() throws Exception{
        String url = "jdbc:postgresql://localhost/marcel";
        Properties props = new Properties();
        props.setProperty("user","");
        props.setProperty("password","");
        props.setProperty("ssl","true");
        conn = DriverManager.getConnection(url, props);

        //String url = "jdbc:postgresql://localhost/test?user=fred&password=secret&ssl=true";
        //Connection conn = DriverManager.getConnection(url);
    }

    String  sendQuery(String theQuery) throws Exception{
        String SQL = theQuery;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(SQL) ;
        return rs.toString();
    }

    @Override
    public Insight fetches() {
        return null ;
    }

    @Override
    public void run() {

    }
}



