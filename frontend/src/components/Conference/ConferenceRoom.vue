<!-- 실제 화상 회의 방 -->
<!-- "Parameter 'customSessionId' is wrong. Must be an alphanumeric string [a-zA-Z0-9_-]" -->
<!-- sessionId 넘겨줄 때 무조건 영어, 숫자로만 이루어져야함 -->
<template>
  <div class="conferenceRoom" style="height: 100%; overflow:auto; ">
    <!-- 툴바 -->
    <div>
      <tool-bar
        :audioActive="this.audioActive"
        :videoActive="this.videoActive"
        :screenShareActive="this.screenShareActive"
        :chatActive="this.isChatOpen"
        @audioActiveEvent="toggleaudioActive()"
        @videoActiveEvent="togglevideoActive()"
        @screenShareEvent="screenShare()"
        @leaveSessionEvent="leaveSession()"
        @toggleChatEvent="toggleChat()"
      ></tool-bar>
    </div>
    <!-- 회의 이름 -->

    <!-- 내 비디오 -->
    <!-- <div id="main-video"> -->
    <div v-if="session" v-bind:class="{ 'session-open': this.isChatOpen, 'session-close': !this.isChatOpen }">
      <user-video :stream-manager="mainStreamManager" :subscribersNumber="subscribersNumber" />
      <!-- </div> -->
      <!-- 다른 사람 비디오 -->
      <!-- <div id="video-container"> -->
      <!-- <user-video :stream-manager="publisher" @click.native="updateMainVideoStreamManager(publisher)" /> -->
      <user-video
        v-for="sub in subscribers"
        :key="sub.stream.connection.connectionId"
        :stream-manager="sub"
        :subscribersNumber="subscribersNumber"
        @click.native="updateMainVideoStreamManager(sub)"
      />
    </div>
    <chat-window
      :participants="this.participants"
      :titleImageUrl="this.titleImageUrl"
      :messageList="this.messageList"
      :newMessagesCount="this.newMessagesCount"
      :userIsTyping="this.userIsTyping"
      :session="this.session"
      :myUserName="this.myUserName"
      :myUserID="this.myUserID"
      :isChatOpen="this.isChatOpen"
      @toggleChatEvent="toggleChat()"
    ></chat-window>
    <!-- </div> -->
    <!-- <div>
      <chat-component
        v-if="this.chatActive"
        :streamManager="this.streamManager"
        :mySessionId="this.mySessionId"
        :myUserName="this.myUserName"
        :session="this.session"
      ></chat-component>
    </div> -->
  </div>
</template>

