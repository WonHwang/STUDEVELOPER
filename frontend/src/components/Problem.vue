<template>
  <v-container fluid>
    <v-row class="subheader-problem">카테고리</v-row>
    <v-row>
      <!-- 카테고리 고르기 -->
      <v-combobox
        :return-object="false"
        v-model="model"
        :items="items"
        :search-input.sync="search"
        hide-selected
        hint="최대 5개의 카테고리를 설정할 수 있습니다. 선택을 취소하고 싶으시면 Backspace를 이용하세요."
        label="원하시는 카테고리를 선택 혹은 입력하세요."
        multiple
        persistent-hint
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
      <!-- 티어 고르기 -->
      <!-- <v-range-slider :v-model="tiers" :value="[0, 19]" min="0" max="19" thumb-label="always" thumb-color="">
        <template v-slot:thumb-label="item" class="b5">
          <span>{{ tiers[item.value] }}</span>
        </template>
      </v-range-slider> -->
    </v-row>
    <v-row class="subheader">난이도</v-row>
    <v-row>
      <v-col cols="2">
        <v-subheader>
          최소 난이도
        </v-subheader>
      </v-col>
      <v-col cols="3">
        <v-select v-model="minTier" :items="tiers" menu-props="auto" label="Select" hide-details single-line>
          <template v-slot:selection="{ item }">
            <v-chip :class="item">{{ item }}</v-chip>
          </template>
          <template v-slot:item="{ item, attrs, on }">
            <v-list-item v-on="on" v-bind="attrs">
              <v-list-item-action>
                <v-checkbox :input-value="active"></v-checkbox>
              </v-list-item-action>
              <v-list-item-content>
                <v-list-item-title>
                  <v-chip :class="item">{{ item }}</v-chip>
                </v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </template>
        </v-select>
      </v-col>
      <v-col cols="2">
        <v-subheader>
          최대 난이도
        </v-subheader>
      </v-col>
      <v-col cols="3">
        <v-select v-model="maxTier" :items="tiers2" menu-props="auto" label="Select" hide-details single-line>
          <template v-slot:selection="{ item }">
            <v-chip :class="item">{{ item }}</v-chip>
          </template>
          <template v-slot:item="{ item, attrs, on }">
            <v-list-item v-on="on" v-bind="attrs">
              <v-list-item-action>
                <v-checkbox :input-value="active"></v-checkbox>
              </v-list-item-action>
              <v-list-item-content>
                <v-list-item-title>
                  <v-chip :class="item">{{ item }}</v-chip>
                </v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </template>
        </v-select>
      </v-col>
    </v-row>

    <v-row class="subheader">정렬</v-row>
    <v-row>
      <v-switch v-model="sortDefault" inset :label="`기본 순서`"></v-switch>
    </v-row>
    <v-row>
      <v-col cols="6">
        <v-switch v-model="sortLevelUp" inset :label="`레벨 높은 것부터`"></v-switch>
      </v-col>
      <v-col cols="6">
        <v-switch v-model="sortLevelDown" inset :label="`레벨 낮은 것부터`"></v-switch>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="6">
        <v-switch v-model="sortSolvedCountUp" inset :label="`푼 사람 많은 것부터`"></v-switch>
      </v-col>
      <v-col cols="6">
        <v-switch v-model="sortSolvedCountDown" inset :label="`푼 사람 적은 것부터`"></v-switch>
      </v-col>
    </v-row>
    <v-row>
      <v-btn
        style="left: 50%; transform:translate(-50%,0%); margin-top: 20px;"
        min-width="10rem"
        width="10%"
        class="new-button"
        color="#d6d0c2"
        @click="searchProblem()"
        >검색</v-btn
      >
    </v-row>
  </v-container>
</template>

