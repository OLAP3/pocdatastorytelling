/*
    Global parameters here
 */
//const server_domain = "localhost:8080";
const server_domain = window.location.host;


var actcount=0;
var episodecount=0;
var messagecount=0;
var measurecount=0;
var charactercount=0;

function defaulterror(){
    let consoleElt=document.getElementById("console");
    consoleElt.innerText="Something wen wrong";
}

function clearStory() {

     let pb = function (result) {
        console.log("debug: ");
        console.log(result);
    };

    elsaRequest("clear", "clear", processClear, pb, false);
}

function clear(toClear){
    console.log(toClear);
    console.log(document.getElementById(toClear));
    let recap=document.getElementById(toClear);
      console.log(recap);

    if(document.getElementById(toClear)!=null){
        let recaptext = document.getElementById(toClear).value;
        recap.value= "";
        }
}



function processClear (result, endpoint) {
        let code=JSON.parse(result).code;
        let message=JSON.parse(result).message;
        console.log(code);
        console.log(message);

        if(code==0){

         //   let recap=document.getElementById("recap");
         //   let recaptext = document.getElementById("recap").value;
         //   recap.value= "";

         clear("recap");
         $("#listrecap").html("");
          $("#listrecapintentions").html("");;
         clear("goal");
         clear("question");
         clear("observation");
         clear("textresult");
         clear("protagonist");
         clear("message");
         clear("episode");
         clear("act");
         //clear("my_dataviz");
         clearSession();


         actcount=0;
         episodecount=0;
         messagecount=0;
        measurecount=0;
        charactercount=0;

         let consoleElt=document.getElementById("console");
         consoleElt.innerText=message;

        }
}



function processEpisodeClear (result, endpoint) {
        let code=JSON.parse(result).code;
        let message=JSON.parse(result).message;
        console.log(code);
        console.log(message);

       if(code==0){
            let recap=document.getElementById("recap");
            let recaptext = document.getElementById("recap").value;

            episodecount++;
            currentcount=episodecount;
            let button = document.createElement("button");
            button.innerHTML = "episode" + " " + episodecount;

            let body = document.getElementById("listrecap");
            body.appendChild(button);

            button.addEventListener ("click", function() {
                let text=button.innerText;
                console.log(text);
                let pos=text.indexOf(" ");
                let count=text.substr(pos+1);
                console.log(count);
                recallEpisode(parseInt(count));

            });

//            recap.value= recaptext + "\n " + endpoint + ": "+ JSON.parse(message);
            recap.value= recaptext + "\n " + JSON.parse(message);
            clear("observation");
            clear("textresult");
            clear("protagonist");
            clear("message");
            clear("episode");
            clear("my_dataviz");
            clearSession();
       }
       else{
            let consoleElt=document.getElementById("console");
            consoleElt.innerText=message;
       }
}


function processDBload(result, endpoint){

    console.log(result);
      // result contains the info to print for act, episode, measure, character and message
      // parse JSON, clear and update relevant areas
       let code=JSON.parse(result).code;
       let goal=JSON.parse(result).goal;
       let nbActs=JSON.parse(result).nbActs;
       let nbEpisodes=JSON.parse(result).nbEpisodes;

       if(code==0){
          document.getElementById("goal").value=goal;

          // list acts

          for(i=0; i<nbActs;i++){
            actcount++;
            currentcount=actcount;
            console.log(actcount);

            let button = document.createElement("button");
            button.innerHTML = "Act " + actcount;

            let body = document.getElementById("listrecap");
            body.appendChild(button);

            button.addEventListener ("click", function() {
                let text=button.innerText;
                console.log(text);
                let pos=text.indexOf(" ");
                let count=text.substr(pos+1);
                console.log(count);
                recallAct(parseInt(count));
            });

            //list episodes
          console.log(nbEpisodes[actcount-1]);
          for(j=0;j<nbEpisodes[actcount-1];j++){
          episodecount++;
                      currentcount=episodecount;
                      let button = document.createElement("button");
                      button.innerHTML = "episode" + " " + episodecount;

                      let body = document.getElementById("listrecap");
                      body.appendChild(button);

                      button.addEventListener ("click", function() {
                          let text=button.innerText;
                          console.log(text);
                          let pos=text.indexOf(" ");
                          let count=text.substr(pos+1);
                          console.log(count);
                          recallEpisode(parseInt(count));

                      });

          }
          }
        //  list characters and measures

        let listCharacters=JSON.parse(result).listCharacters;
        let listMeasures=JSON.parse(result).listMeasures;
        for(k=0;k<listCharacters.length;k++){
            let button = document.createElement("button");
            button.innerHTML = listCharacters[k];

            let body = document.getElementById("listrecapintentions");
            body.appendChild(button);

             button.addEventListener ("click", function() {
                let text=button.innerText;
                console.log(text);
                            // recall character as current character
                recallCharacter(text);
             });
        }
        for(l=0;l<listMeasures.length;l++){
            let button = document.createElement("button");
            button.innerHTML = listMeasures[l];

                            let body = document.getElementById("listrecapintentions");
                            body.appendChild(button);

                            button.addEventListener ("click", function() {
                            let text=button.innerText;
                            console.log(text);
                            // recall measure as current measure
                            recallMeasure(text);
                            });
        }
       }
       else{
          let consoleElt=document.getElementById("console");
          consoleElt.innerText="Something went wrong";
       }

       //recall episode 1
       recallEpisode(1);

}


