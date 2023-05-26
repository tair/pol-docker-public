<template>
  <div>
    <section style="margin-right: 20px; margin-top: 10px">
      <div class="box box-danger">
        <div class="box-body">
          <h3>Partner Name: {{this.partner}}</h3>
          <div v-if="this.stateUsageIsError" class="text-danger">
            <i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
            Error occurred while loading the data.
          </div>

          <div
            v-if="this.stateUsageIsManyRecordsAlert"
            class="alert alert-warning alert-dismissible"
            id="many-records-alert"
          >
            <button
              type="button"
              class="close"
              @click="this.closeManyRecsAlert"
              aria-hidden="true"
            >
              &times;
            </button>
            <i class="fa fa-exclamation-triangle"></i>
            <strong>Information!</strong>
            <p>
              The table is restricted to render up to 100,000 records. To fetch
              all the records, please contact technical team.
            </p>
          </div>

          <app-no-data
            v-if="
              this.stateUsageData == null || this.stateUsageData.length == 0
            "
          ></app-no-data>
          <div
            v-show="
              this.stateUsageData != null && this.stateUsageData.length > 0
            "
          >
            <table
              v-if="!this.stateUsageIsError"
              :id="tableId"
              class="table table-bordered table-striped"
            />
          </div>
        </div>
        <div v-if="this.stateUsageIsLoading" class="overlay">
          <i class="fa fa-refresh fa-spin"></i>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import * as types from "../../../store/types_usage";
import * as login_types from "../../../store/types_login";
const types2 = { ...login_types };
import { mapGetters } from "vuex";
import { mapActions } from "vuex";
import UsageFilter from "./UsageFilter";
import AWS from 'aws-sdk';