<script>
  export default {
    name: 'Problem',
    data() {
      return {
        items: [
          { text: '수학', values: 'math' },
          { text: '구현', values: 'implementation' },
          { text: '다이나믹 프로그래밍', values: 'dp' },
          { text: '그래프', values: 'graphs' },
          { text: '자료구조', values: 'data_structures' },
          { text: '문자열', values: 'string' },
          { text: '그리디', values: 'greedy' },
          { text: '그래프 탐색', values: 'graph_traversal' },
          { text: '완전탐색', values: 'bruteforcing' },
          { text: '정렬', values: 'sorting' },
          { text: '세그먼트 트리', values: 'segtree' },
          { text: '트리', values: 'trees' },
          { text: '이분 탐색', values: 'binary_search' },
          { text: 'BFS', values: 'bfs' },
          { text: '사칙연산', values: 'arithmetic' },
          { text: '시뮬레이션', values: 'simulation' },
          { text: 'DFS', values: 'dfs' },
          { text: '비트마스킹', values: 'bitmask' },
          { text: '다익스트라', values: 'dijkstra' },
          { text: '분할정복', values: 'divide_and_conquer' },
          { text: '백트래킹', values: 'backtracking' },
          { text: '우선순위 큐', values: 'priority_queue' },
          { text: '스택', values: 'stack' },
          { text: '투 포인터', values: 'two_pointer' },
          { text: '해시', values: 'hash_set' },
          { text: '재귀', values: 'recursion' },
          { text: '최소 스패닝 트리', values: 'mst' },
          { text: '위상정렬', values: 'topological_sorting' },
          { text: '플로이드-와샬', values: 'floyd_warshall' },
          { text: '트라이', values: 'trie' },
          { text: '배낭 문제', values: 'knapsack' },
          { text: 'KMP', values: 'kmp' },
          { text: '슬라이딩 윈도우', values: 'sliding_window' },
          { text: '덱', values: 'deque' },
          { text: '가장 긴 증가하는 부분 수열', values: 'lis' },
          { text: '중간에서 만나기', values: 'mitm' },
          { text: '큐', values: 'queue' },
          { text: '벨만-포드', values: 'bellman_ford' },
        ],
        minTier: 'b5',
        minTierIndex: 0,
        maxTier: 'b5',
        maxTierIndex: 0,
        tiers: ['b5', 'b4', 'b3', 'b2', 'b1', 's5', 's4', 's3', 's2', 's1', 'g5', 'g4', 'g3', 'g2', 'g1', 'p5', 'p4', 'p3', 'p2', 'p1'],
        tiers2: ['b5', 'b4', 'b3', 'b2', 'b1', 's5', 's4', 's3', 's2', 's1', 'g5', 'g4', 'g3', 'g2', 'g1', 'p5', 'p4', 'p3', 'p2', 'p1'],
        model: [],
        categories: [],
        search: null,
        sortDefault: true,
        sortLevelUp: false,
        sortLevelDown: false,
        sortSolvedCountUp: false,
        sortSolvedCountDown: false,
      };
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
                return item.text == element;
              })
            ) {
              console.log(element);
              this.$nextTick(() => this.model.pop());
            }
          }
        }
      },
      minTier(val) {
        this.minTierIndex = this.tiers.findIndex(function(element) {
          return element == val;
        });
        this.tiers2 = this.tiers.slice(this.minTierIndex);
        if (this.minTierIndex > this.maxTierIndex) {
          this.maxTierIndex = this.minTierIndex;
          this.maxTier = this.minTier;
        }
      },
      maxTier(val) {
        this.maxTierIndex =
          this.tiers2.findIndex(function(element) {
            return element == val;
          }) + this.minTierIndex;
      },
      sortDefault() {
        if (this.sortDefault == true) {
          this.sortLevelUp = false;
          this.sortLevelDown = false;
          this.sortSolvedCountUp = false;
          this.sortSolvedCountDown = false;
        }
      },
      sortLevelUp() {
        if (this.sortLevelUp == true) {
          this.sortDefault = false;
          this.sortLevelDown = false;
          this.sortSolvedCountUp = false;
          this.sortSolvedCountDown = false;
        } else {
          if (!this.sortLevelDown && !this.sortSolvedCountUp && !this.sortSolvedCountDown) {
            this.sortDefault = true;
          }
        }
      },
      sortLevelDown() {
        if (this.sortLevelDown == true) {
          this.sortDefault = false;
          this.sortSolvedCountDown = false;
          this.sortSolvedCountUp = false;
          this.sortLevelUp = false;
        } else {
          if (!this.sortLevelUp && !this.sortSolvedCountUp && !this.sortSolvedCountDown) {
            this.sortDefault = true;
          }
        }
      },
      sortSolvedCountUp() {
        if (this.sortSolvedCountUp == true) {
          this.sortDefault = false;
          this.sortLevelUp = false;
          this.sortLevelDown = false;
          this.sortSolvedCountDown = false;
        } else {
          if (!this.sortLevelUp && !this.sortLevelDown && !this.sortSolvedCountDown) {
            this.sortDefault = true;
          }
        }
      },
      sortSolvedCountDown() {
        if (this.sortSolvedCountDown == true) {
          this.sortDefault = false;
          this.sortLevelUp = false;
          this.sortLevelDown = false;
          this.sortSolvedCountUp = false;
        } else {
          if (!this.sortLevelUp && !this.sortLevelDown && !this.sortSolvedCountUp) {
            this.sortDefault = true;
          }
        }
      },
    },

    methods: {
      searchProblem() {
        this.categories.length = 0;
        for (var i = 0; i < this.model.length; i++) {
          var target = this.model[i];
          this.categories.push(
            this.items.find(function(element) {
              return element.text == target;
            }).values
          );
        }
        var urlLink = 'https://solved.ac/search?query=tier%3A' + this.minTier + '..' + this.maxTier;
        // 카테고리 선택하지 않은 경우 -> 모든 카테고리 검색
        if (this.categories.length == 0) {
          console.log('없음');
        }
        // 카테고리 선택된 경우
        else {
          urlLink += '%26(';
          for (var j = 0; j < this.categories.length; j++) {
            urlLink += 'tag%3A' + this.categories[j] + '%7C';
          }
          urlLink = urlLink.slice(0, urlLink.length - 3);
          urlLink += ')';
          console.log('있음');
        }
        if (this.sortLevelUp == true) {
          urlLink += '&sort=level&direction=desc';
        } else if (this.sortLevelDown == true) {
          urlLink += '&sort=level&direction=asc';
        } else if (this.sortSolvedCountUp == true) {
          urlLink += '&sort=solved&direction=asc';
        } else if (this.sortSolvedCountDown == true) {
          urlLink += '&sort=solved&direction=desc';
        }
        console.log(urlLink);
        window.open(urlLink);
      },
    },
  };
</script>
<style>
  .b5,
  .b4,
  .b3,
  .b2,
  .b1 {
    background-color: #b96e24 !important;
    color: white !important;
    font-weight: bold;
  }
  .s5,
  .s4,
  .s3,
  .s2,
  .s1 {
    background-color: #435f7a !important;
    color: white !important;
    font-weight: bold;
  }
  .g5,
  .g4,
  .g3,
  .g2,
  .g1 {
    background-color: #ec9a00 !important;
    color: white !important;
    font-weight: bold;
  }
  .p5,
  .p4,
  .p3,
  .p2,
  .p1 {
    background-color: #27e2a4 !important;
    color: white !important;
    font-weight: bold;
  }
  .subheader-problem {
    font-size: 110%;
    font-weight: bold;
    margin-top: 3% !important;
  }
</style>
