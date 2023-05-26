<!-- <template>
  <div>
    <section style="margin-left: 20px; margin-top: 10px;">
      <button @click.prevent="onSubmit()" class="btn btn-danger btn-sm btn-block margin-bottom">
        <i class="fa fa-search"></i> Search
      </button>

      <div class="form-group">
        <label class="control-label text-primary">Institution / Consortium</label>
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
                :filteredNames="namesFiltered"
                @rowClicked="config.selectedParty = $event"
              ></Institution>
            </div>
            <div class="tab-pane" id="tab_2">
              <Consortium
                @rowClicked="config.selectedParty = $event"
              ></Consortium>
            </div>
          </div>
        </div>
      </div>

    </section>
  </div>

</template>

<script>
  import * as subscription_types from '../../../store/types_subscription';
  import * as partner_types from '../../../store/types_partner';
  import { mapActions } from 'vuex';
  import { mapGetters } from 'vuex';
  import Institution from "../usage/sections/Institution";
  import Consortium from "../usage/sections/Consortium";

  const types = {...subscription_types, ...partner_types};
  export default {
    data() {
      return {
        subscriptionFilter: {
          endDate_From: '',
          endDate_To: '',
          startDate_From: '',
          startDate_To: '',
          partyEmail: '',
          partyName: '',
          partyType: [],
          partner: [],
          transactionType: []
        },
        config: {
          partyType: "organization",
          selectedParty: "",
          selectedPartyName: "",
        },
        namesFiltered: [],
      }
    },
    created() {
      // initialize the filter values from the store.
      // this can then be persisted to DB.
      this.subscriptionFilter = this.stateSubFilter;
      this.statePrtrGetData();
    },
    computed: {
      ...mapGetters({
        stateSubFilter: types.SUB_GET_FILTER,
        statePrtrIsError: types.PRTR_IS_ERROR,
        statePrtrIsLoading: types.PRTR_IS_LOADING,
        statePrtrData: types.PRTR_GET_DATA,
      }),
      filteredPrtrData(){
        return this.statePrtrData.filter((element) => {
          return !['phoenix'].includes(element.partnerId);
        });
      }
    },
    watch: {
      '$route': 'fetchData'
    },
    components: {
      Institution: Institution,
      Consortium: Consortium,
    },
    methods: {
      ...mapActions({
        stateSubSearchByFilter: types.SUB_ACTION_FILTER,
        statePrtrGetData: types.PRTR_ACTION_GET_DATA,
      }),
      fetchData() {
        if (this.$route.path == '/subscription' && this.$route.query.submit == 'true') {
          this.onSubmit();
        }
      },
      onSubmit() {
        this.subscriptionFilter = { partyName: this.config.selectedParty.name };
        // console.log(this.subscriptionFilter);
        this.stateSubSearchByFilter(this.subscriptionFilter);
      },
    },
    mounted() {

      this.subscriptionFilter = this.config.selectedPartyName;

      if (this.$route.query.submit == 'true') {
        this.onSubmit();
      }
    }
  }
</script>

<style scoped>
</style> -->

<template>
  <div>
    <section style="margin-left: 20px; margin-top: 10px;">

      <div class="form-group">
        <label class="control-label text-primary">Institution / Consortium</label>
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
                :filteredNames="namesFiltered"
                :associatedPartyIds="associatedPartyIds"
                @rowClicked="config.selectedParty = $event"
              ></Institution>
            </div>
            <div class="tab-pane" id="tab_2">
              <Consortium
                :associatedPartyIds="associatedPartyIds"
                @rowClicked="config.selectedParty = $event"
              ></Consortium>
            </div>
          </div>
        </div>
      </div>

    </section>
  </div>

</template>

<script>
  import * as subscription_types from '../../../store/types_subscription';
  import * as partner_types from '../../../store/types_partner';
  import { mapActions } from 'vuex';
  import { mapGetters } from 'vuex';
  import Institution from "../usage/sections/Institution";
  import Consortium from "../usage/sections/Consortium";

  const types = {...subscription_types, ...partner_types};
  export default {
    data() {
      return {
        subscriptionFilter: {
          endDate_From: '',
          endDate_To: '',
          startDate_From: '',
          startDate_To: '',
          partyEmail: '',
          partyName: '',
          partyType: [],
          partner: [],
          transactionType: []
        },
        config: {
          partyType: "organization",
          selectedParty: "",
          selectedPartyName: "",
        },
        namesFiltered: [],
        partyAssociation: {},
      }
    },
    created() {
      // initialize the filter values from the store.
      // this can then be persisted to DB.
      this.subscriptionFilter = this.stateSubFilter;
      this.statePrtrGetData();
    },
    computed: {
      ...mapGetters({
        stateSubFilter: types.SUB_GET_FILTER,
        statePrtrIsError: types.PRTR_IS_ERROR,
        statePrtrIsLoading: types.PRTR_IS_LOADING,
        statePrtrData: types.PRTR_GET_DATA,
      }),
      filteredPrtrData(){
        return this.statePrtrData.filter((element) => {
          return !['phoenix'].includes(element.partnerId);
        });
      },
    },
    watch: {
      '$route': 'fetchData',
      'config.selectedParty': function (newVal, oldVal) {
        if (newVal !== oldVal) {
          this.onSubmit();
        }
      }
    },
    components: {
      Institution: Institution,
      Consortium: Consortium,
    },
    methods: {
      ...mapActions({
        stateSubSearchByFilter: types.SUB_ACTION_FILTER,
        statePrtrGetData: types.PRTR_ACTION_GET_DATA,
      }),
      fetchData() {
        if (this.$route.path == '/subscription' && this.$route.query.submit == 'true') {
          this.onSubmit();
        }
      },
      onSubmit() {
        this.subscriptionFilter = { partyName: this.config.selectedParty.name };
        // console.log(this.subscriptionFilter);
        this.stateSubSearchByFilter(this.subscriptionFilter);
      },
    },
    mounted() {

      this.subscriptionFilter = this.config.selectedPartyName;

      if (this.$route.query.submit == 'true') {
        this.onSubmit();
      }
    }
  }
</script>

<style scoped>
</style>