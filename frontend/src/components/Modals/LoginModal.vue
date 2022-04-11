<template>
  <div class="login-modal">
    <div class="overlay" @click="closeLoginModal"></div>
    <div class="modal-body">
      <v-img src="../../assets/logo/whole_name.png"></v-img>
      <v-divider style="margin-bottom: 5%;"></v-divider>
      <h2 class="temp">로그인</h2>
      <v-form ref="form" v-model="valid" lazy-validation style="font-weight:bold;">
        <v-text-field v-model="email" :rules="emailRules" label="E-mail" required></v-text-field>
        <v-text-field
          v-model="password"
          :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
          :rules="passwordRules"
          :type="show ? 'text' : 'password'"
          name="input-10-1"
          label="Password"
          @click:append="show = !show"
        ></v-text-field>
        <v-row style="margin-top: 3%">
          <v-col cols="4">
            <v-btn large :disabled="!valid" color="blue darken-2" class="button-login" @click="doLogin">
              Login
            </v-btn>
          </v-col>
          <v-col cols="4">
            <v-btn large color="red lighten-1" class="button-login" @click="reset">
              Reset
            </v-btn>
          </v-col>
          <v-col cols="4">
            <v-btn large color="grey lighten-1" class="button-login" @click="closeLoginModal">
              Close
            </v-btn>
          </v-col>
        </v-row>
      </v-form>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'LoginModal',
    data: () => ({
      valid: true,
      email: '',
      emailRules: [(v) => !!v || 'E-mail is required'],
      passwordRules: [(value) => !!value || 'Required.'],
      show: false,
      password: '',
    }),
    methods: {
      validate() {
        this.$refs.form.validate();
      },
      reset() {
        this.$refs.form.reset();
      },
      resetValidation() {
        this.$refs.form.resetValidation();
      },
      closeLoginModal: function() {
        this.$emit('close-login-modal');
      },
      doLogin: function() {
        const credentials = {
          userAuthId: this.email,
          password: this.password,
        };
        this.$store.dispatch('doLogin', credentials);
      },
    },
  };
</script>

<style scoped>
  .login-modal,
  .overlay {
    width: 100%;
    height: 100%;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
  }

  .overlay {
    opacity: 0.5;
    margin-top: 10px;
    background-color: black;
  }

  .temp {
    color: #818080;
    font-weight: 900;
    margin-bottom: 5%;
  }
  .modal-body {
    max-width: 35vw;
    margin: auto;
    margin-top: 10%;
    padding-top: 3%;
    padding-left: 5%;
    padding-right: 5%;
    padding-bottom: 3%;
    background-color: white;
    min-height: 500px;
    z-index: 10;
    opacity: 1;
  }

  .button-login {
    color: white !important;
    font-weight: bold !important;
    margin-left: 5%;
    margin-right: 5%;
    width: 100%;
    height: 110%;
  }
</style>
