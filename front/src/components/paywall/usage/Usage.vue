<template>
  <div>
    <app-detail-header :title="'Pageview Statistics'" :sub-title="''"></app-detail-header>

    <div class="row">
      <div class="col-lg-4 col-md-6 col-sm-12">
        <appFilter></appFilter>
      </div>
      <div class="col-lg-8 col-md-6 col-sm-12">
        <appResult></appResult>
      </div>
    </div>
  </div>
</template>

<script>
import UsageFilter from "./UsageFilter";
import UsageResult from "./UsageResult";

export default {
  data() {
    return {};
  },
  components: {
    appFilter: UsageFilter,
    appResult: UsageResult
  }
};
</script>

<style scoped>
</style>
