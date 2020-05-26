package fr.univtours.info.simpleStory;

import fr.univtours.info.model.Structural.Act;
import fr.univtours.info.model.Structural.Episode;
import fr.univtours.info.model.Structural.Plot;
import fr.univtours.info.model.factual.Finding;
import fr.univtours.info.model.presentational.Dashboard;
import fr.univtours.info.model.presentational.DashboardComponent;
import fr.univtours.info.model.presentational.VisualNarrative;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.util.ArrayList;
import java.util.Collection;

public abstract class PDFnarrative implements VisualNarrative {

    Collection<Dashboard> theDashboards;
    Plot thePlot;
    String theRendering="";
    PDDocument thePDF;

    public PDFnarrative(){
        theDashboards = new ArrayList<Dashboard>();
    }

    public PDDocument getThePDF(){
        return thePDF;
    }

    @Override
    public String toString() {
        return theRendering;
    }

    @Override
    public void contains(Dashboard aDashboard) {
        theDashboards.add(aDashboard);
    }

    @Override
    public Collection<Dashboard> contains() {
        return theDashboards;
    }




       /*
        @Override
        public Story renders() {
            theRendering= "This is the story for goal: " + theStory.has().toString() + "\n";
            for(Dashboard d : theDashboards){
                d.renders();
                theRendering=theRendering + ((SimpleDashboard) d).getRendering() + "\n";
            }
            try {
                PDDocument pdf = createPDF();
                this.thePDF=pdf;
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return theStory;
        }



    public PDDocument createPDF() throws IOException {
        //Creating PDF document object
        PDDocument document = new PDDocument();

        System.out.println("PDF created");

        for (int i=0; i<1; i++) {
            //Creating a blank page
            PDPage blankPage = new PDPage();
            //Adding the blank page to the document
            document.addPage( blankPage );

            PDPageContentStream contentStream = new PDPageContentStream(document, blankPage);
            contentStream.beginText();
            //Setting the leading
            contentStream.setLeading(14.5f);
            //Setting the position for the line
            contentStream.newLineAtOffset(25, 725);


            String text=theRendering;

            String[] toPrint = text.split("\n");

            // prints title (goal)
            contentStream.setFont(PDType1Font.TIMES_BOLD, 16);
            contentStream.showText(toPrint[0]);
            contentStream.newLine();
            contentStream.newLine();

            contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);
            for (int x=1; x<toPrint.length; x++){

               if(toPrint[x].startsWith("Act")){ // this should go in dashboard, etc.
                   contentStream.endText();
                   contentStream.close();
                   blankPage = new PDPage(); // each act starts a new page
                   //Adding the blank page to the document
                   document.addPage( blankPage );
                   contentStream = new PDPageContentStream(document, blankPage);
                   contentStream.beginText();
                   //Setting the leading
                   contentStream.setLeading(14.5f);
                   //Setting the position for the line
                   contentStream.newLineAtOffset(25, 725);
                   contentStream.setFont(PDType1Font.TIMES_BOLD_ITALIC, 12);
                   contentStream.showText(toPrint[x]);
                   contentStream.newLine();
                   contentStream.newLine();

               }else {
                   if(toPrint[x].startsWith("Message: data")) {// means image
                       contentStream.endText();
                       contentStream.close();
                       blankPage = new PDPage(); // each act starts a new page
                       //Adding the blank page to the document
                       document.addPage( blankPage );
                       contentStream = new PDPageContentStream(document, blankPage);


                           int index = toPrint[x].indexOf(',');

                           if (index != -1) toPrint[x] = toPrint[x].substring(index + 1);

                           byte[] bytes = Base64.getDecoder().decode(new String(toPrint[x]).getBytes("UTF-8"));
                       //System.out.println(bytes);
                       PDImageXObject pdImage = PDImageXObject.createFromByteArray(document,bytes,"insight");

                       contentStream.drawImage(pdImage, 50, 50,400,600);
                       contentStream.close();
                       blankPage = new PDPage(); // each act starts a new page
                       //Adding the blank page to the document
                       document.addPage( blankPage );
                       contentStream = new PDPageContentStream(document, blankPage);
                       contentStream.beginText();
                       //Setting the leading
                       contentStream.setLeading(14.5f);
                       //Setting the position for the line
                       contentStream.newLineAtOffset(25, 725);
                   }
                else{
                       contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);
                       contentStream.showText(toPrint[x]);
                       contentStream.newLine();
                   }

               }
            }

            contentStream.endText();
            System.out.println("Content added");
            contentStream.close();

        }




        //Saving the document
        document.save("/Users/marcel/Documents/RECHERCHE/STUDENTS/Faten/pocdatastory/public/pdfs/test.pdf");

        //Closing the document
        document.close();
        return document;
    }

*/

}
