<template>
  <div>
    <!-- <CarouselContent /> -->
    <div class="row">
      <div class="col-3"></div>
      <div class="col-6">
        <img src="../assets/after_nav/site_info_1.png" width="100%">
      </div>
      <div class="col-3"></div>
      <div class="col-3"></div>
      <div class="col-6">
        <img src="../assets/after_nav/home_1.png" width="100%">
      </div>
      <div class="col-3"></div>
      <div class="col-3"></div>
      <div class="col-6">
        <img src="../assets/after_nav/home_2.png" width="100%">
      </div>
      <div class="col-3"></div>
      <div class="col-3"></div>
      <div class="col-6">
        <img src="../assets/after_nav/home_3.png" width="100%">
      </div>
      <div class="col-3"></div>


      <div class="row">
        <div class="col-3"></div>
        <div class="col-6">
          <v-carousel height="100%">
            <v-carousel-item
              v-for="(item,i) in items"
              :key="i"
              :src="item.src"
              reverse-transition="fade-transition"
              transition="fade-transition"

              style=" width: 100% !important; 
                      heights: 100% !important;
                      "
              
            ></v-carousel-item>
          </v-carousel>
        </div>
        <div class="col-4"></div>
      </div>
    </div>

        <!-- <img src="../assets/after_nav/home_3.png"> -->
        
  </div>
</template>

<script>
  // import CarouselContent from '../components/CarouselContent'

  export default {
    name: 'Home',

    components: {
      // CarouselContent
    },

    data () {
      return {
        items: [
          {
            src: require("../assets/after_nav/study_content_1.png"),
          },
          {
            src: require("../assets/after_nav/conference_content_1.png"),
          },
          {
            src: require("../assets/after_nav/mypage_content_1.png"),
          },
          {
            src: require("../assets/after_nav/announce_content_1.png"),
          },
        ]
      }
    },
    
    
  }
</script>

<style>

</style>