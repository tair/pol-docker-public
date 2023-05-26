<template>
  <div>

    <section style="margin-left: 20px; margin-top: 10px;">
      <button @click.prevent="onSubmit()" class="btn btn-danger btn-sm btn-block margin-bottom">
        <i class="fa fa-search"></i> Search
      </button>

      <div class="box box-solid">
        <div class="box-body">
          <form role="form">
            <div class="form-group">
              <label for="purchaseDate" class="control-label text-primary">Purchase Date Range</label>
              <div class="input-group">
                <input placeholder="purchase date range" type="text" class="form-control" id="purchaseDate">
                <div class="input-group-addon">
                  <i class="fa fa-calendar"></i>
                </div>
              </div>
            </div>

            <hr style="border: 0.5px solid #efefef">
            <div class="form-group">
              <label for="code" class="control-label text-primary">
                Activation Code
              </label>
              <div class="input-group">
                <input v-model="activationFilter.code"
                       placeholder="activation ode"
                       type="text" class="form-control" id="code">
                <div class="input-group-addon">
                  <i class="fa fa-superpowers"></i>
                </div>
              </div>
            </div>
            <div class="form-group">
              <label for="partyEmail" class="control-label text-primary">
                Party Email
              </label>
              <div class="input-group">
                <input v-model="activationFilter.partyEmail"
                       placeholder="email address"
                       type="text" class="form-control" id="partyEmail">
                <div class="input-group-addon">
                  <i class="fa fa-envelope-o"></i>
                </div>
              </div>
            </div>
            <div class="form-group">
              <label for="partyEmail" class="control-label text-primary">
                Party Name
              </label>
              <div class="input-group">
                <input v-model="activationFilter.partyName"
                       placeholder="party name"
                       type="text" class="form-control" id="partyName">
                <div class="input-group-addon">
                  <i class="fa fa-user"></i>
                </div>
              </div>
            </div>

            <hr style="border: 0.5px solid #efefef">
            <div class="form-group">
              <label class="control-label text-primary">Transaction Types</label>
              <br>
              <input type="checkbox"
                     id="ttypeCreate" value="create"
                     v-model="activationFilter.transactionType"> Create<br>
              <input type="checkbox"
                     id="ttypeCreateFree" value="create_free"
                     v-model="activationFilter.transactionType"> Create Free<br>
              <input type="checkbox"
                     id="ttypeCreateTeaching" value="create_teaching"
                     v-model="activationFilter.transactionType"> Create Teaching<br>
              <input type="checkbox"
                     id="ttypeRenew" value="renew"
                     v-model="activationFilter.transactionType"> Renew
            </div>

            <hr style="border: 0.5px solid #efefef">
            <div class="form-group">
              <label class="control-label text-primary">Partners</label>
              <br>
              <div v-if="statePrtrIsError" class="text-danger">
                <i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
                Error occurred while loading the data.
              </div>
              <div v-show="filteredPrtrData != null && filteredPrtrData.length > 0 && !statePrtrIsError">
                <div v-for="partner in filteredPrtrData">
                  <input type="checkbox"
                  :id="'partner'+partner.partnerId" :value="partner.partnerId"
                  v-model="activationFilter.partner"> {{partner.partnerId | capitalize}}
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
  import * as activation_types from '../../../store/types_activation';
  import * as partner_types from '../../../store/types_partner';
  import {mapActions} from 'vuex';
  import {mapGetters} from 'vuex';

  const types = {...activation_types, ...partner_types};

  export default {
    data() {
      return {
        activationFilter: null
      }
    },
    created() {
      // initialize the filter values from the store.
      // this can then be persisted to DB.
      this.activationFilter = this.stateActivationFilter;
      this.statePrtrGetData();
    },
    computed: {
      ...mapGetters({
        stateActivationFilter: types.ACT_GET_FILTER,
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
    methods: {
      ...mapActions({
        stateActivationSearchByFilter: types.ACT_ACTION_FILTER,
        statePrtrGetData: types.PRTR_ACTION_GET_DATA,
      }),
      fetchData() {
        if (this.$route.path == '/activation' && this.$route.query.submit == 'true') {
          this.onSubmit();
        }
      },
      onSubmit() {

        var date = $('#purchaseDate').val();
        if (date != undefined || date != '') {
          var res = date.split(' - ');
          this.activationFilter.purchaseDate_From = res[0];
          this.activationFilter.purchaseDate_To = res[1];
        }

        this.stateActivationSearchByFilter(this.activationFilter);
      },
    },
    mounted() {
      $('#purchaseDate').daterangepicker({autoUpdateInput: false});
      $('#purchaseDate').on('apply.daterangepicker', function (ev, picker) {
        $(this).val(picker.startDate.format('MM/DD/YYYY') + ' - ' + picker.endDate.format('MM/DD/YYYY'));
      });
      $('#purchaseDate').on('cancel.daterangepicker', function (ev, picker) {
        $(this).val('');
      });

      this.activationFilter = this.stateActivationFilter;

      if (this.$route.query.submit == 'true') {
        this.onSubmit();
      }
    }
  }
</script>

<style scoped>
</style>
