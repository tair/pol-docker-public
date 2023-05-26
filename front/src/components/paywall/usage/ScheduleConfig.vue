<template>
  <div>
    <section style="margin-left: 20px; margin-top: 10px">
      <div class="box box-solid">
        <div class="box-body">
          <form role="form">
            <label class="control-label text-primary">
              Start Date
              <i
                class="fas fa-info-circle"
                data-toggle="tooltip"
                data-placement="right"
                title="We typically take 1-2 weeks to process monthly data hence you can only select start dates after the 14th of each month."
              ></i>
            </label>
            <div class="row">
              <div class="col-md-12">
                <div class="input-group">
                  <Datepicker
                    v-model="config.startDate"
                    id="startDate"
                    placeholder="start date"
                    format="yyyy-MM-dd"
                    :input-class="'form-control'"
                    :calendar-button="true"
                    :calendar-button-icon="'fa fa-calendar'"
                    :disabled-dates="disabledDates"
                  ></Datepicker>
                </div>
              </div>
            </div>
            <div class="form-group">
              <label class="control-label text-primary">
                Email
                <i
                  class="fas fa-info-circle"
                  data-toggle="tooltip"
                  data-placement="right"
                  title="Choose email that you want the report to be sent to."
                ></i>
              </label>
              <input
                type="email"
                v-model="config.email"
                class="form-control"
                :placeholder="this.stateLoggedinUser.username"
              />
            </div>
            <div class="form-group">
              <label class="control-label text-primary"
                >Schedule Email Period</label
              >
              <br />
              <input
                type="radio"
                id="scheduleMonthly"
                value="monthly"
                v-model="config.schedulePeriod"
              />
              Monthly
              <span>&nbsp;&nbsp;&nbsp;</span>
              <input
                type="radio"
                id="scheduleYearly"
                value="yearly"
                v-model="config.schedulePeriod"
              />
              Yearly
            </div>
            <div class="form-group">
              <label class="control-label text-primary">Partner Type</label>
              <br />
              <input
                type="radio"
                id="partnerTair"
                value="tair"
                v-model="config.partnerType"
              />
              TAIR
              <span>&nbsp;&nbsp;&nbsp;</span>
              <input
                type="radio"
                id="partnerRep"
                value="repbase"
                v-model="config.partnerType"
              />
              Repbase
              <span>&nbsp;&nbsp;&nbsp;</span>
              <input
                type="radio"
                id="partnerMor"
                value="morphobank"
                v-model="config.partnerType"
              />
              Morphobank
            </div>
            <div class="form-group">
              <label class="control-label text-primary"
                >Institution / Consortium</label
              >
              <br />
              <div class="nav-tabs-custom">
                <ul class="nav nav-tabs">
                  <li class="active">
                    <a
                      style="color: cornflowerblue"
                      href="#tab_1"
                      @click="config.partyType = 'organization'"
                      data-toggle="tab"
                    >
                      Institution
                    </a>
                  </li>
                  <li>
                    <a
                      style="color: cornflowerblue"
                      href="#tab_2"
                      data-toggle="tab"
                      @click="config.partyType = 'consortium'"
                    >
                      Consortium
                    </a>
                  </li>
                </ul>
                <div class="tab-content">
                  <div class="tab-pane active" id="tab_1">
                    <Institution
                      :filteredNames="filteredNames"
                      @rowClicked="config.selectedParty = $event"
                    ></Institution>
                  </div>
                  <!-- /.tab-pane -->
                  <div class="tab-pane" id="tab_2">
                    <Consortium
                      @rowClicked="config.selectedParty = $event"
                    ></Consortium>
                  </div>
                </div>
              </div>
            </div>

            <hr style="border: 0.5px solid #efefef" />
          </form>
        </div>
      </div>

      <button
        @click.prevent="onSubmit()"
        class="btn btn-danger btn-sm btn-block margin-bottom"
      >
        <i class="fa fa-calendar"></i> Create Schedule
      </button>
    </section>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import { mapGetters } from "vuex";

