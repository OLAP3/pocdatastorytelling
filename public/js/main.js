/*
    Global parameters here
 */
//const server_domain = "localhost:8080";
const server_domain = window.location.host;

function clearStory() {

     let pb = function (result) {
        console.log("debug: ");
        console.log(result);
    };

    elsaRequest("clear", "clear", processClear, pb, false);
}

function clear(toClear){
    let recap=document.getElementById(toClear);
    let recaptext = document.getElementById(toClear).value;
    recap.value= "";
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
         clear("goal");
         clear("question");
         clear("observation");
         clear("textresult");
         clear("protagonist");
         clear("message");
         clear("episode");
         clear("act");

         let consoleElt=document.getElementById("console");
         consoleElt.innerText=message;

        }
}



 function processRender (result, endpoint) {
        let code=JSON.parse(result).code;
        let message=JSON.parse(result).message;
        console.log(code);
        console.log(message);

        let consoleElt=document.getElementById("console");
        consoleElt.innerText=JSON.parse(message);


}



 function processControllerAnswer (result, endpoint) {
        let code=JSON.parse(result).code;
        let message=JSON.parse(result).message;
        console.log(code);
        console.log(message);

        if(code==0){
            let recap=document.getElementById("recap");
            let recaptext = document.getElementById("recap").value;
            // console.log(recap);
             // console.log(recaptext);
            //recap.innerText= recaptext + "\n " + endpoint + ": "+ message;
            recap.value= recaptext + "\n " + endpoint + ": "+ JSON.parse(message);
        }
        else{
            let consoleElt=document.getElementById("console");
            consoleElt.innerText=message;

        }
}



 function processSQLAnswer (result, endpoint) {
        let code=JSON.parse(result).code;
        let message=JSON.parse(result).message;
        console.log(code);
        console.log(message);

        if(code==0){
            let div = document.getElementById("result");
            let textresult=document.getElementById("textresult");
            textresult.value=message;

        }
        else{
            let consoleElt=document.getElementById("console");
            consoleElt.innerText=message;

        }
}

function processDescribeAnswer(model_component) {
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



function describeformHandler() {
    let query = document.getElementById("describeQuery").value;

    let msg = {"describeQuery" : query};

    document.getElementById("myDescribeForm").style.display = "none";

   let pb = function (result) {
        console.log("debug: ");
        console.log(result);
    };

    //elsaRequest(msg, "query", processSQLAnswer, pb,true);
    sendDescribe(msg, processDescribeAnswer,pb);
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


function resultformHandler() {
    let result = document.getElementById("textresult");
    let selection = (result.value).substring(result.selectionStart,result.selectionEnd);

    let msg = selection;

   let pb = function (result) {
        console.log("debug: ");
        console.log(result);
    };

    elsaRequest(msg, "result", processInsightAnswer, pb,false);
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



function messageformHandler() {
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

    elsaRequest(msg, "episode", processControllerAnswer, pb,false);
}



function renderListnener() {
    //let demodiv = document.getElementById('demodiv');

   let msg="Story rendered."

  let pb = function (result) {
        console.log("debug: ");
        console.log(result);
    };
    elsaRequest(msg, "render", processRender, pb, false);
}


function DBdumpListnener(){
//to do
}







function elsaRequest(body, endpoint, callback, errorCallback, is_json=false) {
    // construct server url for API request
    const url = "http://" + server_domain + "/api/" + endpoint;
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
    xhr.open('POST', url);

     if (is_json)
            xhr.setRequestHeader('Content-Type', 'application/json');

    // function to be called on state change
    xhr.onreadystatechange = internCallback;
    // send request

    body=JSON.stringify(body);

    xhr.send(body);
}



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


// Old stuff below

function formHandler() {
    let first_name = document.getElementById("query").value;
    let last_name = document.getElementById("message").value;
    console.log(first_name);
    console.log(last_name);

    let msg = {"fn" : first_name, "ln": last_name};

    let changeDiv = function (result) {
        let div = document.getElementById("result");
        div.innerText = result;
    };

   let pb = function (result) {
        console.log("debug: ");
        console.log(result);
    };

    elsaRequest(msg, "form", changeDiv, pb,true);
}
