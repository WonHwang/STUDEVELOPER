<template>
  <v-card style="font-family: 'Roboto', sans-serif;" class="text-left">
    <!-- 창 타이틀? -->
    <v-card-title class="text-h5 grey lighten-2" style="margin-bottom:3%;">{{ study.title }}</v-card-title>

    <v-card-text>
      <v-row>
        <v-col cols="1"></v-col>
        <!-- 스터디 이미지 -->
        <v-col cols="5">
          <v-img :src="require(`../../assets/Category/${study.categories[0]}.png`)"></v-img>
        </v-col>

        <!-- 스터디 정보 -->
        <v-col cols="5" style="margin-top:2%; line-height:1.6;">
          <div class="h2 font-weight-bold">
            {{ study.title }}
          </div>
          <div>
            <span class="study-title">인원수: </span>
            <!-- 참가 인원 수는 어떻게 계산이 될까요? -->
            <span class="study-content">{{ study.participantUserNumber }} / {{ study.targetUserNumber }}</span>
          </div>
          <div>
            <span class="study-title">작성 날짜: </span>
            <span class="study-content">{{ createTime }}</span>
          </div>
          <div>
            <span class="study-title">마감 날짜: </span>
            <span class="study-content">{{ deadline }}</span>
          </div>
          <div>
            <v-icon class="icon-style">mdi-account-outline</v-icon>
            <span class="study-content">{{ study.userInfo.name }}</span>
          </div>
          <div>
            <v-icon class="icon-style">mdi-pound</v-icon>
            <span class="tage-style" v-for="(category, idx) in study.categories" :key="idx"> <v-chip>{{ category }}</v-chip> </span>
          </div>
        </v-col>
        <v-col cols="1"></v-col>
      </v-row>

      <!-- 스터디 진행 규칙, 벌금 규칙 등 -->
      <v-row>
        <v-col cols="1"></v-col>
        <v-col cols="10" class="text-left">
          <v-card outlined>
            <!-- 진행 규칙 -->
            <div class="h2 font-weight-bold" style="margin-left:3%; margin-top:3%;">스터디 설명</div>
            <v-divider class="mx-7"></v-divider>
            <div class="font-weight-regular" style="margin-left:3%; margin-top:3%; margin-bottom:3%; margin-right:3%">
              {{ study.explanation }}
              <!-- 엔터처리가 왜 안될까?? -->
            </div>
            <br />

            <!-- 벌금 규칙 -->
            <div class="h2 font-weight-bold" style="margin-left:3%; margin-top:3%;">규칙</div>
            <v-divider class="mx-7"></v-divider>
            <div class="font-weight-regular" style="margin-left:3%; margin-top:3%; margin-bottom:3%; margin-right:3%">
              일주일에 {{ study.timePerWeek }}회 회당 {{ study.hourPerTime }}시간씩 진행됩니다.
              <br>
              지각 벌금 {{ study.tardyPenaltyAmount }}원, 결석 벌금 {{ study.absentPenaltyAmount }}원, 과제 미제출 벌금 {{ study.assignmentPenaltyAmount }}원 입니다.
            </div>
          </v-card>
        </v-col>
        <v-col cols="1"></v-col>
      </v-row>

      <v-row>
        <v-col cols="1"></v-col>
        <v-col cols="10">
          <v-card outlined>
            <div v-for="(comment, idx) in comments" :key="idx">
              <p class="mt-5 ml-5">{{ comment.authorName }} : {{ comment.content }}</p>
            </div>
          </v-card>
        </v-col>
        <v-col cols="1"></v-col>
      </v-row>
      <!-- 댓글 창 -->
      <v-row>
        <v-col cols="1"></v-col>
        <v-col cols="10">
          <v-container fluid>
            <v-textarea label="Comment"
            no-resize
            rows="1"
            v-model="inputComment"
            append-outer-icon="mdi-comment"
            @keypress.prevent.enter="createComment"
            ></v-textarea>
          </v-container>
        </v-col>
        <v-col cols="1"></v-col>
      </v-row>
    </v-card-text>

    <!-- 버튼 -->
    <v-card-actions>
      <v-row align="center" justify="space-around" style="margin-bottom:3%;">
        <v-col cols="1"></v-col>
        <v-col cols="3">
          <v-btn v-if="isApplied" width="100%" x-large class="btn-shadow" @click="cancelApply">신청 취소</v-btn>
          <v-btn v-else width="100%" x-large class="btn-shadow" @click="applyStudy">스터디 신청</v-btn>
        </v-col>

        <v-col cols="3">
          <v-btn width="100%" x-large class="btn-shadow">메시지 보내기</v-btn>
        </v-col>

        <v-col cols="1"></v-col>
      </v-row>
    </v-card-actions>
  </v-card>
