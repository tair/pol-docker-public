<template>
  <div>
    <app-detail-header
      :title="'Organizations'"
      :sub-title="''"
    ></app-detail-header>

    <div class="row">
      <div class="col-md-3">
        <section style="margin-left: 20px; margin-top: 10px">
          <div class="box box-solid">
            <div class="box-body">
              <h3>Associate new Organization</h3>
              <button class="btn btn-primary" @click="togglePanel">
                Show/Hide Panel
              </button>
              <!-- Add a nested panel that is initially hidden -->
              <div class="panel mt-3" v-if="showPanel">
                <div class="input-group mb-3">
                  <span class="input-group-text" id="search-label">Search</span>
                  <input
                    type="text"
                    class="form-control"
                    placeholder="Enter org name"
                    aria-label="Search"
                    aria-describedby="search-label"
                    v-model="searchTerm"
                  />
                </div>
                <ul class="list-group scroller">
                  <li
                    v-for="org in filteredOrgs"
                    :key="org.id"
                    class="list-group-item d-flex justify-content-between align-items-center"
                  >
                    {{ org.name }}
                    <div class="btn-group" role="group">
                      <button
                        type="button"
                        class="btn btn-success"
                        @click="associateOrg(org.id)"
                        v-if="!isAssociated(org.id)"
                      >
                        Add
                      </button>
                      <span class="badge bg-info" v-else>Added</span>
                    </div>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </section>
      </div>
      <div class="col-md-9">
        <section style="margin-top: 10px">
          <h3>Approved Organizations</h3>
          <p
            v-if="currentApprovedOrgs.length === 0"
            class="alert alert-warning"
          >
            No associated orgs found.
          </p>
          <div class="card" v-for="org in currentApprovedOrgs" :key="org.id">
            <div class="card-body">
              <h5 class="card-title">{{ org.name }}</h5>
              <p class="card-text">{{ org.description }}</p>
            </div>
          </div>

          <div class="mt-2">
            <h3>Pending Organizations</h3>
            <p
              v-if="currentPendingOrgs.length === 0"
              class="alert alert-warning"
            >
              No pending orgs found.
            </p>
            <div class="card" v-for="org in currentPendingOrgs" :key="org.id">
              <div class="card-body">
                <h5 class="card-title">{{ org.name }}</h5>
                <p class="card-text">{{ org.description }}</p>
              </div>
            </div>
          </div>
          <div class="mt-3">
            <!-- Add a submit button at the end of section -->
            <button
              class="btn btn-primary"
              @click="submitOrgs"
              :disabled="submitUpdate.submitting"
            >
              Submit
            </button>
            <!-- Add a "submitting" message when submit is clicked -->
            <p v-if="submitUpdate.submitting" class="alert alert-info">
              Submitting...
            </p>
            <!-- Add a message "Updated" if successful in submit -->
            <p v-if="submitUpdate.success" class="alert alert-success">
              Updated
            </p>
            <!-- Show error message if submit fails for any reason -->
            <p v-if="submitUpdate.error" class="alert alert-danger">
              {{ error }}
            </p>
          </div>
        </section>
      </div>
    </div>
  </div>