function processDBsave(result, endpoint){
  let code=JSON.parse(result).code;
  let message=JSON.parse(result).message;
  console.log(code);
  console.log(message);
  let consoleElt=document.getElementById("console");
  consoleElt.innerText=message;
}


function processRender (result, endpoint) {
        let code=JSON.parse(result).code;
        let message=JSON.parse(result).message;
        console.log(code);
        console.log(message);

        let consoleElt=document.getElementById("console");
        consoleElt.innerText=JSON.parse(message);

        let pb = function (result) {
            console.log("debug: ");
            console.log(result);
        };

        //var file=new File("/Users/marcel/Documents/RECHERCHE/STUDENTS/Faten/pocdatastory/public/pdfs/data-narrative.pdf");
        //saveAs(file);
        //saveAs("pdfs/data-narrative.pdf","data-narrative.pdf",{type: "application/pdf"});
        window.open("pdfs/data-narrative.pdf");
}


function processRenderSQLNB (result, endpoint) {
        let code=JSON.parse(result).code;
        let message=JSON.parse(result).message;
        console.log(code);
        console.log(message);

        let consoleElt=document.getElementById("console");
        consoleElt.innerText=JSON.parse(message);

        let pb = function (result) {
            console.log("debug: ");
            console.log(result);
        };

        // import  html file in franchise if the application runs on the server
        // or if not save or open? + message in the console
        window.open("html/sql-data-narrative.html");

}



 function processControllerAnswer (result, endpoint) {
        let code=JSON.parse(result).code;
        let message=JSON.parse(result).message;
        console.log(code);
        console.log(message);

        if(code==0){
            let recap=document.getElementById("recap");
            let recaptext = document.getElementById("recap").value;

            //recap.innerText= recaptext + "\n " + endpoint + ": "+ message;
            //recap.value= recaptext + "\n " + endpoint + ": "+ JSON.parse(message);
            recap.value= recaptext + "\n" +  JSON.parse(message);

            if(endpoint=="act"){
                actcount++;
                currentcount=actcount;
                console.log(actcount);

                let button = document.createElement("button");
                button.innerHTML = endpoint + " " + actcount;

                let body = document.getElementById("listrecap");
                body.appendChild(button);

                button.addEventListener ("click", function() {
                    let text=button.innerText;
                    console.log(text);
                    let pos=text.indexOf(" ");
                    let count=text.substr(pos+1);
                    console.log(count);
                    recallAct(parseInt(count));
                });
            }
            /*
            if(endpoint=="message"){ // episode and message have the same count
                messagecount++;
                currentcount=actcount;
                let button = document.createElement("button");
                button.innerHTML = endpoint + " " + messagecount;

                let body = document.getElementById("listrecap");
                body.appendChild(button);

                button.addEventListener ("click", function() {
                let text=button.innerText;
                console.log(text);
                let pos=text.indexOf(" ");
                let count=text.substr(pos+1);
                console.log(count);
                recallEpisode(parseInt(count));
                });
            }
            */
            // for character, measure, we do not recall act/episode
            if(endpoint=="protagonist"){

                let button = document.createElement("button");
                button.innerHTML = JSON.parse(message);

                let body = document.getElementById("listrecapintentions");
                body.appendChild(button);

                button.addEventListener ("click", function() {
                let text=button.innerText;
                console.log(text);
                // recall character as current character
                recallCharacter(text);
                });
            }
            if(endpoint=="message"){

                let button = document.createElement("button");
                button.innerHTML = JSON.parse(message);

                let body = document.getElementById("listrecapintentions");
                body.appendChild(button);

                button.addEventListener ("click", function() {
                let text=button.innerText;
                console.log(text);
                // recall measure as current measure
                recallMeasure(text);
                });
            }
        }
        else{
            if(code==2){
            // reuse of existing character or measure
            console.log(code);
                let consoleElt=document.getElementById("console");
                consoleElt.innerText="Reusing " + JSON.parse(message);
            }
            else{
            console.log(code);
                let consoleElt=document.getElementById("console");
                consoleElt.innerText=message;
            }

        }
}



