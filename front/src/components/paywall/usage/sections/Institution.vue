<template>
  <div>
    <app-table-ex
      @isError="isError = $event"
      @isLoading="isLoading = $event"
      @rowClicked="showDetails((selectedRow = $event))"
      :url="url"
      :tableColumns="tableColumns"
      :isSimple="true"
      :filteredNames="filteredNames"
    ></app-table-ex>
  </div>
</template>

<script>
export default {
  props: {
    filteredNames: {
      type: Array,
    },
  },
  data() {
    return {
      selectedRow: null,
      url: "/party/organization",
      tableColumns: [
        {
          data: "name",
          title: "Name",
          type: "string",
          render: null,
        },
      ],
      // filteredNames: [],
    };
  },
  methods: {
    showDetails() {
      this.$emit("rowClicked", this.selectedRow);
    },
  },
};
</script>

<style scoped></style>
