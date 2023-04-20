<template>
  <div>
    <h1>test router</h1>
    <h1>home</h1>
    <button @click="testToken()">testToken</button>
  </div>
</template>
<script lang="ts">
import { ref } from "vue";
import axios from "axios";

export default {
  name: "testRouter",
  setup() {
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
    return {
      testToken,
    };
  },
};
</script>