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

public abstract class PDFdashboard implements Dashboard {
    Act theAct;
    Collection<DashboardComponent> theComponents;
    String theRendering="";
    PDDocument document;

    public PDFdashboard(){
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

      public void setPDF(PDDocument pdf) {
        document=pdf;
    }

    public String getRendering(){
        return theRendering;
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

}
