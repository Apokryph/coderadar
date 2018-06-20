<template>
    <div class="project-settings">
        <h1>Project Settings</h1>

        <ul>
            <li v-for="project of projects._embedded.projectResourceList">
              <span class="project-name">name: {{ project.name }} </span>| <span class="project-url">url: <a href="">{{ project.vcsUrl }}</a></span> | <span class="status">status: online {{ project.vcsOnline }}</span>
            </li>
        </ul>
    </div>
</template>

<script>
    import axios from 'axios';

    export default {
        data() {
            return {
                projects: [],
                errors: []
            }
        },
        created() {
            axios({
                method:'get',
                url:'/api/projects'
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

</style>
