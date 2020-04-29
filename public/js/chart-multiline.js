function drawMultiline(prop, data, d1, M) {
    // set the dimensions and margins of the graph
    var margin = prop.margin, width = prop.width, height = prop.height;

    // parse the date / time
    var timeFormat = "%Y-%m-%e";
    if (d1.includes("month")) {
        timeFormat = "%Y-%m";
    } else if (d1.includes("year")) {
        timeFormat = "%Y";
    }
    var parseTime = d3.timeParse(timeFormat);
    var miny = 0, maxy = 0;
    data.forEach(function(d) {
          d["parseTime"] = parseTime(d[d1]);
          M.forEach(function (m) {
            miny = Math.min(miny, d[m]);
            maxy = Math.max(maxy, d[m]);
        })
    });

    // set the ranges
    var x = d3.scaleTime().range([0, width]);
    var y = d3.scaleLinear().range([height, 0]);

    // append the svg object to the body of the page
    var div = d3.select("#my_dataviz").append("div");
    var svg = div
        .append("svg")
            .attr("width", width + margin.left + margin.right)
            .attr("height", height + margin.top + margin.bottom)
        .append("g")
            .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

    // Add a scale for bubble color
    var lineColor = d3.scaleOrdinal().domain(M).range(greyPalette());

    // Scale the range of the data
    x.domain(d3.extent(data, function(d) { return d["parseTime"]; }));
    y.domain([miny, maxy]);

    var tooltip = createTooltip(div);
    M.forEach(function (m) {
        var valueline = d3.line()
            .x(function(d) { return x(d["parseTime"]); })
            .y(function(d) { return y(d[m]); })
            .curve(d3.curveMonotoneX);
            
        svg.append("path")
            .data([data])
            .attr("class", "line")
            .style("stroke", function(d) { return lineColor(m); })
            .attr("d", valueline);

        svg.selectAll("line")
            .data(data)
            .enter()
            .append("circle")
            .attr("class", "dot") // Assign a class for styling
            .attr("cx", function(d) { return x(d["parseTime"]) })
            .attr("cy", function(d) { return y(d[m]) })
            .attr("datapoint", "colored")
            .attr("r", 4)
            .on("mouseover",  function f (d) { showTooltip(d, tooltip) })
            .on("mousemove",  function f (d) { moveTooltip(d, tooltip) })
            .on("mouseleave", function f (d) { hideTooltip(d, tooltip) });
    });

    appendXaxis(svg, x, height, d3.timeFormat(timeFormat))
    appendXlabel(svg, d1, width, height);
    appendYaxis(svg, y);
    appendLegend(svg, M, width, height, lineColor);
}