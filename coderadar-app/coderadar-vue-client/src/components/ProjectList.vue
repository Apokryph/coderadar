<template>
  <div class="project-list">
    <b-list-group>
      <b-list-group-item v-for="project of projects._embedded.projectResourceList">
        <span class="project-name">name: {{ project.name }} </span>| <span class="project-url">url: <a href="">{{ project.vcsUrl }}</a></span>
        | <span class="status">status: online {{ project.vcsOnline }}</span>
        | <span class="project-id">self: {{ project._links.self.href }}</span>
        <!--<router-link :to="{ name: 'projects-detail', params: { projectName: project.name } }">Zur Detailseite</router-link>-->
        <br>
        <b-button v-on:click="deleteProject(project._links.self.href)" size="sm" variant="danger">
          delete
        </b-button>
      </b-list-group-item>
    </b-list-group>
    <b-alert variant="success"
             dismissible
             :show="showDismissibleSuccess"
             @dismissed="showDismissibleSuccess=false">
      Project deleted!
    </b-alert>
    <b-alert variant="danger"
             dismissible
             :show="showDismissibleFail"
             @dismissed="showDismissibleFail=false">
      Something went wrong!
    </b-alert>
  </div>
</template>

<script>
  import axios from 'axios';

  export default {
    data() {
      return {
        projects: [],
        errors: [],
        showDismissibleSuccess: false,
        showDismissibleFail: false
      }
    }, methods: {
      deleteProject: function (url) {
        axios.delete(''+url+'')
          .then(response => {
            this.showDismissibleFail = false;
            this.showDismissibleSuccess = true
          })
          .catch(e => {
            this.errors.push(e)
            this.showDismissibleSuccess = false
            this.showDismissibleFail = true;
          })
      }
    },
    created() {
      axios({
        method: 'get',
        url: '/api/projects'
      })
        .then(response => {
          this.projects = response.data
        })
        .catch(e => {
          this.errors.push(e)
        })
    }

  }
</script>

<style scoped>
  .project-list {
    padding: 10px;
  }
</style>
