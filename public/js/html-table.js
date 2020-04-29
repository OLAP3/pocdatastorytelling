var _table_ = document.createElement('table'), _tr_ = document.createElement('tr'), _th_ = document.createElement('th'), _td_ = document.createElement('td');

// Builds the HTML Table out of myList json data from Ivy restful service.
function buildHtmlPivot(arr, measures) {
    var table = _table_.cloneNode(false);
    if (typeof arr === 'undefined') { return table; }
    for (var i=0, maxi=arr.length; i < maxi; ++i) {
        var tr = _tr_.cloneNode(false);
        for (var j=0, maxj=arr[i].length; j < maxj; ++j) {
            var jsonObj = arr[i][j];
            var td = _td_.cloneNode(false);
            if (typeof jsonObj["type"] !== 'undefined') {
                td.setAttribute("type", jsonObj["type"]);
                if (jsonObj["type"] === "header") {
                    td.appendChild(document.createTextNode(jsonObj["attribute"]));
                } else {
                    var txt = ""
                    measures.forEach(function(m) { txt += jsonObj[m] + "; "; });
                    Object.keys(jsonObj).sort(function(a, b) { return a.localeCompare(b); }).forEach(function(key) {
                        if (key.includes("model")) {
                            td.setAttribute(header2att(key), jsonObj[key]);
                        }
                    });
                    td.appendChild(document.createTextNode(txt.substr(0, txt.length - 2))); 
                }
            } else {
                td.appendChild(document.createTextNode(""));
            }
            // td.onclick = function(e) { update(e.target.getAttribute("content")); };
            tr.appendChild(td);
        }
        table.appendChild(tr);
    }
    return table;
}

function componentToString(model, component) {
    if (model.includes("clustering")) {
        return "cluster " + component;
    } else {
        var text = model.replace("model_", "").replace("_", " ");
        if (component == "True") {
            return text;
        } else {
            return "not " + text;
        }
    }
}
// Builds the HTML Table out of myList json data from Ivy restful service.
function buildHtmlTable(arr, behavior) {
    // console.log(arr);
    var table = _table_.cloneNode(false);
    if (typeof arr === 'undefined') { return table; }
    var columns = addAllColumnHeaders(arr, table);
    for (var i=0, maxi=arr.length; i < maxi; ++i) {
        var tr = _tr_.cloneNode(false);
        var jsonObj = arr[i];
        
        for (var j=0, maxj=columns.length; j < maxj ; ++j) {
            var td = _td_.cloneNode(false);
            // console.log(columns[j]);
            if (columns[j] == "component") {
                var input = jsonObj["component"].split("=");
                var model = input[0];
                var component = input[1];
                td.appendChild(document.createTextNode(componentToString(model, component)));
            } else {
                td.appendChild(document.createTextNode(
                    JSON.stringify(arr[i][columns[j]])
                        .replace('"', "") // no
                        .replace("\":\"", ": ") // fucking
                        .replace("{", "") // idea
                        .replace("\"}", "") // why
                        .replace("}", "") // need this
                )); //  || '' cellvalue = arr[i][columns[j]];
            }
            // console.log(arr[i]);
            // console.log(columns[j]);
            // console.log(arr[i][columns[j]]);
            Object.keys(jsonObj).forEach(function(key) {
                if (behavior == "raw") {
                    if (key.includes("model")) {
                        td.setAttribute(header2att(key), jsonObj[key]);
                        if (key == columns[j]) {
                            td.setAttribute("content", header2att(key) + "=" + jsonObj[key]);
                        }
                    }
                } else {
                    var input = jsonObj["component"].split("=");
                    var model = header2att(input[0]);
                    var component = input[1];
                    td.setAttribute(model, component);
                    td.setAttribute("content", model + "=" + component);
                }
                td.onclick = function(e) { update(e.target.getAttribute("content")); };
            });
            tr.appendChild(td);
        }
        table.appendChild(tr);
    }
    return table;
}
    
// Adds a header row to the table and returns the set of columns.
// Need to do union of keys from all records as some records may not contain
// all records
function addAllColumnHeaders(arr, table) {
    var columnSet = [], tr = _tr_.cloneNode(false);
    for (var i=0, l=arr.length; i < l; i++) {
        for (var key in arr[i]) {
            if (arr[i].hasOwnProperty(key) && columnSet.indexOf(key)===-1) {
                columnSet.push(key);
                var th = _th_.cloneNode(false);
                th.appendChild(document.createTextNode(key));
                tr.appendChild(th);
            }
        }
    }
    table.appendChild(tr);
    return columnSet;
}

function buildKeyValueTable(obj) {
    var table = _table_.cloneNode(false);
    Object.keys(obj).forEach(function(key) {
        var tr = _tr_.cloneNode(false);
        var td = _td_.cloneNode(false);
        td.appendChild(document.createTextNode(key));
        tr.appendChild(td);
        var td = _td_.cloneNode(false);
        td.appendChild(document.createTextNode(obj[key]));
        tr.appendChild(td);
        table.appendChild(tr);
    });
    return table;
}
    