function recallCharacter(text){
    console.log(text);
    elsaRequest(text, "recallCharacter", displayCharacter, defaulterror,false);
}


function recallMeasure(text){
    console.log(text);
    elsaRequest(text, "recallMeasure", displayMeasure, defaulterror,false);
}





function displayCharacter(result){
    console.log(result);
    // result contains the info to print for act, episode, measure, character and message
    // parse JSON, clear and update relevant areas
     let code=JSON.parse(result).code;
     let character=JSON.parse(result).message;

     if(code==0){
        document.getElementById("protagonist").value=character;
     }
     else{
        let consoleElt=document.getElementById("console");
        consoleElt.innerText="Something went wrong";

     }
}




function displayMeasure(result){
    console.log(result);
    // result contains the info to print for act, episode, measure, character and message
    // parse JSON, clear and update relevant areas
     let code=JSON.parse(result).code;
     let measure=JSON.parse(result).message;

     if(code==0){
        document.getElementById("message").value=measure;
     }
     else{
        let consoleElt=document.getElementById("console");
        consoleElt.innerText="Something went wrong";

     }
}


function recallEpisode(count){
    console.log(count);
    elsaRequest(count, "recallEpisode", displayEpisode, defaulterror,false);
}


function recallAct(count){
    console.log(count);
    elsaRequest(count, "recallAct", displayEpisode, defaulterror,false);
}



function displayEpisode(result){
    console.log(result);
    // result contains the info to print for act, episode, measure, character and message
    // parse JSON, clear and update relevant areas
     let code=JSON.parse(result).code;
     let episode=JSON.parse(result).theEpisode;
     let act=JSON.parse(result).theAct;
     let message=JSON.parse(result).theMessage;
     let measure=JSON.parse(result).theMeasure;
     let character=JSON.parse(result).theCharacter;

     if(code==0){
        console.log(act);
        actelem=document.getElementById("act");
        actelem.value=act;
        document.getElementById("episode").value=episode;
        document.getElementById("observation").value=message;
        document.getElementById("protagonist").value=character;
        document.getElementById("message").value=measure;
     }
     else{
        let consoleElt=document.getElementById("console");
        consoleElt.innerText="Something went wrong";

     }
}


function processModificationAnswer(result, endpoint){
    let code=JSON.parse(result).code;
    let message=JSON.parse(result).message;
    console.log(code);
    console.log(message);

    // not sure, should we distinguish code=1 from code=0?
    let consoleElt=document.getElementById("console");
    consoleElt.innerText=message;

// clean everything after!!
     clear("observation");
     clear("textresult");
     clear("protagonist");
     clear("message");
     clear("episode");
     clear("my_dataviz");
}



function actModifHandler(){
    let result = document.getElementById("act");
    let selection = result.value;
    let msg = selection;

   let pb = function (result) {
        console.log("debug: ");
        console.log(result);
    };

    elsaRequest(msg, "modifyAct", processModificationAnswer, pb,false);

}



