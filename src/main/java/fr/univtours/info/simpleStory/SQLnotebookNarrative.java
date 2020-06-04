package fr.univtours.info.simpleStory;

import fr.univtours.info.model.Structural.Act;
import fr.univtours.info.model.Structural.Episode;
import fr.univtours.info.model.Structural.Plot;
import fr.univtours.info.model.factual.Finding;
import fr.univtours.info.model.presentational.Dashboard;
import fr.univtours.info.model.presentational.DashboardComponent;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.FileWriter;

public class SQLnotebookNarrative extends HTMLnarrative {
    int id;
    int nbCells=0;
    String theCells="";

    public SQLnotebookNarrative(){
        super();
    }

    @Override
    public void renders(Plot aPlot) {
        // this is where the visual story creates its component before rendering
        thePlot = aPlot;

        for(Act act : thePlot.includes()){
            Dashboard d = new SQLnotebookDashboard();
            this.contains(d);
            d.renders(act);

            nbCells++;

            for(Episode ep : act.includes()){
                    DashboardComponent dbc = new SQLnotebookDashboardComponent();
                    d.contains(dbc);
                    dbc.renders(ep);

            }

            //d.renders(act);
        }

    }




    @Override
    public Plot renders() {
        try {
            beginHTML();

           // addString("This is the story for goal: " + thePlot.has().toString(), 16, PDType1Font.TIMES_BOLD, width);

            String theCell="{\\\"rowId\\\":\\\"" +
                    getId()
                    +"\\\",\\\"items\\\":[{\\\"query\\\":\\\""
                    + replaceNL(thePlot.has().toString())
                    +"\\\",\\\"id\\\":\\\""
                    + getId()
                    +"\\\",\\\"loading\\\":false,\\\"markdown\\\":true}]}";


            addCell(theCell);


            for(Dashboard d : theDashboards){
                ((SQLnotebookDashboard) d).setNarrative(this);
                d.renders();
            }

            fw.write(theCells);

            endHTML();
        }
        catch (Exception e){
            e.printStackTrace();
        }


        return thePlot;
    }

    void beginHTML() throws Exception{
        f = new File(HTMLfile);
        fw= new FileWriter(f);
        fw.write(beginString);
    }

    void endHTML() throws Exception{
        fw.write("],\\\"forceRenderToken\\\":"+nbCells);
        fw.write(endString);
        fw.close();
    }


//{
//				rowId:lfyudsw,
//				items: [{
//				query:select sum(cases) as cases, continentexp, daterep \\nfrom covid\\ngroup by daterep , continentexp\\norder by daterep;,
//				id:nmsy3wl,
//				loading:false
//					}]
//				}
    void addCell(String theCell){
        if(id==2){// first cell added
            this.theCells+=theCell;
        }
        else{
            this.theCells+=","+theCell;
        }
        nbCells++;
    }


    String replaceNL(String param){
        return param.replace("\n","\\\\n");
    }

    int getId(){
        id=id+1;
        return id;
    }

