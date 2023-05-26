<template>
  <div>
    <app-detail-header :title="'Users Panel'"></app-detail-header>
    <div class="row">
      <!-- <div class="col-md-3">
        <appFilter></appFilter>
      </div> -->
      <div class="ml-2 col-md-12">
        <appResult></appResult>
      </div>
    </div>
  </div>
</template>
<script>
import UserFilter from "../../components/paywall/users/UserFilter";
import UserResult from "../../components/paywall/users/UserResult";

export default {
  data() {
    return {};
  },
  created() {},
  components: {
    appFilter: UserFilter,
    appResult: UserResult,
  },
};
</script>
