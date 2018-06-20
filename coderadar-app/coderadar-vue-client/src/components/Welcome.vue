<template>
    <div class="welcome">
        <h1>{{ msg }}</h1>
        <p>
            coderadar aims to be a continuous source code analysis tool that connects to your version control system
            (VCS) and automatically runs code analyzers that provide you with the metrics that are most important to you
            and your team. coderadar provides per-commit code metrics and even allows viewing the history of code
            metrics in legacy projects. Also, coderadar aims to provide metrics for each committer, thus enabling
            gamification features that add fun to creating quality code.
            <br><br>
            Check out the project on GitHub: <a href="https://github.com/reflectoring/coderadar" target="_blank">Coderadar
            GitHub Project</a>.
        </p>
    </div>
</template>

<script>
    export default {
        name: 'Welcome',
        props: {
            msg: String
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    h3 {
        margin: 40px 0 0;
    }

    ul {
        list-style-type: none;
        padding: 0;
    }

    li {
        display: inline-block;
        margin: 0 10px;
    }

    a {
        color: #42b983;
    }
</style>
