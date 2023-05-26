<template>
    <div>
        <section style="margin-right: 20px; margin-top: 10px;">
            <div class="box box-danger">
                <div class="box-body">
                    <div v-if="this.stateSubIsError" class="text-danger">
                        <i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
                        Error occurred while loading the data.
                    </div>

                    <app-no-data v-if="this.stateSubData == null || this.stateSubData.length == 0"></app-no-data>
                    <div v-show="this.stateSubData != null && this.stateSubData.length > 0">
                        <table v-if="!this.stateSubIsError" :id="tableId"
                               class="table table-bordered table-striped table-hover"/>
                    </div>
                </div>
                <div v-if="this.stateSubIsLoading" class="overlay">
                    <i class="fa fa-refresh fa-spin"></i>
                </div>
            </div>
            <div class="box box-danger" v-show="this.stateSubTransactions != null && this.stateSubTransactions.length > 0">
                <div class="box-body">
                    <label class="control-label text-primary">
                        Transactions - {{selectedPartyName}}
                    </label>
                    <div>
                        <table :id="tableId2"
                               class="table table-bordered table-striped"/>
                    </div>
                </div>
            </div>
        </section>
    </div>
</template>
<!-- 
<template>
    <div>
      <section style="margin-right: 20px; margin-top: 10px;">
        <div class="box box-danger">
          <div class="box-body">
            <div v-if="this.stateSubIsError" class="text-danger">
              <i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
              Error occurred while loading the data.
            </div>
  
            <app-no-data v-if="this.stateSubData == null || this.stateSubData.length == 0"></app-no-data>
            <div v-show="this.stateSubData != null && this.stateSubData.length > 0">
              <div v-if="!this.stateSubIsError" class="card">
                <div class="card-body">
                  <div v-for="item in stateSubData" :key="item.id">
                    <div class="card mb-3">
                      <div class="card-header">{{ item.startDate }} - {{ item.endDate }}</div>
                      <div class="card-body">
                        <h5 class="card-title">{{ item.partyName }}</h5>
                        <p class="card-text">Subscribed To: {{ item.partnerId }}</p>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div v-if="this.stateSubIsLoading" class="overlay">
            <i class="fa fa-refresh fa-spin"></i>
          </div>
        </div>
        <div class="box box-danger" v-show="this.stateSubTransactions != null && this.stateSubTransactions.length > 0">
          <div class="box-body">
            <label class="control-label text-primary">
              Transactions - {{selectedPartyName}}
            </label>
            <div>
              <div :id="tableId2" class="card">
                <div class="card-body">
                  <div v-for="transaction in stateSubTransactions" :key="transaction.id">
                    <div class="card mb-3">
                      <div class="card-header">{{ transaction.transactionType }}</div>
                      <div class="card-body">
                        <p class="card-text">Start Date: {{ transaction.startDate }}</p>
                        <p class="card-text">End Date: {{ transaction.endDate }}</p>
                        <p class="card-text">Transaction Date: {{ transaction.transactionDate }}</p>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
  </template> -->
  

