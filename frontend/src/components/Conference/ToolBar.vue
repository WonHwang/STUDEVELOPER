<template>
  <div>
    <v-toolbar dense color="#D6D0C2">
      <v-col cols="4"></v-col>
      <v-col cols="4">
        <span @click="toggleAudio()">
          <v-hover v-slot="{ hover }">
            <v-icon color="#818080" :class="{ 'on-hover': hover }" class="icon-on " v-if="audioActive">mdi-microphone</v-icon>
            <v-icon class="icon-off" color="red" v-else>mdi-microphone-off</v-icon>
          </v-hover>
        </span>
        <span @click="toggleVideo()">
          <v-hover v-slot="{ hover }">
            <v-icon color="#818080" :class="{ 'on-hover': hover }" class="icon-on" v-if="videoActive">mdi-video</v-icon>
            <v-icon class="icon-off" color="red" v-else>mdi-video-off</v-icon>
          </v-hover>
        </span>
        <!-- <span @click="shareScreen()">
          <v-hover v-slot="{ hover }">
            <v-icon color="#818080" :class="{ 'on-hover': hover }" class="icon-on" v-if="screenShareActive">mdi-television</v-icon>
            <v-icon class="icon-off" color="red" v-else>mdi-television-off</v-icon>
          </v-hover>
        </span> -->
        <span @click="leaveSession()">
          <v-hover v-slot="{ hover }">
            <v-icon color="#818080" :class="{ 'on-hover': hover }" class="icon-on">mdi-close-outline</v-icon>
          </v-hover>
        </span>
      </v-col>
      <v-col cols="4">
        <span @click="toggleChat()">
          <v-hover v-slot="{ hover }">
            <v-icon color="#818080" :class="{ 'on-hover': hover }" class="icon-on" v-if="!chatActive">mdi-message-text</v-icon>
            <v-icon class="icon-off" color="red" v-else>mdi-message-text</v-icon>
          </v-hover>
        </span>
      </v-col>
    </v-toolbar>
  </div>
</template>
<script>
  export default {
    name: 'tool-bar',
    props: {
      //   state: Boolean,
      videoActive: Boolean,
      audioActive: Boolean,
      screenShareActive: Boolean,
      //   stopScreenShare: Boolean,
      //   toggleFullscreen: Boolean,
      //   leaveSession: Boolean,
      chatActive: Boolean,
    },
    data() {
      return {};
    },
    methods: {
      toggleAudio() {
        // this.audioActive = !this.audioActive;
        this.$emit('audioActiveEvent');
        // emit하기
      },
      toggleVideo() {
        // this.videoActive = !this.videoActive;
        this.$emit('videoActiveEvent');
      },
      shareScreen() {
        this.$emit('screenShareEvent');
      },
      leaveSession() {
        this.$emit('leaveSessionEvent');
      },
      toggleChat() {
        this.$emit('toggleChatEvent');
      },
    },
  };
</script>

<style>
  .tool-bar {
    border-color: black;
  }
  .inactive {
    color: #fff;
  }
  .icon-on {
    letter-spacing: -0.3px;
    font-size: 16px;
    margin-right: 4px;
    padding: 4px 12px;
    border-radius: 100px;
    background-color: white;
    font-weight: 400;
  }
  .icon-off {
    letter-spacing: -0.3px;
    font-size: 16px;
    margin-right: 4px;
    padding: 4px 12px;
    border-radius: 100px;
    background-color: #c2b2a9;
    font-weight: 400;
  }
  .on-hover {
    background-color: #c2b2a9;
  }
</style>
