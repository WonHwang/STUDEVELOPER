<template>
  <div class="display-inline-block">
    {{ task.postUserName }}-<a href="#">{{ task.uploadFileName }}</a>
    <v-divider></v-divider>
  </div>
</template>

<script>
export default {
  data: () => ({
    task: this.task,
    taskId: this.taskId,
    urlPath: `${this.$store.state.domain}/api/v1/attach/${this.taskId}`
  })
  ,
  props: {
    task: Object
  },
  created: { 
  }

}
</script>

<style>

</style>
