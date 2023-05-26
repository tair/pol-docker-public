<template>
  <div>
    <app-detail-header :title="'Sales Dashboard'"></app-detail-header>
    <app-under-construction></app-under-construction>
    <!-- <div class="row container">
      <div class="col-sm-6">
        <div class="row">
          <div class="col-sm-6">
            <div class="card d-flex align-items-center justify-content-center">
              <div class="text-xl">4</div>
              <div>New organizations added this week!</div>
            </div>
          </div>
          <div class="col-sm-6">
            <div class="card d-flex align-items-center justify-content-center">
              <div class="text-xl">20</div>
              <div>New users registered this week!</div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6">
            <div class="card d-flex align-items-center justify-content-center">
              <div class="text-xl">February</div>
              <div>Usage stats processed for this month!</div>
            </div>
          </div>
          <div class="col-sm-6">
            <div class="card d-flex align-items-center justify-content-center">
              <div class="text-xl">2000</div>
              <div>New sessions recorded last month!</div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-6">
        <div class="row notif-panel">
          <div class="col-sm-12 text-xl notif-title">Notifications</div>
          <div class="col-sm-12 notif-content">
            <div class="row">
              <div class="col-sm-12 notif-entry">
                <div>Usage and Sessions updated for February 2023</div>
              </div>
              <div class="col-sm-12 notif-entry">
                <div>Librarian added: John Doe, University of LA</div>
              </div>
              <div class="col-sm-12 notif-entry">
                <div>Activation Code Deleted: Sales Guy</div>
              </div>
              <div class="col-sm-12 notif-entry">
                <div>Librarian added: Jane Joe, Stanford University</div>
              </div>
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
  margin-top: 20px;
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