function episodeModifHandler(){
    let result = document.getElementById("episode");
    let selection = result.value;
    let msg = selection;

   let pb = function (result) {
        console.log("debug: ");
        console.log(result);
    };

    elsaRequest(msg, "modifyEpisode", processModificationAnswer, pb,false);

}


function characterModifHandler(){
    let result = document.getElementById("protagonist");
    let selection = result.value;
    let msg = selection;

   let pb = function (result) {
        console.log("debug: ");
        console.log(result);
    };

    elsaRequest(msg, "modifyCharacter", processModificationAnswer, pb,false);

}


function measureModifHandler(){
    let result = document.getElementById("message");
    let selection = result.value;
    let msg = selection;

   let pb = function (result) {
        console.log("debug: ");
        console.log(result);
    };

    elsaRequest(msg, "modifyMeasure", processModificationAnswer, pb,false);

}



function messageModifHandler(){
    let result = document.getElementById("observation");
    let selection = result.value;
    let msg = selection;

   let pb = function (result) {
        console.log("debug: ");
        console.log(result);
    };

    elsaRequest(msg, "modifyMessage", processModificationAnswer, pb,false);

}




function processSQLAnswer (result, endpoint) {
        let code=JSON.parse(result).code;
        let message=JSON.parse(result).message;
        let filename=JSON.parse(result).filename;
        console.log(result);
        console.log(code);
        console.log(filename);
        console.log(message);


        if(code==0){
            let div = document.getElementById("result");
            let textresult=document.getElementById("textresult");
            textresult.value=message;


            var img = document.createElement("img");
            //img.src = "img/BarChart-0.png";

            filename=filename.substr(7);//remove public/ !!!
            console.log(filename);
            img.src = filename;

            img.style.width='170%';
            img.style.padding='20px';
            var src = document.getElementById("my_dataviz");
            src.appendChild(img);


        }
        else{
            let consoleElt=document.getElementById("console");
            consoleElt.innerText=message;

        }
}


function processDescribeAnswer (result, endpoint) {
        let code=JSON.parse(result).code;
        let message=JSON.parse(result).message;
        console.log(code);
        console.log(message);

        if(code==0){
            sendToDescribeServer();



        }
        else{
            let consoleElt=document.getElementById("console");
            consoleElt.innerText=message;

        }
}





 function processInsightAnswer (result, endpoint) {
        let code=JSON.parse(result).code;
        let message=JSON.parse(JSON.parse(result).message);
        console.log(code);
        console.log(message);


        if(code==0){
            let obs=document.getElementById("observation");
            obs.value= message;
        }
        else{
            let consoleElt=document.getElementById("console");
            consoleElt.innerText=message;

        }
}



function notifyDescribeInsight(){ // called by sentToDescribeServer, on success
html2canvas($("#my_dataviz").get(0)).then(function(canvas) {
                    // Export the canvas to its data URI representation
                var base64image = canvas.toDataURL("image/png");
                    // Open the image in a new window
                    //window.open(base64image , "_blank");

                const theurl = "http://" + server_domain + "/api/" + "describeInsight";

                console.log("processDescribeAnswer");
                console.log(base64image);

                $.ajax({
                        type: 'POST',
                        url: theurl,
                        processData: false,
                        contentType: 'application/octet-stream',
                        data: base64image,
                        //data: { base64: base64image },
                        dataType: "string"
                });
            });
}



function goalformHandler() {
    let goal = document.getElementById("goal").value;

    let msg = goal;

    let changeDiv = function (result) {
        let recap=document.getElementById("recap");
        let recaptext = document.getElementById("recap").innerText;
        recap.innerText= recaptext + "\n Goal: " + result;
    };

   let pb = function (result) {
        console.log("debug: ");
        console.log(result);
    };

    elsaRequest(msg, "goal", processControllerAnswer, pb,false);
}



function questionFormHandler() {
    let question = document.getElementById("question").value;

    let msg = question;

   let pb = function (result) {
        console.log("debug: ");
        console.log(result);
    };

    elsaRequest(msg, "question", processControllerAnswer, pb,false);
}


function openSQLform() {
  document.getElementById("mySQLForm").style.display = "block";
}



