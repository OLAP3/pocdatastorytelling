package fr.univtours.info.simpleStory;

import fr.univtours.info.model.Structural.Episode;
import fr.univtours.info.model.presentational.DashboardComponent;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.util.Base64;

public class SimpleDescribeDashboardComponent extends PDFdashboardComponent {

    byte[] theGraphic;
    String theStringGraphic;




    @Override
    public Episode render() {
        try {
            String text=theEpisode.toString();
            String[] toPrint = text.split("\n");

            for (int x=0; x<toPrint.length; x++) {
                if(toPrint[x].startsWith("Message: data")) {

                    narrative.addDescribeImage(toPrint[x]);
                }
                else{// Episode: text AND THEN message
                    narrative.addString(toPrint[x],10,PDType1Font.TIMES_ROMAN,narrative.getWidth());

                    //contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);
                    //contentStream.showText(toPrint[x]);
                    //contentStream.newLine();
                }



            }
            //contentStream.endText();
            //contentStream.close();
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
