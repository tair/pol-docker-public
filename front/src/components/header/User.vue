<template>
  <!-- User Account: style can be found in dropdown.less -->
  <li class="dropdown myuser user-menu">
    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
      <i class="fa fa-user-o"></i>
      <!--<img src="/src/assets/img/user2-160x160.jpg" class="user-image" alt="User Image">-->
      <span class="hidden-xs"
        >{{ stateLoggedinUser.firstName }}
        {{ stateLoggedinUser.lastName }}</span
      >
    </a>
    <ul class="dropdown-menu">
      <!-- User image -->
      <li class="user-header">
        <!--<img src="/src/assets/img/user2-160x160.jpg" class="img-circle" alt="User Image">-->
        <i class="fa fa-4x fa-user" style="color: white"></i>
        <p>
          <small>Name: </small>
          {{ stateLoggedinUser.firstName }} {{ stateLoggedinUser.lastName }}
        </p>
        <p>
          <small>Email: </small>
          {{ stateLoggedinUser.username }}
        </p>
      </li>
      <!-- Menu Footer-->
      <li class="user-footer">
        <!-- <div class="pull-left">
          <a href="#" class="btn btn-default btn-flat">Profile</a>
        </div> -->
        <div class="pull-right">
          <!-- <b-button class="btn btn-flat" @click="editProfile">Edit</b-button> -->
          <b-button class="btn btn-flat">
            <router-link to="/user">Edit</router-link>
          </b-button>
          <b-button class="btn btn-flat" @click="signOut">Sign out</b-button>
        </div>
      </li>
    </ul>
  </li>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
import * as login_types from "../../store/types_login";

const types = { ...login_types };
export default {
  computed: {
    ...mapGetters({
      stateLoggedinUser: types.USG_GET_USER,
    }),
  },
  methods: {
    signOut() {
      this.$store.dispatch(types.USG_ACTION_LOGOUT);
    },
    editProfile() {},
  },
};
</script>
<style scoped>
.myuser {
  margin-right: 20px;
}
.dropdown-menu {
  left: -45px;
}
.myuser a {
  color: white;
  font-size: 15px;
}
.user-header {
  padding: 10px;
  margin-bottom: 5px;
  width: 100%;
}
.user-header p {
  color: white;
  font-size: 15px;
}
</style>
