<template>
  <v-row>
    <v-col
      cols="3"
    ></v-col>
    <v-col
      cols="6">
      <v-expansion-panels
        v-model="panel" 
        focusable>
        <v-expansion-panel>
          <study-schedule
            v-for="(schedule, index) in schedules"
            v-bind:schedule="schedule"
            v-bind:index="index"
            v-bind:key="schedule.id"/>
        </v-expansion-panel>
      </v-expansion-panels>
    </v-col>
    <v-col
      cols="3"
    ></v-col>
  </v-row>
</template>

<script>
import axios from 'axios'
import StudySchedule from '../StudyManager/StudySchedule.vue'

export default {
  components: { 
    StudySchedule
  },
  data: () => ({
    panel: [],
    schedules: [],
  }),
  props: {
    studyId: Number
  },
  created () {
    console.log(this.studyId)
    axios({
      method: 'get',
      url: `${this.$store.state.domain}/api/v1/studies/${Number(this.studyId)}/study-schedule`,
    })
      .then((res)=>{
        this.schedules = res.data
      })
      .catch(() => {
        this.schedules = []
      })
  }

}
</script>

<style>

</style>