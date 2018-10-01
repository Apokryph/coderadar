<template>
  <div class="login">
    <h2>Login</h2>
    <b-form @submit.prevent="login">

      <b-form-group id="loginNameGroup"
                    label="Your Name:"
                    label-for="loginNameInput">
        <b-form-input id="loginNameInput"
                      type="text"
                      v-model="form.name"
                      required
                      placeholder="Enter name">
        </b-form-input>
      </b-form-group>
      <b-form-group id="loginPwGroup"
                    label="Your Password:"
                    label-for="loginPwInput">
        <b-form-input id="loginPwInput"
                      type="password"
                      v-model="form.password"
                      required
                      placeholder="Enter password">
        </b-form-input>

      </b-form-group>
      <b-button variant="primary" v-on:click="login">Login</b-button>
      <b-button variant="secondary" v-on:click="register">Register</b-button>

      <b-alert variant="success"
               dismissible
               :show="showDismissibleSuccess"
               @dismissed="showDismissibleSuccess=false">
        Successful!
      </b-alert>

      <b-alert variant="danger"
               dismissible
               :show="showDismissibleFail"
               @dismissed="showDismissibleFail=false">
        {{ error }}
      </b-alert>
    </b-form>
  </div>
</template>

<script>
  import axios from 'axios';

  export default {
    name: "Login",
    data () {
      return {
        form: {
          password: '',
          name: '',
        },
        showDismissibleSuccess: false,
        showDismissibleFail: false,
        error: ''
      }
    },
    methods: {
      login() {
        axios.post('/api/user/auth',
          {
            'username': '' + this.username + '',
            'password': '' + this.password + ''
          },
          {
            headers: {
              'Content-type': 'application/json'
            }
          }
        )
          .then(response => {
            this.loginSuccessful(request)
          })
          .catch(e => {
            this.loginFailed()
          })

      },

      loginSuccessful (req) {
        if (!req.data.accessToken) {
          this.loginFailed()
          return
        }

        localStorage.token = req.data.accessToken
        this.error = false

        this.showDismissibleFail = false;
        this.showDismissibleSuccess = true;

        this.$router.replace(this.$route.query.redirect || '/authors')
      },

      loginFailed () {
        this.error = 'Login failed!'

        this.showDismissibleSuccess = false;
        this.showDismissibleFail = true;

        delete localStorage.token
      },

      register(){
        axios.post('/api/user/registration',
          {
            'username': '' + this.username + '',
            'password': '' + this.password + ''
          },
          {
            headers: {
              'Content-type': 'application/json',
            }
          }
        )
          .then(response => {
            this.showDismissibleFail = false;
            this.showDismissibleSuccess = true;
          })
          .catch(e => {
            this.error = 'Registration failed!'
            this.showDismissibleSuccess = false;
            this.showDismissibleFail = true;
          })
      }
    }
  }
</script>

<style scoped>
input {
  text-align: center;
}
</style>
