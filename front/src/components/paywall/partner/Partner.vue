<template>

  <div>
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

</template>

<script>
  export default {
    data() {
      return {
        name: 'Partner',
      }
    }
  }
</script>
