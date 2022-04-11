<template>
  <div>
    <b-card style="max-width: 15rem;" class="mb-2">
      <b-card-text class="d-flex justify-content-end">
        <div class="participant-box px-4">{{ study.participantUserNumber }} / {{ study.targetUserNumber }}</div>
      </b-card-text>
      <b-card-img :src="require(`../../assets/Category/${study.categories[0]}.png`)" style="width:70%; height:auto;" />
      <b-card-title :title="resizedTitle" class="study-title mt-4 fw-bold" />
      <!-- :img-src="study.imageUrl" 로 수정 요망.-->
      <b-card-text>
        <div class="tag-box">
          <span v-for="(idx, category) in study.categoryIdList" :key="idx">{{ category }}</span>
        </div>
        <div class="deadline">
          <p>마감기한 : {{ study.deadline }}</p>
        </div>
        <div class="createTime">
          <p>작성일자 : {{ study.createTime }}</p>
        </div>
      </b-card-text>
      <v-btn
        v-if="!this.study.conferenceOpened && this.study.userInfo.userId == this.$store.state.userInfo.id"
        color="primary lighten-2"
        dark
        v-on:click="openConference(study)"
      >
        생성하기
      </v-btn>
      <v-btn v-else-if="!this.study.conferenceOpened" color="brown lighten-1" dark v-on:click="conferenceNotOpened()">
        안열렸어요..
      </v-btn>
      <v-btn v-else color="red lighten-2" dark v-on:click="enterConference(study)">
        참여하기
      </v-btn>
    </b-card>
  </div>
</template>

<script>
  export default {
    name: 'ConferenceCard',
    components: {},
    props: {
      study: Object,
    },
    data() {
      return {
        imgName: 'C++',
      };
    },
    computed: {
      resizedTitle: function() {
        if (this.study.title.length > 10) {
          return this.study.title.slice(0, 9) + '...';
        }
        return this.study.title;
      },
    },
    methods: {
      enterConference(study) {
        this.$router.push({ name: 'room', params: { study: study } });
      },
      conferenceNotOpened() {
        this.$fire({
          title: '화상 회의가 안열렸어요..',
          text: '스터디장에게 화상 회의를 열어달라고 하세요!',
          type: 'error',
        });
        // alert('화상 회의가 열리지 않았습니다.');
      },
      openConference(study) {
        this.$store.dispatch('openStudyConference', study);
      },
    },
  };
</script>

<style>
  .participant-box {
    background-color: #bea48f;
  }

  .deadline,
  .createTime {
    font-size: 12px;
  }

  .study-title {
    font-size: 16px;
  }
</style>
