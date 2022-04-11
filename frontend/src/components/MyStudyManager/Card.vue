<template>
  <v-dialog width="50%">
    <StudyDetailModal :study_Id="study.studyId" />
    <template v-slot:activator="{ on, attrs }">
      <b-card style="max-width: 15rem;" class="mb-2" v-on="on" v-bind="attrs">
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
        <!-- vuetify로 구현한 스터디 상세보기 Modal창 -->
      </b-card>
    </template>
  </v-dialog>
</template>

<script>
  import StudyDetailModal from './StudyDetailModal';

  export default {
    name: 'Card',
    components: {
      StudyDetailModal,
    },
    props: {
      study: Object,
    },
    computed: {
      resizedTitle: function() {
        if (this.study.title.length > 10) {
          return this.study.title.slice(0, 9) + '...';
        }
        return this.study.title;
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
