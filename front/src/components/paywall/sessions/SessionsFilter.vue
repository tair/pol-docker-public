<template>
  <div>
    <section style="margin-left: 20px; margin-top: 10px">
      <button
        @click.prevent="onSubmit()"
        class="btn btn-danger btn-sm btn-block margin-bottom"
      >
        <i class="fa fa-search"></i> Search
      </button>

      <div class="alert alert-info" id="err-alert">
        <i class="fa fa-exclamation-triangle"></i>
        <strong>Error!</strong>
        <p>{{ errMsg }}</p>
      </div>

      <div class="box box-solid">
        <div class="box-body">
          <form role="form">
            <label class="control-label text-primary">Date Range</label>

            <div class="row">
              <div class="col-md-6">
                <div class="form-group">
                  <div class="input-group">
                    <input
                      type="text"
                      class="form-control"
                      id="ssnDtFrom"
                      placeholder="from date"
                      autocomplete="off"
                    />
                    <div class="input-group-addon">
                      <i class="fa fa-calendar"></i>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-md-6">
                <div class="form-group">
                  <div class="input-group">
                    <input
                      type="text"
                      class="form-control"
                      id="ssnDtTo"
                      placeholder="to date"
                      autocomplete="off"
                    />
                    <div class="input-group-addon">
                      <i class="fa fa-calendar"></i>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <input v-show="false" type="text" v-model="sessFilter.partyType" />
            <input v-show="false" type="text" v-model="sessFilter.partyId" />

            <div class="form-group">
              <label class="control-label text-primary">Partners</label>
              <br />
              <div v-if="statePrtrIsError" class="text-danger">
                <i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
                Error occurred while loading the data.
              </div>
              <div
                v-show="
                  filteredPrtrData != null &&
                  filteredPrtrData.length > 0 &&
                  !statePrtrIsError
                "
              >
                <span
                  v-for="partner in filteredPrtrData"
                  style="margin-right: 5px"
                >
                  <input
                    type="radio"
                    :id="'partner' + partner.partnerId"
                    :value="partner.partnerId"
                    v-model="sessFilter.partner"
                  />
                  {{ partner.partnerId | capitalize }}
                </span>
              </div>
              <div v-if="statePrtrIsLoading" class="overlay">
                <i class="fa fa-refresh fa-spin"></i>
              </div>

              <!-- <hr style="border: 0.5px solid #efefef" /> -->

              <!-- <label class="control-label text-primary">Page Views</label> -->

              <!-- <div style="padding-left: 10px; padding-right: 10px;">
                <input
                  id="pageHitMinMax"
                  type="text"
                  class="slider-pageHitMinMax form-control"
                  data-slider-min="0"
                  data-slider-max="20000"
                  data-slider-step="50"
                  data-slider-value="[0,20000]"
                  data-slider-orientation="horizontal"
                  data-slider-selection="before"
                  data-slider-id="blue"
                />
              </div>-->
            </div>
            <hr style="border: 0.5px solid #efefef" />

            <div class="nav-tabs-custom">
              <ul class="nav nav-tabs">
                <li class="active">
                  <a
                    style="color: cornflowerblue"
                    href="#tab_1"
                    @click="sessFilter.partyType = 'organization'"
                    data-toggle="tab"
                    >Institution</a
                  >
                </li>
                <li>
                  <a
                    style="color: cornflowerblue"
                    href="#tab_2"
                    data-toggle="tab"
                    @click="sessFilter.partyType = 'consortium'"
                    >Consortium</a
                  >
                </li>
                <li>
                  <a
                    style="color: cornflowerblue"
                    href="#tab_3"
                    data-toggle="tab"
                    @click="sessFilter.partyType = null"
                    >IP Range</a
                  >
                </li>
              </ul>
              <div class="tab-content">
                <div class="tab-pane active" id="tab_1">
                  <appInstitution
                    @rowClicked="selectedInstitution = $event"
                  ></appInstitution>
                </div>
                <!-- /.tab-pane -->
                <div class="tab-pane" id="tab_2">
                  <appConsortium
                    @rowClicked="selectedConsortium = $event"
                  ></appConsortium>
                </div>
                <!-- /.tab-pane -->
                <div class="tab-pane" id="tab_3">
                  <!--<appIpRange></appIpRange>-->
                  <div>
                    <div class="text-green" style="margin-bottom: 10px">
                      Enter multiple IP ranges, one per line. Examples for IP
                      Ranges are as follows:
                      <pre>
10.20.108.9 - 10.20.108.255
10.20.30.* - 10.20.80.*
10.20.30.*
*.*.*.* (for all IPs)
</pre
                      >
                    </div>
                    <textarea
                      class="form-control"
                      v-model="sessFilter.ipRange"
                      rows="10"
                      placeholder="Enter IP Ranges"
                    ></textarea>
                  </div>
                </div>
              </div>
            </div>
          </form>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import * as sessions_types from "../../../store/types_sessions";
