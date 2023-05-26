<template>
  <div>
    <app-detail-header :title="'Admin Dashboard'"></app-detail-header>
    <div class="col-sm-12">
      <h1>Coming Soon!</h1>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
    };
  },
};
</script>

<style scoped>
.mylabel {
  width: 100%;
  text-align: center;
  margin: auto;
  font-size: 2.2em;
  font-style: italic;
  text-anchor: middle;
  color: black;
}
.card {
  height: 100px;
  /* margin-left: 10px; */
  /* margin-right: 20px; */
  border-radius: 10px;
  margin-bottom: 10px;
  background-color: #d9dfe9;
}

.text-xl {
  font-size: 20px;
  font-weight: bold;
}
.text-md {
  font-size: 15px;
  font-weight: bold;
}
.text-log {
  font-size: 15px;
  font-style: italic;
}

.container {
  margin-left: 10px;
}
.notif-panel {
  height: 100%;
  max-height: 300px;
  background-color: #d9dfe9;
  overflow-y: scroll;
  padding-bottom: 10px;
  margin: 10px;
  border-radius: 10px;
}
.notif-title {
  min-height: 10%;
  background-color: #367fa9;
}
.notif-content {
  height: 90%;
  margin-top: 10px;
  /* background-color: #d9dfe9; */
}
.notif-entry {
  min-height: 30px;
  background-color: white;
  border-radius: 10px;
  padding: 10px;
  margin: 10px;
  /* max-width: 90%; */
}
</style>
