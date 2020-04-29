function drawHeatmap(prop, data, d1, d2, c, selectedModel, selectedComponent, highlightColor) {
    // Labels of row and columns
    var X = []; // ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J"]
    var Y = []; // ["v1", "v2", "v3", "v4", "v5", "v6", "v7", "v8", "v9", "v10"]
    var minc = 0, maxc = 0;
    data.forEach(function(d) {
        X.push(d[d1]);
        Y.push(d[d2]);
        minc = Math.min(minc, d[c]);
        maxc = Math.max(maxc, d[c]);
    });

    // Build color scale
    var hsl = d3.color(greyPalette()[0]);
    highlightColor = d3.color(highlightColor);
    highlightColor.h = hsl.s;
    highlightColor.l = hsl.l;
    var grayScaleColor =     d3.scaleLinear().range(["white", greyPalette()[0]]).domain([minc, maxc])
    var selectedScaleColor = d3.scaleLinear().range(["white", highlightColor]).domain([minc, maxc])

    // set the dimensions and margins of the graph
    var margin = prop.margin, width = prop.width, height = prop.height;

    var div = d3.select("#my_dataviz").append("div");
    var svg = div
                .append("svg")
                    .attr("width", width + margin.left + margin.right)
                    .attr("height", height + margin.top + margin.bottom)
                .append("g")
                    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");
  
    // Build X scales and axis:
    var x = d3.scaleBand()
        .range([0, width])
        .domain(X)
        .padding(0.01);
    
        // Build X scales and axis:
    var y = d3.scaleBand()
        .range([0, height])
        .domain(Y)
        .padding(0.01);

    // add the squares
    var tooltip = createTooltip(div);
    svg.selectAll()
        .data(data, function(d) {return d[d1]+':'+d[d2];})
        .enter()
        .append("rect")
            .attr("x", function(d) { return x(d[d1]) })
            .attr("y", function(d) { return y(d[d2]) })
            .attr("datapoint", "component")
            .attr("width", x.bandwidth() )
            .attr("height", y.bandwidth() )
            .style("fill", function(d) { 
                if (d[selectedModel] == selectedComponent) { return selectedScaleColor(d[c]); }
                else return grayScaleColor(d[c]); 
            })
            .on("mouseover",  function f (d) { showTooltip(d, tooltip) })
            .on("mousemove",  function f (d) { moveTooltip(d, tooltip) })
            .on("mouseleave", function f (d) { hideTooltip(d, tooltip) });


    appendXaxis(svg, x, height);
    appendXlabel(svg, d1, width, height);
    appendYaxis(svg, y);
    appendYlabel(svg, d2, width, height);
}