<template>
  <div>
    <b-card id="cardLogin" class="scale-in-bl">
      <b-form @submit="onSubmit" @reset="onReset" v-if="show" id="formLogin">
        <img src="../../assets/img/phoenix-logo.png" class="card-logo" alt="Phoenix Bioinformatics"></img>
        <h1 class="text-center mb-3">Login</h1>
        <h2 class="text-center mb-4">Phoenix Bioinformatics Usage Portal</h2>
        <b-form-group id="input-group-1" label-for="email">
          <b-form-input
            id="email"
            class="input"
            v-model="form.username"
            type="email"
            placeholder="E-mail"
            @change="onChangeEmail"
            required
            autofocus
          ></b-form-input>
        </b-form-group>
        <b-form-group id="input-group-2" label-for="input-2">
          <b-form-input
            id="passord"
            class="input"
            v-model="form.password"
            placeholder="Password"
            type="password"
            required
            @change="onChangePwd"
          ></b-form-input>
        </b-form-group>
        <div class="d-flex justify-content-between align-items-center mt-3 mb-3">
          <b-form-checkbox size="lg" class="remember" value="remember" style="margin-right: 10px !important;">
            Remember Me
          </b-form-checkbox>
          <router-link to="/recover" class="forgotPassword">
            Forgot Password?
          </router-link>
        </div>

        <b-button
          v-if="!loading"
          type="submit"
          variant="primary"
          class="btn-block"
          >Login</b-button
        >
        <b-button v-if="loading" disabled variant="primary" class="btn-block"
          >...
        </b-button>
        <div v-if="errMsg != null" class="text-danger mt-3 mb-3 text-center">
          {{ errMsg }}
        </div>
        <div class="text-center mt-3 mb-3">
          <span class="mr-2">No Account?</span>
          <router-link to="/register" class="createAccount"
            >Register</router-link
          >
        </div>
      </b-form>
    </b-card>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
import * as login_types from "../../store/types_login";

const types = { ...login_types };
export default {
  data() {
    return {
      form: {
        username: "",
        password: "",
      },
      show: true,
      loading: false,
      errMsg: null,
    };
  },
  computed: {
    ...mapGetters({
      stateLoggedinUser: types.USG_GET_USER,
    }),
  },
  watch: {
    stateLoggedinUser: {
      handler: function (val, oldVal) {
        // console.log("stateLoggedinUser", val);
      },
      deep: true,
    },
  },
  methods: {
    ...mapActions({
      stateLoginAction: types.USG_ACTION_LOGIN,
    }),
    onSubmit(event) {
      event.preventDefault();
      this.loading = true;
      this.errMsg = null;
      this.stateLoginAction(this.form).then(
        (res) => {
          // console.log("Login Success ", this.stateLoggedinUser);        
          this.loading = false;
          this.errMsg = null;
          let val = this.stateLoggedinUser;
          this.$cookies.set(
            "session",
            this.stateLoggedinUser,
            "1h"
          );
          if (val != null && val.accessToken != null) {
            if (val.role == "Admin") {
              this.$router.push("/main/dashboard/admin");
            } else if (val.role == "Sales") {
              this.$router.push("/main/dashboard/sales");
            } else if (val.role == "Librarian") {
              this.$router.push("/main/dashboard/librarian");
            } else if (val.role == 'User') {
              this.$router.push("/main/dashboard/general");
            }
          }
        },
        (err) => {
          this.loading = false;
          this.errMsg = err;
        }
      );
    },
    onReset(event) {
      event.preventDefault();
      // Reset our form values
      this.form.email = "";
      this.form.name = "";
      this.form.food = null;
      this.form.checked = [];
      // Trick to reset/clear native browser form validation state
      this.show = false;
      this.$nextTick(() => {
        this.show = true;
      });
    },
    onChangeEmail(e) {
      // console.log(e);
      this.errMsg = null;
    },
    onChangePwd(e) {
      this.errMsg = null;
    },
  },
};
</script>

<style>
#cardLogin {
  width: 30%;
  margin: 0 auto;
  position: absolute;
  top: 25%;
  left: 50%;
  transform: translate(-50%, -50%);
}
.error-msg {
  color: red;
  font-size: 14px;
}
h1 {
  font-size: 3em !important;
}
.input {
  font-size: 1.5em !important;
}
.mt-3.mb-3 {
  font-size: 1.5em !important;
}
.remember {
  height: 10px;
  width: 200px;
  vertical-align: middle;
}
.card-logo {
    position: absolute;
    top: 10px;
    right: 10px;
    /* width: 5%;
    height: 5%; */
}
body {
  background-color:  #3d8cbc !important;
}
</style>
