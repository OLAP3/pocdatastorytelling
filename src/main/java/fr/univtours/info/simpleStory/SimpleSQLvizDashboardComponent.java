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
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

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


                toBarChartImage();
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



    public void toBarChartImage() throws Exception{

            this.metaData = resultSet.getMetaData();

            if (metaData.getColumnCount() == 3) {
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

                int width = 640;    /* Width of the image */
                int height = 480;   /* Height of the image */
                filename = "public/img/BarChart-" + nbCharts + ".jpeg";
                File BarChart = new File(filename);

                ChartUtilities.saveChartAsJPEG(BarChart, barChart, width, height);
                this.nbCharts++;
            }





    }


}
