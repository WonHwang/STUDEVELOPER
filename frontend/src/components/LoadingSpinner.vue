<template>
<div class="loading-page">
  <div class="overlay">
  </div>
  <div class="loading-spinner">
      <v-progress-circular
      indeterminate
      color="primary"
    ></v-progress-circular>
  </div>
</div>
</template>

<script>
export default {
  name: 'LoadingSpinner',
}
</script>

<style>
  .loading-page,
  .overlay {
    width: 100%;
    height: 100%;
    position: fixed;
    left: 0;
    top: 0;
  }
  
  .overlay {
    opacity: 0.8;
    margin-top: 10px;
    background-color: black;
  }
  .loading-spinner {
    position: relative;
    max-width: 80%;
    margin: auto;
    margin-top: 10rem;
    padding: 20px;
    min-height: 500px;
    z-index: 10;
    opacity: 1;
  }
</style>