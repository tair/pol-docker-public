<template>
  <div>
    <app-detail-header :title="'Usage Reports'"></app-detail-header>
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
import ScheduleConfig from "../../components/paywall/usage/ScheduleConfig";
import SchedulePanel from "../../components/paywall/usage/SchedulePanel";
export default {
  data() {
    return {};
  },
  components: {
    appFilter: ScheduleConfig,
    appResult: SchedulePanel,
  },
};
</script>
