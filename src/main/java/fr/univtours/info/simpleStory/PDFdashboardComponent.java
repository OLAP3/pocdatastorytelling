package fr.univtours.info.simpleStory;

import fr.univtours.info.model.Structural.Episode;
import fr.univtours.info.model.presentational.DashboardComponent;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

public abstract class PDFdashboardComponent implements DashboardComponent {
    Episode theEpisode;
    String theRendering;
    PDDocument document;
    PDPageContentStream contentStream;

    public void setNarrative(PDFnarrative narrative) {
        this.narrative = narrative;
    }

    PDFnarrative narrative;

    @Override
    public void renders(Episode anEpisode) {
        this.theEpisode=  anEpisode;
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
