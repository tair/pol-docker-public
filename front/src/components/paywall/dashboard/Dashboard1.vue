<template>
  <div>
    <!--<app-detail-header :title="'Dashboard'"></app-detail-header>-->
    <!--<app-under-construction></app-under-construction>-->
    <div class="row">
      <div class="col-sm-12">
        <div class="mylabel">
          <label>Page Views by Country</label>
        </div>
        <app-map :w="'1400'" :h="'600'" :month="currMonth"></app-map>
      </div>
      <div class="col-sm-12">
        <div class="mylabel">
          <h1>
            <label>Year: {{ currMonth }}/2017</label>
          </h1>
          <input
            id="ex1"
            data-slider-id="monthslider"
            type="text"
            data-slider-min="1"
            data-slider-max="12"
            data-slider-step="1"
            data-slider-value="1"
          />
          <!--<button class="btn btn-danger btn-sm btn-block margin-bottom"-->
          <!--@click="changeMonth()">{{currMonth}}</button>-->
          <br />
        </div>
      </div>
      <div class="col-sm-6">
        <app-bars
          :id="'tair'"
          :month="currMonth"
          :bars_data="all_views_tair"
        ></app-bars>
      </div>
      <div class="col-sm-6">
        <app-bars
          :id="'biocyc'"
          :month="currMonth"
          :bars_data="all_views_biocyc"
        ></app-bars>
      </div>
      <div class="col-sm-12">
        <br />
        <hr />
        <br />
      </div>
      <div class="col-sm-12">
        <div class="mylabel">
          <label>Page Views by Partner</label>
        </div>
      </div>
      <div class="col-sm-6">
        <piechart :id="'pie1'" :label="'Tair'" :chartdata="pie_tair"></piechart>
      </div>
      <div class="col-sm-6">
        <piechart
          :id="'pie2'"
          :label="'Biocyc'"
          :chartdata="pie_biocyc"
        ></piechart>
      </div>
      <div class="col-sm-12">
        <div class="mylabel">
          <label>Page Views by Month</label>
          <br /><br />
        </div>
      </div>
      <div class="col-sm-6">
        <barcharts
          ref="sparks"
          :id="'total_views'"
          :title="'Total Views'"
          :chartData="all_view_counts"
          :maxY="12000000"
        >
        </barcharts>
      </div>
      <div class="col-sm-6">
        <barcharts
          ref="sparks"
          :id="'paid_views'"
          :title="'Paid Content Views'"
          :chartData="paid_views"
          :maxY="3200000"
        >
        </barcharts>
      </div>
      <div class="col-sm-6">
        <barcharts
          ref="sparks"
          :id="'institution_views'"
          :title="'Institution Views'"
          :chartData="inst_views"
          :maxY="2200000"
        >
        </barcharts>
      </div>
      <div class="col-sm-6">
        <barcharts
          ref="sparks"
          :id="'search_engine_views'"
          :title="'Search Engine Views'"
          :chartData="search_views"
          :maxY="7500000"
        >
        </barcharts>
      </div>
      <div class="col-sm-6">
        <barcharts
          ref="sparks"
          :id="'unsubscribed_views'"
          :title="'Unsubscribed User Views'"
          :chartData="unsub_views"
          :maxY="4000000"
        >
        </barcharts>
      </div>
      <div class="col-sm-6">
        <barcharts
          ref="sparks"
          :id="'individual_sub_views'"
          :title="'Subscribed User Views'"
          :chartData="indi_views"
          :maxY="300000"
        >
        </barcharts>
      </div>
    </div>
  </div>
</template>

<script>
import WorldMap from "./WorldMap";
import StackedSimpleBar from "./StackedSimpleBar";
import barcharts from "./Barcharts";
import piechart from "./PieChart";

import * as d3 from "d3";

