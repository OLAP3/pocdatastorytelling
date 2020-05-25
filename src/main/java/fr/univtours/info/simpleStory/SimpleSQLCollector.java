package fr.univtours.info.simpleStory;

import fr.univtours.info.model.factual.Collector;
import fr.univtours.info.model.factual.Finding;

import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

public class SimpleSQLCollector implements Collector {
    Connection conn;
    String sqlQuery;
    ResultSet resultset;
    Collection<Finding> theFindings;


    public SimpleSQLCollector(String sqlQuery) {
        try {
            this.connectToPostgresql();
            this.sqlQuery = sqlQuery;
            theFindings = new ArrayList<Finding>();
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
        Statement stmt = conn.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery(this.sqlQuery) ;
        this.resultset=rs;
        // now produces insights
        Finding i = new SimpleSQLFinding(resultset);
        this.fetches(i);
    }



    @Override
    public boolean hasInsight() {
        return true;
    }

    @Override
    public void fetches(Finding anFinding) {
        theFindings.add(anFinding);
    }

    @Override
    public Collection<Finding> fetches() {
        return theFindings;
    }

    @Override
    public void run() throws Exception{
        //throw(new SQLException(sqlQuery));
        this.sendQuery();
    }
}



