<template>
  <div>
    <app-detail-header :title="'Librarian Dashboard'"></app-detail-header>
    <app-under-construction></app-under-construction>
    <!-- <div class="row container">
      <div class="col-sm-6">
        <div class="row notif-panel">
          <div class="col-sm-12 text-xl notif-title">Summary</div>
          <div class="col-sm-12 notif-content">
            <div class="row">
              <div
                class="col-sm-12 notif-entry d-flex justify-content-between align-content-center"
              >
                <div class="text-lg">Associated Institutions</div>
                <div class="text-md">Stanford University</div>
              </div>
              <div
                class="col-sm-12 notif-entry d-flex justify-content-between align-content-center"
              >
                <div class="text-lg">Associated Partners</div>
                <div class="text-md">Tair</div>
              </div>
              <div class="col-sm-12 notif-entry">
                <div>IP Address change</div>
              </div>
              <div class="col-sm-12 notif-entry">
                <div>IP Address change</div>
              </div>
              <div class="col-sm-12 notif-entry">
                <div>IP Address change</div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-6">
        <div class="row">
          <div class="col-sm-6">
            <div class="card d-flex align-items-center justify-content-center">
              <div class="text-xl">42</div>
              <div>New leads this week</div>
            </div>
          </div>
          <div class="col-sm-6">
            <div class="card d-flex align-items-center justify-content-center">
              <div class="text-xl">42</div>
              <div>New leads this week</div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6">
            <div class="card d-flex align-items-center justify-content-center">
              <div class="text-xl">42</div>
              <div>New leads this week</div>
            </div>
          </div>
          <div class="col-sm-6">
            <div class="card d-flex align-items-center justify-content-center">
              <div class="text-xl">42</div>
              <div>New leads this week</div>
            </div>
          </div>
        </div>
      </div>
    </div> -->
  </div>
</template>
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

.text-lg {
  font-size: 16px;
  font-weight: bold;
}
.text-md {
  font-size: 14px;
  color: green;
}

.container {
  margin-left: 10px;
}
.notif-panel {
  height: 100%;
  max-height: 200px;
  background-color: #d9dfe9;
  overflow-y: scroll;
  padding-bottom: 10px;
}
.notif-title {
  min-height: 10%;
  background-color: #367fa9;
}
.notif-content {
  height: 90%;
  margin-top: 5px;
  /* background-color: #d9dfe9; */
}
.notif-entry {
  min-height: 30px;
  background-color: white;
  border-radius: 10px;
  padding: 10px;
  margin: 10px;
  max-width: 90%;
}
</style>
