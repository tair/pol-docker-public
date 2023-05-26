<template>
  <div>
    <app-detail-header :title="'Admin Actions'"></app-detail-header>
    <div class="row container">
      <div class="col-sm-12">
        <div class="row notif-panel">
          <div class="col-sm-12 text-xl notif-title">Action Items</div>

          <div class="col-sm-12" style="overflow-y: auto; max-height: 500px">
            <div
              v-for="actionItem in actionItems"
              :key="actionItem.id"
              class="row action-item"
            >
              <!-- Display the username -->
              <div class="col-sm-12 text-xl mt-2">
                Username: {{ actionItem.username }}
              </div>
              <!-- Display the table of organizations -->
              <b-table
                :items="actionItem.organizations"
                :fields="['name', 'actions']"
                striped
                :width="{ name: '200px', actions: '100px' }"
              >
                <template #cell(name)="data">
                  <div
                    class="d-flex justify-content-left align-items-left text-md"
                  >
                    {{ data.item.name }}
                  </div>
                </template>
                <!-- Define the template for the actions column -->
                <template #cell(actions)="data">
                  <b-form-checkbox
                    v-model="data.item.approved"
                    switch
                    @change="toggle(data.item, actionItem.username)"
                  >
                    {{ data.item.approved ? "Approved" : "Pending" }}
                  </b-form-checkbox>
                </template>
              </b-table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { mapActions, mapGetters } from "vuex";
import * as user_types from "../../store/types_user";
import * as party_types from "../../store/types_party";

const types = { ...user_types, ...party_types };

export default {
  data() {
    return {
      allOrgs: [],
      actionItems: [],
    };
  },
  created() {},
  mounted() {
    this.statePrtGetOrgs().then((res) => {
      // console.log(res);
      //from res get all orgs as a map of id and name
      this.allOrgs = res.map((item) => {
        return { partyId: item.partyId, name: item.name };
      });
      // console.log(this.allOrgs);
      this.refresh();
    });
  },
  methods: {
    ...mapActions({
      actionUsrGetAll: types.USR_ACTION_GETALL,
      actionUsrDelete: types.USR_ACTION_DELETE,
      statePrtGetOrgs: types.PRT_ACTION_GET_ORGS,
      stateUsrAddAssoc: types.USR_ACTION_ADD_ASSOC,
      stateUsrRemoveAssoc: types.USR_ACTION_RMV_ASSOC,
    }),
    toggle(data, username) {
      if (!data.approved) {
        this.stateUsrRemoveAssoc({ username: username, partyId: data.id }).then(
          (res) => {
            // console.log("stateUsrRemoveAssoc", res);
            this.refresh();
          }
        );
      } else {
        this.stateUsrAddAssoc({ username: username, partyId: data.id }).then(
          (res) => {
            // console.log("stateUsrAddAssoc", res);
            this.refresh();
          }
        );
      }

      this.refresh();
    },
    refresh() {
      this.actionUsrGetAll().then((res) => {
        res = res.filter((item) => item.role == "Librarian");
        let actionItems = [];
        res.forEach((item) => {
          console.log(item);
          let orgs = [];
          if (
            item.associatedPartyIds.length == 0 &&
            item.addedPartyIds.length == 0
          ) {
          } else {
            item.associatedPartyIds.split(",").forEach((id) => {
              let org = this.allOrgs.find((o) => o.partyId == id);
              if (org) {
                orgs.push({
                  id: id,
                  name: org.name,
                  approved: true,
                });
              }
            });
            item.addedPartyIds.split(",").forEach((id) => {
              let org = this.allOrgs.find((o) => o.partyId == id);
              if (org) {
                orgs.push({
                  id: id,
                  name: org.name,
                  approved: false,
                });
              }
            });
            actionItems.push({
              id: item.id,
              username: item.username,
              organizations: orgs,
            });
          }
        });
        this.actionItems = actionItems;
      });
    },
  },
};
</script>
<style scoped>
.text-xl {
  font-size: 20px;
  font-weight: bold;
}
.text-md {
  font-size: 15px;
  font-weight: bold;
}
.text-log {
  font-size: 15px;
  font-style: italic;
}

.container {
  /* margin-left: 10px; */
}
.notif-panel {
  height: 100%;
  /* max-height: 300px; */
  background-color: #d9dfe9;
  padding-bottom: 10px;
  margin: 10px;
  border-radius: 10px;
}
.notif-title {
  min-height: 10%;
  background-color: #367fa9;
}
.notif-content {
  height: 90%;
  margin-top: 10px;
  /* background-color: #d9dfe9; */
}
.notif-entry {
  min-height: 30px;
  background-color: white;
  border-radius: 10px;
  padding: 10px;
  margin: 10px;
  /* max-width: 90%; */
}
</style>
