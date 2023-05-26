<template>
  <div>
    <app-detail-header :title="'Subscription Terms'" :sub-title="''"></app-detail-header>

    <section class="content">
      <div class="box box-warning">
        <div class="box-body">
          <div v-if="isError" class="text-danger">
            <i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
            Error occurred while loading the data.
          </div>

          <app-table-ex
            @isError="isError = $event"
            @isLoading="isLoading = $event"
            @rowClicked="showDetails(selectedRow = $event)"
            :url="url"
            :tableColumns="tableColumns"
          ></app-table-ex>
        </div>

        <div v-if="isLoading" class="overlay">
          <i class="fa fa-refresh fa-spin"></i>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import DetailHeader from "../../../common/DetailHeader";

export default {
  data() {
    return {
      selectedRow: null,
      isError: false,
      isLoading: false,
      url: "/subscriptionTerm",
      tableColumns: [
        {
          data: "period",
          title: "Period",
          type: "numeric",
          render: null
        },
        {
          data: "price",
          title: "Price",
          type: "numeric",
          render: $.fn.dataTable.render.number(",", ".", 2, "$")
        },
        {
          data: "groupDiscountPercentage",
          title: "Discount",
          type: "numeric",
          render: function(data, type, row) {
            return data + "%";
          }
        },
        {
          data: "partnerId",
          title: "Partner",
          type: "string",
          render: null
        },
        {
          data: "description",
          title: "Description",
          type: "string",
          render: null
        }
      ]
    };
  },
  components: {
    appDetailHeader: DetailHeader
  },
  methods: {
    showDetails() {
      this.$router.push(
        "terms/detail/" + this.selectedRow[Object.keys(this.selectedRow)[0]]
      );
    }
  }
};
</script>

<style scoped>
</style>
