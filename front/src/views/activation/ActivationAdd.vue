<template>
  <div id="app">
    <b-container>
      <b-row>
        <b-col>
          <b-card
            title="Add New Activation Code"
            class="mt-3"
            header-tag="header"
            variant="primary"
          >
            <b-form @submit.prevent="onSubmit">
              <b-form-group
                label="Activation Code"
                label-class="font-weight-bold"
              >
                <b-form-input
                  v-model.trim="formData.activationCode"
                  required
                ></b-form-input>
              </b-form-group>

              <b-form-group label-class="font-weight-bold">
                <b-row>
                  <b-col md="6">
                    <b-form-group label="Period">
                      <b-form-select
                        v-model="formData.period"
                        :options="periodOptions"
                        required
                      ></b-form-select>
                    </b-form-group>
                  </b-col>
                  <b-col md="6">
                    <b-form-group label="Purchase Date">
                      <b-form-datepicker
                        v-model="formData.purchaseDate"
                        lazy
                        required
                      ></b-form-datepicker>
                    </b-form-group>
                  </b-col>
                </b-row>
              </b-form-group>

              <b-form-group label-class="font-weight-bold">
                <b-row>
                  <b-col md="6">
                    <b-form-group
                      label="Partner Type"
                      label-class="font-weight-bold"
                    >
                      <b-form-radio-group v-model="formData.partnerId" required>
                        <b-form-radio
                          v-for="(obj, i) in partnerTypeOptions"
                          :key="i"
                          :value="obj.value"
                          >{{ obj.text }}</b-form-radio
                        >
                      </b-form-radio-group>
                    </b-form-group>
                  </b-col>
                  <b-col md="6">
                    <b-form-group
                      label="Transaction Type"
                      label-class="font-weight-bold"
                    >
                      <b-form-select
                        v-model="formData.transactionType"
                        :options="transactionTypeOptions"
                        required
                      ></b-form-select>
                    </b-form-group>
                  </b-col>
                </b-row>
              </b-form-group>

              <b-form-group label="Party ID" label-class="font-weight-bold">
                <b-form-input
                  v-model.trim="formData.partyId"
                  required
                  type="number"
                ></b-form-input>
              </b-form-group>

              <b-button type="submit" variant="primary" :disabled="submitting">
                <b-spinner small v-if="submitting" class="mr-2"></b-spinner>
                {{ submitting ? "Submitting" : "Submit" }}
              </b-button>
            </b-form>
            <b-alert
              :show="showNotification"
              :variant="notificationVariant"
              class="mt-3"
            >
              {{ notificationMessage }}
            </b-alert>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import * as activation_types from "../../store/types_activation";
const types = { ...activation_types };
import { mapGetters, mapActions } from "vuex";
export default {
  data() {
    return {
      formData: {
        activationCode: "",
        period: 365,
        purchaseDate: "",
        partnerId: "",
        partyId: null,
      },
      periodOptions: [
        { text: "Select Period", value: null, disabled: true },
        { text: "Month", value: 30 },
        { text: "Year", value: 365 },
      ],
      partnerTypeOptions: [
        { text: "Tair", value: "tair" },
        { text: "Repbase", value: "repbase" },
        { text: "Morphobank", value: "morphobank" },
      ],
      partyIdOptions: [
        // Add your organizations here
        { text: "Select Organization", value: null, disabled: true },
        { text: "Organization 1", value: 1 },
        { text: "Organization 2", value: 2 },
      ],
      transactionTypeOptions: [
        { text: "Select Transaction Type", value: null, disabled: true },
        { text: "create", value: "create" },
        { text: "create_free", value: "create_free" },
        { text: "create_teaching", value: "create_teaching" },
      ],
      submitting: false,
      showNotification: false,
      notificationVariant: "success",
      notificationMessage: "",
    };
  },
  methods: {
    ...mapActions({
      stateActCreate: types.ACT_ACTION_CREATE,
    }),
    async onSubmit() {
      // console.log("Form submitted ", this.formData);
      let date = new Date(this.formData.purchaseDate);
      let formattedDate = date.toISOString().split("T")[0] + " 00:00:00";
      this.formData.purchaseDate = formattedDate;
      this.submitting = true;
      this.stateActCreate(this.formData)
        .then((res) => {
          this.showNotification = true;
          this.notificationVariant = "success";
          this.notificationMessage = "Activation code submitted successfully!";
          this.submitting = false;
        })
        .catch((err) => {
          this.showNotification = true;
          this.notificationVariant = "danger";
          this.notificationMessage =
            "An error occurred while submitting the activation code.";
        });
    },
  },
};
</script>
