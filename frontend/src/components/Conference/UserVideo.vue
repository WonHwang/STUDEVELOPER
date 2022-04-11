<template>
  <div v-if="streamManager && this.subscribersNumber > 4" class="flexItem-many">
    <div class="nickname">
      <p>{{ clientData }}</p>
    </div>
    <ov-video :stream-manager="streamManager" />
  </div>
  <div v-else-if="streamManager && this.subscribersNumber >= 2" class="flexItem-middle">
    <div class="nickname">
      <p>{{ clientData }}</p>
    </div>
    <ov-video :stream-manager="streamManager" />
  </div>

  <div v-else-if="streamManager && this.subscribersNumber >= 0" class="flexItem-low">
    <div class="nickname">
      <p>{{ clientData }}</p>
    </div>
    <ov-video :stream-manager="streamManager" />
  </div>
</template>

<script>
  import OvVideo from './OvVideo';

  export default {
    name: 'UserVideo',

    components: {
      OvVideo,
    },

    props: {
      streamManager: Object,
      subscribersNumber: Number,
    },

    computed: {
      clientData() {
        const { clientData } = this.getConnectionData();
        const { clientName } = this.getConnectionData();
        console.log(clientName);
        console.log(clientData);
        return clientData;
      },
    },
    mounted() {
      if (this.subscribersNumber >= 4) {
        console.log(this.subscribersNumber);
        console.log(this.streamManager);
      } else if (this.subscribersNumber >= 2) {
        console.log(this.subscribersNumber);
        console.log(this.streamManager);
      } else {
        console.log(this.subscribersNumber);
        console.log(this.streamManager);
      }
    },

    methods: {
      getConnectionData() {
        const { connection } = this.streamManager.stream;
        return JSON.parse(connection.data);
      },
    },
  };
</script>

<style>
  @import './StreamComponent.css';

  .flexItem-many {
    /* flex: 0 1 240px; */
    align-self: stretch;
    flex: 0 1 24%;
    max-height: 40vh;
    max-width: 40vh;
  }
  .flexItem-middle {
    align-self: stretch;
    /* width: 35%; */
    flex: 0 1 40%;
    max-height: 40vh;
    max-width: 50vh;
    /* object-fit: scale-down; */
  }
  .flexItem-low {
    align-self: stretch;
    /* width: 70%; */
    flex: 0 1 70%;
    max-height: 70vh;
    max-width: 100vh;
  }
</style>
