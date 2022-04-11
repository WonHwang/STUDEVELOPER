<template>
  <div>
  <v-container>
    <v-row>
      <v-col
        cols="2">
      </v-col>
      <v-col
        cols="3">
        스터디 기록실
      </v-col>
      <v-col
        cols="2"
      ></v-col>
      <v-col
        cols="3">
    <CreateScheduleModal :studyId="this.studyId"/></v-col>
    </v-row>
    <StudyScheduleList 
      :studyId="this.studyId"
      class="mt-5"/>
  </v-container>
  </div>
</template>

<script>
import CreateScheduleModal from '../components/Modals/CreateScheduleModal.vue'
import StudyScheduleList from '../components/StudyManager/StudyScheduleList.vue'

export default {
  name: 'StudyManager',
  components: {
    StudyScheduleList,
    CreateScheduleModal
  },
  data: ()=> ({
    isScheduleModalViewed: false,
    studyId: null
  }),
  methods: {

  },
  created() {
    this.studyId = Number(this.$route.params.studyId)
  }
}
</script>

<style>

</style>