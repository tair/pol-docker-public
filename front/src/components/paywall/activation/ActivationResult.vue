<template>
  <div>
    <section style="margin-right: 20px; margin-top: 10px">
      <div class="box box-danger">
        <div class="box-body">
          <div v-if="this.stateActIsError" class="text-danger">
            <i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
            Error occurred while loading the data.
          </div>

          <!--<button type="button" class="btn btn-success" data-toggle="modal" data-target="#modal-danger">-->
          <!--Launch Success Modal-->
          <!--</button>-->

          <app-no-data
            v-if="this.stateActData == null || this.stateActData.length == 0"
          ></app-no-data>
          <div
            v-show="this.stateActData != null && this.stateActData.length > 0"
          >
            <table
              v-if="!this.stateActIsError"
              :id="tableId"
              class="table table-bordered table-striped"
            />
          </div>
        </div>
        <div v-if="this.stateActIsLoading" class="overlay">
          <i class="fa fa-refresh fa-spin"></i>
        </div>
      </div>

      <div class="modal modal-danger fade" id="modal-danger">
        <div class="modal-dialog" style="width: 240px">
          <div class="modal-content">
            <div class="modal-header">
              <button
                type="button"
                class="close"
                data-dismiss="modal"
                aria-label="Close"
              >
                <span aria-hidden="true">&times;</span>
              </button>
              <h4 class="modal-title">Are you sure?</h4>
            </div>
            <!--<div class="modal-body">-->
            <!--<p>Are you sure to delete?</p>-->
            <!--</div>-->
            <div class="modal-footer">
              <button
                type="button"
                class="btn btn-outline pull-left"
                data-dismiss="modal"
                @click="deactivate()"
              >
                Yes
              </button>
              <button
                type="button"
                class="btn btn-outline"
                data-dismiss="modal"
              >
                No
              </button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
    </section>
  </div>
</template>

<script>
import * as types from "../../../store/types_activation";
import * as sub_types from "../../../store/types_subscription";
import { mapActions } from "vuex";
import { mapGetters } from "vuex";
import ActivationFilter from "./ActivationFilter";

