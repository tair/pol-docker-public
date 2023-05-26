<template>
  <b-container id="mainContainer" align-v="center">
    <b-row align-v="center" align-h="center"> <FormRegister /> </b-row>
  </b-container>
</template>

<script>
import FormRegister from "../components/login/FormRegister";
export default {
  components: {
    FormRegister,
  },
};
</script>

<style></style>