</template>

<script>
  import axios from 'axios'
  export default {

    data: () => ({
      study: {
        title: 'Empty',
        categories: null,
        createTime: null,
        deadline: null,
        explanation: null,
        imageUrl: null,
        participantUserNumber: null,
        recruitClosed: null,
        timePerWeek: null,
        hourPerTime: null,
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
      applyInfo: [0],
      isApplied: false,
    }),

    props: {
      study_Id: Number,
    },

    methods: {
      createComment: function () {
        const data = {
          comment: this.inputComment,
          study_id: this.study.studyId
        }
        this.$store.dispatch('createComment', data)

        axios({
          method: 'get',
          url: `${this.$store.state.domain}/api/v1/studies/${this.study_Id}/comments`,
        })
        .then((res) => {
          this.comments = res.data
        });
        
        this.inputComment = ""
        alert('댓글이 등록됐습니다.')
      },
      applyStudy: function () {
        if (!this.isApplied) {
          this.$store.dispatch('applyStudy', this.study_Id)
          axios({
            method: 'get',
            url: `${this.$store.state.domain}/api/v1/studies/${this.study_Id}/apply`
          })
          .then((res) => {
            this.applyInfo = [0]
            for (const apply of res.data) {
              this.applyInfo.push(apply.userId)
            }
            this.isApplied = true
          })
        }
      },
      cancelApply: function () {
        if (this.isApplied){
          this.$store.dispatch('cancelApply', this.study_Id)
          axios({
            method: 'get',
            url: `${this.$store.state.domain}/api/v1/studies/${this.study_Id}/apply`
          })
          .then((res) => {
            this.applyInfo = [0]
            for (const apply of res.data) {
              this.applyInfo.push(apply.userId)
            }
            this.isApplied = false
          })
        }
      }
    },

    created: function () {
      axios({
        method: 'get',
        url: `${this.$store.state.domain}/api/v1/studies/${this.study_Id}`
      })
      .then(res => {
        this.study = res.data
      })
      
      axios({
        method: 'get',
        url: `${this.$store.state.domain}/api/v1/studies/${this.study_Id}/comments`,
      })
      .then((res) => {
        this.comments = res.data
      });

      axios({
        method: 'get',
        url: `${this.$store.state.domain}/api/v1/studies/${this.study_Id}/apply`
      })
      .then(res => {
        this.applyInfo = []
        for (const apply of res.data) {
          this.applyInfo.push(apply.userId)
        }

        for (const id of this.applyInfo) {
          if (id === this.$store.state.userInfo.id) {
            this.isApplied = true
          }
        }
      })
    },

    computed: {
      createTime: function () {
        const date = new Date(this.study.createTime)
        return date.toLocaleDateString()
      },
      deadline: function () {
        const date = new Date(this.study.deadline)
        return date.toLocaleDateString()
      },
      participantUserNumber: function () {
        return this.participantList.length
      },
    },

    watch: {
      applyInfo: function () {
        this.isApplied = false
        for (const id of this.applyInfo) {
          if (id === this.$store.state.userInfo.id) {
            this.isApplied = true
          }
        }
      },
      // comments: function () {
      //   axios({
      //     method: 'get',
      //     url: `${this.$store.state.domain}/api/v1/studies/${this.study_Id}/comments`,
      //   })
      //   .then((res) => {
      //     this.comments = res.data
      //   });
      // }
    }
  }
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