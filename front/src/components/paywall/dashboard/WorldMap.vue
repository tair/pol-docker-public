<template>
    <div style="margin: 20px;">
        <div id="graph">
            <svg :width="w" :height="h">
                <g id="states"></g>
                <g id="circles"></g>
            </svg>
        </div>
    </div>
</template>

<script>
    import * as d3 from 'd3';

    export default {
        props: ["w","h","month"],
        watch: {
            month: function(newVal, oldVal) { // watch it
                //console.log('Prop changed: ', newVal, ' | was: ', oldVal);
                this.redraw();
            }
        },
        data() {
            return {
                currMonth: 1,
                country_views_per_year: []
            }
        },
        mounted() {
            console.log('test...');
            this.onInit();
        },
        computed: {
            svg() {
                return d3.select("svg");
            },
            circles() {
                return this.svg.select("#circles");
            }
        },
        methods: {
            redraw() {
                this.circles.selectAll("circle")
                    .transition()
                    .duration(500).ease(d3.easeLinear)
                    .attr("r", (d) => {
                        var views = 0;
                        this.country_views_per_year.forEach(v => {
                            if (v.code === d[0] & v.month == this.month) {
                                views = v.views;
                            }
                        });
                        var radius = views * 0.0005;
                        if (radius > 17) radius = 17 + views * 0.000009;
                        if(radius < 2) radius = 2;
                        return radius;
                    });
            },
            onInit() {
                var projection;
                var states;
                var labels;

                setTimeout(() => {
                    projection = d3.geoEquirectangular()
                        .scale(220).translate([this.w / 2 - 120, this.h / 2 + 50]);

                    var path = d3.geoPath().projection(projection);

                    states = this.svg.select("#states");

                    labels = this.svg.append("svg:g")
                        .attr("id", "labels");

                    d3.json("/src/assets/data/world-countries.json", function (collection) {
                        states.selectAll("path")
                            .data(collection.features)
                            .enter().append("svg:path")
                            .attr("d", path)
                            .on("mouseover", function (d) {
                                d3.select(this).style("fill", "#ff6666")
                                    .append("svg:title")
                                    .text(d.properties.name);
                            })
                            .on("mouseout", function (d) {
                                d3.select(this).style("fill", "#d6d6c2");
                            })
                    });
                }, 500)

                var country_location = [];
                var country_info = [];
                var country_codes = [];
                d3.json("/src/assets/data/countries.json", function (countries) {
                    countries.map(c => {
                        var locn = {code: c.cca2, locn: c.latlng};
                        country_location.push(locn);
                    });
                });
                d3.csv("/src/assets/data/bycountries.csv", (csv) => {
                    csv.map(d => {
                        if (country_codes.indexOf(d.country_code) === -1) {
                            var info = [d.country_code, d.latitude, d.longitude];
                            country_info.push(info);
                            country_codes.push(d.country_code);
                        }
                        if (d.year == 2017) {
                            for (var i = 1; i <= 12; i++) {
                                if (d.month == i) {
                                    this.country_views_per_year.push(
                                        {
                                            code: d.country_code,
                                            views: d.views,
                                            month: i
                                        }
                                    );
                                }
                            }
                        }
                    });
                });

                // console.log(country_info.length);

                setTimeout(() => {
                    var that = this;
                    console.log(country_location.length);
                    country_info.forEach(i => {
                        var found = country_location.find(function (element) {
                            return element.code === i[0];
                        });
                        if (found) {
                            i[1] = found.locn[0];
                            i[2] = found.locn[1];
                            //console.log(i);
                        }
                    });
                    //console.log(country_views_per_year);
                    that.circles.selectAll("circle")
                        .data(country_info)
                        .enter()
                            .append("svg:circle")
                            .attr("cx", function (d, i) {
                                //console.log(d);
                                return projection([+d[2], +d[1]])[0];
                            })
                            .attr("cy", function (d, i) {
                                return projection([+d[2], +d[1]])[1];
                            })
                            .attr("r", function (d) {
                                var views = 0;
                                that.country_views_per_year.forEach(v => {
                                    if (v.code === d[0]) {
                                        views = v.views;
                                    }
                                });
                                var radius = views * 0.0005;
                                if (radius > 17) radius = 17 + views * 0.000009;
                                if(radius < 2) radius = 2;
                                return radius;
                            })
                            .on("mouseover", function (d) {
                                d3.select(this).style("fill", "#ff9900");
                            })
                            .on("mouseout", function (d) {
                                d3.select(this).style("fill", "#0080ff");
                            });

                    // labels.selectAll("labels")
                    //     .data(country_info)
                    //     .enter()
                    //         .append("svg:text")
                    //         .attr("x", function(d, i) {
                    //             return xy([+d[2], +d[1]])[0];
                    //         })
                    //         .attr("y", function(d, i) {
                    //             return xy([+d[2], +d[1]])[1];
                    //         })
                    //         .attr("dy", "0.3em")
                    //         .attr("text-anchor", "middle")
                    //         .text(function(d) {
                    //             var views = 0;
                    //             that.country_views_per_year.forEach(v => {
                    //                 if (v.code === d[0]) {
                    //                     views = v.views;
                    //                 }
                    //             });
                    //             var text_views = "";
                    //             if(views < 1000) {
                    //                 text_views = "";
                    //             } else {
                    //                 text_views = views;
                    //             }
                    //             return text_views;
                    //         });

                }, 2000);
            }
        }
    }
</script>

<style>
    /*body {*/
    /*font-family: Arial, Helvetica, sans-serif;*/
    /*}*/
    .svg-line-container {
        display: inline-block;
        position: relative;
        width: 100%;
        padding-bottom: 100%;
        vertical-align: top;
        overflow: visible;
    }

    #ex1Slider {
        width: 70%
    }
    #ex1Slider .slider-selection {
        background: #BABABA;
    }

    #states path {
        fill: #d6d6c2;
        stroke: #8a8a5c;
    }

    circle {
        fill: #0080ff;
        fill-opacity: .6;
        stroke: #fff;
    }

    /*text {*/
        /*visibility: hidden;*/
    /*}*/

    text:hover {
        visibility: visible;
    }
</style>
