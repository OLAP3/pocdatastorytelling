package fr.univtours.info.simpleStory;

import fr.univtours.info.model.Structural.Act;
import fr.univtours.info.model.Structural.Episode;
import fr.univtours.info.model.Structural.Plot;
import fr.univtours.info.model.factual.Finding;
import fr.univtours.info.model.presentational.Dashboard;
import fr.univtours.info.model.presentational.DashboardComponent;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class SimpleDiscourseOnlyVisualNarrative extends PDFnarrative {

    public SimpleDiscourseOnlyVisualNarrative(){
        super();
    }

    @Override
    public void renders(Plot aPlot) {
        // this is where the visual story creates its component before rendering
        thePlot = aPlot;

        for(Act act : thePlot.includes()){
            Dashboard d = new SimpleDiscourseOnlyDashboard();
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
                    DashboardComponent dbc = new SimpleDiscourseOnlyDescribeDashboardComponent();
                    d.contains(dbc);
                    dbc.renders(ep);

                }
                else {
                    //DashboardComponent dbc = new SimpleDashboardComponent();
                    DashboardComponent dbc = new SimpleDiscourseOnlySQLvizDashboardComponent();
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

            // prints title goal
            /*contentStream.setFont(PDType1Font.TIMES_BOLD, 16);
            contentStream.showText(thePlot.has().toString() );
            contentStream.newLine();
            contentStream.newLine();
            contentStream.endText();
            contentStream.close();
            */
            addString(thePlot.has().toString(), 16, PDType1Font.TIMES_BOLD, width);



            // prints acts
            for(Dashboard d : theDashboards){
                ((SimpleDiscourseOnlyDashboard) d).setPDF(document);
                ((SimpleDiscourseOnlyDashboard) d).setNarrative(this);
                d.renders();

            }

            endDocument();
        }
        catch (Exception e){
            e.printStackTrace();
        }


        return thePlot;
    }



}