<script>
    import * as types from '../../../store/types_subscription';
    import * as act_types from '../../../store/types_activation';
    import {mapActions} from 'vuex';

    import {mapGetters} from 'vuex';
    import SubscriptionFilter from './SubscriptionFilter';

    export default {
        data() {
            return {
                tableRef: null,
                tableRef2: null,
                tableId: 'ID' + Math.floor(Math.random() * (1000 - 1 + 1)) + 1 + Date.now(),
                tableId2: 'ID' + Math.floor(Math.random()),
                tableColumns: [
                    {
                        "data": "startDate",
                        "title": "Start Date",
                        "type": "date",
                        "render": function (data, type, row) {
                            return new Date(data).toLocaleDateString("en-US");
                        },
                        "orderData": [0]
                    },
                    {
                        "data": "endDate",
                        "title": "End Date",
                        "type": "date",
                        "render": function (data, type, row) {
                            return new Date(data).toLocaleDateString("en-US");
                        },
                        "orderData": [1]
                    },
                    {
                        "data": "partyName",
                        "title": "Party Name",
                        "type": "string",
                    },
                    {
                        "data": "partnerId",
                        "title": "Subscribed To",
                        "type": "string",
                        "defaultContent": "",
                        "visible": true,
                        "orderData": [5],
                        "render": function(data, type, full, meta) {
                            return "<span style='text-transform: uppercase;'>" + data + "</span>";
                        }
                    },
                ],
                tableColumns2: [
                    {
                        "data": "transactionType",
                        "title": "Transaction Type",
                        "type": "string",
                        "defaultContent": null
                    },
                    {
                        "data": "startDate",
                        "title": "Start Date",
                        "type": "date",
                        "render": function (data, type, row) {
                            return new Date(data).toLocaleDateString("en-US");
                        }
                    },
                    {
                        "data": "endDate",
                        "title": "End Date",
                        "type": "date",
                        "render": function (data, type, row) {
                            return new Date(data).toLocaleDateString("en-US");
                        }
                    },
                    {
                        "data": "transactionDate",
                        "title": "Transaction Date",
                        "type": "date",
                        "render": function (data, type, row) {
                            return new Date(data).toLocaleDateString("en-US");
                        }
                    }
                ],
                selectedPartyName: "",
                isLoading: false,
                isError: false,
                showTransaction: false
            }
        },
        components: {
            appFilter: SubscriptionFilter
        },
        computed: {
            ...mapGetters({
                stateSubData: types.SUB_GET_DATA,
                stateSubTransactions: types.SUB_GET_TRANSACTIONS,
                stateSubFilter: types.SUB_GET_FILTER,
                stateSubIsError: types.SUB_IS_ERROR,
                stateSubIsLoading: types.SUB_IS_LOADING,
            })
        },
        mounted() {
            if (this.tableRef != null)
                this.tableRef.destroy();
            this.buildTable();
            this.buildTransactionTable();
        },
        watch: {
            stateSubData: {
                handler: function (val, oldVal) {
                    if (this.tableRef != null)
                        this.tableRef.destroy();
                    this.buildTable();
                    this.resetState();
                },
                deep: true
            },
            stateSubTransactions: {
                handler: function (val, oldVal) {
                    if (this.tableRef2 != null)
                        this.tableRef2.destroy();
                    this.buildTransactionTable();
                },
                deep: true
            }
        },
        methods: {
            ...mapActions({
                stateActSetPartyEmail: act_types.ACT_ACTION_FILTER_EMAIL,
                stateGetTransactions: types.SUB_GET_TRANSACTIONS,
                stateSubClearTransactions: types.SUB_ACTION_CLEAR_TRANSACTIONS
            }),
            partyRender(data, type, row) {

                if (!data) return '';

                let col = '<span class="pull-right"><img height="20px" src="/src/assets/img/'+row.partner+'-logo.png"></span>';

                col += '<span style="margin-right: 5px" class="label pull-right bg-green">' + row.partyType + '</span>';

                col += '<div class="text-fuchsia">' + data + '</div>';
                if (row.partyEmail != undefined)
                    col += '<a style="cursor: pointer" class="text-blue text-sm">' + row.partyEmail + '</a>';

                return col;
            },
            navigateToActivationCodes(data) {
                console.log('Navigate to ACTIVATION....');
                this.stateActSetPartyEmail(data.partyEmail);
                this.$router.push('/activation?submit=true');
            },
            showTransactionTable(partyId) {
                this.stateGetTransactions(partyId);
            },
            resetState() {
               //console.log("Clear Transactions");
               this.stateSubClearTransactions();
            },
            buildTable() {
                this.tableRef = $('#' + this.tableId).DataTable({
                    "deferRender": true,
                    "aaData": this.stateSubData,
                    "aoColumns": this.tableColumns,
                    'paging': true,
                    'lengthChange': true,
                    'searching': true,
                    'ordering': true,
                    'info': true,
                    'autoWidth': false,
                    dom: '<"top"Bt><"bottom"<"row"<"col-md-6 ln1"il><"col-md-6 pull-right"p>>>',
                    "oLanguage": {"sSearch": '<i class="fa fa-search text-primary"></i>'},
                    buttons: [
                        // {
                        //     "extend": 'excel',
                        //     "text": '<i class="fa fa-file-excel-o"></i> Excel',
                        //     "className": 'btn btn-success btn-xs',
                        //     "exportOptions": {
                        //         columns: [0, 1, 3, 4, 5, 6]
                        //     }
                        // },
                        // {
                        //     "extend": 'pdf',
                        //     "exportOptions": {
                        //         columns: [0, 1, 3, 4, 5, 6]
                        //     },
                        //     "text": '<i class="fa fa-file-pdf-o"></i> PDF', "className": 'btn bg-primary btn-xs'
                        // },
                        // {
                        //     "extend": 'print',
                        //     "exportOptions": {
                        //         columns: [0, 1, 3, 4, 5, 6]
                        //     },
                        //     "text": '<i class="fa fa-print"></i> Print', "className": 'btn btn-warning btn-xs'
                        // },
                        // {
                        //     "extend": 'copy',
                        //     "exportOptions": {
                        //         columns: [0, 1, 3, 4, 5, 6]
                        //     },
                        //     "text": '<i class="fa fa-copy"></i> Copy', "className": 'btn btn-danger btn-xs'
                        // },
                    ]
                });

                $('div.dataTables_filter input').css(
                    {'display': 'inline-block', 'border': '1px solid #3b8ab8', 'padding': '5px'}
                );
                $('div.dataTables_filter input').attr('placeholder', 'Quick search...');

                let table = this.tableRef;
                let vueObj = this;
                $('#' + this.tableId + ' tbody').off('click');

                $('#' + this.tableId + ' tbody').on('click', 'tr', function () {
                    if ( $(this).hasClass('selected') ) {
                        $(this).removeClass('selected');
                        vueObj.resetState();
                    } else {
                        table.$('tr.selected').removeClass('selected');
                        $(this).addClass('selected');
                        var data = table.rows('.selected').data();
                        vueObj.selectedPartyName = data[0].partyName;
                        vueObj.showTransactionTable(data[0].partyId);
                    }
                });
                $('#' + this.tableId + ' tbody').on('click', 'a', function () {
                    var data = table.row($(this).parents('tr')).data();
                    vueObj.navigateToActivationCodes(data);
                });
            },
            buildTransactionTable() {
                //console.log("Build Transaction");
                this.tableRef2 = $('#' + this.tableId2).DataTable({
                    "deferRender": true,
                    "aaData": this.stateSubTransactions,
                    "aoColumns": this.tableColumns2,
                    'paging': false,
                    'lengthChange': true,
                    'searching': false,
                    'ordering': true,
                    'info': true,
                    'autoWidth': false,
                    dom: '<"top"Bt><"bottom"<"row"<"col-md-6 ln1"il><"col-md-6 pull-right"p>>>',
                    "oLanguage": {"sSearch": '<i class="fa fa-search text-primary"></i>'},
                    buttons: []
                });
            }
        }
    }
</script>

<style scoped>
</style>
