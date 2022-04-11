<template>
  <v-card style="font-family: 'Roboto', sans-serif;" class="text-left">
    <!-- 창 타이틀? -->
    <v-card-title class="text-h5 grey lighten-2" style="margin-bottom:3%;">{{ study.title }}</v-card-title>

    <v-card-text>
      <v-list v-for="(item, idx) in $store.state.studyApplyList" :key="idx">
        <div v-if="item.userId != $store.state.userInfo.id && item.accepted && !item.blocked">
          <v-list-item>
            <v-list-content>
              <v-list-item-title v-text="item.name" />
            </v-list-content>
            <v-list-item-icon>
              <v-icon @click="acceptApply(item.userId)">mdi-check-bold</v-icon>
              <v-icon @click="denyApply(item.userId)">mdi-close-thick</v-icon>
            </v-list-item-icon>
          </v-list-item>
          <hr>
        </div>
      </v-list>
    </v-card-text>

    <!-- 버튼 -->
  </v-card>
</template>

<script>
  import axios from 'axios'
  export default {

    data: () => ({
      study: {
        title: 'Empty',
        categoryIdList: null,
        createTime: null,
        deadline: null,
        explanation: null,
        imageUrl: null,
        participantUserNumber: null,
        recruitClosed: null,
        rule: null,
        studyClosed: null,
        targetUserNumber: null,
        userInfo: {
          name: null,
          department: null,
          position: null,
          userAuthId: null
        },
      },
      participantList: [],
      comments: [],
      inputComment: "",
    }),

    props: {
      study_Id: Number,
    },

    methods: {
      acceptApply: function (userId) {
        const info = {
          studyId: this.study_Id,
          userId: userId
        }
        this.$store.dispatch('acceptApply', info)
        this.$store.dispatch('getStudyApplyList', this.study_Id)
      },
      denyApply: function (userId) {
        const info = {
          studyId: this.study_Id,
          userId: userId
        }
        this.$store.dispatch('denyApply', info)
        this.$store.dispatch('getStudyApplyList', this.study_Id)
      }
    },

    mounted: function () {
      axios({
        method: 'get',
        url: `${this.$store.state.domain}/api/v1/studies/${this.study_Id}`
      })
      .then(res => {
        this.study = res.data
      })
      this.$store.dispatch('getStudyApplyList', this.study_Id)
    },
    computed: {
    },
  };
</script>

<style>
  .btn-shadow {
    box-shadow: 0 4px 6px hsla(0, 0%, 0%, 0.2);
  }

  .study-title {
    margin-right: 6px;
    font-weight: 700;
    font-size: 18px;
  }

  .study-content {
    font-weight: 400;
    font-size: 16px;
  }

  .tag-style {
    letter-spacing: -0.3px;
    font-size: 16px;
    margin-right: 4px;
    padding: 4px 12px;
    border-radius: 100px;
    background-color: #5f5f5f;
    color: #fff;
    font-weight: 400;
  }

  .icon-style {
    margin-right: 6px;
    width: 18px;
    height: 18px;
  }
</style>