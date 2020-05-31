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

public class SimpleDashboard extends PDFdashboard {


    public SimpleDashboard(){

        super();
    }


    @Override
    public Act renders() {
        try {
            // render this

            //beginAct();
            //contentStream.showText(theAct.toString() );

            narrative.addPage();
            narrative.addString(theAct.toString() ,12, PDType1Font.TIMES_BOLD_ITALIC, narrative.getWidth());


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
                    ((SimpleSQLvizDashboardComponent) dc).setNarrative(narrative);
                    ((SimpleSQLvizDashboardComponent) dc).setPDF(document);
                    ((SimpleSQLvizDashboardComponent) dc).setContentStream(contentStream);
                    dc.render();

                }
                else {// describe component

                    ((SimpleDescribeDashboardComponent) dc).setNarrative(narrative);
                    ((SimpleDescribeDashboardComponent) dc).setPDF(document);
                    ((SimpleDescribeDashboardComponent) dc).setContentStream(contentStream);

                    dc.render();

                }

            }

            //contentStream.endText();

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return theAct;
    }


}
