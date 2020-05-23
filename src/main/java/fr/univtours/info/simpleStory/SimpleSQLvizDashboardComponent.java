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

public class SimpleSQLvizDashboardComponent implements DashboardComponent {
    Episode theEpisode;
    String theRendering;
    PDDocument document;
    PDPageContentStream contentStream;
    private static int nbCharts=0;
    private String filename=null;
    private ResultSetMetaData metaData;
    String rangeAxisLabel;
    String domainAxisLabel;
    private ResultSet resultSet;

    @Override
    public void renders(Episode anEpisode) {
        this.theEpisode=anEpisode;
    }

    @Override
    public Episode render() {
        Collection<Finding> theFindings=  theEpisode.narrates().produces();
        for(Finding f : theFindings){
            resultSet = ((SimpleSQLFinding) f).getResultSet();

            try {


                String text=theEpisode.toString();
                String[] toPrint = text.split("\n");
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);
                for (int x=0; x<toPrint.length; x++) {
                    contentStream.showText(toPrint[x]);
                    contentStream.newLine();
                }


                toImage();
                if(filename!=null){
                    contentStream.endText();
                    contentStream.close();
                    PDPage blankPage = new PDPage(); // each viz starts a new page
                    //Adding the blank page to the document
                    document.addPage(blankPage);
                    contentStream = new PDPageContentStream(document, blankPage);

                    PDImageXObject pdImage = PDImageXObject.createFromFile(filename,document);

                    contentStream.drawImage(pdImage, 50, 50, 400, 600);
                    contentStream.close();
                    blankPage = new PDPage(); // each act starts a new page
                    document.addPage(blankPage);
                    contentStream = new PDPageContentStream(document, blankPage);
                    contentStream.beginText();
                    //Setting the leading
                    contentStream.setLeading(14.5f);
                    //Setting the position for the line
                    contentStream.newLineAtOffset(25, 725);
                }
                contentStream.endText();
                contentStream.close();

            }
            catch (Exception e){
                e.printStackTrace();
            }


        }


