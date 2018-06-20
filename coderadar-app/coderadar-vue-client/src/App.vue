<template>
  <div id="app">
    <div id="nav">
      <b-nav tabs>
        <b-nav-item to="/">Home</b-nav-item>
        <b-nav-item to="/project-settings">Project Settings</b-nav-item>
      </b-nav>
    </div>
    <div id="container">
      <router-view/>
    </div>
  </div>
</template>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
  }

  #nav {
    padding: 30px;
  }

  #nav a {
    font-weight: bold;
    color: #2c3e50;
  }

  #nav a.router-link-exact-active {
    color: #42b983;
  }

  #container {
    width: 900px;
    margin-left: auto;
    margin-right: auto;
  }

</style>
