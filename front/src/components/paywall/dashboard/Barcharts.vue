<template>
  <div class="svg-line-container">
    <svg :id="id" class="svg-content" viewBox="0 0 500 400"></svg>
  </div>
</template>

<script>
import * as d3 from "d3";
import * as underscore from "underscore";

export default {
  name: "barcharts",
  props: ["id", "title", "maxY", "chartData"],
  data() {
    return {
      labelOne: "Tair",
      colorOne: "#4682B4",
      labelTwo: "Repbase",
      colorTwo: "#1AC7C2",
    };
  },
  mounted() {
    //console.log("Before ", this.chartData);
    if (this.chartData && this.chartData.length > 0) {
      //console.log("Mounted ", this.chartData.length);
      var inputData = this.getChartData();
      this.render(inputData);
    }
  },
  computed: {},
  methods: {
    getChartData() {
      var inputData = [];
      var sortedData = underscore.sortBy(this.chartData, "month");
      var tairData = sortedData.filter(function (user) {
        return user.tair > 0;
      });
      tairData.map((d) => {
        if (d.tair > 0) {
          var entry = {
            period: d.month,
            tair: d.tair,
            repbase: 0,
          };
          inputData.push(entry);
        }
      });
      var repbaseData = this.chartData.filter(function (user) {
        return user.repbase > 0;
      });
      // console.log({ repbaseData });
      var values = repbaseData.map((m) => {
        return m.repbase;
      });
      //console.log(values);
      values.forEach((d, i) => {
        inputData[i].repbase = d;
      });
      return inputData;
    },
    render(inputData) {
      //canvas clear all
      d3.select("#" + this.id)
        .selectAll("*")
        .remove();

      var canvasWidth = 500;
      var canvasHeight = 400;
      var margins = { top: 20, bottom: 30, left: 55, rigth: 0 };

      var width = canvasWidth - margins.left - margins.rigth;
      var height = canvasHeight - margins.top - margins.bottom;

      var x = d3
        .scaleBand()
        .domain(
          d3.entries(inputData).map((d) => {
            return d.value.period;
          })
        )
        .rangeRound([0, width])
        .padding(0.2);

      var y = d3.scaleLinear().domain([0, this.maxY]).range([height, 0]);

      // Define the axes
      var xAx = d3.axisBottom(x).tickSize(0);
      var yAx = d3.axisLeft(y);

      var canvas = d3.select("#" + this.id);

      //Axes
      canvas
        .append("g")
        .attr("class", "spark-y axis")
        .attr("transform", "translate(" + margins.left + ",0)")
        .call(yAx);

      canvas
        .append("g")
        .attr("class", "spark-x axis")
        .attr("transform", "translate(" + margins.left + "," + height + ")")
        .call(xAx);

      var yOffset = 20;
      var xOffset = 50;

      //Legend
      canvas
        .append("text")
        .attr("class", "label-text")
        .attr("y", height + 40)
        .attr("x", width / 2 + 30)
        .text(this.title + " (2022)")
        .attr("fill", "black");

      canvas
        .append("rect")
        .attr("y", y(this.maxY) + yOffset)
        .attr("x", width / 2 - 50 + xOffset)
        .attr("transform", "translate(0,-8)")
        .attr("width", 8)
        .attr("height", 8)
        .style("fill", this.colorOne);
      canvas
        .append("text")
        .attr("class", "spark-text")
        .attr("y", y(this.maxY) + yOffset)
        .attr("x", width / 2 - 40 + xOffset)
        .text(this.labelOne)
        .attr("fill", "black");

      canvas
        .append("rect")
        .attr("y", y(this.maxY) + yOffset)
        .attr("x", width / 2 + 50 + xOffset)
        .attr("transform", "translate(0,-8)")
        .attr("width", 8)
        .attr("height", 8)
        .style("fill", this.colorTwo);
      canvas
        .append("text")
        .attr("class", "spark-text")
        .attr("y", y(this.maxY) + yOffset)
        .attr("x", width / 2 + 60 + xOffset)
        .text(this.labelTwo)
        .attr("fill", "black");

      //Bars
      canvas
        .selectAll(".success")
        .data(d3.entries(inputData))
        .enter()
        .append("rect")
        .attr("transform", "translate(" + margins.left + ",0)")
        .attr("class", "success")
        .attr("x", function (d) {
          return x(d.value.period);
        })
        .attr("width", x.bandwidth() / 2)
        .attr("y", function (d) {
          return y(d.value.tair);
        })
        .attr("height", (d) => {
          return height - y(d.value.tair);
        })
        .style("fill", this.colorOne);

      canvas
        .selectAll(".fail")
        .data(d3.entries(inputData))
        .enter()
        .append("rect")
        .attr("transform", "translate(" + margins.left + ",0)")
        .attr("class", "fail")
        .attr("x", function (d) {
          return x(d.value.period) + x.bandwidth() / 2;
        })
        .attr("width", x.bandwidth() / 2)
        .attr("y", function (d) {
          return y(d.value.repbase);
        })
        .attr("height", function (d) {
          return height - y(d.value.repbase);
        })
        .style("fill", this.colorTwo);
    },
  },
  watch: {
    chartData: {
      handler: function (val, oldVal) {
        //console.log(val);
        var inputData = this.getChartData();
        this.render(inputData);
      },
      deep: true,
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
.spark {
  stroke: steelblue;
  stroke-width: 1;
}

.label-text {
  font-size: 20px;
  text-anchor: middle;
  text-transform: uppercase;
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

.axis {
  shape-rendering: crispEdges;
}

.spark-x line {
  stroke: lightgrey;
}

.spark-x .minor {
  stroke-opacity: 0.5;
}

.spark-x path {
  display: block;
}

.spark-y line,
.spark-y path {
  fill: #999;
  stroke: #000;
}

.axis path,
.axis line {
  fill: none;
  stroke: grey;
  stroke-width: 1;
  shape-rendering: crispEdges;
}
</style>