        return theEpisode;
    }

    public String getRendering(){
        theRendering = theEpisode.toString();
        return theRendering;
    }


    public void setPDF(PDDocument pdf) {
        document=pdf;
    }

    public void setContentStream(PDPageContentStream contentStream){
        this.contentStream=contentStream;
    }

    // measures should be first
    public void toImage() throws Exception{
        this.metaData = resultSet.getMetaData();
        /*
        if(metaData.getColumnCount() == 3){
            toLineChart();
        }

         */
        if (metaData.getColumnCount() == 3) {
            toBarChartImage();
        }
        if (metaData.getColumnCount() == 2) {
            toPieChartImage();
        }
        if (metaData.getColumnCount() == 4) {
            toWorldMapImage();
        }
    }


    public void toBarChartImage() throws Exception{

                rangeAxisLabel = metaData.getColumnName(1);
                domainAxisLabel = metaData.getColumnName(3);

                final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                //dataset.addValue( 1.0 , fiat , speed );

                resultSet.beforeFirst();

                ResultSetIterator rsit = new ResultSetIterator(resultSet);
                while (rsit.hasNext()) {
                    Object[] tab = rsit.next();
                    System.out.println(Double.parseDouble(tab[0].toString()) +  tab[1].toString() + tab[2].toString());
                    dataset.addValue(Double.parseDouble(tab[0].toString()), tab[1].toString(), tab[2].toString());
                }


                JFreeChart barChart = ChartFactory.createStackedBarChart(
                        "",
                        domainAxisLabel, rangeAxisLabel,
                        dataset, PlotOrientation.VERTICAL,
                        true, true, false);

                Plot plot = barChart.getPlot();
                plot.setBackgroundPaint(Color.WHITE);


                int width = 640;    /* Width of the image */
                int height = 480;   /* Height of the image */
                filename = "public/img/BarChart-" + nbCharts + ".png";
                File BarChart = new File(filename);

                ChartUtilities.saveChartAsPNG(BarChart, barChart, width, height);
                this.nbCharts++;

    }





    public void toWorldMapImage() throws Exception {

        DefaultXYZDataset defaultxyzdataset = new DefaultXYZDataset( );
        //double ad[ ] = { 30 , 40 , 50 , 60 , 70 , 80 };
        //double ad1[ ] = { 10 , 20 , 30 , 40 , 50 , 60 };
        //double ad2[ ] = { 4 , 5 , 10 , 8 , 9 , 6 };
        //double ad3[ ][ ] = { ad , ad1 , ad2 };
        //defaultxyzdataset.addSeries( "Series 1" , ad3 );



        resultSet.last();    // moves cursor to the last row
        int size = resultSet.getRow();
        resultSet.beforeFirst();
        double cases[] = new double[size];
        double longitude[] = new double[size];
        double latitude[] = new double[size];
        int i=0;
        ResultSetIterator rsit = new ResultSetIterator(resultSet);
        while (rsit.hasNext()) {
            Object[] tab = rsit.next();
            cases[i]=Double.parseDouble(tab[0].toString())/100000; // /100000 for scaling
            longitude[i]=Double.parseDouble(tab[2].toString());
            latitude[i]=Double.parseDouble(tab[3].toString());
            i++;
        }

        double ad3[ ][ ] = { longitude , latitude, cases };
        defaultxyzdataset.addSeries( metaData.getColumnName(1) , ad3 );

        JFreeChart bubblechart = ChartFactory.createBubbleChart(
                "",
                "",
                "",
                defaultxyzdataset,
                PlotOrientation.HORIZONTAL,
                true, true, false);

        XYPlot xyplot = ( XYPlot )bubblechart.getPlot( );
        //xyplot.setForegroundAlpha( 0.65F );
        XYItemRenderer xyitemrenderer = xyplot.getRenderer( );
        xyitemrenderer.setSeriesPaint( 0 , Color.red );
        //NumberAxis numberaxis = ( NumberAxis )xyplot.getDomainAxis( );
        //numberaxis.setLowerMargin( 0.2 );
        //numberaxis.setUpperMargin( 0.5 );
        //NumberAxis numberaxis1 = ( NumberAxis )xyplot.getRangeAxis( );
        //numberaxis1.setLowerMargin( 0.8 );
        //numberaxis1.setUpperMargin( 0.9 );

        Image icon = ImageIO.read(new File("public/img/map.gif"));
        bubblechart.setBackgroundImage(icon);

        //axis.setVisible(false);

        //xyplot.setBackgroundImage(icon);
        bubblechart.removeLegend();
        bubblechart.setBackgroundImage(icon);
        Color trans = new Color(0xFF, 0xFF, 0xFF, 0);
        xyplot.setBackgroundPaint( trans );
        xyplot.setOutlineVisible(false);

        //plot.setRangeGridlinesVisible(false);
        //plot.setDomainGridlinesVisible(false);

        int width = 800;    /* Width of the image */
        int height = 500;   /* Height of the image */
        filename = "public/img/BarChart-" + nbCharts + ".png";
        File BarChart = new File(filename);

        ChartUtilities.saveChartAsPNG(BarChart, bubblechart, width, height);
        this.nbCharts++;



    }


        public void toPieChartImage() throws Exception{

        rangeAxisLabel = metaData.getColumnName(1);
        domainAxisLabel = metaData.getColumnName(2);

        final DefaultPieDataset dataset = new DefaultPieDataset( );
        //dataset.addValue( 1.0 , fiat , speed );

        resultSet.beforeFirst();

        ResultSetIterator rsit = new ResultSetIterator(resultSet);
        while (rsit.hasNext()) {
            Object[] tab = rsit.next();
           // System.out.println(Double.parseDouble(tab[0].toString()) +  tab[1].toString() + tab[2].toString());
            dataset.setValue(tab[1].toString(), Double.parseDouble(tab[0].toString()));
        }


        JFreeChart barChart = ChartFactory.createPieChart(
                "",
              //  domainAxisLabel, rangeAxisLabel,
                dataset,
                //PlotOrientation.VERTICAL,
                true, true, false);

        int width = 640;    /* Width of the image */
        int height = 480;   /* Height of the image */
        filename = "public/img/PieChart-" + nbCharts + ".png";
        File pieChart = new File(filename);

        ChartUtilities.saveChartAsPNG(pieChart, barChart, width, height);
        this.nbCharts++;

    }


}
