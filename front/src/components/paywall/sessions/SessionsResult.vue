<template>
  <div>
    <section style="margin-right: 20px; margin-top: 10px;">
      <div class="box box-danger">
        <div class="box-body">
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
            >&times;</button>
            <i class="fa fa-exclamation-triangle"></i>
            <strong>Information!</strong>
            <p>
              The table is restricted to render up to 100,000 records. To fetch all the records, please
              contact technical team.
            </p>
          </div>

          <app-no-data v-if="this.stateUsageData == null || this.stateUsageData.length == 0"></app-no-data>
          <div v-show="this.stateUsageData != null && this.stateUsageData.length > 0">
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
import * as types from "../../../store/types_sessions";
import { mapGetters } from "vuex";
import { mapActions } from "vuex";

export default {
  data() {
    return {
      tableRef: null,
      tableId:
        "ID" + Math.floor(Math.random() * (1000 - 1 + 1)) + 1 + Date.now(),
      tableColumns: [
        {
          data: "ip",
          title: "IP Address",
          type: "string",
          render: null
        },
        {
          data: "month",
          title: "Month/Year",
          type: "string",
          render: function(data, type, row) {
            return row.month + "/" + row.year;
          }
        },
        {
          data: "session_count",
          title: "Session Count",
          type: "number",
          render: null
        }
      ],

      isLoading: false,
      isError: false
    };
  },
  components: {
    // appFilter: UsageFilter
  },
  computed: {
    ...mapGetters({
      stateUsageData: types.SESS_GET_DATA,
      stateUsageFilter: types.SESS_GET_FILTER,
      stateUsageIsError: types.SESS_IS_ERROR,
      stateUsageIsLoading: types.SESS_IS_LOADING,
      stateUsageIsManyRecordsAlert: types.SESS_GET_MANY_RECORDS_ALERT
    })
  },
  mounted() {
    this.buildTable();
  },
  methods: {
    ...mapActions({
      stateUsageSetTableRef: types.SESS_ACTION_TABLE_REF
      //   stateUsageSetTooManyRecsAlert: types.USG_ACTION_MANY_RECORDS_ALERT
    }),
    closeManyRecsAlert() {
      //   this.stateUsageSetTooManyRecsAlert(false);
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
            className: "btn btn-success btn-xs"
          },
          {
            extend: "pdf",
            text: '<i class="fa fa-file-pdf-o"></i> PDF',
            className: "btn bg-primary btn-xs"
          },
          {
            extend: "print",
            text: '<i class="fa fa-print"></i> Print',
            className: "btn btn-warning btn-xs"
          },
          {
            extend: "copy",
            text: '<i class="fa fa-copy"></i> Copy',
            className: "btn btn-danger btn-xs"
          }
        ]
      });
      this.stateUsageSetTableRef(this.tableRef);

      $("div.dataTables_filter input").css({
        display: "inline-block",
        border: "1px solid #3b8ab8",
        padding: "5px"
      });
      $("div.dataTables_filter input").attr("placeholder", "Quick search...");
    }
  }
};
</script>

<style scoped>
</style>
