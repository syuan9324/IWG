<template>
  <div>
    <h1 class="text-center">
      <!-- <font-awesome-icon icon="search"></font-awesome-icon> -->
      批次服務查詢
    </h1>
    <button class="btn btn-danger">删除</button>
    <b-button variant="info" size="sm">修改</b-button>
    <b-button variant="danger">Button</b-button>
    <b-button variant="success">Button</b-button>
    <b-button variant="outline-primary">Button</b-button>
  </div>
</template>

<script lang="ts">
import { ref, computed, reactive, onMounted } from "vue";
import { BButton } from "bootstrap-vue-3";
import axios from "axios";
// import appHeader from "./header/app-header.vue";

export default {
  name: "log",
  components: { BButton },
  setup() {
    let username = ref("");
    let password = ref("");
    let batchServiceCategory = ref("");
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
      batchServiceCategory,
    };
  },
};
</script>

<style scoped>
</style>