<template>
  <div>
    <app-detail-header :title="'Subscriptions'" :sub-title="''"></app-detail-header>

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
import SubscriptionFilter from "../../components/paywall/subscription/SubscriptionFilter";
import SubscriptionResult from "../../components/paywall/subscription/SubscriptionResult";

export default {
  data() {
    return {};
  },
  components: {
    appFilter: SubscriptionFilter,
    appResult: SubscriptionResult
  }
};
</script>

<style scoped>
</style>
