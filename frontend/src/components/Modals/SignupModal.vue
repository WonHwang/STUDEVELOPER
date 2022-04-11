<template>
  <div class="signup-modal">
    <div class="overlay" @click="closeSignupModal"></div>
    <div class="modal-body">
      <v-img src="../../assets/logo/whole_name.png"></v-img>
      <v-divider style="margin-bottom: 5%;"></v-divider>
      <h2 class="temp">회원 가입</h2>
      <v-form ref="form" v-model="valid" lazy-validation style="font-weight:bold;">
        <v-text-field v-model="email" :rules="[rules.required, rules.emailValidation]" label="E-mail" required></v-text-field>
        <v-text-field
          v-model="password"
          :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
          :rules="[rules.required]"
          :type="show ? 'text' : 'password'"
          name="input-10-1"
          label="Password"
          @click:append="show = !show"
        ></v-text-field>
        <v-text-field
          v-model="passwordCheck"
          :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
          :rules="[rules.required, passwordConfirmationRule]"
          :type="show ? 'text' : 'password'"
          name="input-10-1"
          label="PasswordCheck"
          @click:append="show = !show"
        ></v-text-field>
        <v-text-field v-model="nickname" :rules="[rules.required]" label="Nickname" required></v-text-field>
        <v-row style="margin-top:3%;">
          <v-col cols="4">
            <v-btn large :disabled="!valid" color="blue darken-2" class="button-signup" @click="doSignup">
              Sign up
            </v-btn>
          </v-col>
          <v-col cols="4">
            <v-btn large color="red lighten-1" class="button-signup" @click="reset">
              Reset
            </v-btn>
          </v-col>
          <v-col cols="4">
            <v-btn large color="grey lighten-1" class="button-signup" @click="closeSignupModal">
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
    name: 'SignupModal',
    data: () => ({
      valid: true,
      email: '',
      rules: {
        required: (value) => !!value || 'Required',
        emailValidation: (v) => /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || 'E-mail must be valid',
      },
      password: '',
      passwordCheck: '',
      show: false,
      nickname: '',
      department: '',
      position: '',
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
      closeSignupModal: function() {
        this.$emit('close-signup-modal');
      },
      doSignup: function() {
        const credentials = {
          userAuthId: this.email,
          password: this.password,
          name: this.nickname,
          department: this.department,
          position: this.position,
        };
        if (this.valid) {
          this.$store.dispatch('doSignup', credentials);
        }
      },
    },
    computed: {
      passwordConfirmationRule: function() {
        return () => this.password === this.passwordCheck || 'Wrong.';
      },
    },
  };
</script>

<style scoped>
  .signup-modal,
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

  .button-signup {
    color: white !important;
    font-weight: bold !important;
    margin-left: 5%;
    margin-right: 5%;
    width: 100%;
    height: 110%;
  }
</style>
