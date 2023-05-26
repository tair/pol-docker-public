<template>
  <div>
    <span v-show="false">{{ gotResults }}</span>

    <section style="margin-right: 20px; margin-top: 10px">
      <div class="box box-danger">
        <div class="box-body">
          <div v-if="this.stateIprIsError" class="text-danger">
            <i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
            Error occurred while loading the data.
          </div>

          <app-no-data
            v-if="this.stateIprData == null || this.stateIprData.length == 0"
          ></app-no-data>
          <div
            v-show="this.stateIprData != null && this.stateIprData.length > 0"
          >
            <table
              v-if="!this.stateIprIsError"
              :id="tableId"
              class="table table-bordered table-striped"
            />
          </div>
        </div>
        <div v-if="this.stateIprIsLoading" class="overlay">
          <i class="fa fa-refresh fa-spin"></i>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import * as types from "../../../store/types_iprange";

import { mapGetters, mapActions } from "vuex";

export default {
  props: {
    partyType: {
      type: String,
      required: false,
      default: "organization",
    },
  },
  data() {
    return {
      tableRef: null,
      tableId:
        "ID" + Math.floor(Math.random() * (1000 - 1 + 1)) + 1 + Date.now(),
      tableColumns: [
        {
          data: "partyName",
          title: "Party Name",
          type: "string",
          render: this.partyRender,
          orderData: [1],
        },
        {
          data: "start",
          title: "Start Date",
          type: "string",
          visible: true,
        },
        {
          data: "end",
          title: "End Date",
          type: "string",
          visible: true,
        },
        {
          data: "createdAt",
          title: "Created At",
          type: "string",
          render: this.dateRender,
          visible: true,
        },
      ],

      isLoading: false,
      isError: false,
    };
  },
  mounted() {
    // this.stateIprSearchByFilter();
  },
  computed: {
    ...mapGetters({
      stateIprData: types.IPR_GET_DATA,
      stateIprFilter: types.IPR_GET_FILTER,
      stateIprIsError: types.IPR_IS_ERROR,
      stateIprIsLoading: types.IPR_IS_LOADING,
    }),
    gotResults() {
      if (this.tableRef != null) this.tableRef.destroy();
      this.buildTable();
      return this.stateIprData;
    },
  },
  methods: {
    ...mapActions({
      stateIprSearchByFilter: types.IPR_ACTION_FILTER,
    }),
    dateRender(data, type, row) {
      if (!data) return "";
      return moment(data).format("DD-MMM-YYYY");
    },
    partyRender(data, type, row) {
      if (!data) return "";

      let col = "";
      col += '<div class="text-fuchsia">' + data + "</div>";
      return col;
    },
    buildTable() {
      this.tableRef = $("#" + this.tableId).DataTable({
        deferRender: true,
        aaData: this.stateIprData,
        aoColumns: this.tableColumns,
        paging: true,
        lengthChange: true,
        searching: true,
        ordering: true,
        info: true,
        autoWidth: false,
        dom: '<"top"Bt><"bottom"<"row"<"col-md-6 ln1"il><"col-md-6 pull-right"p>>>',
        oLanguage: { sSearch: '<i class="fa fa-search text-primary"></i>' },
        buttons: [
          {
            extend: "excel",
            text: '<i class="fa fa-file-excel-o"></i> Excel',
            className: "btn btn-success btn-xs",
            exportOptions: {
              columns: [1, 3, 2, 4],
            },
          },
          {
            extend: "pdf",
            exportOptions: {
              columns: [1, 3, 2, 4],
            },
            text: '<i class="fa fa-file-pdf-o"></i> PDF',
            className: "btn bg-primary btn-xs",
          },
          {
            extend: "print",
            exportOptions: {
              columns: [1, 3, 2, 4],
            },
            text: '<i class="fa fa-print"></i> Print',
            className: "btn btn-warning btn-xs",
          },
          {
            extend: "copy",
            exportOptions: {
              columns: [1, 3, 2, 4],
            },
            text: '<i class="fa fa-copy"></i> Copy',
            className: "btn btn-danger btn-xs",
          },
        ],
      });

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

<style scoped></style>
