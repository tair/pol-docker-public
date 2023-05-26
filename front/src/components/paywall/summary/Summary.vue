<template>
  <div>
    <app-detail-header :title="'Summary'" :sub-title="''"></app-detail-header>

    <div class="row">
      <div class="col-md-3">
        <appFilter></appFilter>
      </div>
      <div class="col-md-9">
        <appResult></appResult>
      </div>
    </div>
  </div>
</template>

<script>
import SummaryResult from "./SummaryResult";
import SummaryFilter from "./SummaryFilter";
export default {
  data() {
    return {};
  },
  components: {
    appFilter: SummaryFilter,
    appResult: SummaryResult
  }
};
</script>

<style scoped>
</style>
