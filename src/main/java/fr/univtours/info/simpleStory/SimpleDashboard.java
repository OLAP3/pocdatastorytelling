package fr.univtours.info.simpleStory;

import fr.univtours.info.model.Structural.Act;
import fr.univtours.info.model.presentational.Dashboard;
import fr.univtours.info.model.presentational.DashboardComponent;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.util.ArrayList;
import java.util.Collection;

public class SimpleDashboard implements Dashboard {
    Act theAct;
    Collection<DashboardComponent> theComponents;
    String theRendering="";
    PDDocument document;

    public SimpleDashboard(){
        theComponents=new ArrayList<DashboardComponent>();
    }

    @Override
    public void renders(Act anAct) {
        this.theAct=anAct;
    }

    @Override
    public void contains(DashboardComponent aDashboardComponent) {
        theComponents.add(aDashboardComponent);
    }

    @Override
    public Collection<DashboardComponent> contains() {
        return theComponents;
    }

    /*
    @Override
    public Act renders() {
        theRendering = theRendering + theAct.toString() + "\n";
        for(DashboardComponent dc : theComponents){
            dc.render();
            theRendering=theRendering+ ((SimpleDashboardComponent) dc).getRendering();
        }
        return theAct;
    }
    */
    @Override
    public Act renders() {
        try {
            // render this
            PDPage blankPage = new PDPage(); // each act starts a new page
            //Adding the blank page to the document
            document.addPage(blankPage);
            PDPageContentStream contentStream = new PDPageContentStream(document, blankPage);
            contentStream.beginText();
            //Setting the leading
            contentStream.setLeading(14.5f);
            //Setting the position for the line
            contentStream.newLineAtOffset(25, 725);
            contentStream.setFont(PDType1Font.TIMES_BOLD_ITALIC, 12);
            contentStream.showText(theAct.toString() );
            contentStream.newLine();
            contentStream.newLine();

            // then render component
            // for all components -> render
            for(DashboardComponent dc : theComponents){
                // must have an abstract PDFDashComponent class

                /*
                if(dc.getClass().getName().equals("fr.univtours.info.simpleStory.SimpleDashboardComponent")){
                    ((SimpleDashboardComponent) dc).setPDF(document);
                    ((SimpleDashboardComponent) dc).setContentStream(contentStream);
                    dc.render();
                }*/
                if(dc.getClass().getName().equals("fr.univtours.info.simpleStory.SimpleSQLvizDashboardComponent")){
                    ((SimpleSQLvizDashboardComponent) dc).setPDF(document);
                    ((SimpleSQLvizDashboardComponent) dc).setContentStream(contentStream);
                    dc.render();

                }
                else {// describe component
                    //contentStream.endText();
                    //contentStream.close();
                    ((SimpleDescribeDashboardComponent) dc).setPDF(document);
                    ((SimpleDescribeDashboardComponent) dc).setContentStream(contentStream);

                    dc.render();
                    /*
                    blankPage = new PDPage(); // each act starts a new page
                    //Adding the blank page to the document
                    document.addPage(blankPage);
                    contentStream = new PDPageContentStream(document, blankPage);
                    contentStream.beginText();
                    //Setting the leading
                    contentStream.setLeading(14.5f);
                    //Setting the position for the line
                    contentStream.newLineAtOffset(25, 725);

                     */
                }
                //theRendering=theRendering+ ((SimpleDashboardComponent) dc).getRendering();

                blankPage = new PDPage(); // each act starts a new page
                //Adding the blank page to the document
                document.addPage(blankPage);
                contentStream = new PDPageContentStream(document, blankPage);
                contentStream.beginText();
                //Setting the leading
                contentStream.setLeading(14.5f);
                //Setting the position for the line
                contentStream.newLineAtOffset(25, 725);

            }

            contentStream.setFont(PDType1Font.TIMES_BOLD_ITALIC, 12);
            contentStream.showText("End of the story.");

            contentStream.endText();
            contentStream.close();

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return theAct;
    }


    public void setPDF(PDDocument pdf) {
        document=pdf;
    }


    public String getRendering(){
        return theRendering;
    }
}
