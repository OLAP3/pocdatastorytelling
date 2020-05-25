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
    private String filename=null;

    public SimpleSQLFinding(ResultSet aResultSet){
        this.resultSet=aResultSet;
        ToImage ti=new ToImage(resultSet);
        try {
            filename = ti.toImage();
            resultSet.beforeFirst();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public ResultSet getResultSet(){
        return resultSet;
    }

    public String getFilename(){
        return filename;
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
