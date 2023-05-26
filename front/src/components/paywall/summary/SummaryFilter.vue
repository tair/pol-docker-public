<template>
  <div>
    <section style="margin-left: 20px; margin-top: 10px">
      <div class="alert alert-info" id="err-alert">
        <i class="fa fa-exclamation-triangle"></i>
        <strong>Error!</strong>
        <p>{{ errMsg }}</p>
      </div>

      <div class="box box-solid">
        <div class="box-body">
          <form role="form">
            <!-- <input v-show="false" type="text" v-model="summFilter.partner" /> -->
            <div class="form-group">
              <label class="control-label text-primary">Types of Usage</label>
              <br />

              <b-button-group size="sm">
                <b-button
                  pill
                  variant="info"
                  v-for="(btn, idx) in typeUsage"
                  :key="idx"
                  :pressed.sync="btn.state"
                  @click="onTypePress(btn, idx)"
                  >{{ btn.caption }}</b-button
                >
              </b-button-group>
              <p>
                Selected Type:
                <strong>{{ this.selectedType }}</strong>
              </p>
            </div>

            <div class="form-group">
              <label class="control-label text-primary">Partners</label>
              <br />

              <b-button-group size="sm">
                <b-button
                  pill
                  variant="info"
                  v-for="(btn, idx) in partners"
                  :key="idx"
                  :pressed.sync="btn.state"
                  @click="onPartnerPress(btn, idx)"
                  >{{ btn.caption }}</b-button
                >
              </b-button-group>
              <p>
                Selected Partner:
                <strong>{{ this.selectedPartner }}</strong>
              </p>
            </div>

            <div class="form-group">
              <label class="control-label text-primary">Years</label>
              <br />

              <b-button-group size="sm">
                <b-button
                  pill
                  variant="info"
                  v-for="(btn, idx) in years"
                  :key="idx"
                  :pressed.sync="btn.state"
                  @click="onYearPress(btn, idx)"
                  >{{ btn.caption }}</b-button
                >
              </b-button-group>
              <p>
                Selected Year:
                <strong>{{ this.selectedYear }}</strong>
              </p>
            </div>
            <hr style="border: 0.5px solid #efefef" />
          </form>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import * as summary_types from "../../../store/types_summary";
import { mapActions } from "vuex";
import { mapGetters } from "vuex";

const types = { ...summary_types };

export default {
  data() {
    return {
      summFilter: null,
      errMsg: null,
      typeUsage: [
        { caption: "Pageview Count", state: false, val: "pv_count" },
        { caption: "Sessions Count", state: true, val: "sess_count" },
      ],
      partners: [
        { caption: "Tair", state: true },
        { caption: "Biocyc", state: false },
        { caption: "Morphobank", state: false },
        { caption: "Repbase", state: false },
      ],
      years: [
        { caption: "2022", state: true },
        { caption: "2021", state: false },
        { caption: "2020", state: false },
        { caption: "2019", state: false },
        { caption: "2018", state: false },
        { caption: "2017", state: false },
        { caption: "2016", state: false },
      ],
      selectedPartner: "Tair",
      selectedYear: "2021",
      selectedType: "sess_count",
    };
  },
  components: {},
  created() {
    // initialize the filter values from the store.
    // this can then be persisted to DB.
    this.summFilter = this.stateSummFilter;
    console.log("sessFilter ", this.summFilter);
  },
  computed: {
    ...mapGetters({
      stateSummFilter: types.SUMM_GET_FILTER,
    }),
    btnStates() {
      return this.partners.map((btn) => btn.state);
    },
  },
  methods: {
    ...mapActions({
      stateSummarySearchByFilter: types.SUMM_ACTION_FILTER,
    }),
    onTypePress(btn, idx) {
      this.typeUsage.forEach((b) => {
        b.state = false;
      });
      this.typeUsage[idx].state = true;
      this.selectedType = this.typeUsage[idx].val;
      this.summFilter.type = this.selectedType.toLowerCase();
      this.stateSummarySearchByFilter(this.summFilter);
    },
    onPartnerPress(btn, idx) {
      this.partners.forEach((b) => {
        b.state = false;
      });
      this.partners[idx].state = true;
      this.selectedPartner = this.partners[idx].caption;
      this.summFilter.partner = this.selectedPartner.toLowerCase();
      this.stateSummarySearchByFilter(this.summFilter);
    },
    onYearPress(btn, idx) {
      this.years.forEach((b) => {
        b.state = false;
      });
      this.years[idx].state = true;
      this.selectedYear = this.years[idx].caption;
      this.summFilter.year = this.selectedYear.toLowerCase();
      this.stateSummarySearchByFilter(this.summFilter);
    },
    onSubmit() {
      this.errMsg = null;
      this.stateSummarySearchByFilter(this.summFilter);
    },
    onErrMsg(msg) {
      this.errMsg = msg;
      $("#err-alert")
        .fadeTo(4000, 500)
        .slideUp(500, function () {
          $("#err-alert").slideUp(500);
        });
    },
  },
  mounted() {
    this.summFilter = this.stateSummFilter;
    $("#err-alert").hide();
    this.stateSummarySearchByFilter(this.summFilter);
  },
};
</script>

<style scoped>
</style>
