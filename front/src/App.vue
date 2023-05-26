<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
import * as login_types from "./store/types_login";

const types = { ...login_types };

export default {
  name: "App",
  created() {
    console.log("App created ", process.env.VUE_APP_AWS_ACCESS_KEY_ID);
    const currentPath = this.$router.history.current.path;
    let session = this.$cookies.get("session");
    if (session != null) {
      this.stateSessionAction(session);
    }

    if (
      this.stateLoggedinUser.accessToken != null ||
      (session && session.accessToken != null)
    ) {
      if (currentPath === "/" || currentPath === "/app") {
        this.$router.push("/main/dashboard");
      }
    } else {
      if (
        currentPath != "/activate" &&
        currentPath != "/recover" &&
        currentPath != "/reset"
      ) {
        this.$router.push("/login");
      }
    }
  },

  computed: {
    ...mapGetters({
      stateLoggedinUser: types.USG_GET_USER,
    }),
  },
  watch: {
    stateLoggedinUser: {
      handler: function (val, oldVal) {
        if (val != null && val.accessToken != null) {
        } else {
          this.$router.push("/login");
        }
      },
      deep: true,
    },
  },
  methods: {
    ...mapActions({
      stateSessionAction: types.USG_ACTION_SESSION,
    }),
  },
};
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  height: 100vh;
}
tbody tr.selected {
  /*color: #ffffff;*/
  font-weight: bold;
  /*background-color: #e4b9b9;*/
  color: #d73925;
}

tbody tr:hover {
  cursor: pointer;
}
</style>