import Institution from "./sections/Institution";
import Consortium from "./sections/Consortium";
import IpRange from "./sections/IpRange";
import Datepicker from "vuejs-datepicker";

import * as userTypes from "../../../store/types_login";
import * as scheduleTypes from "../../../store/types_schedule";
import * as party_types from "../../../store/types_party";
import * as usage_types from "../../../store/types_usage";

import $ from "jquery";
import "bootstrap";
const types = {
  ...userTypes,
  ...scheduleTypes,
  ...party_types,
  ...usage_types,
};

export default {
  data() {
    return {
      config: {
        startDate: "",
        schedulePeriod: "",
        // institutionName: "",
        partnerType: "",
        partyType: "organization",
        selectedParty: "",
        selectedPartyName: "",
        ipRange: "",
        email: "",
      },
      filteredNames: [], // namesFiltered Add this line to define the filteredNames property
    };
  },
  components: {
    Institution: Institution,
    Consortium: Consortium,
    IpRange: IpRange,
    Datepicker: Datepicker,
  },
  created() {
    // initialize the filter values from the store.
    // this can then be persisted to DB.
  },
  computed: {
    ...mapGetters({
      stateLoggedinUser: userTypes.USG_GET_USER,
    }),
    disabledDates() {
      const today = new Date();
      const tomorrow = new Date(today);
      tomorrow.setDate(tomorrow.getDate() + 1);
      tomorrow.setHours(0, 0, 0, 0);
      return {
        to: tomorrow,
        customPredictor: (date) => date.getDate() < 15,
      };
    },
  },
  watch: {
    $route: "fetchData",
  },
  methods: {
    ...mapActions({
      stateUsageSearchByFilter: types.USG_ACTION_FILTER,
      stateUsgTwaActionSearchByFilter: types.USG_TWA_ACTION_FILTER,
      statePrtGetOrgs: types.PRT_ACTION_GET_ORGS,
      stateSendSchedule: types.SCH_ACTION_SUBMIT_SCHEDULE,
    }),
    datesDisabled(date) {
      return date.getDate() < 15;
    },
    fetchData() {
      if (this.$route.path == "/main/usage/schedule") {
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
    onSubmit() {
      // Send the data to your API
      // this.config.selectedParty = this.config.selectedParty.name;
      const payload = {
        scheduler: this.stateLoggedinUser.username,
        startDate: this.config.startDate.toISOString(),
        // startDate: this.config.startDate,
        schedulePeriod: this.config.schedulePeriod,
        partnerType: this.config.partnerType,
        partyType: this.config.partyType,
        selectedParty: this.config.selectedParty.partyId,
        // ipRange: this.config.ipRange,
        selectedPartyName: this.config.selectedParty.name,
        email: this.config.email,
      };
      const requiredFields = [
        "scheduler",
        "startDate",
        "schedulePeriod",
        "partnerType",
        "partyType",
        "selectedParty",
        "selectedPartyName",
        "email",
      ];

      let hasError = false;

      requiredFields.forEach((field) => {
        if (!payload[field]) {
          console.error(`Error: ${field} cannot be null or empty.`);
          hasError = true;
        }
      });

      if (!hasError) {
        // Make your API call here
        // Replace the following with your API call
        this.stateSendSchedule(payload)
          .then(() => {
            // Handle success
            alert("Successfully Added Schedule");
          })
          .catch((error) => {
            // Handle error
            console.error("Error submitting schedule:", error);
          });
      } else {
        // Show an error message to the user
        alert(
          "Error! Please make sure to select all required fields. If that does not work, please log out and log back in"
        );
      }
    },
    onErrMsg(msg) {},
  },
  mounted() {
    this.$nextTick(() => {
      $('[data-toggle="tooltip"]').tooltip();
    });
    this.updateFilteredInstitutions();
  },
};
</script>

<style scoped></style>

<style>
.form-control {
  background-color: #fff !important;
}
</style>
