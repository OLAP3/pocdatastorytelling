package fr.univtours.info.simpleStory;

import fr.univtours.info.model.factual.*;
import org.apache.commons.dbutils.ResultSetIterator;

import java.io.File;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class SimpleSQLFinding implements Finding {
    private ResultSet resultSet;

    public SimpleSQLFinding(ResultSet aResultSet){
        this.resultSet=aResultSet;

    }

    public ResultSet getResultSet(){
        return resultSet;
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
