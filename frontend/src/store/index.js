import Vue from 'vue';
import Vuex from 'vuex';
import axios from 'axios';
import VueSimpleAlert from 'vue-simple-alert';

Vue.use(VueSimpleAlert);

Vue.use(Vuex);

// const domain = 'https://i5a503.p.ssafy.io:8080';
const domain = 'http://localhost:8080';

export default new Vuex.Store({
  state: {
    // domain: 'https://i5a503.p.ssafy.io:8080',
    domain: 'http://localhost:8080',
    isLogin: false,
    isLoginModalViewed: false,
    isSignupModalViewed: false,
    studies: [],
    isLoading: false,
    category: [],
    userInfo: {
      id: null,
      name: '',
    },
    studyApplyList: [],
  },
  mutations: {
    GET_STUDY_LIST(state, data) {
      state.studies = [];
      state.studies.push(data);
    },
    GET_MY_STUDY_LIST(state, userId) {
      const token = localStorage.getItem('jwt');
      const config = {
        Authorization: `Bearer ${token}`,
      };

      state.studies = [];
      axios({
        method: 'get',
        url: `${domain}/api/v1/studies/${userId}/head`,
        headers: config,
      })
        .then((res) => {
          state.studies.push(res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    GET_CATEGORY_LIST(state, data) {
      state.category = data;
    },
    DO_LOGIN: function(state, token) {
      localStorage.setItem('jwt', token);
      state.isLogin = true;
    },
    CHANGE_TO_LOGIN: function(state) {
      state.isLogin = true;
    },
    OPEN_LOGIN_MODAL: function(state) {
      state.isLoginModalViewed = true;
    },
    CHANGE_TO_LOGOUT: function(state) {
      state.isLogin = false;
      state.userInfo.id = null;
      state.userInfo.name = '';
    },
    CLOSE_LOGIN_MODAL: function(state) {
      state.isLoginModalViewed = false;
    },
    OPEN_SIGNUP_MODAL: function(state) {
      state.isSignupModalViewed = true;
    },
    CLOSE_SIGNUP_MODAL: function(state) {
      state.isSignupModalViewed = false;
    },
    DO_LOGOUT: function(state) {
      state.isLogin = false;
    },
    START_LOADING: function(state) {
      state.isLoading = true;
    },
    END_LOADING: function(state) {
      state.isLoading = false;
    },
    SET_USER_INFO(state, data) {
      state.userInfo.id = data.id;
      state.userInfo.name = data.name;
    },
    GET_STUDY_APPLY_LIST(state, data) {
      state.studyApplyList = data
    }
  },
  actions: {
    getStudyList: function(context) {
      context.commit('START_LOADING');
      axios({
        method: 'get',
        url: `${domain}/api/v1/studies`,
      })
        .then((res) => {
          context.commit('GET_STUDY_LIST', res.data);
        })
        .catch((err) => {
          console.log(err);
        });
      setTimeout(function() {
        context.commit('END_LOADING');
      }, 500);
    },

    getMyStudyList: function (context) {
      const token = localStorage.getItem('jwt');
      const config = {
        Authorization: `Bearer ${token}`,
      };
      
      context.commit('START_LOADING'); 
      axios({
        method: 'get',
        url: `${domain}/api/v1/users/check-info`,
        headers: config,
      }).then((res) => {
        context.commit('SET_USER_INFO', res.data);
        context.commit('GET_MY_STUDY_LIST', res.data.id)
      });
      setTimeout(function() {
        context.commit('END_LOADING');
      }, 500);
    },

    getStudyApplyList: function (context, studyId) {
      const token = localStorage.getItem('jwt');
      const config = {
        Authorization: `Bearer ${token}`,
      };

      axios({
        method: 'get',
        url: `${domain}/api/v1/studies/${studyId}/apply`,
        headers: config,
      })
      .then((res) => {
        context.commit('GET_STUDY_APPLY_LIST', res.data)
      })
    },

    getCategoryList: function(context) {
      axios({
        method: 'get',
        url: `${domain}/api/v1/category`,
      }).then((res) => {
        context.commit('GET_CATEGORY_LIST', res.data);
      });
    },
    doLogin: function(context, credentials) {
      context.commit('START_LOADING');
      axios({
        method: 'post',
        url: `${domain}/api/v1/auth/login`,
        data: credentials,
      })
        .then((res) => {
          context.commit('DO_LOGIN', res.data.accessToken);
          context.commit('CLOSE_LOGIN_MODAL');
          const token = localStorage.getItem('jwt');
          const config = {
            Authorization: `Bearer ${token}`,
          };
          axios({
            method: 'get',
            url: `${domain}/api/v1/users/check-info`,
            headers: config,
          }).then((res) => {
            context.commit('SET_USER_INFO', res.data);
          });
        })
        .catch((err) => {
          alert(err);
        });

      setTimeout(function() {
        context.commit('END_LOADING');
      }, 500);
    },

    doSignup: function(context, credentials) {
      context.commit('START_LOADING');
      axios({
        method: 'post',
        url: `${domain}/api/v1/users`,
        data: credentials,
      })
        .then(() => {
          context.commit('CLOSE_SIGNUP_MODAL');
        })
        .then((res) => {
          context.commit('SIGNUP', res);
          context.commit('CLOSE_SIGNUP_MODAL');
        })
        .catch((err) => {
          alert(err);
        });
      setTimeout(function() {
        context.commit('END_LOADING');
      }, 500);
    },

    doLogout: function(context) {
      context.commit('START_LOADING');
      localStorage.removeItem('jwt');
      context.commit('DO_LOGOUT');
      setTimeout(function() {
        context.commit('END_LOADING');
      }, 500);
    },

    checkLogin: function(context) {
      if (localStorage.getItem('jwt')) {
        context.commit('CHANGE_TO_LOGIN');
        const token = localStorage.getItem('jwt');
        const config = {
          Authorization: `Bearer ${token}`,
        };
        axios({
          method: 'get',
          url: `${domain}/api/v1/users/check-info`,
          headers: config,
        }).then((res) => {
          context.commit('SET_USER_INFO', res.data);
        });
      } else {
        context.commit('CHANGE_TO_LOGOUT');
      }
    },
    openLoginModal: function(context) {
      context.commit('OPEN_LOGIN_MODAL');
    },
    closeLoginModal: function(context) {
      context.commit('CLOSE_LOGIN_MODAL');
    },
    openSignupModal: function(context) {
      context.commit('OPEN_SIGNUP_MODAL');
    },
    closeSignupModal: function(context) {
      context.commit('CLOSE_SIGNUP_MODAL');
    },

    createRecruitItem: function(context, data) {
      context.commit('START_LOADING');
      const token = localStorage.getItem('jwt');
      const config = {
        Authorization: `Bearer ${token}`,
      };
      axios({
        method: 'post',
        url: `/api/v1/studies`,
        headers: config,
        data: data,
      })
        .then(() => {
          // studies 리스트 새로고침
          axios({
            method: 'get',
            url: `/api/v1/studies`,
          }).then((res) => {
            context.commit('GET_STUDY_LIST', res.data);
          });
        })
        .catch((err) => {
          alert(err);
        });
      setTimeout(function() {
        context.commit('END_LOADING');
      }, 500);
    },

    uploadFile: function(context, data) {
      const token = localStorage.getItem('jwt');
      const scheduleId = Number(data.scheduleId)
      const config = {
        "Content-Type": "multipart/form-data",
        Authorization: `Bearer ${token}`
      };
      const formData= new FormData()
      formData.append("taskFile",data.attachFile)
      console.log(data.attachFile)
      axios({
        method: 'post',
        url: `/api/v1/study-schedule/${scheduleId}`,
        headers: config,
        data: formData,
      })
      .then((res)=>{
        console.log(res)
      })
      setTimeout(function() {
        context.commit('END_LOADING');
      }, 500);
    },

    startLoading: function(context) {
      context.commit('START_LOADING');
    },

    endLoading: function(context) {
      context.commit('END_LOADING');
    },

    createComment: function(context, get_data) {
      // credentials가 필요하겠다.
      const token = localStorage.getItem('jwt');
      const config = {
        Authorization: `Bearer ${token}`,
      };
      const data = {
        content: get_data.comment,
      };
      axios({
        method: 'post',
        url: `${domain}/api/v1/studies/${get_data.study_id}/comments`,
        data: data,
        headers: config,
      })
    },

    updateUserScore: function(context, data) {
      context.commit('START_LOADING');
      axios({
        method: 'post',
        url: `/api/v1/users/score`,
        data: data,
      }).catch((err) => {
        alert(err);
      });
      setTimeout(function() {
        context.commit('END_LOADING');
      }, 500);
    },

    applyStudy: function (context, studyId) {
      const token = localStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${token}`,
      }
      axios({
        method: 'post',
        url: `${domain}/api/v1/studies/${studyId}/apply`,
        headers: config,
      })
    },

    cancelApply: function (context, studyId) {
      const token = localStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${token}`,
      }
      axios({
        method: 'post',
        url: `${domain}/api/v1/studies/${studyId}/cancel-apply`,
        headers: config,
      })
    },

    acceptApply: function (context, info) {
      const token = localStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${token}`,
      }
      axios({
        method: 'post',
        url: `${domain}/api/v1/studies/${info.studyId}/accept-apply/users/${info.userId}`,
        headers: config,
      })
      .catch (() => {
        alert("인원이 다 찼거나 유효하지 않은 요청입니다.");
      })
    },

    denyApply: function (context, info) {
      const token = localStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${token}`,
      }
      axios({
        method: 'post',
        url: `${domain}/api/v1/studies/${info.studyId}/deny-apply/users/${info.userId}`,
        headers: config,
      })
    },

    getParticipatingStudyList: function(context, userId) {
      context.commit('START_LOADING');
      const token = localStorage.getItem('jwt');
      const config = {
        Authorization: `Bearer ${token}`,
      };
      axios({
        method: 'get',
        url: `${domain}/api/v1/studies/${userId}/participation`,
        headers: config,
      })
        .then((res) => {
          context.commit('GET_STUDY_LIST', res.data);
        })
        .catch((err) => {
          console.log(err);
        });
      setTimeout(function() {
        context.commit('END_LOADING');
      }, 500);
    },
    getOpenedStudyList: function(context, userId) {
      context.commit('START_LOADING');
      const token = localStorage.getItem('jwt');
      const config = {
        Authorization: `Bearer ${token}`,
      };
      axios({
        method: 'get',
        url: `${domain}/api/v1/studies/${userId}/open-study`,
        headers: config,
      })
        .then((res) => {
          context.commit('GET_STUDY_LIST', res.data);
        })
        .catch((err) => {
          console.log(err);
        });
      setTimeout(function() {
        context.commit('END_LOADING');
      }, 500);
    },
    openStudyConference: function(context, study) {
      context.commit('START_LOADING');
      const token = localStorage.getItem('jwt');
      const config = {
        Authorization: `Bearer ${token}`,
      };
      const isOpen = true;
      axios({
        method: 'post',
        url: `${domain}/api/v1/studies/${study.studyId}/session/${isOpen}`,
        headers: config,
      })
        .then(() => {
          // studies 리스트 새로고침
          axios({
            method: 'get',
            url: `${domain}/api/v1/studies/${this.state.userInfo.id}/participation`,
            headers: config,
          }).then((res) => {
            context.commit('GET_STUDY_LIST', res.data);
          });
        })
        .catch((err) => {
          alert(err);
        });
      setTimeout(function() {
        context.commit('END_LOADING');
      }, 500);
    },
    closeStudyConference: function(context, study) {
      context.commit('START_LOADING');
      const token = localStorage.getItem('jwt');
      const config = {
        Authorization: `Bearer ${token}`,
      };
      const isOpen = false;
      axios({
        method: 'post',
        url: `${domain}/api/v1/studies/${study.studyId}/session/${isOpen}`,
        headers: config,
      })
        .then(() => {
          // studies 리스트 새로고침
          axios({
            method: 'get',
            url: `${domain}/api/v1/studies/${this.state.userInfo.id}/participation`,
            headers: config,
          }).then((res) => {
            context.commit('GET_STUDY_LIST', res.data);
          });
        })
        .catch((err) => {
          alert(err);
        });
      setTimeout(function() {
        context.commit('END_LOADING');
      }, 500);
    },
  },
  modules: {},
});