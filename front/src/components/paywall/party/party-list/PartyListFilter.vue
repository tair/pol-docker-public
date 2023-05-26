<template>
  <div>
    <section style="margin-left: 20px; margin-top: 10px">
      <button
        @click.prevent="onSubmit()"
        class="btn btn-danger btn-sm btn-block margin-bottom"
      >
        <i class="fa fa-search"></i> Search
      </button>

      <div class="box box-solid">
        <div class="box-body">
          <form role="form">
            <div class="form-group">
              <label for="name" class="control-label text-primary">
                Party Name
              </label>
              <div class="input-group">
                <input
                  v-model="partyFilter.name"
                  placeholder="party name"
                  type="text"
                  class="form-control"
                  id="name"
                />
                <div class="input-group-addon">
                  <i class="fa fa-envelope-o"></i>
                </div>
              </div>
            </div>

            <!-- <div class="form-group">
              <label for="username" class="control-label text-primary">
                User Name
              </label>
              <div class="input-group">
                <input
                  v-model="partyFilter.username"
                  placeholder="user name"
                  type="text"
                  class="form-control"
                  id="username"
                />
                <div class="input-group-addon">
                  <i class="fa fa-envelope-o"></i>
                </div>
              </div>
            </div> -->
            <!-- <div class="form-group">
              <label for="email" class="control-label text-primary">
                Email
              </label>
              <div class="input-group">
                <input
                  v-model="partyFilter.email"
                  placeholder="email address"
                  type="text"
                  class="form-control"
                  id="email"
                />
                <div class="input-group-addon">
                  <i class="fa fa-envelope-o"></i>
                </div>
              </div>
            </div> -->
            <!-- <div class="form-group">
              <label for="userIdentifier" class="control-label text-primary">
                User Identifier
              </label>
              <div class="input-group">
                <input
                  v-model="partyFilter.userIdentifier"
                  placeholder="user identifier"
                  type="text"
                  class="form-control"
                  id="userIdentifier"
                />
                <div class="input-group-addon">
                  <i class="fa fa-envelope-o"></i>
                </div>
              </div>
            </div> -->
            <!-- <div class="form-group">
              <label for="institution" class="control-label text-primary">
                Institution
              </label>
              <div class="input-group">
                <input
                  v-model="partyFilter.institution"
                  placeholder="institution"
                  type="text"
                  class="form-control"
                  id="institution"
                />
                <div class="input-group-addon">
                  <i class="fa fa-envelope-o"></i>
                </div>
              </div>
            </div> -->

            <hr style="border: 0.5px solid #efefef" />
            <div class="form-group">
              <label class="control-label text-primary">Party Types</label>
              <br />
              <input
                type="checkbox"
                id="ptypeOrganization"
                value="organization"
                v-model="partyFilter.partyTypes"
              />
              Organization<br />
              <input
                type="checkbox"
                id="ptypeConsortium"
                value="consortium"
                v-model="partyFilter.partyTypes"
              />
              Consortium<br />
              <input
                type="checkbox"
                id="ptypeUser"
                value="user"
                v-model="partyFilter.partyTypes"
              />
              User
            </div>

            <hr style="border: 0.5px solid #efefef" />

            <div class="form-group">
              <label class="control-label text-primary">Partners</label>
              <br />
              <div v-if="statePrtrIsError" class="text-danger">
                <i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
                Error occurred while loading the data.
              </div>
              <div
                v-show="
                  statePrtrData != null &&
                  statePrtrData.length > 0 &&
                  !statePrtrIsError
                "
              >
                <div v-for="(partner, i) in statePrtrData" :key="i">
                  <input
                    type="checkbox"
                    :id="'partner' + partner.partnerId"
                    :value="partner.partnerId"
                    v-model="partyFilter.partners"
                  />
                  {{ partner.partnerId | capitalize }}
                </div>
              </div>
              <div v-if="statePrtrIsLoading" class="overlay">
                <i class="fa fa-refresh fa-spin"></i>
              </div>
            </div>
          </form>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import * as party_types from "../../../../store/types_party";
import * as partner_types from "../../../../store/types_partner";
import { mapActions } from "vuex";
import { mapGetters } from "vuex";

const types = { ...party_types, ...partner_types };

export default {
  data() {
    return {
      partyFilter: null,
    };
  },
  created() {
    // initialize the filter values from the store.
    // this can then be persisted to DB.
    this.partyFilter = this.statePrtFilter;
    this.statePrtrGetData();
  },
  computed: {
    ...mapGetters({
      statePrtFilter: types.PRT_GET_FILTER,
      statePrtrIsError: types.PRTR_IS_ERROR,
      statePrtrIsLoading: types.PRTR_IS_LOADING,
      statePrtrData: types.PRTR_GET_DATA,
    }),
  },
  watch: {
    $route: "fetchData",
  },
  methods: {
    ...mapActions({
      statePrtSearchByFilter: types.PRT_ACTION_FILTER,
      statePrtrGetData: types.PRTR_ACTION_GET_DATA,
    }),
    fetchData() {
      if (this.$route.path == "/party" && this.$route.query.submit == "true") {
        this.onSubmit();
      }
    },
    onSubmit() {
      this.statePrtSearchByFilter(this.partyFilter);
    },
  },
  mounted() {
    this.partyFilter = this.statePrtFilter;

    if (this.$route.query.submit == "true") {
      this.onSubmit();
    }
  },
};
</script>

<style scoped></style>
