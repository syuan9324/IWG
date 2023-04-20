<template>
  <div>
    <div>
      <router-view></router-view>
    </div>
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