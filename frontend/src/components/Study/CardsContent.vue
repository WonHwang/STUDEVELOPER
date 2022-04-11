<template>
  <div>
    <!-- 상단 -->
    <div class="function-section row">
      <SearchInput />
    </div>

    <div class="btn-section d-flex justify-content-end mr-15">
      <v-btn width="10rem" class="new-button" color="#d6d0c2" style="font-weight:bold;" @click="createStudy">
        스터디 생성하기
      </v-btn>
    </div>

    <!-- 카드 -->
    <b-container align-v="center" class="mb-15">
      <b-row class="d-flex" v-if="this.$store.state.studies[0] && this.$store.state.studies[0].length != 0">
        <b-col sm="6" lg="3" xl="2" xxl="2" v-for="(study, idx) in this.$store.state.studies[0]" :key="idx">
          <Card :study="study" />
        </b-col>
      </b-row>
      <b-row v-else>
        <StudyNotExist></StudyNotExist>
      </b-row>
    </b-container>
  </div>
</template>
<script>
  import Card from './Card.vue';
  import SearchInput from './SearchInput';
  import StudyNotExist from './StudyNotExist.vue';
  export default {
    components: {
      Card,
      SearchInput,
      StudyNotExist,
    },

    methods: {
      createStudy: function() {
        this.$router.push('create-study');
      },
    },
  };
</script>

<style>
  .new-button {
    background-color: #d6d0c2;
  }
</style>
