package fr.univtours.info.simpleStory;

import fr.univtours.info.model.Structural.Episode;
import fr.univtours.info.model.presentational.DashboardComponent;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class SimpleDashboardComponent implements DashboardComponent {
    Episode theEpisode;
    String theRendering;
    PDDocument document;
    PDPageContentStream contentStream;

    @Override
    public void renders(Episode anEpisode) {
        this.theEpisode=anEpisode;
    }

    @Override
    public Episode render() {
        try {
            String text=theEpisode.toString();
            String[] toPrint = text.split("\n");
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);
            for (int x=0; x<toPrint.length; x++) {
                contentStream.showText(toPrint[x]);
                contentStream.newLine();
            }
        }
        catch (Exception e){
            e.printStackTrace();
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
}
