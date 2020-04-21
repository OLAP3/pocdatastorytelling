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

function SQLformHandler() {
    let query = document.getElementById("query").value;

    let msg = {"query" : query};

    let changeDiv = function (result) {
        let div = document.getElementById("result");

        let textresult=document.getElementById("textresult");
        textresult.innerText=result;
    };

   let pb = function (result) {
        console.log("debug: ");
        console.log(result);
    };

    elsaRequest(msg, "query", changeDiv, pb,true);
}


function goalformHandler() {
    let goal = document.getElementById("goal").value;

    let msg = goal;

    let changeDiv = function (result) {
        let recap=document.getElementById("recap");
        //let textRecap=recap.innerText;
        //recap.innerText= result;
        let recaptext = document.getElementById("recap").innerText;
        recap.innerText= recaptext + "\n" + result;
    };

   let pb = function (result) {
        console.log("debug: ");
        console.log(result);
    };

    elsaRequest(msg, "goal", changeDiv, pb,false);
}


function resultformHandler() {
    let result = document.getElementById("textresult");
    let selection = (result.value).substring(result.selectionStart,result.selectionEnd);

    let msg = selection;

    let changeDiv = function (result) {
        let obs=document.getElementById("observation");
        obs.innerText= result;
    };

   let pb = function (result) {
        console.log("debug: ");
        console.log(result);
    };

    elsaRequest(msg, "result", changeDiv, pb,false);
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
            callback(xhr.responseText);
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