</template>
<script>
import * as party_types from "../../store/types_party";
import * as login_types from "../../store/types_login";
import { mapActions, mapGetters } from "vuex";
import axios from "axios";
const types = { ...party_types, ...login_types };
export default {
  data() {
    return {
      // The selected org from the dropdown
      selectedOrg: "",
      searchTerm: "",
      availableOrgs: [],
      currentApprovedOrgs: [],
      currentPendingOrgs: [],
      showPanel: false,
      submitUpdate: {
        submitting: false,
        success: false,
        error: null,
      },
    };
  },
  computed: {
    filteredOrgs() {
      // Return a filtered list of orgs based on the search term
      return this.availableOrgs.filter((org) =>
        org.name.toLowerCase().includes(this.searchTerm.toLowerCase())
      );
    },
    ...mapGetters({
      stateLoggedinUser: types.USG_GET_USER,
    }),
  },
  mounted() {
    this.updateAvailableOrgs();
  },
  methods: {
    ...mapActions({
      statePrtGetOrgs: types.PRT_ACTION_GET_ORGS,
      stateLogin_updateUser: types.USG_ACTION_USER_UPDATE,
    }),
    updateAvailableOrgs() {
      this.statePrtGetOrgs().then((res) => {
        this.availableOrgs = res.map((item, index) => {
          return {
            ...item, // Spread the original item properties
            id: index, // Add the id property with the index value
          };
        });
        this.updateCurrentAssociatedOrgs();
        this.updateCurrentPendingOrgs();
      });
    },
    updateCurrentAssociatedOrgs() {
      let associatedPartyIds = this.stateLoggedinUser.associatedPartyIds;
      if (associatedPartyIds != "") {
        associatedPartyIds = associatedPartyIds.split(",");
      } else {
        associatedPartyIds = [];
      }
      // Filter the available orgs to only those that are associated
      this.currentApprovedOrgs = this.availableOrgs.filter((org) =>
        associatedPartyIds.includes(org.partyId)
      );
    },
    updateCurrentPendingOrgs() {
      let pendingPartyIds = this.stateLoggedinUser.addedPartyIds;
      // console.log(pendingPartyIds);
      if (pendingPartyIds != "") {
        pendingPartyIds = pendingPartyIds.split(",");
      } else {
        pendingPartyIds = [];
      }
      // Filter the available orgs to only those that are pending
      this.currentPendingOrgs = this.availableOrgs.filter((org) =>
        pendingPartyIds.includes(org.partyId)
      );
    },
    // This method is called when the user selects an org from the dropdown
    associateOrg(id) {
      const org = this.availableOrgs.find((i) => i.id === id);
      // Add the org object to the current orgs list
      this.currentPendingOrgs.push(org);
      // Reset the selected org to empty
      this.selectedOrg = "";
    },
    togglePanel() {
      this.showPanel = !this.showPanel;
    },
    isAssociated(id) {
      return (
        this.currentApprovedOrgs.some((org) => org.id === id) ||
        this.currentPendingOrgs.some((org) => org.id === id)
      );
    },
    // This method is called when the user clicks on the remove button of an org card
    removeOrg(id) {
      // Filter out the org object from the current orgs list by id
      this.currentPendingOrgs = this.currentPendingOrgs.filter(
        (i) => i.id !== id
      );
    },
    async submitOrgs() {
      // A method to update the database with the current orgs
      this.submitUpdate.submitting = true; // Set the submitting flag to true
      this.submitUpdate.success = false; // Reset the success flag to false
      this.submitUpdate.error = null; // Reset the error message to null

      let pendingPartyIds = this.currentPendingOrgs.map((cp) => cp.partyId);
      if (pendingPartyIds.length > 0) {
        pendingPartyIds = pendingPartyIds.join(",");
      } else {
        pendingPartyIds = "";
      }
      try {
        const response = await axios.post("/api/auth/updateuser", {
          username: this.stateLoggedinUser.username,
          addedPartyIds: pendingPartyIds,
        });
        this.submitUpdate.submitting = false; // Set the submitting flag to true
        this.submitUpdate.success = true;
        let updatedUser = this.stateLoggedinUser;
        updatedUser.addedPartyIds = pendingPartyIds;
        this.stateLogin_updateUser(updatedUser);
        this.updateCurrentAssociatedOrgs();
        setTimeout(() => {
          this.submitUpdate.success = false;
        }, 1000);
      } catch (error) {
        console.error(error);
        this.submitUpdate.submitting = false; // Set the submitting flag to true
        this.submitUpdate.success = false; // Reset the success flag to false
        this.submitUpdate.error = "Unknwon Error";
      }
    },
  },
};
</script>
<style scoped>
.scroller {
  height: 400px; /* adjust the height as per your preference */
  overflow-y: scroll; /* add a vertical scrollbar */
}
.alert {
  border-radius: 10px;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
}

/* Change the font-weight and text-transform of the submit button */
.btn-primary {
  font-weight: bold;
  text-transform: uppercase;
}
</style>
