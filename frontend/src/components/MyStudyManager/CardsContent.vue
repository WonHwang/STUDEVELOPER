<template>
  <div>
    <!-- 카드 -->
    <b-container align-v="center" class="mb-15">
      <b-row class="d-flex" v-if="this.$store.state.studies[0].length != 0">
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
  import StudyNotExist from './StudyNotExist.vue';
  export default {
    components: {
      Card,
      StudyNotExist,
    },

    methods: {
    },
  };
</script>

<style>
  .new-button {
    background-color: #d6d0c2;
  }
</style>
