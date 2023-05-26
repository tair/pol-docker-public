<template>
  <div class="white-box">
    <img
      src="../../assets/img/phoenix-logo.png"
      alt="Phoenix Bioinformatics"
      class="logo"
    />
    <h2 class="text-center mb-4">Phoenix Bioinformatics Usage Portal</h2>
    <div class="content">
      <p class="subtitle">New Password for {{ email }}:</p>
      <div class="input-group">
        <input type="password" class="form-control" v-model="newPassword" placeholder="Enter new password">
        <p></p>
        <input type="password" class="form-control" v-model="newConfirmPassword" placeholder="Confirm new password">
      </div>
      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
      <button @click="handleSubmit" class="btn btn-primary">Submit</button>
      <p class="value">{{ responseData.message }}</p>
    </div>
  </div>
</template>
<style scoped>
  .white-box {
    background-color: white;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0px 0px 10px lightgray;
    width: 500px;
    margin: 50px auto;
    position: relative;
  }

  .logo {
    position: absolute;
    top: 10px;
    right: 10px;
    width: 20px;
    height: 25px;
  }

  .content {
    display: flex;
    flex-direction: column;
    align-items: left;
    text-align: left;
  }

  .subtitle {
    font-size: 14px;
    font-weight: bold;
    margin-bottom: 8px;
  }

  .input-group {
    display: flex;
    flex-direction: column;
    margin-bottom: 16px;
  }

  .form-control {
    font-size: 14px;
    margin-bottom: 8px;
  }

  .error {
    color: red;
    font-size: 12px;
    margin-bottom: 8px;
  }

  .btn-primary {
    font-size: 14px;
    padding: 6px 12px;
  }

  .value {
    font-size: 16px;
    margin-top: 16px;
  }
</style>

<template>
  <div class="white-box">
    <img
      src="../../assets/img/phoenix-logo.png"
      alt="Phoenix Bioinformatics"
      class="logo"
    />
    <h2 class="text-center mb-4">Phoenix Bioinformatics Usage Portal</h2>
    <div class="content">
      <!-- <p class="small_head">Password Reset For Email: {{ email }}</p> -->
      <p class="subtitle">New Password for {{ email }}:</p>
      <input type="password" v-model="newPassword">
      <p class="subtitle">Confirm New Password:</p>
      <input type="password" v-model="newConfirmPassword">
      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
      <button @click="handleSubmit">Submit</button>
      <!-- <p class="subtitle">Response:</p> -->
      <p class="value">{{ responseData.message }}</p>
    </div>
  </div>
</template>

<style scoped>
  .white-box {
    background-color: white;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0px 0px 10px lightgray;
    width: 500px;
    margin: 50px auto;
    position: relative;
  }

  .logo {
    position: absolute;
    top: 10px;
    right: 10px;
    width: 20px;
    height: 25px;
  }

  .content {
    display: flex;
    flex-direction: column;
    align-items: left;
    text-align: left;
  }
  .small_head {
    font-size: 12px;
    font-weight: bold;
    margin-bottom: 8px;
  }
  .subtitle {
    font-size: 12px;
    font-weight: bold;
    margin-bottom: 8px;
  }

  .value {
    font-size: 20px;
    margin-bottom: 16px;
  }
</style>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      form: {
        newPassword: '',
        newConfirmPassword: ''
      },
      passwordResetToken: this.$route.query.passwordResetToken,
      email: this.$route.query.email,
      responseData: '',
      newPassword: '',
      newConfirmPassword: '',
      errorMessage: ''
    }
  },
  watch: {
    '$route.query': {
      deep: true,
      handler: function() {
        this.passwordResetToken = this.$route.query.passwordResetToken;
        this.email = this.$route.query.email;
        this.sendPostRequest();
      }
    }
  },
  mounted () {
    this.sendPostRequest();
  },
  methods: {
    async sendPostRequest () {
      try {
        const response = await axios.post('/api/auth/reset', {
          passwordResetToken: this.passwordResetToken,
          email: this.email,
          password: this.newPassword
        });
        this.responseData = response.data;
      } catch (error) {
        console.error(error);
      }
    },
    handleSubmit() {
      if (this.newPassword !== this.newConfirmPassword) {
        this.errorMessage = 'Passwords do not match';
      } else {
        this.errorMessage = ''; // clear error message
        this.sendPostRequest();
        setTimeout(() => {
          this.$router.push("login");
        }, 3000); // 3 second delay
      }
    }
  }
}
</script>
  