export default {
  data() {
    return {
      firstName: "",
      lastName: "",
      email: "",
      tableRef: null,
      tableId:
        "ID" + Math.floor(Math.random() * (1000 - 1 + 1)) + 1 + Date.now(),
      tableColumns: [
        {
          data: "ip",
          title: "IP Address",
          type: "string",
          render: null,
        },
        {
          data: "total_views",
          title: "Total page views",
          type: "number",
          render: null,
        },
        {
          data: "paid_content_count",
          title: "Paid page views",
          type: "number",
          render: null,
        },
        {
          data: "paid_content_count",
          title: "Free page views",
          type: "number",
          render: function (data, type, row) {
            return row.total_views - row.paid_content_count;
          },
        },
        {
          data: "session_count",
          title: "Sessions",
          type: "number",
          render: null,
        },
        {
          data: "total_turnaways",
          title: "Turnaways",
          type: "number",
          render: null,
        },
        {
          data: "partyName",
          title: "Party Name",
          type: "string",
          render: null,
        },
        {
          data: "partnerId",
          title: "Partner",
          type: "string",
          render: null,
        },
        {
          data: "month",
          title: "Year/month",
          type: "string",
          render: function (data, type, row) {
            return row.year + "-" + row.month;
          },
        },
      ],

      isLoading: false,
      isError: false,
      partner: ""
    };
  },
  components: {
    appFilter: UsageFilter
  },
  computed: {
    ...mapGetters({
      stateUsageData: types.USG_GET_DATA,
      stateUsageFilter: types.USG_GET_FILTER,
      stateUsageIsError: types.USG_IS_ERROR,
      stateUsageIsLoading: types.USG_IS_LOADING,
      stateUsageIsManyRecordsAlert: types.USG_GET_MANY_RECORDS_ALERT,
      stateLoggedinUser: types2.USG_GET_USER,
    }),
  },
  watch: {
    stateUsageFilter: {
        handler: function (val, oldVal) {
          this.partner = val.partner;
        },
        deep: true
    },
    // stateUsageData: {
    //   handler: function (val, oldVal) {
    //     console.log(val);
    //   },
    //   deep: true
    // },
  },
  mounted() {
    this.buildTable();
    this.firstName = this.stateLoggedinUser.firstName;
    this.lastName = this.stateLoggedinUser.lastName;
    this.email = this.stateLoggedinUser.username;
  },
  methods: {
    ...mapActions({
      stateUsageSetTableRef: types.USG_ACTION_TABLE_REF,
      stateUsageSetTooManyRecsAlert: types.USG_ACTION_MANY_RECORDS_ALERT,
    }),
    closeManyRecsAlert() {
      this.stateUsageSetTooManyRecsAlert(false);
    },
    buildTable() {
      this.tableRef = $("#" + this.tableId).DataTable({
        destroy: true,
        deferRender: true,
        aoColumns: this.tableColumns,
        paging: true,
        lengthChange: true,
        searching: true,
        ordering: true,
        info: true,
        autoWidth: false,
        dom:
          '<"top"Bt><"bottom"<"row"<"col-md-6 ln1"il><"col-md-6 pull-right"p>>>',
        oLanguage: { sSearch: '<i class="fa fa-search text-primary"></i>' },
        buttons: [
          {
            extend: "excel",
            text: '<i class="fa fa-file-excel-o"></i> Excel',
            className: "btn btn-success btn-xs",
          },
          {
            extend: "pdf",
            text: '<i class="fa fa-file-pdf-o"></i> PDF',
            className: "btn bg-primary btn-xs",
          },
          {
            extend: "print",
            text: '<i class="fa fa-print"></i> Print',
            className: "btn btn-warning btn-xs",
          },
          {
            extend: "copy",
            text: '<i class="fa fa-copy"></i> Copy',
            className: "btn btn-danger btn-xs",
          },
          {
            text: '<i class="fa fa-envelope"></i> Send Email',
            className: "btn btn-primary btn-xs",
            action:( e, dt, button, config ) => {
              var startDate = this.stateUsageFilter.dateFrom;
              var endDate = this.stateUsageFilter.dateTo;
              // Add an input box to let the user enter an email address
              var inputEmail = prompt("Enter email address you want the report emailed to:", this.email);
              if (inputEmail == null || inputEmail == "") {
                return;
              }
              var data = dt.buttons.exportData();
              var csvContent = "IP Address,Total page views,Paid page views,Free page views,Sessions,Turnaways,Party Name,Partner,Year/Month,\r\n";
              data.body.forEach(function(rowArray){
                var row = rowArray.map(function(cell) {
                  return String(cell).replace(/,/g, ';'); // Convert cell to string and replace commas with semicolons
                }).join(",");
                csvContent += row + "\r\n";
              });
              var loggedInUserFirstName = this.firstName;
              var encodedUri = encodeURI(csvContent);
              var readerBase = new FileReader();
              //conver to base64
              readerBase.readAsDataURL(new Blob([csvContent]));
              readerBase.onload = function() {
                var base64 = readerBase.result;
                var base64 = base64.slice(37);
                // send email with the csv file as attachment here
                var AWS = require("aws-sdk");
                AWS.config.update({
                  accessKeyId: process.env.VUE_APP_AWS_ACCESS_KEY_ID,
                  secretAccessKey: process.env.VUE_APP_AWS_SECRET_ACCESS_KEY,
                  region: 'us-west-2',
                })
                var ses = new AWS.SES();
                var params = {
                  Destinations: [inputEmail], // Use the entered email address here
                  RawMessage: {
                    Data: "From: subscriptions@phoenixbioinformatics.org\r\n" +
                          "To: " + inputEmail + "\r\n" +
                          "Subject: Monthly Usage Report\r\n" +
                          "MIME-Version: 1.0\r\n" +
                          "Content-Type: multipart/mixed; boundary=NextPart\r\n" +
                          "\r\n" +
                          "--NextPart\r\n" +
                          "Content-Type: text/html\r\n" +
                          "\r\n" +
                          "<html><p>Dear "  + loggedInUserFirstName + ",</p><p>Here are your usage statistics from " + startDate + " to " + endDate +". Please email <a href='mailto:subscriptions@phoenixbioinformatics.org'>subscriptions@phoenixbioinformatics.org</a> for any questions or concerns.</p><p>Kind Regards,<br>Phoenix Bioinformatics</p></html>\r\n" +
                          "--NextPart\r\n" +
                          "Content-Type: text/csv\r\n" +
                          "Content-Disposition: attachment; filename=data.csv\r\n" +
                          "Content-Transfer-Encoding: base64\r\n" +
                          "\r\n" + base64 + "\r\n"+
                          "--NextPart--"
                  }
                };
                ses.sendRawEmail(params, (err, data) => {
                  if (err) {
                    // console.log(err, err.stack);
                  } else {
                    // console.log(data);
                    alert('Email sent to ' + inputEmail + '! Please wait up to 5 minutes for email to arrive in your inbox.');
                  }
                });
              }
            }
          }             
        ],
      });
      this.stateUsageSetTableRef(this.tableRef);

      $("div.dataTables_filter input").css({
        display: "inline-block",
        border: "1px solid #3b8ab8",
        padding: "5px",
      });
      $("div.dataTables_filter input").attr("placeholder", "Quick search...");
    },
  },
};
</script>

<style scoped>
</style>