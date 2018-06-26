<template>
  <div class="analyzers">
    <h1>Analyzers</h1>
    <AnalyzerList/>
  </div>
</template>

<script>
  import AnalyzerList from '@/components/AnalyzerListGlobal.vue'; // @ is an alias to /src

  export default {
    name: 'analyzersView',
    components: {
      AnalyzerList
    }
  }
</script>

<style scoped>

</style>
