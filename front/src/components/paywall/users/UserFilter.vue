<template>
  <div>
    <section style="margin-left: 20px; margin-top: 10px">
      <button
        @click.prevent="onSubmit()"
        class="btn btn-danger btn-sm btn-block margin-bottom"
      >
        <i class="fa fa-search"></i> Search
      </button>

      <div class="box box-solid">
        <div class="box-body">
          <form role="form">
            <div class="form-group">
              <label for="partyEmail" class="control-label text-primary">
                First Name
              </label>
              <div class="input-group">
                <input
                  v-model="userFilter.firstName"
                  placeholder="first name"
                  type="text"
                  class="form-control"
                  id="firstName"
                />
                <div class="input-group-addon">
                  <i class="fa fa-user"></i>
                </div>
              </div>
            </div>
            <hr style="border: 0.5px solid #efefef" />
            <div class="form-group">
              <label class="control-label text-primary">User Type</label>
              <br />
              <input
                type="checkbox"
                id="ttypeCreate"
                value="create"
                v-model="userFilter.userType"
              />
              Sales<br />
              <input
                type="checkbox"
                id="ttypeCreateFree"
                value="create_free"
                v-model="userFilter.userType"
              />
              Librarian<br />
              <input
                type="checkbox"
                id="ttypeCreateTeaching"
                value="create_teaching"
                v-model="userFilter.userType"
              />
              Admin
            </div>
          </form>
        </div>
      </div>
    </section>
  </div>
</template>
<script>
import * as types from "../../../store/types_activation";
import { mapActions } from "vuex";
import { mapGetters } from "vuex";
export default {
  name: "UserFilter",
  data() {
    return {
      userFilter: {},
    };
  },
  computed: {
    ...mapGetters({
      stateActData: types.ACT_GET_DATA,
      stateActFilter: types.ACT_GET_FILTER,
      stateActIsError: types.ACT_IS_ERROR,
      stateActIsLoading: types.ACT_IS_LOADING,
    }),
  },
  methods: {
    onSubmit() {
      this.$emit("onSubmit", this.userFilter);
    },
  },
};
</script>
