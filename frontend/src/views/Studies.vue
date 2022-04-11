<template>
  <div>
    <CardsContent />
  </div>
</template>

<script>
  import CardsContent from '../components/Study/CardsContent.vue';

  export default {
    name: 'Studies',
    components: {
      CardsContent,
    },
    data() {
      return {
        studyId: null,
      };
    },
    created: function() {
      this.$store.dispatch('getStudyList');
      // this.$store.dispatch('getCategoryList')
    },

    methods: {},
  };
</script>

<style></style>
