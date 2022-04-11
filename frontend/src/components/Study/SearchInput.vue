<template>
  <v-container>
    <v-row>
      <div class="col-3">
        <v-container fluid>
          <v-row> <v-select v-model="value" :items="filteritems" label="필터" class="mt-3 mr-15" style="margin-left:10%;"></v-select> </v-row
        ></v-container>
      </div>
      <div class="col-6">
        <v-container fluid>
          <v-row>
            <v-combobox
              v-model="model"
              :items="items"
              :search-input.sync="search"
              hide-selected
              hint="최대 5개의 카테고리를 설정할 수 있습니다. 선택을 취소하고 싶으시면 Backspace를 이용하세요."
              label="원하시는 카테고리를 선택 혹은 입력하세요."
              multiple
              persistent-hint
              small-chips
            >
              <template v-slot:no-data>
                <v-list-item>
                  <v-list-item-content>
                    <v-list-item-title>
                      No results matching "<strong>{{ search }}</strong
                      >". Press <kbd>enter</kbd> to create a new one
                    </v-list-item-title>
                  </v-list-item-content>
                </v-list-item>
              </template>
            </v-combobox>
          </v-row>
        </v-container>
      </div>

      <div class="col-3">
        <v-container>
          <v-row>
            <v-col cols="12">
              <v-text-field v-model="search" outlined clearable label="Search" type="text" @keyup.enter="submit()" style="margin-right:10%;">
                <template v-slot:append>
                  <v-fade-transition leave-absolute>
                    <v-icon @click="submit()">mdi-magnify</v-icon>
                  </v-fade-transition>
                </template>
              </v-text-field>
            </v-col>
          </v-row>
        </v-container>
      </div>
    </v-row></v-container
  >
</template>

<script>
  import axios from 'axios';
  export default {
    data() {
      return {
        search: '',
        loading: false,
        items: [],
        model: [],
        filteritems: ['최신순', '마감임박순', '인원적은순', '인원많은순'],
        value: '최신순',
      };
    },

    methods: {
      submit() {
        var StudyList = new Array();
        if (this.search == null || this.search == '') {
          console.log('qe');
          this.search = 'null';
        }
        // context.commit('START_LOADING');
        axios({
          method: 'get',
          url: `${this.$store.state.domain}/api/v1/studies/search/${this.search}/${this.value}`,
        })
          .then((res) => {
            console.log(res.data);
            if (this.model.length == 0) {
              console.log('카테고리 없음');
              StudyList = res.data;
            } else {
              console.log('카테고리 있음');
              for (var i = 0; i < res.data.length; i++) {
                var categoryList = res.data[i].categories;
                for (var j = 0; j < categoryList.length; j++) {
                  const category = categoryList[j];
                  if (
                    this.model.find(function(model) {
                      return model == category;
                    })
                  ) {
                    StudyList.push(res.data[i]);
                    break;
                  }
                }
              }
            }
            if (this.search == 'null') this.search = '';
            console.log(StudyList);
            this.$store.state.studies = [];
            this.$store.state.studies.push(StudyList);
            // this.$store.state.studies[0] = StudyList;
            console.log(this.$store.state);
          })
          .catch((err) => {
            console.log(err);
          });
        // setTimeout(function() {
        //   context.commit('END_LOADING');
        // }, 500);
      },
    },
    mounted() {
      axios({
        method: 'get',
        url: `${this.$store.state.domain}/api/v1/category`,
      }).then((res) => {
        this.items = res.data;
      });
    },

    watch: {
      model(val) {
        if (val.length != 0) {
          if (val.length > 5) {
            this.$nextTick(() => this.model.pop());
          } else {
            var element = val[val.length - 1];
            if (
              !this.items.find(function(item) {
                return item == element;
              })
            ) {
              console.log(element);
              this.$nextTick(() => this.model.pop());
            }
          }
        }
      },
      value(target) {
        console.log(target);
      },
    },
  };
</script>
