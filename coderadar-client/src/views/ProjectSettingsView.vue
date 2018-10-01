<template>
    <div class="project-settings">
      <h1>Projects</h1>
        <b-button to="/project-new" variant="success">new project</b-button>
        <ProjectList/>
    </div>
</template>

<script>
    import ProjectList from '@/components/ProjectList.vue'; // @ is an alias to /src

    export default {
        name: 'projectSettingsView',
        components: {
            ProjectList
        }
    }
</script>

<style scoped>

</style>
