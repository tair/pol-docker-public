<template>
  <div>
    <app-detail-header :title="'Activation Codes'"></app-detail-header>
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
import ActivationFilter from "../../components/paywall/activation/ActivationFilter";
import ActivationResult from "../../components/paywall/activation/ActivationResult";

export default {
  data() {
    return {};
  },
  created() {},
  components: {
    appFilter: ActivationFilter,
    appResult: ActivationResult,
  },
};
</script>
