<template>
  <div class="white-box">
    <h1 class="title">Activate Your Account</h1>
    <div class="content">
      <p class="subtitle">Activation Code:</p>
      <p class="value">{{ activationCode }}</p>
      <p class="subtitle">Email:</p>
      <p class="value">{{ email }}</p>
      <p class="subtitle">Response:</p>
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
    text-align: center;
  }

.title {
  font-size: 32px;
  font-weight: bold;
  margin-bottom: 24px;
}

.content {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
}

.subtitle {
  font-size: 24px;
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
      activationCode: this.$route.query.activationCode,
      email: this.$route.query.email,
      responseData: ''
    }
  },
  watch: {
    '$route.query': {
      deep: true,
      handler: function() {
        this.activationCode = this.$route.query.activationCode;
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
        const response = await axios.post('/api/auth/activate', {
          activationCode: this.activationCode,
          email: this.email
        });
        this.responseData = response.data;
        setTimeout(() => {
          this.$router.push("/login");
        }, 3000);
      } catch (error) {
        console.error(error);
      }
    }
  }
}
</script>
