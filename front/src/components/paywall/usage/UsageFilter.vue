<template>
  <div>
    <section style="margin-left: 20px; margin-top: 10px">
      <button
        @click.prevent="onSubmit()"
        class="btn btn-danger btn-sm btn-block margin-bottom"
      >
        <i class="fa fa-search"></i> Search
      </button>

      <div v-if="errMsg != null" class="alert alert-error" id="err-alert">
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
                      id="dtFrom"
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
                      id="dtTo"
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

            <input v-show="false" type="text" v-model="usageFilter.partyType" />
            <input v-show="false" type="text" v-model="usageFilter.partyId" />

            <div
              class="form-group"
              v-if="stateLoggedinUser && stateLoggedinUser.role == 2"
            >
              <label class="control-label text-primary">Partners</label>
              <br />
              <input
                type="radio"
                id="partnerTair"
                value="tair"
                v-model="usageFilter.partner"
              />
              TAIR
              <span>&nbsp;&nbsp;&nbsp;</span>
            </div>
            <div class="form-group" v-else>
              <label class="control-label text-primary">Partners</label>
              <br />
              <input
                type="radio"
                id="partnerBiocyc"
                value="biocyc"
                v-model="usageFilter.partner"
              />
              Biocyc
              <span>&nbsp;&nbsp;&nbsp;</span>
              <input
                type="radio"
                id="partnerTair"
                value="tair"
                v-model="usageFilter.partner"
              />
              TAIR
              <span>&nbsp;&nbsp;&nbsp;</span>
              <input
                type="radio"
                id="partnerRep"
                value="repbase"
                v-model="usageFilter.partner"
              />
              Repbase
              <span>&nbsp;&nbsp;&nbsp;</span>
              <input
                type="radio"
                id="partnerMor"
                value="morphobank"
                v-model="usageFilter.partner"
              />
              Morphobank
            </div>
            <hr style="border: 0.5px solid #efefef" />

            <div class="nav-tabs-custom">
              <ul class="nav nav-tabs">
                <li class="active">
                  <a
                    style="color: cornflowerblue"
                    href="#tab_1"
                    @click="usageFilter.partyType = 'organization'"
                    data-toggle="tab"
                    >Institution</a
                  >
                </li>
                <li
                  v-if="
                    stateLoggedinUser && stateLoggedinUser.role != 'Librarian'
                  "
                >
                  <a
                    style="color: cornflowerblue"
                    href="#tab_2"
                    data-toggle="tab"
                    @click="usageFilter.partyType = 'consortium'"
                    >Consortium</a
                  >
                </li>
                <li
                  v-if="
                    stateLoggedinUser && stateLoggedinUser.role != 'Librarian'
                  "
                >
                  <a
                    style="color: cornflowerblue"
                    href="#tab_3"
                    data-toggle="tab"
                    @click="usageFilter.partyType = null"
                    >IP Range</a
                  >
                </li>
              </ul>
              <div class="tab-content">
                <div class="tab-pane active" id="tab_1">
                  <appInstitution
                    :filteredNames="filteredNames"
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
                      v-model="usageFilter.ipRange"
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
import * as usage_types from "../../../store/types_usage";
import * as login_types from "../../../store/types_login";
import * as party_types from "../../../store/types_party";
import { mapActions } from "vuex";
import { mapGetters } from "vuex";

import Institution from "./sections/Institution";
import Consortium from "./sections/Consortium";
import IpRange from "./sections/IpRange";

const types = { ...party_types, ...login_types, ...usage_types };