function SQLformHandler() {
    let query = document.getElementById("query").value;

    let msg = {"query" : query};

    document.getElementById("mySQLForm").style.display = "none";

   let pb = function (result) {
        console.log("debug: ");
        console.log(result);
    };

    elsaRequest(msg, "query", processSQLAnswer, pb,true);
}


function openDescribeform() {
  document.getElementById("myDescribeForm").style.display = "block";
}


function sendDescribe(){

let query = document.getElementById("describe_text").value;

    let msg = query;

    document.getElementById("mySQLForm").style.display = "none";

   let pb = function (result) {
        console.log("debug: ");
        console.log(result);
    };

    elsaRequest(msg, "describeQuery", processDescribeAnswer, pb,false);


}







function resultformHandler() {
    let result = document.getElementById("textresult");
    //let selection = (result.value).substring(result.selectionStart,result.selectionEnd);
    let selection=result.value;
    let msg = selection;

   let pb = function (result) {
        console.log("debug: ");
        console.log(result);
    };

    elsaRequest(msg, "result", processInsightAnswer, pb,false);
}


function describeResultFormHandler(){

   html2canvas($("#my_dataviz").get(0)).then(function(canvas) {
                    // Export the canvas to its data URI representation
                    var base64image = canvas.toDataURL("image/png");
                    // Open the image in a new window
                    //window.open(base64image , "_blank");

                    const theurl = "http://" + server_domain + "/api/" + "describeViz";

                    //console.log(base64image);

                    $.ajax({
                        type: 'POST',
                        url: theurl,
                        processData: false,
                        contentType: 'application/octet-stream',
                        data: base64image,
                        //data: { base64: base64image },
                        dataType: "string",
                        success: function(newdata) {
                            console.log(newdata);
                            let obs=document.getElementById("observation");
                            //obs.value=base64image;
                            //obs.value=$("#my_dataviz").get(0);
                        },
                        error: function(newdata){
                            console.log("ajax error");
                            console.log(newdata);
                            let obs=document.getElementById("observation");
                            //obs.value=$("#my_dataviz").get(0).value;
                            //obs.value=base64image;
                            obs.value="...as illustrated by the data viz...";
                        }
                    });
   });


}

function observationformHandler() {
    let result = document.getElementById("observation");
    let selection = result.value;
    let msg = selection;

    let changeDiv = function (result) {
            let recap=document.getElementById("recap");
            let recaptext = document.getElementById("recap").innerText;
            recap.innerText= recaptext + "\n Observation: " + result;
        };

   let pb = function (result) {
        console.log("debug: ");
        console.log(result);
    };

    elsaRequest(msg, "observation", processControllerAnswer, pb,false);
}



function messageformHandler() { // now measure
    let result = document.getElementById("message");
    let selection = result.value;
    let msg = selection;

    let changeDiv = function (result) {
            let recap=document.getElementById("recap");
            let recaptext = document.getElementById("recap").innerText;
            recap.innerText= recaptext + "\n Message: " + result;
        };

   let pb = function (result) {
        console.log("debug: ");
        console.log(result);
    };

    elsaRequest(msg, "message", processControllerAnswer, pb,false);
}


function protagonistformHandler() {
    let result = document.getElementById("protagonist");
    let selection = result.value;
    let msg = selection;

    let changeDiv = function (result) {
            let recap=document.getElementById("recap");
            let recaptext = document.getElementById("recap").innerText;
            recap.innerText= recaptext + "\n Protagonist: " + result;
        };

   let pb = function (result) {
        console.log("debug: ");
        console.log(result);
    };

    elsaRequest(msg, "protagonist", processControllerAnswer, pb,false);
}




function actformHandler() {
    let result = document.getElementById("act");
    let selection = result.value;
    let msg = selection;

    let changeDiv = function (result) {
            let recap=document.getElementById("recap");
            let recaptext = document.getElementById("recap").innerText;
            recap.innerText= recaptext + "\n Act: " + result;
        };

   let pb = function (result) {
        console.log("debug: ");
        console.log(result);
    };

    elsaRequest(msg, "act", processControllerAnswer, pb,false);
}



