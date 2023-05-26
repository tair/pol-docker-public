<template>
  <div>
    <app-detail-header :title="'IP Range'" :sub-title="''"></app-detail-header>

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
import IpRangeFilter from "../../components/paywall/iprange/IpRangeFilter";
import IpRangeResult from "../../components/paywall/iprange/IpRangeResult";

export default {
  data() {
    return {};
  },
  components: {
    appResult: IpRangeResult,
    appFilter: IpRangeFilter,
  },
};
</script>

<style scoped></style>