<script>
  import axios from 'axios';
  import { OpenVidu } from 'openvidu-browser';
  import UserVideo from './UserVideo.vue';
  import ToolBar from './ToolBar.vue';
  import ChatWindow from './ChatWindow.vue';
  // import SessionContainer from './SessionContainer'
  // import ChatComponent from './ChatComponent.vue';

  // Openvidu 설정
  axios.defaults.headers.post['Content-Type'] = 'application/json';
  // const OPENVIDU_SERVER_URL = 'https://' + location.hostname + ':4443';
  // const OPENVIDU_SERVER_URL = 'https://localhost:4443';
  const OPENVIDU_SERVER_URL = 'https://i5a503.p.ssafy.io:8443';
  const OPENVIDU_SERVER_SECRET = 'MY_SECRET';

  export default {
    components: {
      UserVideo,
      ToolBar,
      ChatWindow,
      // ChatComponent,
    },
    props: {
      study: Object,
      hostUserId: Number,
    },

    data() {
      return {
        participants: [],
        participantsInfo: [],
        titleImageUrl: 'https://a.slack-edge.com/66f9/img/avatars-teams/ava_0001-34.png',
        messageList: [],
        newMessagesCount: 0,
        isChatOpen: false,
        showTypingIndicator: '',
        availableColors: 'blue',
        alwaysScrollToBottom: true,
        messageStyling: true,
        userIsTyping: false,

        OV: undefined,
        session: undefined,
        mainStreamManager: undefined,
        publisher: undefined,
        subscribers: [],
        audioActive: true,
        videoActive: true,
        screenShareActive: true,
        streamManager: undefined,
        userType: 'local',

        // 유저 ID, 유저 이름, 회의 ID, 회의 참여자 수
        myUserID: String,
        myUserName: String,
        conferenceId: String,
        subscribersNumber: Number,
      };
    },
    mounted() {
      this.subscribersNumber = this.subscribers.length;
      console.log('화상 회의 입장');
      console.log(this.study);
      this.conferenceId = 'session' + this.study.studyId;
      console.log(this.conferenceId);
      this.joinSession();

      // 메시지 받기 시그널
      this.session.on('signal:chat', (event) => {
        console.log('메시지 받음');
        console.log(event);
        const data = JSON.parse(event.data);
        console.log(data);
        if (data.author == this.myUserName) {
          data.author = 'me';
        }
        this.messageList = [...this.messageList, data];
        console.log(this.messageList);
      });

      // 다른 참여자가 나갈 때 시그널
      // this.session.on('streamDestroyed', (event) => {
      //   console.log('streamDestroyed');
      //   console.log(event);
      //   if (this.subscribers) this.subscribersNumber = 1 + this.subscribers.length;
      //   else this.subscribersNumber = 1;
      //   console.log(this.subscribersNumber);
      // });

      // 1
      this.session.on('connectionDestroyed', (event) => {
        console.log('connectionDestroyed');
        console.log(event);
      });
      //  회의가 완전히 사라질 경우 (delete한 경우), 이벤트처리하기
      this.session.on('sessionDisconnected', (event) => {
        console.log(event);
        // var participantsID = [];
        // var participantsName = [];
        console.log('회의 강제 종료된 경우');
        var evaluationList = new Array();
        for (var i = 0; i < this.participantsInfo.length; i++) {
          var data = this.participantsInfo[i].stream.connection.data;
          var { clientID } = JSON.parse(data);
          var { clientData } = JSON.parse(data);

          console.log(clientID);
          console.log(clientData);

          evaluationList.push({ id: clientID, name: clientData, score: 0 });
        }
        console.log(evaluationList);
        this.session = undefined;
        this.$router.push({ name: 'scoring', params: { evaluationList: evaluationList } });
      });
    },

    created() {},

    methods: {
      // 오디오 켜기/끄기
      toggleaudioActive() {
        this.audioActive = !this.audioActive;
        this.publisher.publishAudio(this.audioActive);
      },
      // 비디오 켜기/끄기
      togglevideoActive() {
        this.videoActive = !this.videoActive;
        this.publisher.publishVideo(this.videoActive);
      },
      // 채팅창 열기/닫기
      toggleChat() {
        this.isChatOpen = !this.isChatOpen;
      },
      // 화면 공유는 좀 나중에.. 아직 미구현
      screenShare() {
        const videoSource = navigator.userAgent.indexOf('Firefox') !== -1 ? 'window' : 'screen';
        const publisher = this.OV.initPublisher(
          undefined,
          {
            videoSource: videoSource,
            publishAudio: this.videoActive,
            publishVideo: this.audioActive,
            mirror: false,
          },
          (error) => {
            if (error && error.name === 'SCREEN_EXTENSION_NOT_INSTALLED') {
              this.setState({ showExtensionDialog: true });
            } else if (error && error.name === 'SCREEN_SHARING_NOT_SUPPORTED') {
              alert('Your browser does not support screen sharing');
            } else if (error && error.name === 'SCREEN_EXTENSION_DISABLED') {
              alert('You need to enable screen sharing extension');
            } else if (error && error.name === 'SCREEN_CAPTURE_DENIED') {
              alert('You need to choose a window or application to share');
            }
          }
        );
        this.publisher.once('accessAllowed', () => {
          this.state.session.unpublish(this.streamManager);
          this.streamManager = this.publisher;
          this.state.session.publish(this.streamManager).then(() => {
            this.screenShareActive = !this.screenShareActive;
          });
        });
        publisher.on('streamPlaying', () => {
          this.updateLayout();
          publisher.videos[0].video.parentElement.classList.remove('custom-class');
        });

        this.screenShareActive = !this.screenShareActive;
      },
      // 화상 채팅 입장
      // 로그인 된 유저 정보 + 참가하려는 방 정보로 입장
      // ConferenceList에서 해당하는 방 정보 전달받아야함
      joinSession() {
        // --- Get an OpenVidu object ---
        this.OV = new OpenVidu();

        // --- Init a session ---
        this.session = this.OV.initSession();

        // --- Specify the actions when events take place in the session ---

        // On every new Stream received...
        this.session.on('streamCreated', ({ stream }) => {
          console.log('입장');
          console.log(stream);
          const subscriber = this.session.subscribe(stream);
          console.log(subscriber);
          this.subscribers.push(subscriber);

          // const index = this.subscribers.indexOf(stream.streamManager, 0);
          // console.log('인덱스는?');
          var data = stream.connection.data;
          var { clientID } = JSON.parse(data);
          var { clientData } = JSON.parse(data);
          console.log('메시지');
          console.log(clientID);
          console.log(clientData);
          this.participants.push({ id: clientID, name: clientData, imageUrl: 'https://a.slack-edge.com/66f9/img/avatars-teams/ava_0001-34.png' });

          this.participantsInfo.push(subscriber);

          console.log(this.subscribers);
          console.log(this.subscribers.length);
          this.subscribersNumber = this.subscribers.length + 1;
          console.log(this.subscribersNumber);
        });

        //  다른 참여자가 나갈 때 시그널
        //  On every Stream destroyed...
        this.session.on('streamDestroyed', ({ stream }) => {
          const index = this.subscribers.indexOf(stream.streamManager, 0);
          if (index >= 0) {
            this.subscribers.splice(index, 1);
          }
        });

        // On every asynchronous exception...
        this.session.on('exception', ({ exception }) => {
          console.warn(exception);
        });

        console.log(this.subscribersNumber);

        // --- Connect to the session with a valid user token ---

        // 'getToken' method is simulating what your server-side should do.
        // 'token' parameter should be retrieved and returned by your own backend
        this.getToken(this.conferenceId).then((token) => {
          console.log('토큰 가져오기');
          console.log(this.conferenceId);
          console.log(token);
          this.myUserID = this.$store.state.userInfo.id;
          this.myUserName = this.$store.state.userInfo.name;
          this.session
            .connect(token, { clientData: this.myUserName, clientID: this.myUserID })
            .then(() => {
              console.log('gettoken 연결?');
              // --- Get your own camera stream with the desired properties ---
              let publisher = this.OV.initPublisher(undefined, {
                audioActiveSource: undefined, // The source of audioActive. If undefined default microphone
                videoSource: undefined, // The source of video. If undefined default webcam
                publishaudioActive: true, // Whether you want to start publishing with your audioActive unmuted or not
                publishVideo: true, // Whether you want to start publishing with your video enabled or not
                resolution: '640x480', // The resolution of your video
                frameRate: 30, // The frame rate of your video
                insertMode: 'APPEND', // How the video is inserted in the target element 'video-container'
                mirror: false, // Whether to mirror your local video or not
              });
              console.log('연결');
              this.mainStreamManager = publisher;
              this.publisher = publisher;

              // --- Publish your stream ---

              this.session.publish(this.publisher);
            })
            .catch((error) => {
              console.log('There was an error connecting to the session:', error.code, error.message);
            });
        });

        window.addEventListener('beforeunload', this.leaveSession);
      },
      // 세션 떠나기
      // 이것도 고쳐야함
      leaveSession() {
        // 참여했던 인원들 정보 출력
        // console.log("참여 인원 기록 출력하기")
        // for (var i = 0; i < this.participantsInfo.length; i++) {
        //   var { clientID } = JSON.parse(this.participantsInfo[i].stream.connection.data);
        //   console.log(clientID);
        // }
        // --- Leave the session by calling 'disconnect' method over the Session object ---
        // 스터디 장이면
        if (this.myUserID == this.study.userInfo.userId) {
          this.$confirm('스터디장이 떠나면 화상 회의가 닫힙니다.', '세션을 종료하시겠습니까?', 'warning')
            .then(() => {
              console.log('세션 종료');
              if (this.session) this.session.disconnect();

              this.session = undefined;
              this.mainStreamManager = undefined;
              this.publisher = undefined;
              this.subscribers = [];
              this.OV = undefined;
              this.subscribersNumber = this.subscribers.length + 1;

              window.removeEventListener('beforeunload', this.leaveSession);
              // 세션 종료
              axios
                .delete(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${this.conferenceId}`, {
                  auth: {
                    username: 'OPENVIDUAPP',
                    password: OPENVIDU_SERVER_SECRET,
                  },
                })
                .then(console.log('디스트로이'))
                .then(this.$store.dispatch('closeStudyConference', this.study))
                .catch(console.log('오류'));
            })
            .catch(() => {});
        }
        // 스터디 원이면
        else if (this.session != undefined) {
          console.log(this.session);
          this.$confirm('지금 스터디를 떠나면 참여 스터디원 평가를 할 수 없습니다.', '세션을 떠나시겠습니까?', 'warning')
            .then(() => {
              console.log('세션 떠나기');
              if (this.session) this.session.disconnect();

              this.session = undefined;
              this.mainStreamManager = undefined;
              this.publisher = undefined;
              this.subscribers = [];
              this.OV = undefined;
              this.subscribersNumber = this.subscribers.length + 1;

              window.removeEventListener('beforeunload', this.leaveSession);
              this.$router.push('/conference');
            })
            .catch(() => {});
        }

        // 스터디원이면
        // else {
        //   if (this.session) this.session.disconnect();
        // }

        // else

        // this.$router.push({ name: 'scoring', params: { evaluationList: this.participantsInfo } });
      },

      updateMainVideoStreamManager(stream) {
        if (this.mainStreamManager === stream) return;
        this.mainStreamManager = stream;
      },
      // 여기서부턴 다 백엔드로 넘겨야함
      /**
       * --------------------------
       * SERVER-SIDE RESPONSIBILITY
       * --------------------------
       * These methods retrieve the mandatory user token from OpenVidu Server.
       * This behavior MUST BE IN YOUR SERVER-SIDE IN PRODUCTION (by using
       * the API REST, openvidu-java-client or openvidu-node-client):
       *   1) Initialize a Session in OpenVidu Server	(POST /openvidu/api/sessions)
       *   2) Create a Connection in OpenVidu Server (POST /openvidu/api/sessions/<SESSION_ID>/connection)
       *   3) The Connection.token must be consumed in Session.connect() method
       */

      getToken(mySessionId) {
        console.log('gettoken');
        return this.createSession(mySessionId).then((sessionId) => this.createToken(sessionId));
      },

      // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessions
      createSession(sessionId) {
        return new Promise((resolve, reject) => {
          axios
            .post(
              `${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
              JSON.stringify({
                customSessionId: sessionId,
              }),
              {
                auth: {
                  username: 'OPENVIDUAPP',
                  password: OPENVIDU_SERVER_SECRET,
                },
              }
            )
            .then((response) => response.data)
            .then((data) => resolve(data.id))
            .catch((error) => {
              if (error.response.status === 409) {
                resolve(sessionId);
              } else {
                console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`);
                if (
                  window.confirm(
                    `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`
                  )
                ) {
                  location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
                }
                reject(error.response);
              }
            });
        });
      },

      // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessionsltsession_idgtconnection
      createToken(sessionId) {
        console.log('create token');
        console.log(sessionId);
        return new Promise((resolve, reject) => {
          axios
            .post(
              `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
              {},
              {
                auth: {
                  username: 'OPENVIDUAPP',
                  password: OPENVIDU_SERVER_SECRET,
                },
              }
            )
            .then((response) => response.data)
            .then((data) => resolve(data.token))
            .catch((error) => reject(error.response));
        });
      },

      // scoreingParticipants(participantsInfo) {
      //   console.log(participantsInfo);
      //   this.$router.push({ name: 'scoring', params: { evaluationList: this.participantsInfo } });
      // },
    },
  };
</script>

<style>
  @import './VideoRoomComponent.css';
  .session-open {
    display: flex;
    flex-flow: row wrap;
    justify-content: center;
    align-items: center;
    gap: 5px;
    max-height: inherit;
    /* max-width: inherit; */
    /* overflow: inherit; */
    object-fit: contain;
    margin-right: 390px;
  }
  .session-close {
    display: flex;
    flex-flow: row wrap;
    justify-content: center;
    align-items: center;
    gap: 5px;
    max-height: inherit;
    /* max-width: inherit; */
    /* overflow: inherit; */
    object-fit: contain;
  }
  body {
    padding: 0px;
    margin: 0px;
  }

  .demo-description {
    max-width: 500px;
  }

  .demo-description img {
    max-width: 500px;
  }

  .demo-test-area {
    width: 300px;
    box-sizing: border-box;
  }

  .demo-test-area--text {
    box-sizing: border-box;
    width: 100%;
    margin: 0px;
    padding: 0px;
    resize: none;
    background: #fafbfc;
    color: #8da2b5;
    border: 1px solid #dde5ed;
    font-size: 16px;
    padding: 16px 15px 14px;
    margin: 0;
    border-radius: 6px;
    outline: none;
    height: 150px;
    margin-bottom: 10px;
  }

  .demo-monster-img {
    width: 400px;
    display: block;
    margin: 60px auto;
  }

  .text-center {
    text-align: center;
  }

  .colors a {
    color: #fff;
    text-decoration: none;
    padding: 4px 10px;
    border-radius: 10px;
  }

  .toggle a {
    text-decoration: none;
  }

  .messageStyling {
    font-size: small;
  }

</style>
