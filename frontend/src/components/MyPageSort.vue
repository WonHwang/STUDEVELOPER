<template>
    <div>

        <b-button-group>
          <b-button onclick="location.href='./mypage'" class = "ml-15 hover-test fs-5" variant="white">참여중인 스터디</b-button>
          <b-button onclick="location.href='./myprofile'" class = "ml-15 hover-test fs-5" variant="white" >내 프로필</b-button>
          <b-button onclick="location.href='./mystudymanager'" class = "ml-15 hover-test fs-5" variant="white" >내가 만든 스터디</b-button>
        </b-button-group>
        
        <div class="d-flex justify-content-start container row">
            
            <div class="col-1"></div>
            <div class="d-flex justify-content-start col-10">내 스터디룸</div>
            <div class="col-1"></div>
        </div>

        <div>
            <CardsContent
            :studyId="studyId" />
            <!-- <StudyDetailModal v-if="$store.state.isStudyDetailModalViewed" :studyId="studyId"/> -->
        </div>
    </div>
</template>

<script>


import CardsContent from './Study/CardsContent.vue'
// import StudyDetailModal from './Modals/StuyDetailModal'

export default {
    name: 'Mypages',
    components: {
    CardsContent,
    // StudyDetailModal,
  },
  data () {
    return {
      studyId: null,
    }
  },
  created: function () {
    this.$store.dispatch('getStudyList')
  },
}
</script>

<style>

</style>