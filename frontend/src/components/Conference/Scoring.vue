<template>
  <v-card class="elevation-16 mx-auto" width="30%" min-width="400px" style="margin-top:10%">
    <v-card-text class="text-h4 font-weight-bold" style="color:#AE9383;">
      <div style="margin-top:10%">
        스터디 참여자들을<br />
        평가해 보세요!!
      </div>
    </v-card-text>
    <v-card-text>
      스터디원에 대한 평가는 <b style="color:#818080">익명</b>으로 진행되며<br />
      다른 사람들에게 큰 도움이 됩니다.
      <div>
        <div class="text-center mt-12" v-for="evaluation in evaluationList" v-bind:key="evaluation.id">
          <div class="user-name">
            {{ evaluation.name }}
          </div>
          <v-rating
            v-model="evaluation.score"
            color="yellow darken-3"
            background-color="grey darken-1"
            empty-icon="$ratingFull"
            half-increments
            hover
            large
          ></v-rating>
        </div>
      </div>
      <div v-if="this.evaluationList.length == 0" class="user-name" style="margin-top: 10%; color:#E57373;">
        평가 할 스터디원이 없습니다.
      </div>
    </v-card-text>
    <v-divider></v-divider>
    <v-card-actions class="justify-space-between">
      <v-btn class="button-score" text @click="cancleEvaluating()" style="margin-left: 5%">
        취소
      </v-btn>
      <v-btn class="button-score" color="primary" text @click="submitEvaluating()" style="margin-right: 5%">
        평가
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
  export default {
    data() {
      return {};
    },
    props: {
      evaluationList: Array,
    },
    created() {
      console.log(this.evaluationList.length);
    },
    methods: {
      submitEvaluating() {
        var data = new Array();
        for (var i = 0; i < this.evaluationList.length; i++) {
          data.push({ userId: this.evaluationList[i].id, userScore: this.evaluationList[i].score });
        }
        this.$store.dispatch('updateUserScore', data);
        this.$router.push('/conference');
      },
      cancleEvaluating() {
        this.$router.push('/conference');
      },
    },
  };
</script>

<style>
  .user-name {
    font-weight: 800;
    font-size: 130%;
    color: #818080;
  }
  .button-score {
    font-size: 120% !important;
  }
</style>
