package fr.univtours.info.simpleStory;

import fr.univtours.info.model.Structural.Episode;
import fr.univtours.info.model.presentational.DashboardComponent;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.util.Base64;

public class SimpleDiscourseOnlyDescribeDashboardComponent extends PDFdashboardComponent {


    byte[] theGraphic;
    String theStringGraphic;


    @Override
    public Episode render() {
        try {
            //String text=theEpisode.toString();
            String text=theEpisode.getText() + "\n" + theEpisode.narrates().toString() ;
            String[] toPrint = text.split("\n");

            for (int x=0; x<toPrint.length; x++) {
                if(toPrint[x].startsWith("Message: data")) {
                    contentStream.endText();
                    contentStream.close();
                    PDPage blankPage = new PDPage(); // each act starts a new page
                    //Adding the blank page to the document
                    document.addPage(blankPage);
                    contentStream = new PDPageContentStream(document, blankPage);


                    int index = toPrint[x].indexOf(',');

                    if (index != -1) toPrint[x] = toPrint[x].substring(index + 1);

                    byte[] bytes = Base64.getDecoder().decode(new String(toPrint[x]).getBytes("UTF-8"));
                    //System.out.println(bytes);
                    PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, bytes, "insight");

                    contentStream.drawImage(pdImage, 50, 400, 400, 500);
                    contentStream.close();
                    blankPage = new PDPage(); // each act starts a new page
                    document.addPage(blankPage);
                    contentStream = new PDPageContentStream(document, blankPage);
                    contentStream.beginText();
                    //Setting the leading
                    contentStream.setLeading(14.5f);
                    //Setting the position for the line
                    contentStream.newLineAtOffset(25, 725);
                }
                else{// Episode: text AND THEN message

                    contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);
                    contentStream.showText(toPrint[x]);
                    contentStream.newLine();
                }



            }
            contentStream.endText();
            contentStream.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return theEpisode;
    }


    public byte[] getGraphic(){
        return theGraphic;
    }

    public String getTheStringGraphic() {
        return theStringGraphic;
    }

}
