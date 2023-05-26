<template>
    <section class="content">
        <div class="row">
            <div class="col-sm-6">
                <label class="title">{{id}}</label>
                <svg :id="id" width="800" :height="200">
                    <g v-for="node,i in nodes" class="bars">
                        <text x="-120" :y="node.y+15">{{node.text}}</text>
                        <rect :x="node.x" :y="node.y" :height="node.height" :width="node.width"
                              :style="node.style"
                              :key="node.id"
                              v-on:click="select(i, node)"
                              v-on:mouseover="onHover($event, i,node)"
                              v-on:mouseout="onOut()"></rect>
                        <!--<text x="120" :y="node.y+15">{{node.views}}</text>-->
                    </g>
                    <!--<g class="tooltip">-->
                    <!--<rect width="30" height="20"></rect>-->
                    <!--<text x="7" dy="1.2em" style="{text-anchor: middle}" font-size="12px"-->
                    <!--font-weight="bold">Test</text>-->
                    <!--</g>-->
                </svg>
            </div>
        </div>

    </section>
</template>

<script>
    import * as d3 from 'd3';
    import * as underscore from 'underscore';

    export default {
        name: "stacked-simple-bar",
        props: ["id","month", "bars_data"],
        watch: {
            month: function (newVal, oldVal) { // watch it
                //console.log('Prop changed: ', newVal, ' | was: ', oldVal);
                this.redraw();
            },
            bars_data: function (newVal, oldVal) { // watch it
                console.log('Prop changed: ', newVal.length, ' | was: ', oldVal.length);
                //this.redraw();
            }
        },
        data() {
            return {
                colors: ['#FBB65B', '#513551', '#de3163']
            }
        },
        mounted() {
            this.setCountryViews();
        },
        computed: {
            bars() {
                return d3.select(".bars");
            },
            stackedStruct() {
                return d3.stack().keys(['count']);
            },
            nodes() {
                if (this.month && this.bars_data) {
                    var monthViews = [];
                    this.bars_data.map((d) => {
                        if(d.month == this.month) {
                            monthViews.push(d);
                        }
                    });

                    var topViews = underscore.sortBy(monthViews, 'count');
                    var top10Views = topViews.reverse().slice(0, 10);
                    //console.log(top10Views.map((d)=>d.count));

                    var stackedSeries = this.stackedStruct(top10Views);
                    var rects = [];
                    stackedSeries.forEach((key, i) => {
                        // console.log(key);
                        key.forEach((value, j) => {
                            var width = (value[1] - value[0]) * 0.0005;
                            if(width > 490) width = 490;
                            var num = j * 0.1 + 0.2;

                            var singleRect = {
                                id: j,
                                style: {
                                    fill: d3.interpolateCool(num)
                                },
                                width: width,
                                height: 18,
                                x: value[0],
                                y: j * 20,
                                text: value.data.name,
                                views: value[1]
                            };
                            rects.push(singleRect);
                        });
                    });
                    return rects;
                }
            },
            tooltipComp() {
                var tooltip = d3.select(".tooltip");
                return tooltip;
            }
        },
        methods: {
            redraw() {
                if(this.bars_data == undefined) return;

                var monthViews = [];
                //console.log("Bars ", this.bars_data);
                this.bars_data.map((d) => {
                    if(d.month == this.month) {
                        monthViews.push(d);
                    }
                });
                //console.log(monthViews);

                var topViews = underscore.sortBy(monthViews, 'count');
                topViews = topViews.reverse().slice(0, 10);
                //console.log(topViews);

                var viewArray = topViews.map(x=>x.count);
                //console.log(viewArray);
                var namesArray = topViews.map(x=>x.name);
                //console.log(codesArray);
                var data = viewArray.map(x => x*0.0005);

                var id = "#"+this.id;
                var svg = d3.select(id);
                //console.log("Redraw");
                setTimeout(() => {

                },100);
                svg.selectAll("rect")
                    .data(data)
                    .transition()
                    .duration(1000).ease(d3.easeLinear)

                    .attr("width", (d) => {
                        if(d > 490) d = 490;
                        return d;
                    });
                svg.selectAll("text")
                    .data(namesArray)
                    .style("opacity", 0.5)
                    .transition()
                    .duration(500).style("opacity", 1.0)
                    .text(function(d) { return d; });
            },
            setCountryViews() {
                this.country_views_per_year = [];
                d3.csv("/src/assets/data/bycountries.csv", (csv) => {
                    csv.map(d => {
                        if (d.year == 2017) {
                            if (d.month == this.month) {
                                this.country_views_per_year.push(
                                    {
                                        code: d.country_code,
                                        name: d.country_name,
                                        views: parseInt(d.views),
                                        month: this.month
                                    }
                                );
                            }
                        }
                    });
                });
            },
            select(i, node) {
                console.log(node);
            },
            onHover(event, i, node) {
                this.tooltipComp.style("display", null);
                this.tooltipComp.select("text").text(node.width);
                var xPosn = node.x + node.width / 2 + 70;
                var yPosn = node.y + 10;
                this.tooltipComp.attr("transform", "translate(" + xPosn + "," + yPosn + ")");
            },
            onOut() {
                this.tooltipComp.style("display", "none");
            }
        }
    }
</script>

<style scoped>
    rect {
        fill: steelblue;
    }
    text {

    }

    .title {

    }

    /*&:hover*/
    /*fill: yellow*/
    /*cursor: pointer*/
    .bars {
        transform: translate(120px, 0px);
        cursor: pointer;
    }

    .tooltip {
        opacity: 0.7;

    }

    .tooltip rect {
        fill: white;
        transform: translate(-800px, 30px);
    }

    .tooltip text {
        fill: black;
    }
</style>