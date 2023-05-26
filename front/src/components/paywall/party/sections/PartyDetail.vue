<template>

  <div>
    <app-detail-header :title="'Party'" :sub-title="'Details'"></app-detail-header>

    <section class="content">
      <span>{{ title }}</span>
      <div>Details for: {{ id }}</div>
    </section>

  </div>

</template>
<script>
  export default {
    data() {
      return {
        id: this.$route.params.id,
      }
    },
    watch: {
      '$route'(to, from) {
        this.id = to.params.id;
      }
    }
  }
</script>

<style scoped>
</style>
