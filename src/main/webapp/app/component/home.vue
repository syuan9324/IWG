<template>
  <div>
    <h1>Login Page</h1>
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
    </form>
  </div>
</template>

<script lang="ts">
import { ref } from "vue";
import axios from "axios";

export default {
  name: "home",
  setup() {
    let username = ref("");
    let password = ref("");
    let jwt = ref("");
    async function submitForm() {
      if (username.value === "" || password.value === "") {
        alert("Please enter both username and password.");
      } else {
        jwt.value = await axios.post(
          "http://localhost:8080/api/service/sign-in",
          { username: username.value, password: password.value }
        );
      }
    }
    return {
      username,
      password,
      submitForm,
      jwt,
    };
  },
};
</script>

<style scoped>
</style>