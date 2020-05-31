package fr.univtours.info.simpleStory;

import fr.univtours.info.model.Structural.Act;
import fr.univtours.info.model.Structural.Episode;
import fr.univtours.info.model.factual.Finding;
import fr.univtours.info.model.presentational.*;
import fr.univtours.info.model.Structural.Plot;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;


import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

public class SimpleVisualNarrative extends PDFnarrative {

    public SimpleVisualNarrative(){
        super();
    }

    @Override
    public void renders(Plot aPlot) {
        // this is where the visual story creates its component before rendering
        thePlot = aPlot;

        for(Act act : thePlot.includes()){
            Dashboard d = new SimpleDashboard();
            this.contains(d);
            d.renders(act);


            for(Episode ep : act.includes()){
                    // this is the place to choose between different components
                boolean hasGraphic=false;
                for(Finding i : ep.narrates().produces()){
                    if(i.getClass().getName().equals("fr.univtours.info.simpleStory.SimpleDescribeFinding")){
                        hasGraphic=true;
                    }
                }
                if(hasGraphic){
                    DashboardComponent dbc = new SimpleDescribeDashboardComponent();
                    d.contains(dbc);
                    dbc.renders(ep);

                }
                else {
                    //DashboardComponent dbc = new SimpleDashboardComponent();
                    DashboardComponent dbc = new SimpleSQLvizDashboardComponent();
                    d.contains(dbc);
                    dbc.renders(ep);
                }

            }

            //d.renders(act);
        }

    }




    @Override
    public Plot renders() {
        try {
            beginDocument();

            // prints title (goal)

            addString("This is the story for goal: " + thePlot.has().toString(), 16, PDType1Font.TIMES_BOLD, width);

            //contentStream.newLine();
            //contentStream.newLine();
            //contentStream.endText();
            //contentStream.close();



            for(Dashboard d : theDashboards){
                ((SimpleDashboard) d).setPDF(document);
                ((SimpleDashboard) d).setNarrative(this);
                d.renders();
                //theRendering=theRendering + ((SimpleDashboard) d).getRendering() + "\n";
            }

           endDocument();
        }
        catch (Exception e){
            e.printStackTrace();
        }


        return thePlot;
    }



}