export default {
  components: {
    appMap: WorldMap,
    appBars: StackedSimpleBar,
    barcharts: barcharts,
    piechart: piechart,
  },
  data() {
    return {
      currMonth: 1,
      all_views_tair: [],
      all_views_biocyc: [],
      all_view_counts: [],
      paid_views: [],
      inst_views: [],
      search_views: [],
      indi_views: [],
      unsub_views: [],
      colorOne: "#4682b4",
      colorTwo: "#B1B5B5",
      pie_tair: [
        { name: "Institution", value: 19533070 },
        { name: "Subscribed", value: 986675 },
        { name: "Unsubscribed", value: 19635041 },
        { name: "Search Engine", value: 13475400 },
      ],
      pie_biocyc: [
        { name: "Institution", value: 5575736 },
        { name: "Subscribed", value: 869691 },
        { name: "Unsubscribed", value: 25158873 },
        { name: "Search Engine", value: 35166412 },
      ],
    };
  },
  mounted() {
    this.loadAllViews();
    var slider = new Slider("#ex1", {});
    slider.on("change", (data) => {
      this.currMonth = data.newValue;
    });
  },
  computed: {},
  methods: {
    changeMonth() {
      this.currMonth++;
    },
    loadAllViews() {
      d3.csv("/src/assets/data/bycountries_tair.csv", (csv) => {
        csv.map((d) => {
          if (d.year == 2017) {
            for (let i = 1; i <= 12; i++) {
              if (d.month == i) {
                this.all_views_tair.push({
                  code: d.country_code,
                  name: d.country_name,
                  count: parseInt(d.views),
                  month: d.month,
                });
              }
            }
          }
        });
      });
      d3.csv("/src/assets/data/bycountries_biocyc.csv", (csv) => {
        csv.map((d) => {
          if (d.year == 2017) {
            for (let i = 1; i <= 12; i++) {
              if (d.month == i) {
                this.all_views_biocyc.push({
                  code: d.country_code,
                  name: d.country_name,
                  count: parseInt(d.views),
                  month: d.month,
                });
              }
            }
          }
        });
      });
      // this.all_view_counts = [{
      //         type: "total_views",
      //         values: []
      //     },{
      //         type: "paid_content_views",
      //         values: []
      //     },{
      //         type: "search_engine_views",
      //         values: []
      //     }];

      d3.csv("/src/assets/data/page_view_stats_count.csv", (csv) => {
        var total_values = [];
        var paid_values = [];
        var inst_values = [];
        var search_values = [];
        var indi_values = [];
        var unsub_values = [];
        csv.map((d) => {
          if (d.year == 2017) {
            for (let i = 1; i <= 12; i++) {
              if (d.month == i) {
                var total_views = {
                  month: parseInt(d.month),
                  year: parseInt(d.year),
                  tair: 0,
                  biocyc: 0,
                };
                if (d.partner_id == "tair") {
                  total_views.tair = parseInt(d.total_views);
                } else if (d.partner_id == "biocyc") {
                  total_views.biocyc = parseInt(d.total_views);
                }
                total_values.push(total_views);

                var paid_views = {
                  month: parseInt(d.month),
                  year: parseInt(d.year),
                  tair: 0,
                  biocyc: 0,
                };
                if (d.partner_id == "tair") {
                  paid_views.tair = parseInt(d.paid_content_views);
                } else if (d.partner_id == "biocyc") {
                  paid_views.biocyc = parseInt(d.paid_content_views);
                }
                paid_values.push(paid_views);

                var inst_views = {
                  month: parseInt(d.month),
                  year: parseInt(d.year),
                  tair: 0,
                  biocyc: 0,
                };
                if (d.partner_id == "tair") {
                  inst_views.tair = parseInt(d.institution_views);
                } else if (d.partner_id == "biocyc") {
                  inst_views.biocyc = parseInt(d.institution_views);
                }
                inst_values.push(inst_views);

                var search_views = {
                  month: parseInt(d.month),
                  year: parseInt(d.year),
                  tair: 0,
                  biocyc: 0,
                };
                if (d.partner_id == "tair") {
                  search_views.tair = parseInt(d.search_engine_views);
                } else if (d.partner_id == "biocyc") {
                  search_views.biocyc = parseInt(d.search_engine_views);
                }
                search_values.push(search_views);

                var indi_views = {
                  month: parseInt(d.month),
                  year: parseInt(d.year),
                  tair: 0,
                  biocyc: 0,
                };
                if (d.partner_id == "tair") {
                  indi_views.tair = parseInt(d.indi_sub_views);
                } else if (d.partner_id == "biocyc") {
                  indi_views.biocyc = parseInt(d.indi_sub_views);
                }
                indi_values.push(indi_views);

                var unsub_views = {
                  month: parseInt(d.month),
                  year: parseInt(d.year),
                  tair: 0,
                  biocyc: 0,
                };
                if (d.partner_id == "tair") {
                  unsub_views.tair = parseInt(d.unsubscribed_views);
                } else if (d.partner_id == "biocyc") {
                  unsub_views.biocyc = parseInt(d.unsubscribed_views);
                }
                unsub_values.push(unsub_views);
              }
            }
          }
        });
        //console.log(this.all_view_counts);
        this.all_view_counts = total_values;
        this.paid_views = paid_values;
        this.inst_views = inst_values;
        this.search_views = search_values;
        this.indi_views = indi_values;
        this.unsub_views = unsub_values;
        //console.log(this.all_view_counts);
      });
    },
  },
};
</script>

<style scoped>
/*body {*/
/*font-family: Arial, Helvetica, sans-serif;*/
/*}*/
#monthslider {
  width: 100%;
}
#ex1Slider .slider-selection {
  background: #bababa;
}
.mylabel {
  width: 100%;
  text-align: center;
  margin: auto;
  font-size: 2.2em;
  font-style: italic;
  text-anchor: middle;
  color: black;
}
h1 {
  align: center;
  color: #0d6aad;
}
</style>
