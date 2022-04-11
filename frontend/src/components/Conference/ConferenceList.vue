<template>
  <v-container fluid>
    <v-row>
      <v-switch
        class="subheader"
        v-model="isOpenConference"
        inset
        :label="isOpenConference ? `화상 회의 열린 스터디 보기` : `모든 스터디 보기`"
      ></v-switch>
    </v-row>
    <ConferenceContent />
  </v-container>
</template>

<script>
  import ConferenceContent from './ConferenceContent.vue';
  // import Scoring from './Scoring.vue';

  export default {
    name: 'Conference',
    components: {
      ConferenceContent,
      // Scoring,
    },
    data() {
      return {
        isOpenConference: false,
        openStudyList: Array,
        allStudyList: Array,
      };
    },
    created: function() {
      // 참여 가능한 스터디만 출력해야함
      this.$store.dispatch('getParticipatingStudyList', this.$store.state.userInfo.id);
    },
    watch: {
      isOpenConference() {
        if (this.isOpenConference) {
          this.$store.dispatch('getOpenedStudyList', this.$store.state.userInfo.id);
        } else {
          this.$store.dispatch('getParticipatingStudyList', this.$store.state.userInfo.id);
        }
      },
    },
    methods: {},
  };
</script>

<style>
  .subheader {
    position: absolute ip !important;
    font-size: 110%;
    font-weight: bold;
    margin-top: 3% !important;
    margin-left: 42% !important;
  }
</style>
