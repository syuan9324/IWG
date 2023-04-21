<template>
  <div>
    <!-- <div>
      <router-link class="list-group-item" active-class="active" to="/log"
        >紀錄查詢</router-link
      >
    </div> -->
    <!-- <div>
      <router-link class="list-group-item" active-class="active" to="/home"
        >home</router-link
      >
    </div> -->
    <div>
      <router-view></router-view>
    </div>
    <!-- <home></home> -->
  </div>
</template>

<script lang="ts">
import { useRouter } from "vue-router";

export default {
  name: "app",
  setup() {
    const router = useRouter();
    router.push({ name: "login" });
  },
};
</script>

<style scoped>
</style>