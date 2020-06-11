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
    private transient ResultSet resultSet; //transient needed for serialization
    private String filename=null;
    private transient ResultSetMetaData rsmd; //transient needed for serialization
    private Collector theCollector;

    @Override
    public Collector fetches() {
        return theCollector;
    }

    @Override
    public void fetches(Collector theCollector) {
        this.theCollector=theCollector;
    }


    public SimpleSQLFinding(ResultSet aResultSet){
        this.resultSet=aResultSet;
        ToImage ti=new ToImage(resultSet);
        try {
            filename = ti.toImage();
            rsmd = resultSet.getMetaData();
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
        String resultString="Finding: \n";

        try {
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                resultString=resultString+rsmd.getColumnName(i) + " ";
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        resultString = resultString + "\n";
        ResultSetIterator rsit=new ResultSetIterator(resultSet);
        while(rsit.hasNext()){
            Object[] tab=rsit.next();
            for(int i=0;i<tab.length;i++) {
                if(tab[i]!=null)
                    resultString = resultString + tab[i].toString();
                else
                    resultString = resultString + " ";
                resultString = resultString + " ";
            }
            resultString = resultString + "\n";
        }
        return resultString;
    }






}
