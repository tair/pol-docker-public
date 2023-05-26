<template>
  <div>
    <app-detail-header :title="'Session Statistics'" :sub-title="''"></app-detail-header>

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
import SessionsResult from "./SessionsResult";
import SessionsFilter from "./SessionsFilter";
export default {
  data() {
    return {};
  },
  components: {
    appFilter: SessionsFilter,
    appResult: SessionsResult
  }
};
</script>

<style scoped>
</style>
