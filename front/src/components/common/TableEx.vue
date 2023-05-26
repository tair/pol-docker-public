<template>
  <section>
    <div>
      <table :id="tableId" class="table table-bordered table-hover"></table>
    </div>
    <div v-if="this.stateIsLoading">
      <i class="fa fa-refresh fa-spin"></i>
    </div>
    <div v-if="this.stateHasError">Error Loading data</div>
  </section>
</template>

<script>
import axios from "axios";
import authHeader from "../../authHeader";
import Details from "../../components/paywall/partner/sections/details/PartnerSubscriptionTermsDetail";

export default {
  data() {
    return {
      tableId: this.randomId(),
      tableData: null,
      tablePaging: true,
      tableDom:
        '<"top"<"row"<"col-md-5"<sB><"info1"i>><"col-md-7"<"search"f>>>t>           <"bottom"<"row"<"col-md-6 ln1"l><"col-md-6 pull-right"p>>>',
      tableScrollY: "",
      stateIsLoading: false,
      stateHasError: false,
    };
  },
  props: {
    isSimple: {
      type: Boolean,
    },
    url: {
      type: String,
      required: true,
    },
    tableColumns: {
      type: Array,
      required: true,
    },
    filteredNames: {
      type: Array,
    },
  },

  mounted() {
    if (this.isSimple) {
      this.tablePaging = false;
      this.tableDom = '<"top"<"search"f>t>';
      this.tableScrollY = "40vh";
    }

    this.loadData(this);
  },
  watch: {
    filteredNames: {
      handler: function (val, oldVal) {
        if (val && val.length > 0) {
          console.log({ val });
          // this.tableData = [];
          this.stateIsLoading = true;
          this.tableData = val;
          this.stateIsLoading = false;
        }
      },
      deep: true,
      immediate: true,
    },
  },
  components: {
    appDetails: Details,
  },
  methods: {
    randomId() {
      return "ID" + Math.floor(Math.random() * (1000 - 1 + 1)) + 1 + Date.now();
    },
    loadData() {
      this.$emit("isLoading", true);
      this.stateIsLoading = true;
      this.stateHasError = false;
      setTimeout(() => {
        let header = authHeader();
        // console.log(this.url);
        axios
          .get(this.url, { headers: header })
          .then((res) => {
            let data = res.data;
            if (this.filteredNames && this.filteredNames.length > 0) {
              if (
                this.filteredNames.length == 1 &&
                this.filteredNames[0] == "None"
              ) {
                data = [];
              } else {
                // console.log(data);
                data = this.filteredNames;
              }
            }
            this.tableData = data;
            this.$emit("isLoading", false);
            this.stateIsLoading = false;
            this.buildTable(this);
          })
          .catch((error) => {
            this.$emit("isLoading", false);
            this.$emit("isError", true);
            this.stateIsLoading = false;
            this.stateHasError = true;
            console.log(error);
          });
      }, 100);
    },
    buildTable(obj) {
      let table = $("#" + this.tableId).DataTable({
        aaData: this.tableData,
        aoColumns: this.tableColumns,
        paging: this.tablePaging,
        lengthChange: true,
        searching: true,
        ordering: true,
        info: true,
        autoWidth: false,

        scrollY: this.tableScrollY,

        dom: this.tableDom,
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

      // search input control
      $("div.dataTables_filter input")
        .css({
          // 'width': '90%',
          display: "inline-block",
          border: "1px solid #3b8ab8",
          padding: "5px",
        })
        .attr("placeholder", "Quick search...");

      // to track click event
      $("#" + this.tableId + " tbody").on("click", "tr", function () {
        if ($(this).hasClass("selected")) {
          $(this).removeClass("selected");
          obj.selectedRow = null;
        } else {
          table.$("tr.selected").removeClass("selected");
          $(this).addClass("selected");
          obj.selectedRow = table.row(this).data();
        }

        obj.$emit("rowClicked", obj.selectedRow);
      });
    },
  },
};
</script>

<style></style>
