<template>
  <div>
    <app-detail-header
      :title="'Consortiums'"
      :sub-title="''"
    ></app-detail-header>

    <div class="row">
      <div class="col-md-3">
        <appFilter></appFilter>
      </div>
      <div class="col-md-9">
        <appResult partyType="consortium"></appResult>
      </div>
    </div>
  </div>
</template>

<script>
import PartyListResult from "../../components/paywall/party/party-list/PartyListResult";
import PartyListFilter from "../../components/paywall/party/party-list//PartyListFilter";

export default {
  data() {
    return {};
  },
  components: {
    appResult: PartyListResult,
    appFilter: PartyListFilter,
  },
};
</script>

<style scoped></style>
