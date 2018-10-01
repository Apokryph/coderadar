<template>
  <div class="project-details">
    <h1>Project Details</h1>
    <div>{{ project.name }}</div>
  </div>
</template>

<script>
  import axios from 'axios';

  export default {
    name: "project-detail-view",
    props: ['pId'],
    data() {
      project: []
    },
    created() {
      axios({
        method: 'get',
        url: '/api/projects/'+this.pId+''
      })
        .then(response => {
          this.project = response.data
        })
        .catch(e => {
          this.errors.push(e)
        })
    }


  }
</script>

<style scoped>

</style>
