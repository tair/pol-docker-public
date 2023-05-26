<template>
  <div>
    <app-table-ex
      @isError="isError = $event"
      @isLoading="isLoading = $event"
      @rowClicked="showDetails(selectedRow = $event)"
      :url="url"
      :tableColumns="tableColumns"
      :isSimple=true
    ></app-table-ex>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        selectedRow: null,
        url: '/party/consortium',
        tableColumns: [
          {
            "data": "name",
            "title": "Name",
            "type": "string",
            "render": null
          }
        ]
      }
    },
    methods: {
      showDetails() {
        this.$emit('rowClicked', this.selectedRow);
      }
    }
  }
</script>

<style scoped>
</style>
