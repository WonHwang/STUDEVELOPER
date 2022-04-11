<template>
  <div class="visibility-filter">
    <div v-if="this.$store.state.isLogin">
      <b-navbar toggleable="lg" variant="white">
        <v-row justify="center">
          <a href="/" class="shrink mb-8 mt-5">
            <img src="../assets/logo/whole_name.png" class="shrink" transition="scale-transition" width="200" style="margin-left:15%" />
          </a>
        </v-row>

        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav class="hover-test">
            <button onclick="location.href='./studies'" class="hover-test fs-5">스터디 게시판</button>
            <button v-on:click="routeConferenceList()" class="ml-15 hover-test fs-5">화상 회의</button>
            <button v-on:click="routeProblemRecommendation()" class="ml-15 hover-test fs-5">문제 추천</button>
            <button onclick="location.href='./Mypage'" class="ml-15 hover-test fs-5">마이페이지</button>
            <!-- <b-nav-item href="/studies" class= "ml-15">스터디 게시판</b-nav-item>
            <b-nav-item href="/conference" class= "ml-15">화상 회의</b-nav-item>
            <b-nav-item href="/#" class= "ml-15">공지</b-nav-item>
            <b-nav-item href="/#" class= "ml-15">마이페이지</b-nav-item> -->
          </b-navbar-nav>

          <!-- Right aligned nav items -->
          <b-navbar-nav class="ml-auto" style="margin-right:5%">
            <button @click="doLogout" class="mr-15 hover-test">로그아웃</button>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
    </div>

    <div v-else>
      <b-navbar toggleable="lg" variant="white">
        <v-row justify="center">
          <a href="/" class="shrink mb-8 mt-5">
            <img src="../assets/logo/whole_name.png" class="shrin" transition="scale-transition" width="200" style="margin-left:15%" />
          </a>
        </v-row>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <!-- Right aligned nav items -->
          <b-navbar-nav class="ml-auto" style="margin-right:5%">
            <b-navbar-nav>
              <button @click="openSignupModal" class="mr-15 hover-test">회원가입</button>
              <button @click="openLoginModal" class="mr-15 hover-test">로그인</button>
            </b-navbar-nav>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'navbar',

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
      doLogout: function() {
        this.$store.dispatch('doLogout');
        this.$store.dispatch('checkLogin');
        this.$router.push('/');
      },
      routeConferenceList() {
        this.$router.push('/conference');
      },
      routeProblemRecommendation() {
        this.$router.push('/problem');
      },
    },
  };
</script>

<style>
  #app {
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #000000;
  }

  button.hover-test:hover {
    color: #bea48f;
  }

  a {
    text-decoration: none;
    text-align: left;
  }
</style>
