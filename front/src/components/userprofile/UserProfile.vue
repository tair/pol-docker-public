<template>
  <div class="white-box">
    <h2>User Profile</h2>
    <form>
      <p>
        Here is your account information, please click save once you are done
        changing any of the fields for changes to take place.
      </p>
      <div class="form-field">
        <label for="first-name">First Name:</label>
        <input type="text" id="first-name" v-model="firstName" />
      </div>
      <div class="form-field">
        <label for="last-name">Last Name:</label>
        <input type="text" id="last-name" v-model="lastName" />
      </div>
      <div class="form-field">
        <label for="email">Email:</label>
        <input type="text" id="email" v-model="email" />
      </div>
      <div class="form-field">
        <label for="role">Role:</label>
        <input type="text" id="role" v-model="role" readonly />
      </div>
      <div class="form-field">
        <button type="submit" @click.prevent="saveProfile">Save</button>
      </div>
    </form>
    <!-- <p class="value">{{this.responseData.message}}</p> -->
    <p class="value" style="text-align: center">
      {{ this.responseData.message }}
    </p>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import * as login_types from "../../store/types_login";
import authHeader from "../../authHeader";

const types = { ...login_types };
import axios from "axios";
export default {
  data() {
    return {
      firstName: "",
      lastName: "",
      email: "",
      permanentEmail: "",
      responseData: "",
      role: "",
    };
  },
  computed: {
    ...mapGetters({
      stateLoggedinUser: types.USG_GET_USER,
    }),
  },
  mounted() {
    // Prepopulate fields with existing data
    this.firstName = this.stateLoggedinUser.firstName;
    this.lastName = this.stateLoggedinUser.lastName;
    this.email = this.stateLoggedinUser.username;
    this.role = this.stateLoggedinUser.role;
    this.permanentEmail = this.stateLoggedinUser.username;
  },
  methods: {
    async saveProfile() {
      let val = this.stateLoggedinUser;
      console.log(this.email);

      try {
        const response = await axios.post("/api/auth/updateuser", {
          firstName: this.firstName,
          lastName: this.lastName,
          email: this.email,
          username: this.permanentEmail,
          // role: this.role,
        });
        this.responseData = response.data || {};
        this.stateLoggedinUser.firstName = this.firstName;
        this.stateLoggedinUser.lastName = this.lastName;
        this.stateLoggedinUser.username = this.email;
        this.stateLoggedinUser.username = this.email;
        setTimeout(() => {
          if (this.role == "Admin") {
            this.$router.push("/main/dashboard/admin");
          } else if (this.role == "Sales") {
            this.$router.push("/main/dashboard/sales");
          } else if (this.role == "Librarian") {
            this.$router.push("/main/dashboard/librarian");
          } else if (this.role == "User") {
            this.$router.push("/main/dashboard/general");
          }
        }, 3000); // 3 second delay
      } catch (error) {
        console.error(error);
      }
    },
  },
};
</script>

<style scoped>
.white-box {
  background-color: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0px 0px 10px lightgray;
  width: 500px;
  margin: 50px auto;
  text-align: center;
}

.form-field {
  margin-bottom: 1em;
}
label {
  display: inline-block;
  width: 6em;
  text-align: right;
  margin-right: 1em;
}
input[type="text"] {
  width: 20em;
}
button[type="submit"] {
  margin-left: 7em;
}
input[readonly] {
  background-color: lightgrey;
  color: grey;
  cursor: default;
}
</style>
