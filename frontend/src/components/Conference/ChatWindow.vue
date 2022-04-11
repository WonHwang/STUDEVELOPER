<template>
  <!-- bchat -->
  <div :style="{ background: backgroundColor }">
    <beautiful-chat
      :always-scroll-to-bottom="alwaysScrollToBottom"
      :close="closeChat"
      :colors="colors"
      :is-open="isChatOpen"
      :message-list="messageList"
      :message-styling="messageStyling"
      :new-messages-count="newMessagesCount"
      :on-message-was-sent="onMessageWasSent"
      :open="openChat"
      :participants="participants"
      :show-close-button="true"
      :show-launcher="true"
      :show-emoji="true"
      :show-typing-indicator="showTypingIndicator"
      :show-edition="true"
      :show-deletion="true"
      :title-image-url="titleImageUrl"
      :disable-user-list-toggle="false"
      @onType="handleOnType"
      @edit="editMessage"
      @remove="removeMessage"
    >
      <template v-slot:text-message-toolbox="scopedProps">
        <button v-if="!scopedProps.me && scopedProps.message.type === 'text'" @click.prevent="like(scopedProps.message.id)">
          👍
        </button>
      </template>
      <template v-slot:text-message-body="scopedProps">
        <p class="sc-message--text-content" v-html="scopedProps.messageText"></p>
        <p v-if="scopedProps.message.data.meta" class="sc-message--meta" :style="{ color: scopedProps.messageColors.color }">
          {{ scopedProps.message.data.meta }}
        </p>
        <p v-if="scopedProps.message.isEdited || scopedProps.message.liked" class="sc-message--edited">
          <template v-if="scopedProps.message.isEdited">✎</template>
          <template v-if="scopedProps.message.liked">👍</template>
        </p>
      </template>
      <template v-slot:system-message-body="{ message }"> [System]: {{ message.text }} </template>
    </beautiful-chat>
  </div>
</template>

<script>
  import Chat from 'vue-beautiful-chat';
  Vue.use(Chat);

  import availableColors from '././BeautifulChat/colors';
  import Vue from 'vue';
  export default {
    created() {
      // bchat
      this.setColor('blue');
    },
    // bchat
    computed: {
      linkColor() {
        return true;
      },
      backgroundColor() {
        return this.chosenColor === 'dark' ? this.colors.messageList.bg : '#fff';
      },
    },
    mounted() {
      this.messageList.forEach((x) => (x.liked = false));
    },
    /// bchat
    props: {
      messageList: [],
      participants: [],
      titleImageUrl: String,
      newMessagesCount: Number,
      session: {},
      myUserId: String,
      myUserName: String,
      isChatOpen: Boolean,
    },

    data() {
      return {
        showTypingIndicator: '',
        colors: null,
        availableColors,
        chosenColor: null,
        alwaysScrollToBottom: true,
        messageStyling: true,
        userIsTyping: false,
      };
    },
    methods: {
      //bchat
      sendMessage(text) {
        // console.log(this.message);
        // console.log(this.session);
        // console.log(this.myUserName);
        if (text) {
          const data = { message: this.message, nickname: this.myUserName, streamId: this.mySessionId };
          this.session.signal({
            data: JSON.stringify(data),
            type: 'chat',
          });
          //   this.messageList.push(data);
        }
        console.log(this.messageList);
        this.message = '';

        // 원본
        // if (text.length > 0) {
        //   this.newMessagesCount = this.isChatOpen ? this.newMessagesCount : this.newMessagesCount + 1;
        //   this.onMessageWasSent({
        //     author: 'support',
        //     type: 'text',
        //     id: this.myUserId,
        //     data: { text },
        //   });
        // }
      },
      handleTyping(text) {
        this.showTypingIndicator = text.length > 0 ? this.participants[this.participants.length - 1].id : '';
      },
      onMessageWasSent(message) {
        message.author = this.myUserName;
        console.log(this.myUserName);
        message.data.text = this.myUserName + ' : ' + message.data.text;
        console.log(message);
        this.session.signal({
          data: JSON.stringify(Object.assign({}, message, { id: this.myUserId })),
          type: 'chat',
        });
        console.log('메시지 보냄 - chat-window');
        console.log;
        // this.$emit('messageSendEvent', message);
        // this.messageList = [...this.messageList, Object.assign({}, message, { id: this.myUserName })];
        // console.log(this.messageList);
      },
      openChat() {
        this.isChatOpen = true;
        this.newMessagesCount = 0;
        this.$emit('toggleChatEvent');
      },
      closeChat() {
        this.isChatOpen = false;
        this.$emit('toggleChatEvent');
      },
      setColor(color) {
        this.colors = this.availableColors[color];
        this.chosenColor = color;
      },
      showStylingInfo() {
        this.$modal.show('dialog', {
          title: 'Info',
          text:
            'You can use *word* to <strong>boldify</strong>, /word/ to <em>emphasize</em>, _word_ to <u>underline</u>, `code` to <code>write = code;</code>, ~this~ to <del>delete</del> and ^sup^ or ¡sub¡ to write <sup>sup</sup> and <sub>sub</sub>',
        });
      },
      messageStylingToggled(e) {
        this.messageStyling = e.target.checked;
      },
      handleOnType() {
        this.$root.$emit('onType');
        this.userIsTyping = true;
      },
      editMessage(message) {
        const m = this.messageList.find((m) => m.id === message.id);
        m.isEdited = true;
        m.data.text = message.data.text;
      },
      removeMessage(message) {
        if (confirm('Delete?')) {
          const m = this.messageList.find((m) => m.id === message.id);
          m.type = 'system';
          m.data.text = 'This message has been removed';
        }
      },
      like(id) {
        const m = this.messageList.findIndex((m) => m.id === id);
        var msg = this.messageList[m];
        msg.liked = !msg.liked;
        this.$set(this.messageList, m, msg);
      },
    },
    //bchat end
  };
</script>
<style>
  .sc-chat-window {
    width: 370px !important;
    height: 50vh !important;
    max-height: 50vh !important;
    position: fixed !important;
    right: 25px;
    top: 15%;
    box-sizing: border-box;
    box-shadow: 0px 7px 40px 2px rgba(148, 149, 150, 0.1);
    background: white;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    border-radius: 10px;
    animation: fadeIn;
    animation-duration: 0.3s;
    animation-timing-function: ease-in-out;
  }
  .sc-header {
    background: #ae9383 !important;
  }
  .sc-launcher {
    position: fixed;
    right: 25px;
    top: 10% !important;
    background-color: #ae9383 !important;
  }
  .sc-launcher .sc-closed-icon,
  .sc-launcher .sc-open-icon {
    top: 10% !important;
  }
  .sc-message--content.sent .sc-message--text {
    background-color: #ae9383 !important;
  }
</style>
