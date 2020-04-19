package fr.univtours.info.simpleStory;

import fr.univtours.info.model.discursal.Act;
import fr.univtours.info.model.discursal.Episode;
import fr.univtours.info.model.presentational.*;
import fr.univtours.info.model.discursal.Story;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class SimpleVisualStory implements VisualStory {
    Collection<Dashboard> theDashboards;
    Story theStory;
    String theRendering="";

    public SimpleVisualStory(){
        theDashboards = new ArrayList<Dashboard>();
    }

    @Override
    public void print() {
        System.out.println(theRendering);
    }

    @Override
    public void renders(Story aStory) {
        // this is where the visual story creates its component before rendering
        theStory=aStory;

        for(Act act : theStory.includes()){
            Dashboard d = new SimpleDashboard();
            this.contains(d);
            d.renders(act);


            for(Episode ep : act.includes()){
                DashboardComponent dbc = new SimpleDashboardComponent();
                d.contains(dbc);
                dbc.renders(ep);
            }


        }

    }

    @Override
    public void contains(Dashboard aDashboard) {
        theDashboards.add(aDashboard);
    }

    @Override
    public Collection<Dashboard> contains() {

        return theDashboards;
    }

    @Override
    public Story renders() {
        for(Dashboard d : theDashboards){
            d.renders();
            theRendering=theRendering + ((SimpleDashboard) d).getRendering();
        }
        try {
            PDDocument pdf = createPDF();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return theStory;
    }


    public PDDocument createPDF() throws IOException {
        //Creating PDF document object
        PDDocument document = new PDDocument();

        //Saving the document
        document.save("");

        System.out.println("PDF created");

        PDPage my_page = new PDPage();
        document.addPage(my_page);

        PDPageContentStream contentStream = new PDPageContentStream(document, my_page);
        //Begin the Content stream
        contentStream.beginText();

        //Setting the font to the Content stream
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);

        //Setting the position for the line
        contentStream.newLineAtOffset(25, 500);



        //Adding text in the form of string
        contentStream.showText(theRendering);

        //Ending the content stream
        contentStream.endText();

        System.out.println("Content added");

        //Closing the content stream
        contentStream.close();

        //Saving the document
        document.save("");

        //Closing the document
        document.close();
        return document;
    }



}
