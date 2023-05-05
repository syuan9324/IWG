<template>
  <header>
    <div class="desktop-app-header-wrapper">
      <div class="top-nav pr-3">
        <a class="btn-link" @click="logout">登出</a>
      </div>
    </div>
    <div class="img">
      <img src="src\main\webapp\app\img\PCIC_logged_in_logo.png" height="68" />
    </div>
    <div class="desktop-app-header-wrapper2">
      <div class="top-nav pr-3"><strong>公共工程系統監控管理</strong></div>
    </div>
  </header>
</template>

<script lang="ts">
import { ref } from "vue";
import AccountService from "@/account/accout.service";
import axios from "axios";
import { useRouter } from "vue-router";
import { NotifyModalStore } from "@/store/notify-modal-store";
import { useStore } from "vuex";

export default {
  name: "AppHeader",
  setup() {
    const router = useRouter();
    const accountService = new AccountService();
    const logStatus = ref(false);
    const logout = () => {
      accountService.logout();
      router.push({ name: "login" });
    };

    return { logout, logStatus, NotifyModalStore };
  },
};
</script>
<style lang="scss" scoped>
.desktop-app-header-wrapper > .top-nav {
  font-size: 18px; /* 14px */
  color: #fff;
  background-color: #1aa4b7;
  padding-top: 1rem;
  padding-bottom: 1rem;
  text-align: right;
  font-weight: 500;
}
.desktop-app-header-wrapper2 > .top-nav {
  font-size: 20px; /* 14px */
  color: #fff;
  background-color: #1aa4b7;
  padding-top: 1rem;
  padding-bottom: 1rem;
  text-align: center;
  font-weight: 500;
}

.desktop-app-header-wrapper > .top-nav > a {
  margin-left: 0.25rem;
  margin-right: 0.25rem;
  color: #fff;
}
.desktop-app-header-wrapper > .top-nav > a:hover {
  border-bottom: 1px solid;
}
</style>
