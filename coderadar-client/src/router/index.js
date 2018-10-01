import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/views/Home.vue'
import ProjectSettingsView from '@/views/ProjectSettingsView.vue'
import DashboardView from '@/views/DashboardView.vue'
import AnalyzersView from '@/views/AnalyzersView.vue'
import ProjectDetailView from '@/views/ProjectDetailView.vue'
import ProjectNewView from '@/views/ProjectNewView.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/projects',
      name: 'projects',
      component: ProjectSettingsView
    },
    {
      path: '/project-new',
      name: 'project-new',
      component: ProjectNewView
    },
    {
      path: '/projects/:projectName',
      name: 'projects-detail',
      component: ProjectDetailView,
      props: true
    },
    {
      path: '/analyzers',
      name: 'analyzers',
      component: AnalyzersView
    },
    {
      path: '/dashboard',
      name: 'dashboard',
      component: DashboardView
    }
  ]
})
