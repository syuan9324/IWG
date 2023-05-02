<template>
  <div>
    <div class="sidebar">
      <h3>功能</h3>
      <ul>
        <li>
          <router-link
            class="list-group-item"
            active-class="active"
            to="/searchLog"
            >log</router-link
          >
        </li>
        <li>
          <router-link
            class="list-group-item"
            active-class="active"
            to="/addServer"
            >add</router-link
          >
        </li>
        <li>
          <router-link
            class="list-group-item"
            active-class="active"
            to="/testRouter"
            >testRouter</router-link
          >
        </li>
        <li>
          <router-link
            class="list-group-item"
            active-class="active"
            to="/testRouter2"
            >testRouter2</router-link
          >
        </li>
      </ul>
    </div>
    <div></div>
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
import { ref, toRef, Ref, toRefs } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";
import AccountService from "@/account/accout.service";
export default {
  name: "home",
  props: {
    logStatus: {
      type: String,
      required: false,
    },
  },
  setup(props: any) {
    console.log(111);
    const logStatusProps: Ref<any> = toRef(props, "logStatus");
    // const propValue = toRefs(props).username;

    console.log("6666logStatusPropsvalue", logStatusProps.value);
    console.log("6666logStatusProps", logStatusProps);
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
.sidebar {
  background-color: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 4px;
  padding: 10px;
}

/* .sidebar h3 {
  font-size: 20px;
  margin-top: 0;
  margin-bottom: 10px;
} */

.sidebar ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.sidebar li {
  margin-bottom: 10px;
}

.sidebar a {
  color: #337ab7;
  text-decoration: none;
}

.sidebar a:hover {
  text-decoration: underline;
}
</style>