export default {
  data() {
    return {
      dialog: false,
      tableRef: null,
      tableId:
        "ID" + Math.floor(Math.random() * (1000 - 1 + 1)) + 1 + Date.now(),
      tableColumns: [
        {
          data: "activationCode",
          title: "Activation Code",
          type: "string",
          render: function (data, type, row) {
            return '<div style="color:green;">' + data + "</div>";
          },
        },
        {
          data: "purchaseDate",
          title: "Purchase Date",
          type: "date",
          render: function (data, type, row) {
            return new Date(data).toLocaleDateString("en-US");
          },
        },
        {
          data: "period",
          title: "Period",
          type: "number",
          render: null,
        },
        {
          data: "partyName",
          title: "Party Name",
          type: "string",
          render: this.partyRender,
          orderData: [4],
        },
        {
          data: "partyName",
          title: "Party Name",
          type: "string",
          render: null,
          visible: false,
        },
        {
          data: "partnerId",
          title: "Partner",
          type: "string",
          render: null,
          visible: false,
        },
        {
          data: "partyEmail",
          title: "Party Email",
          type: "string",
          render: null,
          visible: false,
        },
        {
          data: "transactionType",
          title: "Transaction Type",
          type: "string",
          render: null,
          visible: false,
        },
        {
          data: null,
          title: "Delete",
          render: function (data, type, row) {
            return (
              '<div><button data-toggle="modal" data-target="#modal-danger" class="btn btn-danger btn-xs">' +
              '  <i class="fa fa-trash"></i></button></div>'
            );
          },
          orderable: false,
        },
      ],

      isLoading: false,
      isError: false,
      selectedCode: null,
    };
  },
  components: {
    appFilter: ActivationFilter,
  },
  computed: {
    ...mapGetters({
      stateActData: types.ACT_GET_DATA,
      stateActFilter: types.ACT_GET_FILTER,
      stateActIsError: types.ACT_IS_ERROR,
      stateActIsLoading: types.ACT_IS_LOADING,
    }),
  },
  mounted() {
    if (this.tableRef != null) this.tableRef.destroy();
    this.buildTable();
  },
  watch: {
    stateActData: {
      handler: function (val, oldVal) {
        if (this.tableRef != null) this.tableRef.destroy();
        this.buildTable();
      },
      deep: true,
    },
  },
  methods: {
    ...mapActions({
      stateSubSetPartyEmail: sub_types.SUB_ACTION_FILTER_EMAIL,
      deleteCodeById: types.ACT_ACTION_DELETE_CODE,
      refreshData: types.ACT_ACTION_REFRESH,
    }),
    partyRender(data, type, row) {
      if (!data) return "";

      let partyPartner =
        '<span class="pull-right"><img height="20px" src="/src/assets/img/' +
        row.partnerId +
        '-logo.png"></span>';

      // partyPartner += '<span style="margin-right: 5px" class="label pull-right bg-aqua">' + row.partyType + '</span>';

      let transType = "";
      if (row.transactionType)
        transType +=
          '<span style="margin-right: 5px" class="label pull-right bg-yellow">' +
          row.transactionType +
          "</span>";

      let col =
        partyPartner +
        transType +
        '<span class="text-fuchsia">' +
        data +
        "</span>";

      if (row.partyEmail)
        col +=
          '<div><a style="cursor: pointer" class="text-blue text-sm">' +
          row.partyEmail +
          "</a></div>";

      return col;
    },
    navigateToActivationCodes(data) {
      this.stateSubSetPartyEmail(data.partyEmail);
      this.$router.push("/subscription?submit=true");
    },
    buildTable() {
      this.tableRef = $("#" + this.tableId).DataTable({
        deferRender: true,
        aaData: this.stateActData,
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
              columns: [0, 1, 2, 4, 5, 6, 7],
            },
          },
          {
            extend: "pdf",
            exportOptions: {
              columns: [0, 1, 2, 4, 5, 6, 7],
            },
            text: '<i class="fa fa-file-pdf-o"></i> PDF',
            className: "btn bg-primary btn-xs",
          },
          {
            extend: "print",
            exportOptions: {
              columns: [0, 1, 2, 4, 5, 6, 7],
            },
            text: '<i class="fa fa-print"></i> Print',
            className: "btn btn-warning btn-xs",
          },
          {
            extend: "copy",
            exportOptions: {
              columns: [0, 1, 2, 4, 5, 6, 7],
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

      let table = this.tableRef;
      let vueObj = this;
      $("#" + this.tableId + " tbody").off("click");
      $("#" + this.tableId + " tbody").on("click", "tr", function () {
        if ($(this).hasClass("selected")) {
          $(this).removeClass("selected");
        } else {
          table.$("tr.selected").removeClass("selected");
          $(this).addClass("selected");
        }
      });
      $("#" + this.tableId + " tbody").on("click", "a", function () {
        var data = table.row($(this).parents("tr")).data();
        vueObj.navigateToActivationCodes(data);
      });
      $("#" + this.tableId + " tbody").on("click", "button", function () {
        var data = table.row($(this).parents("tr")).data();
        vueObj.selectedCode = data.activationCodeId;
        // console.log(data.activationCodeId);
        vueObj.deleteCodeById(data.activationCodeId).then(
          (response) => {
            console.log(response);
            vueObj.refreshData();
          },
          (error) => {
            console.log(error);
          }
        );
      });
    },
    deactivate() {
      console.log("Yes Deactivate");
      if (this.selectedCode != null) {
        this.deleteCodeById(this.selectedCode).then(
          (response) => {
            this.refreshData();
          },
          (error) => {
            console.log(error);
          }
        );
      }
    },
    cancel() {
      this.selectedCode = null;
    },
  },
};
</script>

<style scoped>
.deactivate-modal .modal {
  position: relative;
  top: auto;
  bottom: auto;
  right: auto;
  left: auto;
  display: block;
  z-index: 1;
}

.deactivate-modal .modal {
  background: transparent !important;
}
</style>
