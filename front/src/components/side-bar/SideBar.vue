<template>
  <!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
      <!-- \      <div class="user-panel"></div> -->
      <!--<app-side-bar-user></app-side-bar-user>-->
      <!--<app-side-bar-search></app-side-bar-search>-->
      <app-side-bar-menu></app-side-bar-menu>
    </section>
    <!-- /.sidebar -->
  </aside>
</template>

<script>
import User from "./User.vue";
import Search from "./Search.vue";
import Menu from "./Menu.vue";

export default {
  components: {
    appSideBarUser: User,
    appSideBarSearch: Search,
    appSideBarMenu: Menu,
  },
};
</script>
