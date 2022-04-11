<template>
    <v-container class="d-flex my-10 p-5 border border-2 note-bg shadow w-75 fw-bold">
        <form @submit.prevent="submit">
            <v-row justify="center">
            <v-col cols="2"></v-col>
            <v-col cols="8">
            <div class="my-5 mx-auto p-3 border border-2 row">
            <div class="col-4">
            </div>
            <div class="col-4 fw-bold mt-2">
                <h2>
                    내 정보
                </h2>
            </div>
            <div class="col-4 d-flex justify-content-end">
                <v-btn
                    color="#D6D0C2"
                    fab
                    small
                    @click="isEditing = !isEditing"
                    
                >
                    <v-icon v-if="isEditing">
                    mdi-close
                    </v-icon>
                    <v-icon v-else>
                    mdi-pencil
                    </v-icon>
                </v-btn>
            </div>


            </div></v-col><v-col cols="2"></v-col>
            <!-- 이름 -->


            <v-card-text>
                <v-row>
                    <v-col cols="6">
                        <v-text-field
                        :disabled="!isEditing"
                        color="white"
                        label="이름"
                    ></v-text-field>
                    </v-col>
                    <v-col cols="4">
                        <v-text-field
                        :disabled="!isEditing"
                        color="white"
                        label="직책"
                    ></v-text-field>
                    </v-col>
                    <v-col cols="2">
                        <v-autocomplete
                            :disabled="!isEditing"
                            :items="states"
                            :filter="customFilter"
                            color="white"
                            item-text="name"
                            label="성별"
                        ></v-autocomplete>
                    </v-col>  
                </v-row>

                <v-row>
                    <v-col cols="4">
                        <v-text-field
                            disabled
                            color="white"
                            label="평점 누적"
                        ></v-text-field>
                    </v-col>
                    <v-col cols="4">
                        <v-text-field
                            disabled
                            color="white"
                            label="평가 횟수"
                        ></v-text-field>
                    </v-col>
                </v-row>
            </v-card-text>

            <v-card-text>
                <v-text-field
                    :disabled="!isEditing"
                    color="white"
                    label="GitHub URL"
                ></v-text-field>
            </v-card-text>
            <v-card-text>
                <v-text-field
                    :disabled="!isEditing"
                    color="white"
                    label="Blog URL"
                ></v-text-field>
            </v-card-text>
            <v-card-text>
                <v-text-field
                    :disabled="!isEditing"
                    color="white"
                    label="백준 ID"
                ></v-text-field>
            </v-card-text>
            
            </v-row>
            

            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                    :disabled="!isEditing"
                    color="#D6D0C2"
                    @click="save"
                >
                    저장하기
                </v-btn>
                </v-card-actions>
                <v-snackbar
                v-model="hasSaved"
                :timeout="2000"
                absolute
                bottom
                left
                >
                회원 정보가 업데이트 되었습니다.
            </v-snackbar>
        </form>
    </v-container>

</template>

<script>
import axios from 'axios'
  export default {
    data () {
      return {
        hasSaved: false,
        isEditing: null,
        model: null,
        states: [
          { name: '남자', abbr: 'man', id: 1 },
          { name: '여자', abbr: 'woman', id: 2 },
        ],
        userData: null,
        userId: this.$store.state.userInfo.userId
        
      }
    },

    methods: {
      customFilter (item, queryText) {
        const textOne = item.name.toLowerCase()
        const textTwo = item.abbr.toLowerCase()
        const searchText = queryText.toLowerCase()

        return textOne.indexOf(searchText) > -1 ||
          textTwo.indexOf(searchText) > -1
      },
      save () {
        this.isEditing = !this.isEditing
        this.hasSaved = true
      },
    },
    created: {
        getUserProfile (userId) {
            axios({
                method: 'get',
                url: `${this.$store.state.domain}/api/v1/users/${userId}`,
            }).then((res) => {
                console.log(res.data)
                this.userData = res.data;
            });
        }
    },
    mounted: {
        getUserProfile (userId) {
            axios({
                method: 'get',
                url: `${this.$store.state.domain}/api/v1/users/${userId}`,
            }).then((res) => {
                console.log(res.data)
                this.userData = res.data;
            });
        }
    }
  }
</script>

<style>

</style>