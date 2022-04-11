<template>
  <v-row justify="center">
      <v-dialog
        v-model="dialog"
        persistent
        max-width="600px"
      >
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            color="primary"
            dark
            v-bind="attrs"
            v-on="on">
            일정 추가
          </v-btn>
        </template>
        <v-card>
          <v-card-title>
            <span class="text-h5">스터디 일정 등록</span>
          </v-card-title>
          <template>
            <v-row justify="center">
              <v-date-picker v-model="studyDate"></v-date-picker>
            </v-row>
          </template>
          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="12">
                  <v-text-field
                    v-model="title"
                    label="title"
                    required
                  ></v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-text-field
                    v-model="explanation"
                    label="explanation"
                    required
                  ></v-text-field>
                </v-col>
              </v-row>
            </v-container>
            <small>*indicates required field</small>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              color="blue darken-1"
              text
              @click="dialog = false"
            >
              Close
            </v-btn>
            <v-btn
              color="blue darken-1"
              text
              @click="createSchedule"
            >
              Save
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>
</template>

<script>
import axios from 'axios';
export default {
  data: ()=>({
    dialog: false,
    studyDate: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
    title: null,
    explanation: null,
  }),
  props: {
    studyId: Number
  },
  methods: {
    createSchedule:function() {
      const token = localStorage.getItem('jwt');
      const config = {
        Authorization: `Bearer ${token}`,
      };
      const data={
        studyDate: this.studyDate,
        title: this.title,
        explanation: this.title
      }
      axios({
        method: 'post',
        url:`${this.$store.state.domain}/api/v1/studies/${this.studyId}/study-schedule`,
        headers: config,
        data: data,
      })
      .then(()=>{
        this.dialog=false
      })
    },


  }
}
</script>

<style>

</style>