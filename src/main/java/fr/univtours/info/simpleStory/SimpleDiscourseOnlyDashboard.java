package fr.univtours.info.simpleStory;

import fr.univtours.info.model.Structural.Act;
import fr.univtours.info.model.presentational.DashboardComponent;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class SimpleDiscourseOnlyDashboard extends PDFdashboard {


    public SimpleDiscourseOnlyDashboard(){
        super();
    }


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
            contentStream.showText(theAct.getText() );
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
                if(dc.getClass().getName().equals("fr.univtours.info.simpleStory.SimpleDiscourseOnlySQLvizDashboardComponent")){
                    ((SimpleDiscourseOnlySQLvizDashboardComponent) dc).setPDF(document);
                    ((SimpleDiscourseOnlySQLvizDashboardComponent) dc).setContentStream(contentStream);
                    dc.render();

                }
                else {// describe component
                    //contentStream.endText();
                    //contentStream.close();
                    ((SimpleDiscourseOnlyDescribeDashboardComponent) dc).setPDF(document);
                    ((SimpleDiscourseOnlyDescribeDashboardComponent) dc).setContentStream(contentStream);

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


}
