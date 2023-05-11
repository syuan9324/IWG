<template>
  <div>
    <searchServerVue
      v-if="status1 === 'searchServerVue'"
      currentComponent="父組件進來"
      @queryItemt="queryItemt"
      @status="status"
      @searchClick="searchClick"
    />
    <editServerVue v-else />
    <h3>子组件传值内容：{{ status1 }}</h3>
    <h3>子组件传值内容：{{ searchCon }}</h3>
    <!-- <router-view></router-view> -->
  </div>
</template>

<script lang="ts">
import { ref, toRef, toRaw } from "vue";
import editServerVue from "./edit-server.vue";
import searchServerVue from "./search-server.vue";
export default {
  name: "searchServerHome",
  components: { searchServerVue, editServerVue },

  setup(props: any, content: any) {
    const searchCon = ref("");
    const status1 = ref("searchServerVue");

    const searchClick = (searchServerVue: any) => {
      searchCon.value = searchServerVue.content;
    };

    const queryItemt = (searchServerVue: any) => {
      console.log("searchServerVue1111", searchServerVue);
      searchCon.value = searchServerVue;
    };

    const status = (searchServerVue: any) => {
      status1.value = searchServerVue;
    };

    return {
      searchClick,
      searchCon,
      queryItemt,
      status1,
      status,
    };
  },
};
</script>

<style scoped>
</style>