function episodeformHandler() {
    let result = document.getElementById("episode");
    let selection = result.value;
    let msg = selection;

    let changeDiv = function (result) {
            let recap=document.getElementById("recap");
            let recaptext = document.getElementById("recap").innerText;
            recap.innerText= recaptext + "\n Episode: " + result;
        };

   let pb = function (result) {
        console.log("debug: ");
        console.log(result);
    };

    elsaRequest(msg, "episode", processEpisodeClear, pb,false);
}



function renderListnener() {

   let msg="Story rendered."

  let pb = function (result) {
        console.log("debug: ");
        console.log(result);
    };
    elsaRequest(msg, "render", processRender, pb, false);
}





function renderDOListnener() {

   let msg="Story rendered."

  let pb = function (result) {
        console.log("debug: ");
        console.log(result);
    };
    elsaRequest(msg, "renderDO", processRender, pb, false);
}



function renderSQLNBListnener() {

   let msg="Story rendered."

  let pb = function (result) {
        console.log("debug: ");
        console.log(result);
    };
    elsaRequest(msg, "renderSQLNB", processRenderSQLNB, pb, false);
}



function openDBsave(){
    document.getElementById("DBsaveForm").style.display = "block";
}


function DBsaveListnener(){

    let msg = document.getElementById("plottext").value;

    document.getElementById("DBsaveForm").style.display = "none";

  let pb = function (result) {
        console.log("debug: ");
        console.log(result);
    };

    elsaRequest(msg, "DBsave", processDBsave, pb, false);
}

function openDBload(){
    document.getElementById("DBloadForm").style.display = "block";
}


function DBloadListnener(){
    let msg = document.getElementById("loadText").value;

    document.getElementById("DBloadForm").style.display = "none";

  let pb = function (result) {
        console.log("debug: ");
        console.log(result);
    };

    elsaRequest(msg, "DBload", processDBload, pb, false);
}



function elsaRequest(body, endpoint, callback, errorCallback, is_json=false) {
    // construct server url for API request
    const url = "http://" + server_domain + "/api/" + endpoint;
    let xhr = new XMLHttpRequest();

    console.log(url);

    // modify callback to be executed when request completes
    function internCallback() {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            callback(xhr.responseText, endpoint);
        }
        // if request fails (eg: network error)
        else if (xhr.readyState === 4 && typeof errorCallback !== 'undefined') {
            errorCallback(xhr.responseText, xhr.status);
        }
    }
    xhr.open('POST', url);

     if (is_json)
            xhr.setRequestHeader('Content-Type', 'application/json');

    // function to be called on state change
    xhr.onreadystatechange = internCallback;
    // send request

    body=JSON.stringify(body);

    xhr.send(body);
}


/*
function sendDescribe(body, callback, errorCallback) {

    //http://137.204.72.88:8083/COOL/Describe?sessionid=1588142552276&value=with%20SALES%20describe%20storeCost%20by%20month

    let param="?sessionid=12345678&value=" + body;

    // construct server url for API request
    const url = "http://137.204.72.88:8083/COOL/Describe" + param;

    let xhr = new XMLHttpRequest();

    // modify callback to be executed when request completes
    function internCallback() {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            callback(xhr.responseText, endpoint);
        }
        // if request fails (eg: network error)
        else if (xhr.readyState === 4 && typeof errorCallback !== 'undefined') {
            errorCallback(xhr.responseText, xhr.status);
        }
    }
    // should be a GET
    //xhr.open('POST', url);

    xhr.open('GET',url)

    // function to be called on state change
    xhr.onreadystatechange = internCallback;
    // send request

    //body=JSON.stringify(body);

    xhr.send();
    //xhr.send(body);
}
*/




var data, prevModel = "", prevText = "";
session = new Date().getTime();

