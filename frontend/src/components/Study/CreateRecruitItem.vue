<template>
  <v-container class="d-flex my-10 p-5 border border-2 note-bg shadow w-75 fw-bold">
    <form @submit.prevent="submit">
      <v-row justify="center">
        <v-col cols="4"></v-col>
        <v-col cols="4">
          <div class="my-5 mx-auto p-3 border border-2">
            스터디 모집글 작성
          </div></v-col
        ><v-col cols="4"></v-col>
        <!-- 이름 -->
        <v-col cols="2"></v-col>
        <v-col cols="8">
          <v-text-field v-model="title" :counter="20" label="제목" filled required></v-text-field>
        </v-col>
        <v-col cols="2"> </v-col>
        <!-- 모집인원:recruitnumber -->
        <v-col cols="2">
          <v-select v-model="recruitNumber" :items="recruitNumberOption" label="모집인원" filled></v-select>
        </v-col>
        <v-col cols="1"> </v-col>

        <!-- 카테고리:categories -->
        <v-col cols="5">
          <v-combobox v-model="selectedCategories" :items="this.items" label="스터디의 카테고리를 골라주세요" filled multiple chips></v-combobox>
        </v-col>
        <!-- 설명:explanation -->
        <v-row>
          <v-col cols="2"></v-col>
          <v-col cols="2" class="p-atuo">
            <div class="position-relative top-50 start-50 translate-middle">스터디는 일주일에</div>
          </v-col>
          <v-col cols="1"> <v-select v-model="timePerWeek" :items="timesPerWeek" class="mx-0"></v-select></v-col>
          <v-col cols="1" class="p-atuo"> <div class="position-relative top-50 start-50 translate-middle">회,</div></v-col>
          <v-col cols="1" class="p-atuo"> <div class="position-relative top-50 start-50 translate-middle">회당</div></v-col>
          <v-col cols="1"> <v-select v-model="hourPerTime" :items="hoursPerTime"></v-select></v-col>
          <v-col cols="2" class="p-atuo">
            <div class="position-relative top-50 start-50 translate-middle">시간 씩 진행됩니다.</div>
          </v-col>
        </v-row>
        <v-col cols="2"></v-col>
      </v-row>
      <v-row>
        <v-col cols="2"></v-col>
        <v-col class="d-flex" cols="8">
          <v-textarea v-model="explanation" name="input-7-1" filled label="스터디 설명" auto-grow required></v-textarea>
        </v-col>
        <v-col cols="2"></v-col>
        <v-row>
          <v-col cols="2"></v-col>
          <v-col cols="9">
            <v-card flat color="transparent">
              <v-subheader>지각 벌금</v-subheader>

              <v-card-text class="pt-0">
                <v-slider
                  v-model="tardyPenaltyAmount"
                  label="How much?"
                  :max="10000"
                  :min="0"
                  step="500"
                  :thumb-size="43"
                  thumb-label="always"
                ></v-slider>
              </v-card-text>

              <v-subheader>결석 벌금</v-subheader>

              <v-card-text class="pt-0">
                <v-slider
                  v-model="absentPenaltyAmount"
                  label="How much?"
                  :max="10000"
                  :min="0"
                  step="500"
                  :thumb-size="43"
                  thumb-label="always"
                ></v-slider>
              </v-card-text>

              <v-subheader>과제 미제출 벌금</v-subheader>

              <v-card-text class="pt-0">
                <v-slider
                  v-model="assignmentPenaltyAmount"
                  label="How much?"
                  :max="10000"
                  :min="0"
                  step="500"
                  :thumb-size="43"
                  thumb-label="always"
                ></v-slider>
              </v-card-text>
            </v-card>
          </v-col>
        </v-row>

        <!-- 모집마감일:deadline -->
        <v-row>
          <v-col cols="4"></v-col>
          <v-col cols="4">
            <div class="my-5 mx-auto">
              모집 마감일
            </div></v-col
          ><v-col cols="4"></v-col>
          <v-col cols="2"></v-col>
          <v-col cols="8">
            <v-date-picker v-model="deadline" title="모집 마감 날짜" full-width class="shadow ml-3"></v-date-picker>
          </v-col>
        </v-row>
        <!-- 제출버튼 -->
        <v-row>
          <v-col cols="2"></v-col>
          <v-col cols="4" class="py-10">
            <v-btn @click="goBack" class="bg-white mx-auto border border-primary text-primary" plain>
              작성 취소
            </v-btn>
          </v-col>
          <v-col cols="4" class="py-10">
            <v-btn type="submit" class="bg-primary mx-auto text-white" plain>
              모집글 게시
            </v-btn>
          </v-col>
        </v-row>
      </v-row>
    </form>
  </v-container>
</template>
<script>
  import axios from 'axios';
  export default {
    name: 'CreateRecruitItem',
    data: () => ({
      title: '',
      explanation: '',
      tardyPenaltyAmount: 0,
      absentPenaltyAmount: 0,
      assignmentPenaltyAmount: 0,
      recruitNumber: 2,
      deadline: new Date(Date.now() - new Date().getTimezoneOffset() * 60000).toISOString().substr(0, 10),
      recruitNumberOption: [2, 3, 4, 5, 6],
      selectedCategories: [],
      timesPerWeek: [1, 2, 3, 4, 5, 6, 7],
      hoursPerTime: [0.5, 1, 1.5, 2, 2.5, 3, 3.5, 4],
      timePerWeek: '',
      hourPerTime: '',
      items: [],
    }),
    mounted() {
      axios({
        method: 'get',
        url: `${this.$store.state.domain}/api/v1/category`,
      }).then((res) => {
        this.items = res.data;
      });
    },
    methods: {
      submit() {
        const data = {
          title: this.title,
          explanation: this.explanation,
          timePerWeek: this.timePerWeek,
          hourPerTime: this.hourPerTime,
          categories: this.selectedCategories,
          targetUserNumber: this.recruitNumber,
          deadline: String(this.deadline),
          tardyPenaltyAmount: this.tardyPenaltyAmount,
          absentPenaltyAmount: this.absentPenaltyAmount,
          assignmentPenaltyAmount: this.assignmentPenaltyAmount,
        };
        this.$store.dispatch('createRecruitItem', data);
        this.$router.push({ name: 'Studies' });
      },
      goBack() {
        this.$router.go(-1);
      },
    },
  };
</script>

<style>
  .note-bg {
    font-weight: bold;
  }

  .note-bg::after {
    background-size: cover;
    background-image: url(../../assets/note-bg.jpg);
    opacity: 0.5;
    z-index: -1;
  }

  .vertical-middle {
    display: table-cell;
    vertical-align: middle;
  }

  .full-height {
    height: 100%;
  }

  .dump-area {
    height: 40%;
  }

  form label {
    font-weight: bold;
  }
</style>
