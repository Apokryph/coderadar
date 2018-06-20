import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'
import ProjectSettings from './views/ProjectSettingsView.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/project-settings',
      name: 'project-settings',
      component: ProjectSettings
    }
  ]
})