import * as partner_types from "../../../store/types_partner";
import { mapActions } from "vuex";
import { mapGetters } from "vuex";

import Institution from "./sections/Institution";
import Consortium from "./sections/Consortium";
import IpRange from "./sections/IpRange";

const types = { ...sessions_types, ...partner_types };

export default {
  data() {
    return {
      sessFilter: null,
      selectedConsortium: null,
      selectedInstitution: null,
      errMsg: null,
    };
  },
  components: {
    appInstitution: Institution,
    appConsortium: Consortium,
    appIpRange: IpRange,
  },
  created() {
    // initialize the filter values from the store.
    // this can then be persisted to DB.
    this.sessFilter = this.stateSessFilter;
    // console.log(this.sessFilter);
    this.statePrtrGetData();
  },
  computed: {
    ...mapGetters({
      stateSessFilter: types.SESS_GET_FILTER,
      statePrtrIsError: types.PRTR_IS_ERROR,
      statePrtrIsLoading: types.PRTR_IS_LOADING,
      statePrtrData: types.PRTR_GET_DATA,
    }),
    filteredPrtrData() {
      return this.statePrtrData.filter((element) => {
        return !["phoenix", "test", "agbase"].includes(element.partnerId);
      });
    },
  },
  methods: {
    ...mapActions({
      // stateUsageSearchByFilter: types.USG_ACTION_FILTER,
      stateSessionsSearchByFilter: types.SESS_ACTION_FILTER,
      statePrtrGetData: types.PRTR_ACTION_GET_DATA,
    }),
    onSubmit() {
      this.errMsg = null;

      ////////////////////////////////////////////
      // this.sessFilter.pageHitMin = this.sessFilter.pageHitMax = 0;
      // var pageHitMinMaxStr = $("#pageHitMinMax")[0].getAttribute("data-value");
      // var defaultPageHitVal = $("#pageHitMinMax")[0].getAttribute(
      //   "data-slider-value"
      // );
      // if ("[" + pageHitMinMaxStr + "]" !== defaultPageHitVal) {
      //   this.sessFilter.pageHitMin = Number(pageHitMinMaxStr.split(",")[0]);
      //   this.sessFilter.pageHitMax = Number(pageHitMinMaxStr.split(",")[1]);
      // }
      ////////////////////////////////////////////

      if ($("#ssnDtFrom").val() == "") {
        this.onErrMsg("From Date is required.");
        return;
      }

      this.sessFilter.dateFrom = $("#ssnDtFrom").val();

      if ($("#ssnDtTo").val() == "") $("#ssnDtTo").val($("#ssnDtFrom").val());

      this.sessFilter.dateTo = $("#ssnDtTo").val();

      if (this.sessFilter.partyType == "organization") {
        if (!this.selectedInstitution || !this.selectedInstitution.partyId) {
          this.onErrMsg("Please select an institution.");
          return;
        }
        this.sessFilter.partyId = this.selectedInstitution.partyId;
      } else if (this.sessFilter.partyType == "consortium") {
        if (!this.selectedConsortium || !this.selectedConsortium.partyId) {
          this.onErrMsg("Please select a consortium.");
          return;
        }
        this.sessFilter.partyId = this.selectedConsortium.partyId;
      } else {
        var ipRange = this.sessFilter.ipRange;
        this.sessFilter.ipRange = null;
        if (!ipRange) {
          this.onErrMsg("Please enter IP Range(s).");
          return;
        }
        if (ipRange != null && ipRange.length > 0) {
          if (ipRange.indexOf("\n") != -1) ipRange = ipRange.split("\n");
          if (typeof ipRange == "string") ipRange = [ipRange];
          this.sessFilter.ipRange = ipRange;
        }
      }
      this.stateSessionsSearchByFilter(this.sessFilter);
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
    var startDate = new Date();
    var FromEndDate = new Date();

    $(".slider-pageHitMinMax").slider();

    $("#ssnDtFrom")
      .datepicker({
        autoclose: true,
        minViewMode: 1,
        format: "mm/yyyy",
      })
      .on("changeDate", function (selected) {
        startDate = new Date(selected.date.valueOf());
        startDate.setDate(startDate.getDate(new Date(selected.date.valueOf())));
        // $('#dtFrom').datepicker('setStartDate', startDate);

        $("#ssnDtTo").val($("#ssnDtFrom").val());
        $("#ssnDtTo").datepicker("setStartDate", startDate);
      });

    $("#ssnDtTo")
      .datepicker({
        autoclose: true,
        minViewMode: 1,
        format: "mm/yyyy",
      })
      .on("changeDate", function (selected) {
        FromEndDate = new Date(selected.date.valueOf());
        FromEndDate.setDate(
          FromEndDate.getDate(new Date(selected.date.valueOf()))
        );
        $("#ssnDtTo").datepicker("setStartDate", startDate);
      });

    this.sessFilter = this.stateSessFilter;
    $("#err-alert").hide();
  },
};
</script>

<style scoped>
</style>
