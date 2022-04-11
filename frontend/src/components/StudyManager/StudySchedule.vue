<template>
  <div>
    <v-expansion-panel-header>
      <v-row>
        <v-col
          cols="4">Date:{{ schedule.studyDate }}</v-col>
        <v-col
          cols="3"></v-col>
        <v-col
          cols="3" >{{ schedule.title }}</v-col>
      </v-row>
    </v-expansion-panel-header>
    <v-expansion-panel-content
      class="py-2">
      {{ schedule.explanation }}
      <v-divider></v-divider>
      <ul>
        <li
          is="study-task"
          v-for="(task, index) in schedule.tasks"
          v-bind:task="task"
          v-bind:index="index"
          v-bind:key="task.id"
          v-bind:taskId="task.id"
        ></li>
      </ul>
      <template>
        <div>
          <v-file-input
            v-model="attachFile"
            chips
            label="과제물을 제출하세요"
          ></v-file-input>
          <b-button @click="submit">제출</b-button>
        </div>
      </template>
    </v-expansion-panel-content>
  </div>
</template>

<script>
import StudyTask from '../StudyManager/StudyTask.vue'

export default {
  components: {
    StudyTask
  },
  data: ()=> ({
    attachFile: null,
    schedule: this.schedule
  }),
  props: {
    schedule: Object
  },
  methods: {
    submit() {
      const data= {
        attachFile : this.attachFile,
        scheduleId: this.schedule.id
      }
      console.log(this.attachFile)
      this.$store.dispatch('uploadFile', data)
    }
  }
}
</script>

<style>

</style>