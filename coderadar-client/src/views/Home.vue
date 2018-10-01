<template>
  <div class="home">
    <Login/>
  </div>
</template>

<script>

  import Welcome from '@/components/Welcome.vue'
  import Login from "../components/Login";

  export default {
    name: 'home',
    components: {
      Login,
      Welcome
    }
  }
</script>