    String beginString="<meta charset=\"utf-8\">\n" +
            "<title>Franchise Notebook {{notebook_name}}</title>\n" +
            "<!--\n" +
            " _______  ______ _______ __   _ _______ _     _ _____ _______ _______\n" +
            " |______ |_____/ |_____| | \\  | |       |_____|   |   |______ |______\n" +
            " |       |    \\_ |     | |  \\_| |_____  |     | __|__ ______| |______\n" +
            " __   _  _____  _______ _______ ______   _____   _____  _     _      \n" +
            " | \\  | |     |    |    |______ |_____] |     | |     | |____/       \n" +
            " |  \\_| |_____|    |    |______ |_____] |_____| |_____| |    \\_      \n" +
            "                                                                     \n" +
            "--> <script type=\"franchise_queries\">\n" +
            "========================================================\n" +
            "\n" +
            "{{notebook_contents}}\n" +
            "\n" +
            "========================================================\n" +
            "</script>\n" +
            "<style>\n" +
            "body {\n" +
            "  font-family: sans-serif;\n" +
            "  padding: 100px;\n" +
            "  background: #f7f7f7;\n" +
            "}\n" +
            "iframe {\n" +
            "  position: absolute;\n" +
            "  top: -10000px;\n" +
            "  left: -10000px;\n" +
            "}\n" +
            "a {\n" +
            "  font-size: large;\n" +
            "}\n" +
            "#click {\n" +
            "  background: #2257d9;\n" +
            "    padding: 20px;\n" +
            "    color: white;\n" +
            "    text-decoration: none;\n" +
            "    border-radius: 3px;\n" +
            "}\n" +
            "/*https://projects.lukehaas.me/css-loaders/*/\n" +
            ".loader,\n" +
            ".loader:after {\n" +
            "  border-radius: 50%;\n" +
            "  width: 10em;\n" +
            "  height: 10em;\n" +
            "}\n" +
            ".loader {\n" +
            "  margin: 60px auto;\n" +
            "  font-size: 10px;\n" +
            "  position: relative;\n" +
            "  text-indent: -9999em;\n" +
            "  border-top: 1.1em solid rgba(34,87,217, 0.2);\n" +
            "  border-right: 1.1em solid rgba(34,87,217, 0.2);\n" +
            "  border-bottom: 1.1em solid rgba(34,87,217, 0.2);\n" +
            "  border-left: 1.1em solid #2257d9;\n" +
            "  -webkit-transform: translateZ(0);\n" +
            "  -ms-transform: translateZ(0);\n" +
            "  transform: translateZ(0);\n" +
            "  -webkit-animation: load8 1.1s infinite linear;\n" +
            "  animation: load8 1.1s infinite linear;\n" +
            "}\n" +
            "@-webkit-keyframes load8 {\n" +
            "  0% {\n" +
            "    -webkit-transform: rotate(0deg);\n" +
            "    transform: rotate(0deg);\n" +
            "  }\n" +
            "  100% {\n" +
            "    -webkit-transform: rotate(360deg);\n" +
            "    transform: rotate(360deg);\n" +
            "  }\n" +
            "}\n" +
            "@keyframes load8 {\n" +
            "  0% {\n" +
            "    -webkit-transform: rotate(0deg);\n" +
            "    transform: rotate(0deg);\n" +
            "  }\n" +
            "  100% {\n" +
            "    -webkit-transform: rotate(360deg);\n" +
            "    transform: rotate(360deg);\n" +
            "  }\n" +
            "}\n" +
            "</style>\n" +
            "<div class=\"loader\" id=\"loader\">Loading Franchise...</div>\n" +
            "<center><a id=\"click\" style=\"display:none\" href=\"https://franchise.cloud/app/\" target=\"_blank\">Click here to open Franchise SQL Notebook</a></center>\n" +
            "<script type=\"text/javascript\">\n" +
            "const DATA = \"{\\\"state\\\":{\\\"config\\\":{},\\\"connect\\\":{\\\"active\\\":\\\"postgres\\\",\\\"status\\\":\\\"unconfigured\\\"},\\\"trash\\\":{\\\"open\\\":false,\\\"cells\\\":[]},\\\"deltas\\\":{\\\"open\\\":false,\\\"changes\\\":[]},\\\"notebook\\\":{\\\"layout\\\":[" ;



    //{{bin_data}}

    String endString="}},\\\"autoconnect\\\":false,\\\"version\\\":2}\";\n" +
            "</script>\n" +
            "<script type=\"text/javascript\">\n" +
            "function sendData(target, src){\n" +
            "  console.log(src)\n" +
            "  if(typeof DATA != 'undefined'){\n" +
            "    target.postMessage({\n" +
            "      importData: DATA,\n" +
            "      action: 'franchise-transfer-data'\n" +
            "    }, '*')    \n" +
            "  }\n" +
            "}\n" +
            "window.addEventListener(\"message\", function(e){\n" +
            "  console.log(e.data)\n" +
            "  if(e.data == 'franchise-request-import'){\n" +
            "    e.source.postMessage({ action: 'franchise-import', data: DATA }, '*')\n" +
            "  }else if(e.data.action === 'franchise-redirect'){\n" +
            "    location = e.data.url;\n" +
            "  }\n" +
            "}, false)\n" +
            "setTimeout(function(){\n" +
            "  if(typeof DATA != 'undefined'){\n" +
            "    document.getElementById('click').style.display = ''\n" +
            "    document.getElementById('loader').style.display = 'none'\n" +
            "  }\n" +
            "}, 1500);\n" +
            "</script>\n" +
            "<iframe src=\"http://localhost:3000/reciever.html\" onload=\"sendData(this.contentWindow, this.src)\"></iframe>\n" +
            "<iframe src=\"https://franchise.cloud/app/reciever.html\" onload=\"sendData(this.contentWindow, this.src)\"></iframe>";
}


