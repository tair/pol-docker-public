<template>

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

</template>

<script>
  export default {
    data() {
      return {
      }
    }
  }
</script>
