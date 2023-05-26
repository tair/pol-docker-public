<template>
  <div>
    <app-detail-header :title="'Usage Summary'"></app-detail-header>
    <!-- <div class="row">
      <div class="col-sm-12 d-flex justify-content-center">
        <piechart :id="'pie1'" :label="'Tair'" :chartdata="pie_tair"></piechart>
      </div>
    </div> -->
    <div class="row ml-4 mb-2" style="width: 75%">
      <div class="col-sm-6">
        <label for="year-select">Select Year:</label>
        <select id="year-select" v-model="selectedYear" @change="onYearChange">
          <option v-for="year in years" :key="year" :value="year">
            {{ year }}
          </option>
        </select>
      </div>
      <!-- <div class="col-sm-6">
        <label for="partner-select">Select Partners:</label>
        <select id="partner-select" v-model="selectedPartners" multiple>
          <option v-for="partner in partners" :key="partner" :value="partner">
            {{ partner }}
          </option>
        </select>
      </div> -->
    </div>
    <div class="row ml-4" style="width: 75%">
      <div class="col-sm-12 d-flex justify-content-center">
        <barcharts
          ref="sparks"
          :id="'total_views'"
          :title="'Total Views'"
          :chartData="all_view_counts"
          :maxY="5000000"
        >
        </barcharts>
      </div>
    </div>
  </div>
</template>

<script>
import piechart from "../../components/paywall/dashboard/PieChart.vue";
import barcharts from "../../components/paywall/dashboard/Barcharts";
import * as d3 from "d3";
export default {
  components: {
    piechart: piechart,
    barcharts: barcharts,
  },
  data() {
    return {
      pie_tair: [
        { name: "Institution", value: 19533070 },
        { name: "Subscribed", value: 986675 },
        { name: "Unsubscribed", value: 19635041 },
        { name: "Search Engine", value: 13475400 },
      ],
      all_view_counts: [],
      years: [2020, 2021, 2022, 2023], // Change this to your available years
      partners: ["Partner A", "Partner B", "Partner C"], // Change this to your available partners
      selectedYear: 2022,
      selectedPartners: [],
      repbaseViews: [],
      tairViews: [],
    };
  },
  mounted() {
    d3.csv("/src/assets/data/page_view_stats_count.csv", (csv) => {
      this.repbaseViews = csv.filter((d) => d["partner_id"] == "repbase");
      this.tairViews = csv.filter((d) => d["partner_id"] == "tair");
      this.updateBarCharts();
    });
  },
  methods: {
    onYearChange() {
      console.log("Year changed to: ", this.selectedYear);
      this.updateBarCharts();
    },
    updateBarCharts() {
      var total_values = [];
      this.tairViews.forEach((d) => {
        if (parseInt(d.year) == this.selectedYear) {
          var total_views = {
            month: parseInt(d.month),
            year: parseInt(d.year),
            tair: parseInt(d.total_views),
          };
          total_values.push(total_views);
        }
      });
      this.repbaseViews.forEach((d) => {
        if (parseInt(d.year) == this.selectedYear) {
          total_values.forEach((t) => {
            if (t.month == parseInt(d.month) && t.year == parseInt(d.year)) {
              t["repbase"] = parseInt(d.total_views);
            }
          });
        }
      });
      this.all_view_counts = total_values;
    },
  },
};
</script>
<style scoped>
.chartContainer {
  width: 100%;
  height: 100%;
}

@media (max-width: 768px) {
  .chartContainer {
    width: 100%;
    height: 50%;
  }
}
</style>
