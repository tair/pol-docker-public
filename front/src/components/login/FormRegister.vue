<template>
  <div>
    <b-card id="cardLogin" class="scale-in-bl">
      <b-form @submit="onSubmit" @reset="onReset" v-if="show" id="formLogin">
        <h1 class="text-center mb-3">Register</h1>
        <span class="text-center mb-4 other-account"
          >Create your own account</span
        >
        <b-form-group id="input-group-1" label-for="email">
          <b-form-input
            id="email"
            class="input"
            v-model="form.email"
            type="email"
            placeholder="E-mail"
            required
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
          ></b-form-input>
        </b-form-group>

        <div class="d-flex justify-content-center">
          <b-form-input
            id="firstName"
            class="input"
            v-model="form.firstName"
            placeholder="First Name"
            type="text"
            required
          ></b-form-input>
          <b-form-input
            id="lastName"
            class="input"
            v-model="form.lastName"
            placeholder="Last Name"
            type="text"
            required
          ></b-form-input>
        </div>
        <div class="d-flex justify-content-start">
          <div>
            <span>Role: </span
            ><b-dropdown id="dropdown-1" :text="form.role" class="m-md-2">
              <b-dropdown-item
                v-for="(n, idx) in roles"
                :key="idx"
                @click="form.role = n"
                >{{ n }}</b-dropdown-item
              >
            </b-dropdown>
          </div>
        </div>

        <div class="d-flex justify-content-start flex-wrap">
          <b-form-checkbox value="remember" class="remember"
            >I have read and agree with the terms of use</b-form-checkbox
          >
        </div>

        <div class="d-flex justify-content-center mt-3">
          <b-button
            v-if="!loading"
            type="submit"
            variant="primary"
            class="btn-login"
            >Register</b-button
          >
          <b-button v-if="loading" disabled variant="primary" class="btn-login"
            >...
          </b-button>
        </div>
        <div v-if="errMsg != null" class="d-flex justify-content-center mt-3">
          <span class="error-msg">{{ errMsg }}</span>
        </div>
        <div class="d-flex justify-content-center flex-wrap mt-4 register">
          <span class="mr-2">Already have an account?</span>
          <a @click="$router.go(-1)" class="loginAccount">Login</a>
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
        email: "",
        password: "",
        firstName: "",
        lastName: "",
        role: "Admin",
        activationCode: "",
        active: false,
      },
      roles: ["Admin", "User", "Librarian", "Guest", "Sales"],
      show: true,
      loading: false,
      errMsg: null,
    };
  },
  computed: {},
  watch: {},
  methods: {
    ...mapActions({ stateRegisterAction: types.USG_ACTION_REGISTER }),
    test_function() {
      //Add email
      var AWS = require("aws-sdk");
      AWS.config.update({
        accessKeyId: process.env.VUE_APP_AWS_ACCESS_KEY_ID,
        secretAccessKey: process.env.VUE_APP_AWS_SECRET_ACCESS_KEY,
        region: 'us-west-2',
      })
      var url = process.env.VUE_APP_WEBURL;
      var ses = new AWS.SES();
      // this.configureAwsSdk()
      var params = {
        Destinations: [this.form.email],
        RawMessage: {
          Data:
            "From: subscriptions@phoenixbioinformatics.org\r\n" +
            "To: " + this.form.email + "\r\n" +
            "Subject: Activate your Phoenix Access Manager Account\r\n" +
            "MIME-Version: 1.0\r\n" +
            "Content-Type: multipart/mixed; boundary=NextPart\r\n" +
            "\r\n" +
            "--NextPart\r\n" +
            "Content-Type: text/html\r\n" +
            "\r\n\r\n" +
            "Please click the following link to activate your account: " +
            url + "/activate?activationCode=" +
            this.form.activationCode +
            "&email=" +
            this.form.email +
            "\r\n" +
            "\r\n" +
            "--NextPart--",
        },
      };
      ses.sendRawEmail(params, (err, data) => {
        if (err) {
          console.log(err, err.stack);
        } else {
          // console.log(data);
        }
      });
    },
    onSubmit(event) {
      event.preventDefault();
      this.loading = true;
      const emailId = this.form.email;
      const crypto = require("crypto");
      const activationCode = crypto.randomBytes(15).toString("hex");
      this.form.activationCode = activationCode;
      this.stateRegisterAction(this.form).then(
        (res) => {
          this.loading = false;
          if (res) {
            this.test_function();
            alert("An email has been sent to your email address. Please check your email to activate your account before logging in.");
            this.$router.push("login");
          }
        },
        (err) => {
          this.loading = false;
          this.errMsg = err;
          // console.log("Error ", err);
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
  },
};
</script>

<style>
.error-msg {
  color: red;
  font-size: 12px;
}
</style>
