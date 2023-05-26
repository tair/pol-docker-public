<template>
  <div>
    <section style="margin-right: 20px; margin-top: 10px;">
      <div class="box box-danger">
        <div class="box-body">
          <div v-if="this.stateSummIsError" class="text-danger">
            <i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
            Error occurred while loading the data.
          </div>

          <app-no-data v-if="this.stateSummData == null || this.stateSummData.length == 0"></app-no-data>
          <div v-show="this.stateSummData != null && this.stateSummData.length > 0">
            <table
              v-if="!this.stateSummIsError"
              :id="tableId"
              class="table table-bordered table-striped table-hover"
            />
          </div>
        </div>
        <div v-if="this.stateSummIsLoading" class="overlay">
          <i class="fa fa-refresh fa-spin"></i>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import * as types from "../../../store/types_summary";
import { mapGetters, mapActions } from "vuex";

export default {
  data() {
    return {
      tableRef: null,
      tableRef2: null,
      tableId:
        "ID" + Math.floor(Math.random() * (1000 - 1 + 1)) + 1 + Date.now(),
      tableId2: "ID" + Math.floor(Math.random()),
      tableColumns: [
        {
          data: "year",
          title: "Year",
          type: "string",
          orderData: [0]
        },
        {
          data: "month",
          title: "Month",
          type: "string",
          orderData: [1]
        },
        {
          data: "total_count",
          title: "Total Count",
          type: "string",
          orderData: [2]
        }
      ],
      selectedPartyName: "",
      isLoading: false,
      isError: false,
      showTransaction: false
    };
  },
  components: {
    // appFilter: SubscriptionFilter
  },
  computed: {
    ...mapGetters({
      stateSummData: types.SUMM_GET_DATA,
      stateSummFilter: types.SUMM_GET_FILTER,
      stateSummIsError: types.SUMM_IS_ERROR,
      stateSummIsLoading: types.SUMM_IS_LOADING
    })
  },
  mounted() {
    if (this.tableRef != null) this.tableRef.destroy();
    this.buildTable();
  },
  watch: {
    stateSummData: {
      handler: function(val, oldVal) {
        if (this.tableRef != null) this.tableRef.destroy();
        this.buildTable();
        // this.resetState();
      },
      deep: true
    }
  },
  methods: {
    ...mapActions({}),
    buildTable() {
      this.tableRef = $("#" + this.tableId).DataTable({
        deferRender: true,
        aaData: this.stateSummData,
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
            exportOptions: {
              columns: [0, 1, 3, 4, 5, 6]
            }
          },
          {
            extend: "pdf",
            exportOptions: {
              columns: [0, 1, 3, 4, 5, 6]
            },
            text: '<i class="fa fa-file-pdf-o"></i> PDF',
            className: "btn bg-primary btn-xs"
          },
          {
            extend: "print",
            exportOptions: {
              columns: [0, 1, 3, 4, 5, 6]
            },
            text: '<i class="fa fa-print"></i> Print',
            className: "btn btn-warning btn-xs"
          },
          {
            extend: "copy",
            exportOptions: {
              columns: [0, 1, 3, 4, 5, 6]
            },
            text: '<i class="fa fa-copy"></i> Copy',
            className: "btn btn-danger btn-xs"
          }
        ]
      });

      $("div.dataTables_filter input").css({
        display: "inline-block",
        border: "1px solid #3b8ab8",
        padding: "5px"
      });
      $("div.dataTables_filter input").attr("placeholder", "Quick search...");

      let table = this.tableRef;
      let vueObj = this;
      $("#" + this.tableId + " tbody").off("click");

      $("#" + this.tableId + " tbody").on("click", "tr", function() {
        if ($(this).hasClass("selected")) {
          $(this).removeClass("selected");
          vueObj.resetState();
        } else {
          table.$("tr.selected").removeClass("selected");
          $(this).addClass("selected");
          var data = table.rows(".selected").data();
          vueObj.selectedPartyName = data[0].partyName;
          vueObj.showTransactionTable(data[0].partyId);
        }
      });
      $("#" + this.tableId + " tbody").on("click", "a", function() {
        var data = table.row($(this).parents("tr")).data();
        vueObj.navigateToActivationCodes(data);
      });
    }
  }
};
</script>

<style scoped>
</style>
