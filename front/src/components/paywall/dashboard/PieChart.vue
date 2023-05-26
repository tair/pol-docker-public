<template>
  <div id="chart">
    <svg :id="id" width="500" height="400">
      <g id="label"></g>
    </svg>
  </div>
</template>

<script>
import * as d3 from "d3";

export default {
  name: "pie-chart",
  props: ["id", "label", "chartdata"],
  data() {
    return {
      value: [
        { name: "Sarah", value: 250 },
        { name: "Emma", value: 2005 },
        { name: "Laura", value: 1968 },
      ],
    };
  },
  mounted() {
    if (this.id) {
      var svg = d3.select("#" + this.id);

      svg.append("g").attr("class", "lines");

      var width = +svg.attr("width");
      var height = +svg.attr("height");

      var margin = { top: 0, left: 0, bottom: 20, right: 0 };

      var chartWidth = width - (margin.left + margin.right);
      var chartHeight = height - (margin.top + margin.bottom);

      this.chartLayer = svg
        .append("g")
        .attr("transform", `translate(${chartWidth / 2}, ${chartHeight / 2})`);

      svg
        .append("text")
        .attr("class", "label-text")
        .attr("y", height / 2)
        .attr("x", width / 2)
        .text(this.label)
        .attr("fill", "#0d6aad");
      //
      if (this.chartdata) {
        this.drawChart(this.chartdata);
        this.drawLabels(this.chartdata);
      }
      //this.drawPie();
    }
  },
  methods: {
    drawLabels(data) {
      // var key = function(d){ return d.data.name; };
      // var polyline = svg.select(".lines").selectAll("polyline")
      //     .data(pie(data), key);
      // polyline.enter()
      //     .append("polyline")
      //     .attr("stroke", "gray")
      //     .attr("stroke-width", "2px");
    },
    drawChart(data) {
      var chartdata = [1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89];

      var svg = d3.select("#" + this.id);
      var width = +svg.attr("width");
      var height = +svg.attr("height");
      var radius = Math.min(width, height) / 2;
      var outerRadius = height / 2 - 20,
        innerRadius = outerRadius / 3,
        cornerRadius = 10;

      // creates a new pie generator
      var pie_data = d3
        .pie()
        .sort(null)
        .value(function (d) {
          return d.value;
        })(data);

      // constructs an arc generator. This will be used for the donut.
      // The difference between outer and inner
      // radius will dictate the thickness of the donut
      var arc = d3
        .arc()
        .padRadius(outerRadius)
        .innerRadius(innerRadius)
        .padAngle(0.03)
        .cornerRadius(8);

      // this arc is used for aligning the text labels
      var outerArc = d3
        .arc()
        .outerRadius(radius * 0.9)
        .innerRadius(radius * 0.9);

      // g elements to keep elements within svg modular
      this.chartLayer.append("g").attr("class", "slices");
      this.chartLayer.append("g").attr("class", "labelName");
      this.chartLayer.append("g").attr("class", "lines");

      // Add donut slices
      this.chartLayer
        .select(".slices")
        .selectAll("path")
        //.data(pie(chartdata))
        .data(pie_data)
        .enter()
        .append("path")
        .attr("stroke", "gray")
        .attr("fill", (d, i) => {
          var num = i * 0.1 + 0.2;
          // if (this.id == 'pie2') {
          //     num = (i + 7) * 0.1;
          // }
          return d3.interpolateCool(num);
        })
        .each((d) => {
          d.outerRadius = outerRadius - 20;
        })
        .attr("d", arc)
        .on("mouseover", arcTween(outerRadius, 0))
        .on("mouseout", arcTween(outerRadius - 20, 150));

      // add text labels
      this.chartLayer
        .select(".labelName")
        .selectAll("text")
        .data(pie_data)
        .enter()
        .append("text")
        .attr("dy", ".35em")
        .attr("font-size", "1.0em")
        .attr("font-style", "italic")
        .html(updateLabelText)
        .attr("transform", labelTransform)
        .style("text-anchor", function (d) {
          // if slice centre is on the left, anchor text to start, otherwise anchor to end
          return midAngle(d) < Math.PI ? "start" : "end";
        });

      // this.chartLayer.selectAll("text")
      //     .data(data)
      //     .enter().append("text")
      //         .attr("dx", 20)
      //         .attr("dy", -5)
      //         .append("textPath")
      //         .attr("xlink:href", function(d, i) { return "#arc-" + i; })
      //         .text(function(d) { return d.name })

      // ===========================================================================================
      // add lines connecting labels to slice. A polyline creates straight lines connecting several points
      var polyline = this.chartLayer
        .select(".lines")
        .selectAll("polyline")
        .data(pie_data)
        .enter()
        .append("polyline")
        .attr("points", calculatePoints)
        .attr("fill", "none")
        .attr("stroke", "black");
      // ========================================================================================

      function arcTween(outerRadius, delay) {
        return function () {
          d3.select(this)
            .transition()
            .delay(delay)
            .attrTween("d", function (d) {
              var i = d3.interpolate(d.outerRadius, outerRadius);
              return function (t) {
                d.outerRadius = i(t);
                return arc(d);
              };
            });
        };
      }

      function updateLabelText(d) {
        return d.data.name;
      }

      function labelTransform(d) {
        // effectively computes the centre of the slice.
        // see https://github.com/d3/d3-shape/blob/master/README.md#arc_centroid
        var pos = outerArc.centroid(d);

        // changes the point to be on left or right depending on where label is.
        pos[0] = radius * 0.85 * (midAngle(d) < Math.PI ? 1 : -1);
        pos[1] = pos[1] - 10;
        return "translate(" + pos + ")";
      }

      // calculate the points for the polyline to pass through
      function calculatePoints(d) {
        // see label transform function for explanations of these three lines.
        var pos = outerArc.centroid(d);
        pos[0] = radius * 1.25 * (midAngle(d) < Math.PI ? 1 : -1);
        return [arc.centroid(d), outerArc.centroid(d), pos];
      }

      function midAngle(d) {
        return d.startAngle + (d.endAngle - d.startAngle) / 2;
      }
    },
  },
  watch: {
    chartData: {
      handler: function (val, oldVal) {
        this.drawChart(this.chartdata);
      },
      deep: true,
    },
  },
};
</script>

<style scoped>
.arc:hover {
  transform: scale(1.2);
  cursor: pointer;
}

#chart {
  margin-bottom: 70px;
}

.svg-line-container {
  display: inline-block;
  position: relative;
  width: 100%;
  padding-bottom: 100%;
  vertical-align: top;
  overflow: visible;
}

.svg-content {
  display: inline-block;
  position: absolute;
  overflow: visible !important;
  top: 0;
  left: 0;
}
.label-text {
  font-size: 20px;
  text-anchor: middle;
  text-transform: uppercase;
}

polyline {
  opacity: 0.3;
  stroke: black;
  stroke-width: 2px;
  fill: none;
}

.labelName {
  font-size: 0.8em;
  font-style: italic;
}
</style>
