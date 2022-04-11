<template>
  <div>
    <h4>
      {{ $store.state.userInfo.name }}님이 만드신 스터디에요.
    </h4>
    <CardsContent />
  </div>
</template>

<script>
import CardsContent from '../components/MyStudyManager/CardsContent.vue'

export default {
  name: 'MyStudyManager',
  components: {
    CardsContent,
  },
  data () {
    return {
      studyId: null,
    }
  },
  created: function () {
    this.$store.dispatch('getMyStudyList')
    this.$store.dispatch('getCategoryList')
  },

  methods: {
  }
}
</script>

<style>

</style>