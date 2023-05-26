<template>
  <div>
    <app-detail-header :title="'Party'" :sub-title="''"></app-detail-header>

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
import PartyListResult from "./PartyListResult";
import PartyListFilter from "./PartyListFilter";

export default {
  data() {
    return {};
  },
  components: {
    appResult: PartyListResult,
    appFilter: PartyListFilter
  }
};
</script>

<style scoped>
</style>
