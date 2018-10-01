<template>
  <div class="analyzer-list">
    <b-list-group>
      <b-list-group-item v-for="analyzer of analyzers._embedded.analyzerResourceList">
        <span class="analyzer-name">{{ analyzer.analyzerName }}</span>
      </b-list-group-item>
    </b-list-group>
  </div>
</template>

<script>
  import axios from 'axios';

  export default {
    data() {
      return {
        analyzers: [],
        errors: []
      }
    },
    created() {
      axios({
        method: 'get',
        url: '/api/analyzers?page=0&size=2'
      })
        .then(response => {
          this.analyzers = response.data
        })
        .catch(e => {
          this.errors.push(e)
        })
    }

  }
</script>

<style scoped>

</style>
