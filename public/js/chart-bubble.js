function drawBubble(prop, data, c1, c2, c3, c4, mode) {
    // set the dimensions and margins of the graph
    var margin = prop.margin, width = prop.width, height = prop.height;

    var minx = 0, maxx = 0, miny = 0, maxy = 0, minr = 2, maxr = 2;
    var domain = new Set();
    data.forEach(function(d) {
        if (mode > 1) {
            minx = Math.min(minx, d[c1] - 1);
            maxx = Math.max(maxx, d[c1] + 1);
            miny = Math.min(miny, d[c2] - 1);
            maxy = Math.max(maxy, d[c2] + 1);
        }
        minr = Math.min(minr, d[c3] - 1);
        maxr = Math.max(maxr, d[c3] + 1);
        domain.add(d[c4]);
    });
    domain = Array.from(domain);

    var div = d3.select("#my_dataviz"); //.append("div");
    
    var svg = div
                .append("svg")
                    .attr("width", width + margin.left + margin.right)
                    .attr("height", height + margin.top + margin.bottom)
                .append("g")
                    .attr("transform",
                        "translate(" + margin.left + "," + margin.top + ")");

    // Add X axis
    if (mode > 1) {
        var x = d3.scaleLinear()
            .domain([minx, maxx])
            .range([0, width]);

        var y = d3.scaleLinear()
            .domain([miny, maxy])
            .range([height, 0]);
    } else {
        var x = d3.scaleBand()
                    .rangeRound([0, width])
                    .domain(data.map(function(d) { return d[c1]; }))
                    .padding(0.01);

        var y = d3.scaleBand()
                    .rangeRound([0, height])
                    .domain(data.map(function(d) { return d[c2]; }))
                    .padding(0.01);
    }

    // Add a scale for bubble size
    var z = d3.scaleLinear()
        .domain([minr, maxr])
        .range([4, 20]);

    var tooltip = createTooltip(div);
    svg.append('g')
        .selectAll("dot")
        .data(data)
        .enter()
        .append("circle")
            .attr("class", "bubbles")
            .attr("cx", function (d) { return x(d[c1]); } )
            .attr("cy", function (d) { return y(d[c2]); } )
            .attr("datapoint", "colored")
            .attr("r", function (d) {  if (mode == 2 && c3 == "undefined") return 4; else return z(d[c3]); })
            .on("mouseover",  function f (d) { showTooltip(d, tooltip) })
            .on("mousemove",  function f (d) { moveTooltip(d, tooltip) })
            .on("mouseleave", function f (d) { hideTooltip(d, tooltip) });

    appendXaxis(svg, x, height);
    appendXlabel(svg, c1, width, height);
    appendYaxis(svg, y);
    appendYlabel(svg, c2, width, height);   
}