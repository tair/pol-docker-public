<!--<template>-->
  <!--<div style="margin: 20px;">-->
    <!--&lt;!&ndash;<app-detail-header :title="'Dashboard'"></app-detail-header>&ndash;&gt;-->
    <!--&lt;!&ndash;<app-under-construction></app-under-construction>&ndash;&gt;-->


    <!--<h1>Dashboad 3</h1>-->
    <!--<div id="graph"></div>-->

    <!--<p>-->
      <!--<label for="year">Year :</label>-->
      <!--<input type="text" id="year" style="border:0; color:#f6931f; font-weight:bold; " />-->
    <!--</p>-->
    <!--<div id="slider" style="width: 200px;"></div>-->



  <!--</div>-->
<!--</template>-->

<!--<script>-->
  <!--export default {-->
    <!--data() {-->
      <!--return {-->
      <!--}-->
    <!--},-->
    <!--mounted() {-->

      <!--$(document).ready(function() {-->

        <!--$("#slider").slider({-->
          <!--value:2000,-->
          <!--min: 1971,-->
          <!--max: 2006,-->
          <!--step: 1,-->
          <!--slide: function( event, ui ) {-->
            <!--$("#year").val(ui.value);-->
            <!--redraw(ui.value.toString());-->
          <!--}-->
        <!--});-->
        <!--$("#year").val($("#slider").slider("value") );-->

        <!--var w = 1200;-->
        <!--var h = 500;-->

        <!--var xy = d3.geo.equirectangular().scale(1000);-->

        <!--var path = d3.geo.path()-->
          <!--.projection(xy);-->

        <!--var svg = d3.select("#graph").insert("svg:svg")-->
          <!--.attr("width", w)-->
          <!--.attr("height", h);-->

        <!--var states = svg.append("svg:g")-->
          <!--.attr("id", "states");-->

        <!--var circles = svg.append("svg:g")-->
          <!--.attr("id", "circles");-->

        <!--var labels = svg.append("svg:g")-->
          <!--.attr("id", "labels");-->

        <!--d3.json("/src/assets/data/world-countries.json", function(collection) {-->
          <!--states.selectAll("path")-->
            <!--.data(collection.features)-->
            <!--.enter().append("svg:path")-->
            <!--.attr("d", path)-->
            <!--.on("mouseover", function(d) {-->
              <!--d3.select(this).style("fill","#ff6666")-->
                <!--.append("svg:title")-->
                <!--.text(d.properties.name);})-->
            <!--.on("mouseout", function(d) {-->
              <!--d3.select(this).style("fill","#d6d6c2");})-->
        <!--});-->

        <!--var scalefactor=1./50. ;-->

        <!--d3.csv("/src/assets/data/co2_regions.csv", function(csv) {-->
          <!--circles.selectAll("circle")-->
            <!--.data(csv)-->
            <!--.enter()-->
            <!--.append("svg:circle")-->
            <!--.attr("cx", function(d, i) { return xy([+d["longitude"],+d["latitude"]])[0]; })-->
            <!--.attr("cy", function(d, i) { return xy([+d["longitude"],+d["latitude"]])[1]; })-->
            <!--.attr("r",  function(d) { return (+d["1990"])*scalefactor; })-->
            <!--.attr("title",  function(d) { return d["country"]+": "+Math.round(d["1990"]); })-->
            <!--.on("mouseover", function(d) {-->
              <!--d3.select(this).style("fill","#ff9900");})-->
            <!--.on("mouseout", function(d) {-->
              <!--d3.select(this).style("fill","#0080ff");});-->

          <!--labels.selectAll("labels")-->
            <!--.data(csv)-->
            <!--.enter()-->
            <!--.append("svg:text")-->
            <!--.attr("x", function(d, i) { return xy([+d["longitude"],+d["latitude"]])[0]; })-->
            <!--.attr("y", function(d, i) { return xy([+d["longitude"],+d["latitude"]])[1]; })-->
            <!--.attr("dy", "0.3em")-->
            <!--.attr("text-anchor", "middle")-->
            <!--.text(function(d) { return Math.round(d["1990"]); });-->

        <!--});-->

        <!--function redraw(year) {-->
          <!--circles.selectAll("circle")-->
            <!--.transition()-->
            <!--.duration(500).ease("linear")-->
            <!--.attr("r",  function(d) { return (+d[year])*scalefactor; })-->
            <!--.attr("title",  function(d) { return d["country"]+": "+Math.round(d[year]); });-->

          <!--labels.selectAll("text")-->
            <!--.text(function(d) { return Math.round(d[year]); });-->
        <!--}-->

      <!--});-->
    <!--}-->
  <!--}-->
<!--</script>-->

<!--<style>-->
  <!--body {-->
    <!--font-family: Arial, Helvetica, sans-serif;-->
  <!--}-->

  <!--#states path {-->
    <!--fill: #d6d6c2;-->
    <!--stroke: #8a8a5c;-->
  <!--}-->

  <!--circle {-->
    <!--fill: #008000;-->
    <!--fill-opacity: .6;-->
    <!--stroke: #fff;-->
  <!--}-->
<!--</style>-->
