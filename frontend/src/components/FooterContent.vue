<template>
  <v-footer padless width="100%" >
    <v-card flat tile width="100%" class="brown darken-4 white--text text-center">
      <v-card-text class="white--text">
        <strong> 삼성 소프트웨어 아카데미 서울 5반 3팀 </strong>
      </v-card-text>
    </v-card>
  </v-footer>
</template>

<script>
  export default {
    data: () => ({
      icons: ['mdi-facebook', 'mdi-twitter', 'mdi-linkedin', 'mdi-instagram'],
      urls: ['www.facebook.com/스터디벨로퍼', 'www.facebook.com/스터디벨로퍼', 'https://www.instagram.com/studeveloper/', 'https://www.instagram.com/studeveloper/']
    }),
  };
</script>

<style>

.theme--light.v-card {
    background-color: #968D8A;
    
}

</style>