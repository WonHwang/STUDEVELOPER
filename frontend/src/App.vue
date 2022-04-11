<template>
  <v-app>
    <div id="app">
      <header>
        <div>
          <Navbar />
        </div>
      </header>
    </div>

    <v-main style="margin-left:3%; margin-right:3%">
      <router-view />
      <SignupModal v-if="this.$store.state.isSignupModalViewed" @close-signup-modal="closeSignupModal" />
      <LoginModal v-if="this.$store.state.isLoginModalViewed" @close-login-modal="closeLoginModal" />
      <LoadingSpinner v-if="this.$store.state.isLoading" />
    </v-main>
    <div>
      <footer>
        <footer-content />
      </footer>
    </div>
  </v-app>
</template>

<script>
  import Navbar from './components/Navbar';
  import FooterContent from './components/FooterContent';
  import SignupModal from './components/Modals/SignupModal';
  import LoginModal from './components/Modals/LoginModal';
  import LoadingSpinner from './components/LoadingSpinner';

  export default {
    name: 'app',
    components: {
      Navbar,
      SignupModal,
      LoginModal,
      FooterContent,
      LoadingSpinner,
    },
    created: function() {
      this.$store.dispatch('checkLogin');
    },
    methods: {
      openSignupModal: function() {
        this.$store.dispatch('openSignupModal');
      },
      closeSignupModal: function() {
        this.$store.dispatch('closeSignupModal');
      },
      openLoginModal: function() {
        this.$store.dispatch('openLoginModal');
      },
      closeLoginModal: function() {
        this.$store.dispatch('closeLoginModal');
      },
    },
  };
</script>

<style>
  * {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    font-family: 'NEXON Lv1 Gothic OTF';
  }

  /* 
header {
  width: 100vw;
  background-color: rgb(0, 0, 0);
  padding: 15px;
} */
</style>
