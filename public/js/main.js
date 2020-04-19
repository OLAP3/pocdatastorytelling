/*
    Global parameters here
 */
const server_domain = "localhost:8080";

function demoListnener() {
    let demodiv = document.getElementById('demodiv');

    let fgood = function (result){
        demodiv.innerText = result;
    };

    elsaRequest("gimme tha numberrr !", "random", fgood, null);
}

function formHandler() {
    console.log("test");
    let first_name = document.getElementById("fname").innerText;
    let last_name = document.getElementById("lname").innerText;
    console.log(first_name);
    console.log(last_name);
}

function elsaRequest(body, endpoint, callback, errorCallback) {
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
    // function to be called on state change
    xhr.onreadystatechange = internCallback;
    // send request
    xhr.send(body);
}