<template>
  <div>
    <b-card id="cardLogin" class="scale-in-bl">
      <b-form @submit="onSubmit" @reset="onReset" id="formRecover">
        <img
          src="../../assets/img/phoenix-logo.png"
          class="card-logo"
          alt="Phoenix Bioinformatics"
        />
        <h1 class="text-center mb-3">Password Reset</h1>
        <h2 class="text-center mb-4">Phoenix Bioinformatics Usage Portal</h2>
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
        <b-button type="submit" variant="primary" class="btn-block"
          >Send Password Email Reset</b-button
        >
      </b-form>
    </b-card>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
import * as login_types from "../../store/types_login";
import axios from 'axios';

const types = { ...login_types };
export default {
  data() {
    return {
      form: {
        email: "",
        password: "",
      },
      responseData: {},
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
  watch: {},
  methods: {
    ...mapActions({
      stateLoginAction: types.USG_ACTION_RECOVER,
    }),
    test_function(message){
      //Add email
      var AWS = require("aws-sdk");
      AWS.config.update({
        accessKeyId: process.env.VUE_APP_AWS_ACCESS_KEY_ID,
        secretAccessKey: process.env.VUE_APP_AWS_SECRET_ACCESS_KEY,
        region: 'us-west-2',
      })
      var url = process.env.VUE_APP_WEBURL;
      var ses = new AWS.SES();
      var params = {
            Destinations: [this.form.email],
            RawMessage: {
                Data: "From: subscriptions@phoenixbioinformatics.org\r\n" +
                "To: " + this.form.email + "\r\n" +
                "Subject: Reset Password for Phoenix Access Manager\r\n" +
                "MIME-Version: 1.0\r\n" +
                "Content-Type: multipart/mixed; boundary=NextPart\r\n" +
                "\r\n" +
                "--NextPart\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n\r\n" +
                "Please click the following link to reset your account: " +
                url + "/reset?passwordResetToken=" + message + "&email=" + this.form.email + "\r\n"+
                "\r\n" +
                "--NextPart--"
            }
        };
        ses.sendRawEmail(params, (err, data) => {
            if (err) {
              console.log(err, err.stack);
              }
            else {
              // console.log(data);
            }
        });
    },
    async onSubmit(event) {
      event.preventDefault();
      this.loading = true;
      // this.test_function();
      // console.log(this.form.email);
      try {
        const response = await axios.post('/api/auth/recover', {
          email: this.form.email
        });
        const message = response.data.message;
        // console.log(message);
        this.test_function(message);
        this.$router.push("login");
      } catch (error) {
        // console.log("test");
        console.error(error);
      }
    },
    onReset(event) {
      event.preventDefault();
      // Reset our form values
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
  background-color: #3d8cbc !important;
}
</style>
