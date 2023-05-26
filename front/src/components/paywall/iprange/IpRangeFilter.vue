<template>
  <div>
    <section style="margin-left: 20px; margin-top: 10px">
      <div class="box box-solid">
        <div class="box-body">
          <h3>Associate new Organization</h3>
          <!-- Add a nested panel that is initially hidden -->
          <div class="panel mt-3">
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
                role="button"
                @click="() => onOrgClick(org)"
                :class="{ active: org === selectedOrg }"
              >
                {{ org.name }}
              </li>
            </ul>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import * as iprange_types from "../../../store/types_iprange";
import * as party_types from "../../../store/types_party";
import * as login_types from "../../../store/types_login";
import { mapActions } from "vuex";
import { mapGetters } from "vuex";

const types = { ...party_types, ...iprange_types, ...login_types };

export default {
  data() {
    return {
      iprangeFilter: null,
      searchTerm: "",
      availableOrgs: [],
      selectedOrg: null,
    };
  },
  created() {},
  computed: {
    ...mapGetters({
      stateLoggedinUser: types.USG_GET_USER,
    }),
    filteredOrgs() {
      // Return a filtered list of orgs based on the search term
      return this.availableOrgs.filter((org) =>
        org.name.toLowerCase().includes(this.searchTerm.toLowerCase())
      );
    },
  },
  watch: {
    $route: "fetchData",
  },
  methods: {
    ...mapActions({
      stateIprSearchByFilter: types.IPR_ACTION_FILTER,
      statePrtGetOrgs: types.PRT_ACTION_GET_ORGS,
    }),
    fetchData() {
      if (this.$route.path == "/main/party/ipranges") {
        this.updateAvailableOrgs();
      }
    },
    onOrgClick(org) {
      this.selectedOrg = org;
      let ipRangeFilter = {
        partyName: org.name,
      };
      this.stateIprSearchByFilter(ipRangeFilter);
    },
    updateAvailableOrgs() {
      this.statePrtGetOrgs().then((res) => {
        let allOrgs = res.map((item, index) => {
          return {
            ...item,
            id: index,
          };
        });
        let associatedPartyIds = this.stateLoggedinUser.associatedPartyIds;
        if (associatedPartyIds != "") {
          associatedPartyIds = associatedPartyIds.split(",");
        } else {
          associatedPartyIds = [];
        }
        if (associatedPartyIds.length > 0) {
          this.availableOrgs = allOrgs.filter((org) =>
            associatedPartyIds.includes(org.partyId)
          );
        } else {
          if (this.stateLoggedinUser.role == "Librarian") {
            this.availableOrgs = [];
          } else {
            this.availableOrgs = allOrgs;
          }
        }
        if (!this.selectedOrg) {
          if (this.availableOrgs.length > 0) {
            this.selectedOrg = this.availableOrgs[0];
            let ipRangeFilter = {
              partyName: this.selectedOrg.name,
            };
            this.stateIprSearchByFilter(ipRangeFilter);
          }
        } else {
          if (!this.availableOrgs.includes(this.selectedOrg)) {
            this.selectedOrg = this.availableOrgs[0];
            let ipRangeFilter = {
              partyName: this.selectedOrg.name,
            };
            this.stateIprSearchByFilter(ipRangeFilter);
          }
        }
      });
    },
    onSubmit() {
      // this.stateIprSearchByFilter(this.iprangeFilter);
    },
  },
  mounted() {
    this.updateAvailableOrgs();
  },
};
</script>

<style scoped>
.scroller {
  height: 400px; /* adjust the height as per your preference */
  overflow-y: scroll; /* add a vertical scrollbar */
}
.active {
  background-color: #3a82c1;
}
</style>
