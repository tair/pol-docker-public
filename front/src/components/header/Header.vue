<template>
  <header class="main-header">
    <!-- Logo -->
    <router-link to="/" tag="a" active-class="active" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini"><b>P</b>OL</span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg"><b>Paywall</b> Outlook</span>
    </router-link>

    <div
      class="top-menu d-flex justify-content-between flex-wrap align-items-center"
    >
      <div>
        <a
          href="#"
          class="sidebar-toggle"
          data-toggle="push-menu"
          role="button"
        >
          <span class="sr-only">Toggle navigation</span>
        </a>
      </div>
      <div>
        <app-user></app-user>
      </div>
    </div>

    <!-- Header Navbar: style can be found in header.less -->
    <!-- <nav class="navbar mynav"> -->
    <!-- Sidebar toggle button-->
    <!-- 
      <div></div> -->
    <!-- Navbar Right Menu -->
    <!-- <div class="navbar-custom-menu"> -->
    <!-- <ul class="nav navbar-nav"> -->
    <!--<app-messages></app-messages>-->
    <!-- <app-notifications></app-notifications> -->
    <!--<app-tasks></app-tasks>-->
    <!-- <app-user></app-user> -->

    <!-- Control Sidebar Toggle Button -->
    <!--<li><a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a></li>-->
    <!-- </ul> -->
    <!-- </div> -->
    <!-- </nav> -->
  </header>
</template>

<script>
import User from "./User.vue";

export default {
  components: {
    appUser: User,
  },
};
</script>
<style>
.mynav {
  justify-content: flex-start !important;
}
.top-menu {
  height: 50px;
  background-color: #367fa9;
  padding-left: 5px;
  padding-right: 15px;
}
.sidebar-toggle {
  color: white;
  font-size: 20px;
  padding: 0px !important;
}
</style>
