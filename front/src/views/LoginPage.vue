<template>
  <b-container id="mainContainer" align-v="center">
    <b-row align-v="center" align-h="center"> <FormLogin /> </b-row>
  </b-container>
</template>

<script>
import FormLogin from "../components/login/FormLogin";
export default {
  components: {
    FormLogin,
  },
};
</script>

<style></style>
