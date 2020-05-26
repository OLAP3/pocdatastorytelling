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
                    if(i.getClass().getName().equals("fr.univtours.info.simpleStory.SimpleDiscourseOnlyDescribeFinding")){
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

            PDDocument document = new PDDocument();
            this.thePDF=document;
            System.out.println("PDF created");

            PDPage blankPage = new PDPage();
            document.addPage( blankPage );
            PDPageContentStream contentStream = new PDPageContentStream(document, blankPage);
            contentStream.beginText();
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(25, 725);

            // prints title (goal)
            contentStream.setFont(PDType1Font.TIMES_BOLD, 16);
            contentStream.showText(thePlot.has().toString() );
            contentStream.newLine();
            contentStream.newLine();
            contentStream.endText();
            contentStream.close();

            for(Dashboard d : theDashboards){
                ((SimpleDiscourseOnlyDashboard) d).setPDF(document);
                d.renders();

            }

            //contentStream.endText();
            System.out.println("Content added");
            //contentStream.close();
            //Saving the document
            //ZonedDateTime dateTime = ZonedDateTime.now();
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            //String ts=formatter.toString();
            document.save("/Users/marcel/Documents/RECHERCHE/STUDENTS/Faten/pocdatastory/public/pdfs/data-narrative.pdf");

            //Closing the document
            document.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }


        return thePlot;
    }



}
