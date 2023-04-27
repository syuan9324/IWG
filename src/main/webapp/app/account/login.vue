<template>
  <div>
    <h1 class="text-center">login</h1>
    <form v-on:submit.prevent="submitForm" class="text-center">
      <label for="username">Username:</label>
      <input type="text" id="username" name="username" v-model="username" />
      <label for="password">Password:</label>
      <input type="password" id="password" name="password" v-model="password" />
      <button type="submit">Login</button>
    </form>
    <addVue />
  </div>
</template>

<script lang="ts">
import { ref } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";
import addVue from "@/component/add.vue";
export default {
  name: "home",

  setup() {
    let username = ref("");
    let password = ref("");
    let jwt: any = ref("");
    const router = useRouter();
    async function submitForm() {
      if (username.value === "" || password.value === "") {
        alert("Please enter both username and password.");
      } else {
        jwt.value = await axios.post("auth/authenticate", {
          username: username.value,
          password: password.value,
        });
        console.log(JSON.stringify(jwt.value));
        sessionStorage.setItem("token", jwt.value.data);
        router.push({ name: "home" });
      }
    }

    return {
      username,
      password,
      submitForm,
      jwt,
      addVue,
    };
  },
};
</script>

<style scoped>
</style>