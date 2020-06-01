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
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

public abstract class PDFnarrative implements VisualNarrative {

    Collection<Dashboard> theDashboards;
    Plot thePlot;
    String theRendering="";
    PDDocument thePDF;
    PDDocument document;
    PDPage blankPage; //current page
    PDPageContentStream contentStream; //current stream
    float margin;

    public float getWidth() {
        return width;
    }

    float width;
    float startX ;
    float startY ;
    float yOffset ;
    float leading;



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



    void beginDocument() throws Exception{
        document = new PDDocument();
        this.thePDF=document;
        System.out.println("PDF created");

        blankPage = new PDPage();
        document.addPage( blankPage );
        contentStream = null;

        float fontSize = 10;
        leading = 1.5f*fontSize;
        PDRectangle mediabox = blankPage.getMediaBox();
        margin = 75;
        width = mediabox.getWidth() - 2*margin;
        startX = mediabox.getLowerLeftX() + margin;
        startY = mediabox.getUpperRightY() - margin;
        yOffset = startY;

        contentStream = new PDPageContentStream(document, blankPage);

        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ITALIC, 10);
        contentStream.newLineAtOffset(startX, startY);
        yOffset-=leading;
        contentStream.showText("Created with POC data narrative");
        contentStream.newLineAtOffset(0, -leading);
        yOffset-=leading;
        //contentStream.endText();


