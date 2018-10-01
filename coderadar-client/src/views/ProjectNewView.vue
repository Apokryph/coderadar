<template>
  <div class="project-new">
    <h1>Neues Projekt</h1>
    <b-form-input v-model="projectName"
                  type="text"
                  placeholder="Enter your project name"></b-form-input>
    <b-form-input v-model="vcsUrl"
                  type="text"
                  placeholder="https://github.com/thombergs/diffparser.git"></b-form-input>
    <b-button v-on:click="createProject" variant="success">Create Project</b-button>
    <b-alert variant="success"
             dismissible
             :show="showDismissibleSuccess"
             @dismissed="showDismissibleSuccess=false">
      Project created!
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
        projectName: '',
        vcsUrl: '',
        showDismissibleSuccess: false,
        showDismissibleFail: false,
        errors: []

      }
    }, methods: {
      createProject: function () {
        axios.post('/api/projects',
          {
            'name': '' + this.projectName + '',
            'vcsUrl': '' + this.vcsUrl + '',
            'vcsOnline': 'true'
          },
          {
            headers: {
              'Content-type': 'application/json',
              'Authorization' : localStorage.token
            }
          }
        )
          .then(response => {
            this.showDismissibleFail = false;
            this.showDismissibleSuccess = true;
          })
          .catch(e => {
            this.errors.push(e);
            this.showDismissibleSuccess = false;
            this.showDismissibleFail = true;
          })
      }
    }
  }
</script>

<style scoped>
  button, input {
    margin: 10px;
  }
</style>
