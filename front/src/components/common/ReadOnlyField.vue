<template>
  <div class="detail-d1">
    <div class="detail-lbl">{{ label }}</div>
    <div class="detail-lbl-gry">{{ value }}</div>
  </div>
</template>

<script>
    export default {
        props: {
          label: {
            type: String,
            required: true
          },
          value: {
            required: true
          }
        }
    }
</script>

<style scoped>
  .detail-d1 {
    margin-bottom: 15px;
  }

  .detail-lbl {
    color: #4579B2;
    border-bottom: 1px solid lightgray;
  }

  .detail-lbl-gry {
    background-color: #f8f8f8;
    padding: 7px;
    border: 1px solid #E8E8E8;
  }
</style>