        //addString("This is the story for goal: " + thePlot.has().toString(), 16, PDType1Font.TIMES_BOLD, width);
        //contentStream.beginText();
        //contentStream.setFont( PDType1Font.TIMES_BOLD, 16);
        //contentStream.showText("fsdgdfgdgfggddfg");

//        contentStream.setLeading(14.5f);
//        contentStream.newLineAtOffset(25, 725);

    }


    void endDocument() throws Exception{
        contentStream.endText();
        System.out.println("Content added");
        contentStream.close();
        //Saving the document
        //ZonedDateTime dateTime = ZonedDateTime.now();
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        //String ts=formatter.toString();
        document.save("/Users/marcel/Documents/RECHERCHE/STUDENTS/Faten/pocdatastory/public/pdfs/data-narrative.pdf");

        //Closing the document
        document.close();
    }




    void  addString(String theText, float fontSize, PDFont pdfFont, float width) throws IOException {
        List<String> lines =parseIndividualLines(theText,  fontSize,  pdfFont,  width);
        //leading = 1.5f*fontSize;
        //contentStream.beginText();
        contentStream.setFont(pdfFont, fontSize);

        for (String line:lines) {
            contentStream.showText(line);
            contentStream.newLineAtOffset(0, -leading);
            yOffset-=leading;

            if (yOffset <= 0) {
                contentStream.endText();
                if (contentStream != null) contentStream.close();

                blankPage = new PDPage();
                document.addPage(blankPage);
                contentStream = new PDPageContentStream(document, blankPage);
                contentStream.beginText();
                contentStream.setFont(pdfFont, fontSize);
                yOffset = startY;
                contentStream.newLineAtOffset(startX, startY);
            }
        }
        //contentStream.endText();


    }


    List<String>  parseIndividualLines(String theText, float fontSize, PDFont pdfFont, float width) throws IOException {

        StringBuffer pageText = new StringBuffer(theText);
        List<String> lines = new ArrayList<>();
        String[] paragraphs = pageText.toString().split(System.getProperty("line.separator"));
        for (int i = 0; i < paragraphs.length; i++) {
            int lastSpace = -1;
            lines.add(" ");
            while (paragraphs[i].length() > 0) {
                int spaceIndex = paragraphs[i].indexOf(' ', lastSpace + 1);
                if (spaceIndex < 0) {
                    spaceIndex = paragraphs[i].length();
                }
                String subString = paragraphs[i].substring(0, spaceIndex);
                float size = fontSize * pdfFont.getStringWidth(subString) / 1000;
                if (size > width) {
                    if (lastSpace < 0) {
                        lastSpace = spaceIndex;
                    }
                    subString = paragraphs[i].substring(0, lastSpace);
                    lines.add(subString);
                    paragraphs[i] = paragraphs[i].substring(lastSpace).trim();
                    lastSpace = -1;
                } else if (spaceIndex == paragraphs[i].length()) {
                    lines.add(paragraphs[i]);
                    paragraphs[i] = "";
                } else {
                    lastSpace = spaceIndex;
                }
            }
        }
        return lines;
    }


    void addPage() throws Exception{
        if (contentStream != null) contentStream.close();
        blankPage = new PDPage(); // each act starts a new page
        //Adding the blank page to the document
        document.addPage(blankPage);
        contentStream = new PDPageContentStream(document, blankPage);
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        yOffset = startY;
        contentStream.newLineAtOffset(startX, startY);
    }

    void addImage(String filename) throws Exception{
        contentStream.endText();
        //if (contentStream != null) contentStream.close();
        PDImageXObject pdImage = PDImageXObject.createFromFile(filename,document);

        float scale = width/pdImage.getWidth();
        yOffset-=(pdImage.getHeight()*scale);
        if (yOffset <= 0) {
            System.out.println("Starting a new page");

            if (contentStream != null) contentStream.close();

            blankPage  = new PDPage();
            document.addPage(blankPage);
            contentStream = new PDPageContentStream(document, blankPage);
            yOffset = startY-(pdImage.getHeight()*scale);
        }
        System.out.println("yOffset: " + yOffset);
        System.out.println("page width: " + width + " imageWidth: " + pdImage.getWidth() + " imageHeight: " + (pdImage.getHeight()*scale) + " scale: " + scale);

        contentStream.drawImage(pdImage, startX, yOffset, width, pdImage.getHeight()*scale);
        contentStream.beginText();


        //contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        //yOffset = startY;
        //contentStream.newLineAtOffset(startX, startY);

        /*
        //contentStream.endText();
        if (contentStream != null) contentStream.close();

        PDPage blankPage = new PDPage(); // each viz starts a new page
        //Adding the blank page to the document
        document.addPage(blankPage);
        contentStream = new PDPageContentStream(document, blankPage);

        PDImageXObject pdImage = PDImageXObject.createFromFile(filename,document);

        contentStream.drawImage(pdImage, 50, 200, 500, 400);
        contentStream.close();

        addPage();

        //Setting the leading
        //contentStream.setLeading(14.5f);
        //Setting the position for the line
        //contentStream.newLineAtOffset(25, 725);
    */
    }



    void addDescribeImage(String img) throws Exception{
        contentStream.endText();
        int index = img.indexOf(',');
        if (index != -1) img = img.substring(index + 1);
        byte[] bytes = Base64.getDecoder().decode(new String(img).getBytes("UTF-8"));

        PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, bytes, "insight");

        float scale = width/pdImage.getWidth();
        yOffset-=(pdImage.getHeight()*scale);
        if (yOffset <= 0) {
            System.out.println("Starting a new page");

            if (contentStream != null) contentStream.close();

            blankPage  = new PDPage();
            document.addPage(blankPage);
            contentStream = new PDPageContentStream(document, blankPage);
            yOffset = startY-(pdImage.getHeight()*scale);
        }
        System.out.println("yOffset: " + yOffset);
        System.out.println("page width: " + width + " imageWidth: " + pdImage.getWidth() + " imageHeight: " + (pdImage.getHeight()*scale) + " scale: " + scale);

        contentStream.drawImage(pdImage, startX, yOffset, width, pdImage.getHeight()*scale);
        contentStream.beginText();


        /*
        if (contentStream != null) contentStream.close();

        PDPage blankPage = new PDPage(); // each viz starts a new page
        //Adding the blank page to the document
        document.addPage(blankPage);
        contentStream = new PDPageContentStream(document, blankPage);


        int index = img.indexOf(',');

        if (index != -1) img = img.substring(index + 1);

        byte[] bytes = Base64.getDecoder().decode(new String(img).getBytes("UTF-8"));
        //System.out.println(bytes);
        PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, bytes, "insight");

        contentStream.drawImage(pdImage, 50, 200, 400, 500);
        contentStream.close();

        addPage();

         */
    }


}
