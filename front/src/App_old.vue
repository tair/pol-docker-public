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
  import Header from './components/header/Header.vue';
  import SideBar from './components/side-bar/SideBar.vue';
  import Footer from './components/Footer.vue';

  export default {
      components: {
        appHeader : Header,
        appSideBar : SideBar,
        appFooter : Footer,
      }
  }

</script>

<style>
  /********** Data table style *****************/
  .paginate_button {
    padding : 10px;
    margin-left: 0px;
    display: inline;
    border: 0px;
    cursor: pointer;
  }

  .paginate_button:hover {
    font-weight: bold;
  }

  .paginate_button.current {
    background-color: #3b8ab8;
    color: white;
    border-radius: 4px;
    padding: 3px 7px
  }

  .search {
    margin-bottom: 10px;
  }

  .info1 {
    color: #d73925;
  }

  .ln1 {
    color: #d73925;
  }

  .ln1 select {
    color: black;
  }

  tbody tr.selected {
    /*color: #ffffff;*/
    font-weight: bold;
    /*background-color: #e4b9b9;*/
    color: #d73925;
  }

  /********** Data table style *****************/


</style>
