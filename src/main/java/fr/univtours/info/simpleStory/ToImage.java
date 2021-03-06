package fr.univtours.info.simpleStory;

import org.apache.commons.dbutils.ResultSetIterator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.data.xy.DefaultXYZDataset;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class ToImage {
    private static int nbCharts=0;

    private String filename=null;
    private ResultSet resultSet;
    private ResultSetMetaData metaData;
    String rangeAxisLabel;
    String domainAxisLabel;

    public ToImage(ResultSet theResultset){
        this.resultSet=theResultset;
    }


    // measures should be first
    public String toImage() throws Exception{
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
            //System.out.println(metaData.getColumnType(1));
            //System.out.println(metaData.getColumnType(2));
            toPieChartImage();
        }
        if (metaData.getColumnCount() == 4
        && metaData.getColumnName(3).compareTo("longitude")==0
        && metaData.getColumnName(4).compareTo("latitude")==0) { // and maybe also column names include longitude and latitude
            toWorldMapImage();
        }

        return filename;
    }

    // 3 columns: measure, dim1, dim2
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
        double measure[] = new double[size];
        double longitude[] = new double[size];
        double latitude[] = new double[size];
        int i=0;
        ResultSetIterator rsit = new ResultSetIterator(resultSet);
        while (rsit.hasNext()) {
            Object[] tab = rsit.next();
            //System.out.println(tab[0]);
            if(tab[0]!=null &&
                    tab[2]!=null &&
                    tab[3]!=null){
                measure[i]=Double.parseDouble(tab[0].toString());
                if(measure[i]<100) measure[i] = 1;
                if(measure[i]>=100 && measure[i]<1000) measure[i] = 2;
                if(measure[i]>=1000 && measure[i]<10000) measure[i] = 3;
                if(measure[i]>=10000 && measure[i]<100000) measure[i] = 4;
                if(measure[i]>=100000 ) measure[i] = 5;
                longitude[i]=Double.parseDouble(tab[2].toString());
                latitude[i]=Double.parseDouble(tab[3].toString());
            }

            i++;
        }
        // fake origin point for testing
        //measure[0]=3;
        //longitude[0]=0;
        //latitude[0]=0;
        double ad3[ ][ ] = {  latitude, longitude, measure };
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

//        Image icon = ImageIO.read(new File("public/img/map.gif"));
        Image icon = ImageIO.read(new File("public/img/map-grid.png"));
        //bubblechart.setBackgroundImage(icon);

        NumberAxis domainAxis = ( NumberAxis )xyplot.getDomainAxis( );
        domainAxis.setRange(-96,90);
        //domainAxis.setVisible(false);
        NumberAxis rangeAxis = ( NumberAxis )xyplot.getRangeAxis( );
        rangeAxis.setRange(-200,206);
        //rangeAxis.setVisible(false);

        //xyplot.setBackgroundImage(icon);
        bubblechart.removeLegend();
        bubblechart.setBackgroundImage(icon);
        Color trans = new Color(0xFF, 0xFF, 0xFF, 0);
        xyplot.setBackgroundPaint( trans );
        xyplot.setOutlineVisible(false);

        xyplot.setRangeGridlinesVisible(false);
        xyplot.setDomainGridlinesVisible(false);

        int width = 900;    /* Width of the image */
        int height = 500;   /* Height of the image */
        filename = "public/img/bubble-worldmap-" + nbCharts + ".png";
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


        Plot plot = barChart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);

        int width = 640;    /* Width of the image */
        int height = 480;   /* Height of the image */
        filename = "public/img/PieChart-" + nbCharts + ".png";
        File pieChart = new File(filename);

        ChartUtilities.saveChartAsPNG(pieChart, barChart, width, height);
        this.nbCharts++;

    }

    // TODO for 1 measure, 1 tuple
    public void toMeterChart() throws Exception{
        DefaultValueDataset dataset=new DefaultValueDataset();

        resultSet.beforeFirst();
        ResultSetIterator rsit = new ResultSetIterator(resultSet);
        while (rsit.hasNext()) {
            Object[] tab = rsit.next();
            dataset.setValue(Double.parseDouble(tab[0].toString()));
        }


        MeterPlot meterplot = new MeterPlot(dataset);
        //set minimum and maximum value
        meterplot.setRange(new Range(0.0D, 100000D));

        meterplot.addInterval(new MeterInterval("LOW", new Range(0.0D, 10000D),
                Color.red, new BasicStroke(2.0F), new Color(255, 0, 0, 128)));

        meterplot.addInterval(new MeterInterval("Moderate", new Range(10001D, 90000D),
                Color.yellow, new BasicStroke(2.0F), new Color(255, 255, 0, 64)));

        meterplot.addInterval(new MeterInterval("FULL", new Range(90001D, 100000D),
                Color.green, new BasicStroke(2.0F), new Color(0, 255, 0, 64)));

        meterplot.setNeedlePaint(Color.darkGray);
        meterplot.setDialBackgroundPaint(Color.white);
        meterplot.setDialOutlinePaint(Color.black);
        meterplot.setDialShape(DialShape.CHORD);
        meterplot.setMeterAngle(180);
        meterplot.setTickLabelsVisible(true);
        meterplot.setTickLabelFont(new Font("Arial", 1, 12));
        meterplot.setTickLabelPaint(Color.black);
        meterplot.setTickSize(5D);
        meterplot.setTickPaint(Color.gray);
        meterplot.setValuePaint(Color.black);
        meterplot.setValueFont(new Font("Arial", 1, 14));
        JFreeChart meterchart = new JFreeChart(metaData.getColumnName(1),
                JFreeChart.DEFAULT_TITLE_FONT, meterplot, true);


        int width = 640;    /* Width of the image */
        int height = 480;   /* Height of the image */
        filename = "public/img/MeterChart-" + nbCharts + ".png";
        File pieChart = new File(filename);

        ChartUtilities.saveChartAsPNG(pieChart, meterchart, width, height);
        this.nbCharts++;

    }

}
