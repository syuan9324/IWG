<template>
  <div>
    <div>
      <router-link
        class="list-group-item"
        active-class="active"
        to="/testRouter"
        >testRouter</router-link
      >
      <router-link
        class="list-group-item"
        active-class="active"
        to="/testRouter2"
        >testRouter2</router-link
      >
      <router-link
        class="list-group-item"
        active-class="active"
        to="/log"
        >log</router-link
      >
      <button @click="logout">logout</button>
    </div>
    <div>
      <router-view></router-view>
    </div>
    <!-- <h1>Login Page</h1>
    <form v-on:submit.prevent="submitForm">
      <label for="username">Username:</label>
      <input type="text" id="username" name="username" v-model="username" />
      <label for="password">Password:</label>
      <input type="password" id="password" name="password" v-model="password" />
      <button type="submit">
        <router-link class="list-group-item" active-class="active" to="/log"
          >Login</router-link
        >
      </button>
      {{ jwt }}
    </form> -->
  </div>
</template>

<script lang="ts">
import { ref } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";
import AccountService from "@/account/accout.service";
export default {
  name: "home",
  setup() {
    const router = useRouter();
    const accountService = new AccountService();
    const result = ref("");
    const testToken: any = () => {
      axios
        .get("testResource")
        .then((response: any) => {
          console.log(response);
          result.value = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    };

    const logout = () => {
      accountService.logout();
      router.push({ name: "login" });
    };
    return {
      testToken,
      logout,
    };
  },
};
</script>

<style scoped>
</style>