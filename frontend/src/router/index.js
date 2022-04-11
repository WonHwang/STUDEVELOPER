import Vue from 'vue';
import VueRouter from 'vue-router';
import Home from '../views/Home.vue';
import Studies from '../views/Studies.vue';
import CreateStudy from '../views/CreateStudy.vue';
import MyPage from '../views/MyPage.vue';
import Conference from '../views/Conference.vue';
import CurrentStudies from '../views/CurrentStudies.vue';
import StudyManager from '../views/StudyManager.vue';
import MyStudyManager from '../views/MyStudyManager.vue';

import MyProfile from '../views/MyProfile.vue'


Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
  },
  {
    path: '/studies',
    name: 'Studies',
    component: Studies, // Vue 파일
  },
  {
    path: '/create-study',
    name: 'CreateStudy',
    component: CreateStudy,
  },
  {
    path: '/mypage',
    name: 'MyPage',
    component: MyPage,
  },
  {
    path: '/conference',
    name: 'Conferences',
    component: Conference,
    children: [
      {
        path: '',
        name: 'conference-list',
        component: () => import('@/components/Conference/ConferenceList.vue'),
      },
      {
        path: 'card',
        name: 'conference-card',
        component: () => import('@/components/Conference/ConferenceCard.vue'),
      },
      {
        path: 'room',
        name: 'room',
        props: true,
        component: () => import('@/components/Conference/ConferenceRoom.vue'),
      },
      {
        path: 'scoring',
        name: 'scoring',
        props: true,
        component: () => import('@/components/Conference/Scoring.vue'),
      },
    ],
  },
  {
    path: '/current-studies',
    name: 'CurrentStudies',
    component: CurrentStudies,
  },
  {
    path: '/problem',
    name: 'problem',
    props: true,
    component: () => import('@/components/Problem.vue'),
  },
  {
    path: '/myProfile',
    name: 'MyProfile',
    component: MyProfile,
  },
  {
    path: '/studies/:studyId',
    name: 'StudyManager',
    props: true,
    component: StudyManager
  },
  {
    path: '/mystudymanager',
    name: 'MyStudyManager',
    component: MyStudyManager
  }
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

export default router;
