function chooseChart(data, model, isupdate, selectedModel, selectedComponent, highlightColor) {
    d3.select("#my_dataviz").html("");
    var n = data["pivot"]["headers"]["dimensions"].length;
    var z = data["pivot"]["headers"]["measures"].length;
    var prop = {}
    prop["margin"] = {top: 30, right: 30, bottom: 100, left: 100};
    prop["width"] = 800 - 300 - prop["margin"]["left"] - prop["margin"]["right"];
    prop["height"] = 500 -300 - prop["margin"]["top"] - prop["margin"]["bottom"];
    
    if (n == 1 && !isupdate) {
        var dim = data["pivot"]["headers"]["dimensions"][0]["attribute"];
        if (dim.includes("date") || dim.includes("month") || dim.includes("year")) {
            drawMultiline(
                prop,
                data["raw"],
                data["pivot"]["headers"]["dimensions"][0]["attribute"],
                data["pivot"]["headers"]["measures"]);
        }
    }
    if (z == 1 && data["pivot"]["headers"]["dimensions"][0]["cardinality"] <= 50) {
        if (n == 1) {
            drawGroupedColumn(
                    prop,
                    data["raw"],
                    data["pivot"]["headers"]["dimensions"][0]["attribute"],
                    data["pivot"]["headers"]["measures"][0],
                    model,
                    1, selectedModel, selectedComponent, highlightColor);
        } else if (n == 2 && data["pivot"]["headers"]["dimensions"][0]["cardinality"] <= 8 && !isupdate) {
            drawGroupedColumn(
                    prop,
                    data["raw"],
                    data["pivot"]["headers"]["dimensions"][1]["attribute"],
                    data["pivot"]["headers"]["measures"][0],
                    data["pivot"]["headers"]["dimensions"][0]["attribute"],
                    2, selectedModel, selectedComponent, highlightColor);
        }
    }

    if (n == 2 && z == 1 && !isupdate) {
        drawHeatmap(
                prop,
                data["raw"], 
                data["pivot"]["headers"]["dimensions"][1]["attribute"],
                data["pivot"]["headers"]["dimensions"][0]["attribute"],
                data["pivot"]["headers"]["measures"][0], selectedModel, selectedComponent, highlightColor);
    } else if (n == 1 && !isupdate) {
        drawHeatmap(
            prop,
            data["red"],
            data["pivot"]["headers"]["dimensions"][0]["attribute"],
            "measure",
            "value", selectedModel, selectedComponent, highlightColor);
    }

    if (z == 1 && !isupdate) {
        if (n == 2) {
            drawBubble(
                    prop,
                    data["raw"], 
                    data["pivot"]["headers"]["dimensions"][1]["attribute"],
                    data["pivot"]["headers"]["dimensions"][0]["attribute"],
                    data["pivot"]["headers"]["measures"][0],
                    model,
                    1);
        } else if (n == 3) {
            alert("3D visualization is not implemented in this demo yet");
            /*drawBubble3D(
                prop,
                data["raw"], 
                data["pivot"]["headers"]["dimensions"][0]["attribute"],
                data["pivot"]["headers"]["dimensions"][1]["attribute"],
                data["pivot"]["headers"]["dimensions"][2]["attribute"],
                model,
                1);*/
        }
    } else if (z == 2 && !isupdate) {
        drawBubble(
                    prop,
                    data["raw"], 
                    data["pivot"]["headers"]["measures"][0],
                    data["pivot"]["headers"]["measures"][1],
                    "undefined",
                    model,
                    2);
    } else if (z == 3 && !isupdate) {
        drawBubble(
                    prop,
                    data["raw"], 
                    data["pivot"]["headers"]["measures"][0],
                    data["pivot"]["headers"]["measures"][1],
                    data["pivot"]["headers"]["measures"][2],
                    model,
                    3);
    }
}
/* *****************************************************************************
 * TOOLTIP MANAGEMENT
 * ****************************************************************************/
// Create a tooltip div that is hidden by default
function createTooltip(div) {
    return div
        .append("div")
            .style("opacity", 0)
            .attr("class", "tooltip")
            .style("background-color", "#f9f3bf")
            .style("border-radius", "5px")
            .style("padding", "10px")
            .style("color", "black")
            .style("display", "inline-block")
            .style("position", "absolute")
}
var showTooltip = function(d, tooltip) {
    var newd = {};
    data["pivot"]["headers"]["dimensions"].forEach(function(c) { newd[c["attribute"]] = d[c["attribute"]]; });
    data["pivot"]["headers"]["measures"].forEach(function(m) { newd[m] = d[m]; });
    data["pivot"]["headers"]["models"].forEach(function(m) { newd[m.replace("model_", "").replace("_", " ").replace("clustering", "cluster")] = d[m]; });
    tooltip
        .transition()
        .duration(200)
    tooltip
        .style("opacity", 1)
        .html("<table>" + buildKeyValueTable(newd).innerHTML + "</table>")
        .style("left",(d3.event.pageX + 30) + "px") // d3.mouse(this)[0]
        .style("top", (d3.event.pageY + 30) + "px") // d3.mouse(this)[1]
}
var moveTooltip = function(d, tooltip) {
    tooltip
        .style("left",(d3.event.pageX + 30) + "px") // d3.mouse(this)[0]
        .style("top", (d3.event.pageY + 30) + "px") // d3.mouse(this)[1]
}
var hideTooltip = function(d, tooltip) {
    tooltip
        .transition()
        .duration(200)
        .style("opacity", 0)
}
function appendXaxis(svg, x, height, format) {
    var axis = d3.axisBottom(x);
    if (typeof format === "undefined") {
        axis = axis.ticks(null, "s");
    } else {
        axis = axis.tickFormat(format);
    } 
    svg.append("g")
        .attr("class", "axis")
        .attr("transform", "translate(0," + height + ")")
        .call(axis)
        .selectAll("text")  
        .style("text-anchor", "end")
        .attr("dx", "-.8em")
        .attr("dy", ".15em")
        .attr("transform", "rotate(-65)");
}
function appendXlabel(svg, c1, width, height) {
    svg.append("text")             
        // .attr("transform", "translate(" + (width/2) + " ," + (height + margin.top + 20) + ")")
        .attr("y", height - 4)
        .attr("x", width - 2)
        .style("text-anchor", "end")
        .text(c1);
}
function appendYaxis(svg, y) {
    svg.append("g")
        .attr("class", "axis")
        .call(d3.axisLeft(y).ticks(null, "s"));
}
function appendYlabel(svg, c2, width, height) {
    svg.append("text")
        // .attr("transform", "rotate(-90)")
        .attr("y", 15)
        .attr("x",  5)
        .style("text-anchor", "start")
        .text(c2);
}
function appendLegend(svg, domain, width, height, color) {
    var legend = 
        svg.append("g")
            .attr("font-family", "sans-serif")
            .attr("font-size", 10)
            .attr("text-anchor", "end")
            .selectAll("g")
            .data(domain)
            .enter()
            .append("g")
                .attr("transform", function(d, i) { return "translate("+ i * 100+"," + (-20 ) + ")"; });
    legend.append("rect")
        .attr("x", width/2 - 19)
        .attr("width", 19)
        .attr("height", 19)
        .attr("fill", color);
    legend.append("text")
        .attr("x", width/2 - 24)
        .attr("y", 9.5)
        .attr("dy", "0.32em")
        .text(function(d) { return d; });
}