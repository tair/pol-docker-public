<template>
  <div>
    <app-detail-header
      :title="'Activations'"
      :sub-title="''"
    ></app-detail-header>
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
import ActivationFilter from "./ActivationFilter";
import ActivationResult from "./ActivationResult";

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

<style scoped></style>