function sendToDescribeServer() {
    document.getElementById("myDescribeForm").style.display = "none";

    document.getElementById("loader").style.display = "block"
    // console.log(session)
    $.ajax({
        url: config["ip"],
        type: "GET",
        crossDomain: true,
        data: { sessionid: session, value: $("#describe_text")[0].value + " "},
        dataType: "json",
        success: function(newdata) {
            if (typeof newdata["error"] !== "undefined") {
                alert("ERROR: " + newdata["error"]);
                // session = new Date().getTime();
            } else {
                // console.log(data);
                data = newdata;
                $("#table_raw").html(buildHtmlTable(data["raw"], "raw"));
                $("#table_component").html(buildHtmlTable(data["components"], "components"));
                $("#table_pivot").html(buildHtmlPivot(data["pivot"]["table"], data["pivot"]["headers"]["measures"]));
                var columns;
                if (data["pivot"]["headers"]["columns"]) { columns = data["pivot"]["headers"]["columns"].join("; ") }
                else { columns = ""; }
                $("#table_header").html(buildKeyValueTable({
                    Measures:   data["pivot"]["headers"]["measures"].join("; "),
                    Rows:       data["pivot"]["headers"]["rows"].join("; "),
                    Columns:    columns
                }));
                update(data["components"][0]["component"]);
            }
            /*
            if (prevText != "") {
                $("#old_text").append("<div>" + prevText + "</div>");
            }
            prevText = $("#describe_text")[0].value;*/
            $("#old_text").append("<div>" + $("#describe_text")[0].value + "</div>");
            document.getElementById("loader").style.display = "none";
            notifyDescribeInsight();
        },
        error: function(newdata) {
            alert("ERROR: Server not reachable. " + newdata);
            session = new Date().getTime();
        }
    });
}

function update(model_component) {
    /** ************************************************************************
     * COLOR THE DATA POINTS OF EACH CHART
     * ************************************************************************/
    var components = new Set();
    var input = model_component.split("=");
    var model = att2header(input[0]);
    var component = input[1];
    data["components"].forEach(function(d) {
        var curr = d["component"].split("=");
        var mod = curr[0];
        var com = curr[1];
        if (mod == model) {
            components.add(com);
        }
    });
    components = Array.from(components).sort(function(a, b) { return a.localeCompare(b); });
    var color = d3.scaleOrdinal().domain(components).range(d3.schemeCategory10);
    chooseChart(data, model, false, model, component, color(component)); // update the charts that depends on the components (e.g., grouped column chart)
    /* Color all the datapoints with their respective model's component */
    d3.selectAll("[datapoint=colored]")
        .style("opacity", 1)
        .style("stroke", "white")
        .style("fill", function(d) {
            c = d3.color(color(d[model]));
            if (d[model] != input[1]) { c.opacity = 0.5; }
            return c + "";
        });
    /* Highlight all the datapoints with selected component */
    d3.selectAll("[datapoint]").filter(function(d, i) {
        return d[model] == input[1];
    })
    .style("border", "1px solid")
    .style("stroke", "black");

    model = header2att(model);
    if (prevModel != "") {
        $('[' + prevModel + ']').each(function(i) {
            $(this)
                .css('border', "1px dotted")
                .css("background-color", "white");
        });
    }
    prevModel = model;
    $('[' + model + ']').each(function(i) {
        var c = this.style.background = color(this.getAttribute(model));
        if (this.getAttribute(model) === input[1]) {
            $(this)
                .css('border', "1px solid")
                .css("background-color", c + "");
        } else {
            c = d3.color(c);
            c.opacity = 0.5;
            $(this)
                .css('border', "1px dotted")
                .css("background-color", c + "");
        }
    });
}

/** ****************************************************************************
 * Utility functions
 * ****************************************************************************/
// Check if a string is a measure
function isMeasure(s) {
    return s.includes("(") && !s.includes("zscore") && !s.includes("surprise");
}
// HTML attributes cannot contain brakets, replace them
function header2att(s) {
    return s.replace("(", "--").replace(")", "-");
}
function att2header(s) {
    return s.replace("--", "(").replace("-", ")");
}
// Reset the session id
function clearSession() {
    $("#old_text").html("");
    $("#table_header").html("");
    $("#table_pivot").html("");
    $("#my_dataviz").html("");
    $("#table_component").html("");
    session = new Date().getTime();
}
function greyPalette() {
    return ["#B2B4B3", "#585A59", "#949695", "#3A3C3B", "#767877"];
    // return ["#B2B4B3", "#A3A5A4", "#949695", "#858786", "#767877", "#676968", "#585A59", "#494B4A"];
}