export default {
  data() {
    return {
      usageFilter: null,
      selectedConsortium: null,
      selectedInstitution: null,
      errMsg: null,
      filteredNames: [],
    };
  },
  components: {
    appInstitution: Institution,
    appConsortium: Consortium,
    appIpRange: IpRange,
  },
  created() {
    this.usageFilter = this.stateUsageFilter;
  },
  computed: {
    ...mapGetters({
      stateUsageFilter: types.USG_GET_FILTER,
      stateLoggedinUser: types.USG_GET_USER,
    }),
  },
  watch: {
    stateLoggedinUser: {
      handler: function (val, oldVal) {
        if (val != null) {
          this.updateFilteredInstitutions();
        }
      },
      deep: true,
    },
    $route: "fetchData",
  },

  methods: {
    ...mapActions({
      stateUsageSearchByFilter: types.USG_ACTION_FILTER,
      stateUsgTwaActionSearchByFilter: types.USG_TWA_ACTION_FILTER,
      statePrtGetOrgs: types.PRT_ACTION_GET_ORGS,
    }),
    fetchData() {
      if (this.$route.path == "/main/usage/panel") {
        this.updateFilteredInstitutions();
      }
    },
    updateFilteredInstitutions() {
      if (
        this.stateLoggedinUser &&
        this.stateLoggedinUser.role == "Librarian"
      ) {
        let partyIds = this.stateLoggedinUser.associatedPartyIds;
        if (partyIds != "" && partyIds.split(",").length > 0) {
          partyIds = partyIds.split(",");
          if (partyIds.length == 0) {
            this.filteredNames = ["None"];
          } else {
            this.statePrtGetOrgs().then((res) => {
              this.filteredNames = res.filter((org) =>
                partyIds.includes(org.partyId)
              );
            });
          }
        } else {
          this.filteredNames = ["None"];
        }
      }
    },
    updateUsageFilter() {
      this.usageFilter.dateFrom = $("#dtFrom").val();

      if ($("#dtTo").val() == "") $("#dtTo").val($("#dtFrom").val());

      this.usageFilter.dateTo = $("#dtTo").val();

      var ipRange = this.usageFilter.ipRange;
      this.usageFilter.ipRange = null;

      if (this.usageFilter.partyType == "organization") {
        if (!this.selectedInstitution || !this.selectedInstitution.partyId) {
          this.onErrMsg("Please select an institution.");
          return;
        }
        this.usageFilter.partyId = this.selectedInstitution.partyId;
        this.usageFilter.partyName = this.selectedInstitution.name;
      } else if (this.usageFilter.partyType == "consortium") {
        if (!this.selectedConsortium || !this.selectedConsortium.partyId) {
          this.onErrMsg("Please select a consortium.");
          return;
        }
        this.usageFilter.partyId = this.selectedConsortium.partyId;
      } else {
        if (!ipRange) {
          this.onErrMsg("Please enter IP Range(s).");
          return;
        }

        if (ipRange != null && ipRange.length > 0) {
          if (ipRange.indexOf("\n") != -1) ipRange = ipRange.split("\n");

          if (typeof ipRange == "string") ipRange = [ipRange];

          this.usageFilter.ipRange = ipRange;
        }
      }
    },
    onSubmit() {
      this.errMsg = null;
      if ($("#dtFrom").val() == "") {
        this.onErrMsg("From Date is required.");
        return;
      }
      this.updateUsageFilter();

      this.stateUsageSearchByFilter(this.usageFilter);
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
    this.updateFilteredInstitutions();
    var startDate = new Date();
    var FromEndDate = new Date();

    $(".slider-pageHitMinMax").slider();

    $("#dtFrom")
      .datepicker({
        autoclose: true,
        minViewMode: 1,
        format: "mm/yyyy",
      })
      .on("changeDate", function (selected) {
        startDate = new Date(selected.date.valueOf());
        startDate.setDate(startDate.getDate(new Date(selected.date.valueOf())));
        // $('#dtFrom').datepicker('setStartDate', startDate);

        $("#dtTo").val($("#dtFrom").val());
        $("#dtTo").datepicker("setStartDate", startDate);
      });

    $("#dtTo")
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
        $("#dtTo").datepicker("setStartDate", startDate);
      });

    this.usageFilter = this.stateUsageFilter;
    $("#err-alert").hide();
  },
};
</script>

<style scoped></style>
