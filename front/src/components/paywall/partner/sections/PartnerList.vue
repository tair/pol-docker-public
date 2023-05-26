<template>
  <div>
    <app-detail-header :title="'Partner'" :sub-title="''"></app-detail-header>

    <section class="content">
      <div class="box box-warning">
        <div class="box-body">
          <div v-if="isError" class="text-danger">
            <i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
            Error occurred while loading the data.
          </div>

          <table
            v-if="!isError"
            :id="tableId"
            ref="tableId"
            class="table table-bordered table-striped"
          >
            <thead>
              <tr slot="header">
                <th v-for="header in headers">
                  <span class="text">{{ header }}</span>
                </th>
              </tr>
            </thead>
            <tfoot>
              <tr slot="header">
                <th v-for="header in headers">
                  <span class="text">{{ header }}</span>
                </th>
              </tr>
            </tfoot>
          </table>
        </div>

        <div v-if="isLoading" class="overlay">
          <i class="fa fa-refresh fa-spin"></i>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import axios from "axios";
import authHeader from "../../../../authHeader";
export default {
  data() {
    return {
      tableId: this.randomId(),
      tableData: null,
      tableColumns: null,

      isLoading: false,
      isError: false,

      dataFields: [
        "partnerId",
        "name",
        "description",
        "subscriptionListDesc",
        "loginRedirectErrorText",
      ],
      headers: [
        "ID",
        "Name",
        "Description",
        "Subscription List Description",
        "Login Redirect Error Text",
      ],
      dataTypes: ["numeric", "string", "string", "string", "string", "string"],
      dataRenders: [null, null, null, null],

      url: "/partner",
    };
  },
  // props: ['title', 'url', dataFields, headers, dataTypes, dataRenders],

  mounted() {
    this.loadData();
  },
  methods: {
    randomId() {
      return "ID" + Math.floor(Math.random() * (1000 - 1 + 1)) + 1 + Date.now();
    },
    foo() {
      alert("ss");
    },
    loadData() {
      this.isLoading = true;
      // console.log(this.url);
      let headers = authHeader();
      axios({ method: "GET", url: "/partner", headers: headers })
        .then((res) => {
          let data = res.data;
          this.tableData = data;

          if (data.length > 0) {
            var keys = Object.keys(data[0]);

            this.tableColumns = [];

            var _renderer = null;
            for (let col = 0; col < this.dataFields.length; col++) {
              _renderer = null;

              this.tableColumns.push({
                data: this.dataFields[col],
                type: this.dataTypes[col],
                render: this.dataRenders[col],
              });
            }
          }

          this.isLoading = false;
          this.buildTable();
        })
        .catch((error) => {
          this.isLoading = false;
          this.isError = true;

          console.log("Error while reading data (E8273): " + error);
        });
    },
    buildTable() {
      $("#" + this.tableId).DataTable({
        deferRender: true,
        aaData: this.tableData,
        aoColumns: this.tableColumns,
        paging: true,
        lengthChange: true,
        searching: true,
        ordering: true,
        info: true,
        autoWidth: false,
        dom: '<"top"<"row"<"col-md-6"<B><"info1"i>><"col-md-6"<"search"f>>>t>           <"bottom"<"row"<"col-md-6 ln1"l><"col-md-6 pull-right"p>>>',
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
        ],
      });

      $("div.dataTables_filter input").css({
        width: "350px",
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
