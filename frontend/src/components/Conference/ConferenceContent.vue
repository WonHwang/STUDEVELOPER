<template>
  <b-container align-v="center">
    <b-row v-if="this.$store.state.studies[0] && this.$store.state.studies[0].length != 0">
      <b-col class="mx-auto" sm="6" lg="4" xl="3" v-for="(study, idx) in this.$store.state.studies[0]" :key="idx">
        <ConferenceCard :study="study" />
      </b-col>
    </b-row>
    <b-row v-else>
      <StudyNotExist></StudyNotExist>
    </b-row>
  </b-container>
</template>

<script>
  import ConferenceCard from './ConferenceCard.vue';
  import StudyNotExist from '../Study/StudyNotExist.vue';
  export default {
    components: {
      ConferenceCard,
      StudyNotExist,
    },
    mounted() {
      console.log(this.$store.state.studies[0]);
    },
  };
</script>

<style></style>
