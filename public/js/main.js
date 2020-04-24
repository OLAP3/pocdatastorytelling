/*
    Global parameters here
 */
const server_domain = "localhost:8080";

function renderListnener() {
    let demodiv = document.getElementById('demodiv');

    let fgood = function (result){
        demodiv.innerText = result;
    };

    elsaRequest("gimme tha numberrr !", "random", fgood, null);
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
            recap.innerText= recaptext + "\n " + endpoint + ": "+ message;
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
            textresult.innerText=message;

        }
        else{
            let consoleElt=document.getElementById("console");
            consoleElt.innerText=message;

        }
}


 function processInsightAnswer (result, endpoint) {
        let code=JSON.parse(result).code;
        let message=JSON.parse(result).message;
        console.log(code);
        console.log(message);


        if(code==0){
            let obs=document.getElementById("observation");
            obs.innerText= message;
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
  document.getElementById("myForm").style.display = "block";
}

function SQLformHandler() {
    let query = document.getElementById("query").value;

    let msg = {"query" : query};

    document.getElementById("myForm").style.display = "none";

   let pb = function (result) {
        console.log("debug: ");
        console.log(result);
    };

    elsaRequest(msg, "query", processSQLAnswer, pb,true);
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