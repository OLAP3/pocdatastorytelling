package fr.univtours.info.simpleStory;

import fr.univtours.info.model.Structural.Episode;
import fr.univtours.info.model.factual.Finding;
import fr.univtours.info.model.presentational.DashboardComponent;
import org.apache.commons.dbutils.ResultSetIterator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.DefaultXYZDataset;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Base64;
import java.util.Collection;

public class SimpleSQLvizDashboardComponent extends PDFdashboardComponent {

    //private static int nbCharts=0;
    private String filename=null;
    //private ResultSetMetaData metaData;
    private ResultSet resultSet;



    @Override
    public Episode render() {
        Collection<Finding> theFindings=  theEpisode.narrates().produces();
        for(Finding f : theFindings){
            resultSet = ((SimpleSQLFinding) f).getResultSet();

            try {


                String text=theEpisode.toString();
                String[] toPrint = text.split("\n");

                for (int x=0; x<toPrint.length; x++) {
                    narrative.addString(toPrint[x],10,PDType1Font.TIMES_ROMAN,narrative.getWidth());
                }

                ToImage ti=new ToImage(resultSet);

                filename=ti.toImage();

                if(filename!=null){
                    narrative.addImage(filename);
                }
//

            }
            catch (Exception e){
                e.printStackTrace();
            }


        }


        return theEpisode;
    }




}
