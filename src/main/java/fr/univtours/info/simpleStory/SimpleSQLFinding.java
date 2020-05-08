package fr.univtours.info.simpleStory;

import fr.univtours.info.model.factual.*;
import org.apache.commons.dbutils.ResultSetIterator;

import java.sql.ResultSet;


public class SimpleSQLFinding implements Finding {
    private ResultSet resultSet;

    public SimpleSQLFinding(ResultSet aResultSet){
        this.resultSet=aResultSet;

    }



    public String toString(){
        String resultString="";
        ResultSetIterator rsit=new ResultSetIterator(resultSet);
        while(rsit.hasNext()){
            Object[] tab=rsit.next();
            for(int i=0;i<tab.length;i++) {
                resultString = resultString + tab[i].toString();
                resultString = resultString + " ";
            }
            resultString = resultString + "\n";
        }
        return resultString;
    }

}
