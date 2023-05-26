<template>
  <div>
    <section style="margin-right: 20px; margin-top: 10px">
      <div class="box box-danger">
        <div class="box-header d-flex align-items-center">
          <h3
            class="box-title"
            style="display: inline-block; margin-right: 5px"
          >
            Current Schedules
          </h3>
          <button class="btn btn-default align-middle" @click="refreshData">
            <i class="fa fa-refresh"></i>
          </button>
        </div>
        <div class="box-body">
          <div>
            <table :id="tableId" class="table table-bordered table-striped" />
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import * as types from "../../../store/types_schedule";
import { mapGetters } from "vuex";
import { mapActions } from "vuex";
import * as userTypes from "../../../store/types_login";

export default {
  data() {
    return {
      role: "",
      tableRef: null,
      tableId:
        "ID" + Math.floor(Math.random() * (1000 - 1 + 1)) + 1 + Date.now(),
      // columns to keep = scheduler, startDaet, scchedulePeriod, Parttnertype, email, selectpartyname
      tableColumns: [
        {
          data: "scheduler",
          title: "Scheduled By",
          type: "string",
          render: null,
        },
        {
          data: "startDate",
          title: "Start Date",
          type: "string",
          render: function (data, type, row) {
            return new Date(data).toLocaleDateString("en-US", {
              year: "numeric",
              month: "2-digit",
              day: "2-digit",
            });
          },
        },
        {
          data: "schedulePeriod",
          title: "Schedule Period",
          type: "Date",
          render: null,
        },
        {
          data: "nextDateTrigger",
          title: "Next Schedule Date",
          type: "Date",
          render: function (data, type, row) {
            return new Date(data).toLocaleDateString("en-US", {
              year: "numeric",
              month: "2-digit",
              day: "2-digit",
            });
          },
        },
        {
          data: "partnerType",
          title: "Partner Type",
          type: "string",
          render: null,
        },
        {
          data: "email",
          title: "Receiver Email",
          type: "string",
          render: null,
        },
        {
          data: "selectedPartyName",
          title: "Instituion Name",
          type: "string",
          render: null,
        },
      ],
      isLoading: false,
      isError: false,
      partner: "",
    };
  },
  components: {},
  computed: {
    ...mapGetters({
      stateScheduleData: types.SCH_GET_DATA,
      stateLoggedinUser: userTypes.USG_GET_USER,
    }),
  },
  watch: {
    stateScheduleData: {
      handler: function (val, oldVal) {
        // console.log(val);
      },
      deep: true,
    },
  },
  mounted() {
    this.role = this.stateLoggedinUser.role;
    this.setTableData();
    setInterval(this.refreshData, 60000);
  },
  methods: {
    ...mapActions({
      setScheduleData: types.SCH_SET_SCHEDULE,
    }),
    setTableData() {
      this.setScheduleData()
        .then(() => {
          let filteredData = this.stateScheduleData;

          if (this.role != "Sales") {
            var filter = this.stateLoggedinUser.username;
            filteredData = this.stateScheduleData.filter(
              (row) => row.scheduler === filter
            );
          }

          // Handle success
          this.buildTable(filteredData);
        })
        .catch((error) => {
          // Handle error
          console.error("ERROR Getting Data from Schedule Table", error);
        });
    },
    refreshData() {
      this.setScheduleData().then(() => {
        let filteredData = this.stateScheduleData;

        if (this.role != "Sales") {
          var filter = this.stateLoggedinUser.username;
          filteredData = this.stateScheduleData.filter(
            (row) => row.scheduler === filter
          );
        }

        // Update the table with new data
        this.tableRef.clear().rows.add(filteredData).draw();
      });
    },
    buildTable(filteredData) {
      this.tableRef = $("#" + this.tableId).DataTable({
        destroy: true,
        deferRender: true,
        aaData: filteredData,
        aoColumns: this.tableColumns,
        paging: true,
        pagingType: "full_numbers",
        lengthChange: true,
        searching: true,
        ordering: true,
        order: [[1, "desc"]],
        info: false,
        autoWidth: false,
        pageLength: 10,
        dom: '<"top"Bt><"bottom"<"row"<"col-md-6 ln1"il><"col-md-6 pull-right"p>>>',
        buttons: [
          {
            extend: "excel",
            text: '<i class="fa fa-file-excel-o"></i> Excel',
            className: "btn btn-success btn-xs",
          },
          {
            extend: "copy",
            text: '<i class="fa fa-copy"></i> Copy',
            className: "btn btn-danger btn-xs",
          },
        ],
      });
    },
  },
};
</script>

<style scoped></style>

<style>
.dataTables_wrapper .dataTables_paginate .paginate_button {
  margin-left: 5px;
  margin-right: 5px;
  padding-left: 10px;
  padding-right: 10px;
}
</style>
