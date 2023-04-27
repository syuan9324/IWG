<template>
  <div>
    <div>
      <!-- <router-link class="list-group-item" active-class="active" to="/log"
        >紀錄查詢</router-link
      > -->
    </div>
    <!-- <div>
      <router-link class="list-group-item" active-class="active" to="/home"
        >home</router-link
      >
    </div> -->
    <div>
      <router-view></router-view>
    </div>
    <home></home>
    <NotifyModal></NotifyModal>
  </div>
</template>

<script lang="ts">
import { useRouter } from "vue-router";
import NotifyModal from '@/component/notify-modal.vue'
export default {
  name: "app",
  components:{
    NotifyModal,
  },
  setup() {
    const router = useRouter();
    router.push({ name: "login" });
  },
};
</script>

<style scoped>
</style>