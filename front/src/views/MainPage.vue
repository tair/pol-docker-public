<template>
  <div class="wrapper">
    <app-header></app-header>
    <app-side-bar></app-side-bar>

    <div class="content-wrapper">
      <transition
        appear
        enter-class=""
        enter-active-class="animated fadeIn"
        leave-class=""
        leave-active-class=""
      >
        <keep-alive>
          <router-view></router-view>
        </keep-alive>
      </transition>
    </div>

    <app-footer></app-footer>
  </div>
</template>

<script>
import Header from "../components/header/Header.vue";
import SideBar from "../components/side-bar/SideBar.vue";
import Footer from "../components/Footer.vue";

export default {
  components: {
    appHeader: Header,
    appSideBar: SideBar,
    appFooter: Footer,
  },
};
</script>

<style></style>
