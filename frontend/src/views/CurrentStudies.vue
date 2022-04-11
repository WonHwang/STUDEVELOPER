<template>
  <div>
    <card-content />
    모집 후 진행중인 스터디를 보여주는 페이지
  </div>
</template>

<script>
import CardContent from '../components/Study/CardsContent.vue'

export default {
  name: 'CurrentStudies',
  components: {
    CardContent
  }
}
</script>

<style>

</style>