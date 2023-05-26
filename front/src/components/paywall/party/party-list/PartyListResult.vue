<template>
  <div>
    <span v-show="false">{{ gotResults }}</span>

    <section style="margin-right: 20px; margin-top: 10px">
      <div class="box box-danger">
        <div class="box-body">
          <div v-if="this.statePrtIsError" class="text-danger">
            <i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
            Error occurred while loading the data.
          </div>

          <app-no-data
            v-if="this.statePrtData == null || this.statePrtData.length == 0"
          ></app-no-data>
          <div
            v-show="this.statePrtData != null && this.statePrtData.length > 0"
          >
            <table
              v-if="!this.statePrtIsError"
              :id="tableId"
              class="table table-bordered table-striped"
            />
          </div>
        </div>
        <div v-if="this.statePrtIsLoading" class="overlay">
          <i class="fa fa-refresh fa-spin"></i>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import * as types from "../../../../store/types_party";

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
        // {
        //   "data": "partyType",
        //   "title": "Party Type",
        //   "type": "string",
        //   "visible": true
        // },
        {
          data: "name",
          title: "Party Name",
          type: "string",
          render: this.partyRender,
          orderData: [1],
        },
        {
          data: "name",
          title: "Name",
          type: "string",
          visible: false,
        },
        {
          data: "username",
          title: "User Name",
          type: "string",
          visible: true,
        },
        {
          data: "email",
          title: "Email",
          type: "string",
          visible: false,
        },
        // {
        //   data: "institution",
        //   title: "Institution",
        //   type: "string",
        //   visible: true,
        // },
        {
          data: "partnerId",
          title: "Partner",
          type: "string",
          visible: true,
        },
        // {
        //   data: "userIdentifier",
        //   title: "User Identifier",
        //   type: "number",
        //   visible: true,
        // },
      ],

      isLoading: false,
      isError: false,
    };
  },
  mounted() {
    this.statePrtSearchByFilter({ partyTypes: [this.partyType] });
  },
  computed: {
    ...mapGetters({
      statePrtData: types.PRT_GET_DATA,
      statePrtFilter: types.PRT_GET_FILTER,
      statePrtIsError: types.PRT_IS_ERROR,
      statePrtIsLoading: types.PRT_IS_LOADING,
    }),
    gotResults() {
      if (this.tableRef != null) this.tableRef.destroy();
      this.buildTable();

      return this.statePrtData;
    },
  },
  methods: {
    ...mapActions({
      statePrtSearchByFilter: types.PRT_ACTION_FILTER,
    }),
    partyRender(data, type, row) {
      if (!data) return "";

      let col = "";

      if (row.partnerId != null) {
        col +=
          '<span class="pull-right"><img height="20px" src="/src/assets/img/' +
          row.partnerId +
          '-logo.png"></span>';
      }

      col +=
        '<span style="margin-right: 5px" class="label pull-right bg-green">' +
        row.partyType +
        "</span>";

      col += '<div class="text-fuchsia">' + data + "</div>";
      if (row.email)
        col +=
          '<a style="cursor: pointer" class="text-blue text-sm">' +
          row.email +
          "</a>";

      return col;
    },
    // navigateToActivationCodes(data) {
    //   console.log('Navigate to ACTIVATION....');
    //   this.stateActSetPartyEmail(data.partyEmail);
    //   this.$router.push('/activation?submit=true');
    // },
    buildTable() {
      this.tableRef = $("#" + this.tableId).DataTable({
        deferRender: true,
        aaData: this.statePrtData,
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

      // let table = this.tableRef;
      // let vueObj = this;
      // $('#' + this.tableId + ' tbody').off('click');
      // $('#' + this.tableId + ' tbody').on( 'click', 'a', function () {
      //   var data = table.row( $(this).parents('tr') ).data();
      //   vueObj.navigateToActivationCodes(data);
      // } );
    },
  },
};
</script>

<style scoped></style>
