<template>
    <div class="project-settings">
        <ProjectSettings headline="Project Settings"/>
    </div>
</template>

<script>
    import ProjectSettings from '@/components/ProjectSettings.vue'; // @ is an alias to /src

    export default {
        name: 'projectSettingsView',
        components: {
            ProjectSettings
        }
    }
</script>