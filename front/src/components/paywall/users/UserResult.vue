<template>
  <div>
    <section style="margin-right: 20px; margin-top: 10px">
      <div class="box box-danger">
        <div class="box-body">
          <!-- <div class="text-danger">
            <i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
            Error occurred while loading the data.
          </div> -->

          <app-no-data
            v-if="this.tableData == null || this.tableData.length == 0"
          ></app-no-data>
          <div v-show="this.tableData != null && this.tableData.length > 0">
            <table :id="tableId" class="table table-bordered table-striped" />
          </div>
        </div>
        <div v-if="this.tableIsLoading" class="overlay">
          <i class="fa fa-refresh fa-spin"></i>
        </div>
      </div>
    </section>
  </div>
</template>
<script>
import { mapActions, mapGetters } from "vuex";
import Vue from "vue";
import * as user_types from "../../../store/types_user";
import * as party_types from "../../../store/types_party";

const types = { ...user_types, ...party_types };

Vue.component("table-cell", {
  props: ["row"],
  template: `<div>Table Cell</div>`,
});

export default {
  data() {
    return {
      allOrgs: [],
      tableRef: null,
      tableId:
        "ID" + Math.floor(Math.random() * (1000 - 1 + 1)) + 1 + Date.now(),
      tableColumns: [
        { title: "Role", data: "role" },
        { title: "First Name", data: "firstName" },
        { title: "Last Name", data: "lastName" },
        { title: "Email", data: "email" },
        {
          title: "Associated Orgs",
          data: "associatedPartyIds",
          render: (data, type, row) => {
            const findName = (partyId) => {
              for (let org of this.allOrgs) {
                if (org.partyId === partyId) {
                  return org.name;
                }
              }
              return null;
            };
            let div = document.createElement("div");
            div.style.display = "flex";
            div.style.flexWrap = "wrap";
            let currAllIds = [];
            if (row.associatedPartyIds != null) {
              currAllIds = row.associatedPartyIds.split(",");
            }
            if (row.addedPartyIds != null) {
              currAllIds = currAllIds.concat(row.addedPartyIds.split(","));
            }
            for (let id of currAllIds) {
              // console.log("id: ", findName(id), id);
              let span = document.createElement("span");
              span.textContent = findName(id); // use the helper function to get the name
              span.style.margin = "5px"; // add some margin for spacing
              span.style.fontSize = "15px";
              if (
                row.associatedPartyIds &&
                row.associatedPartyIds.includes(id)
              ) {
                // approved
                span.className = "badge bg-success";
              } else {
                // pending
                span.className = "badge bg-warning";
              }
              // span.addEventListener("click", () => {
              //   console.log("clicked");
              //   $(span).trigger("badge-clicked"); // trigger the event
              // });
              // $(span).data("id", id);
              div.appendChild(span);
            }
            return div.outerHTML;
          },
          createdCell: (cell, cellData, rowData) => {
            //add a click event listener to the cell
            cell.addEventListener("click", () => {
              //show a modal with some information
              // this.showModal(rowData, cellData);
            });
          },
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
      tableData: [],
      tableIsLoading: false,
    };
  },
  computed: {
    ...mapGetters({
      // stateUsrData: types.USR_GET_DATA,
      stateUsrFilter: types.USR_GET_FILTER,
      stateUsrIsError: types.USR_IS_ERROR,
    }),
  },
  mounted() {
    if (this.tableRef != null) this.tableRef.destroy();
    this.tableIsLoading = true;
    this.statePrtGetOrgs().then((res) => {
      // console.log(res);
      //from res get all orgs as a map of id and name
      this.allOrgs = res.map((item) => {
        return { partyId: item.partyId, name: item.name };
      });
      this.actionUsrGetAll()
        .then((res) => {
          // console.log(res);
          //remove all rows where role is empty
          res = res.filter((item) => item.role != null);
          // console.log(res);

          this.tableIsLoading = false;
          this.tableData = res;
          // this.buildTable();
        })
        .catch((err) => {
          console.log(err);
          this.tableIsLoading = false;
        });
    });
  },
  watch: {
    stateUsrData: {
      handler: function (val, oldVal) {
        if (this.tableRef != null) this.tableRef.destroy();
        this.$nextTick(() => {
          // console.log("stateUsrData changed");
          this.buildTable();
        });
      },
      deep: true,
    },
    tableData(val) {
      if (this.tableRef != null) this.tableRef.destroy();
      this.$nextTick(() => {
        // console.log("tableData changed");
        this.buildTable();
      });
    },
  },
  methods: {
    //mapActions
    ...mapActions({
      actionUsrGetAll: types.USR_ACTION_GETALL,
      actionUsrDelete: types.USR_ACTION_DELETE,
      statePrtGetOrgs: types.PRT_ACTION_GET_ORGS,
      stateUsrAddAssoc: types.USR_ACTION_ADD_ASSOC,
    }),
    approve(partyIds) {
      // console.log(partyIds);
    },
    refresh() {
      this.tableIsLoading = true;
      this.actionUsrGetAll()
        .then((res) => {
          // console.log(res);
          this.tableIsLoading = false;
          this.tableData = res;
        })
        .catch((err) => {
          console.log(err);
          this.tableIsLoading = false;
        });
    },
    showModal(rowData, cellData) {
      // console.log("showModal ", cellData);
      let payload = { username: rowData.username, partyId: rowData.partyId };
      this.stateUsrAddAssoc(payload)
        .then((res) => {
          // console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    buildTable() {
      // console.log("buildTable", this.tableData);
      this.tableRef = $("#" + this.tableId).DataTable({
        deferRender: true,
        aaData: this.tableData,
        aoColumns: this.tableColumns,
        paging: true,
        lengthChange: true,
        searching: true,
        ordering: true,
        info: true,
        autoWidth: false,
        columnHoverHighlight: true,
      });
      let table = this.tableRef;
      let vueObj = this;

      // $("#" + this.tableId + " tbody").on("click", "button", function () {
      //   var data = table.row($(this).parents("tr")).data();
      //   vueObj.actionUsrDelete(data).then((res) => {
      //     console.log(res);
      //     vueObj.refresh();
      //   });
      // });
    },
  },
};
</script>
