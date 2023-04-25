<template>
  <div class="text-center">
    <h1 class="text-center">新增批次服務服務</h1>
    <!-- <b-container fluid>
      <b-row class="my-1" v-for="type in types" :key="type">
        <b-col sm="3">
          <label :for="`type-${type}`"
            ><code>{{ type }}</code
            >:</label
          >
        </b-col>
        <b-col sm="9">
          <b-form-input :id="`type-${type}`" :type="type"></b-form-input>
        </b-col>
      </b-row>
    </b-container> -->

    <b-form>
      <b-form-group
        class="col-12"
        label-cols="2"
        content-cols="2"
        label="帳號"
        label-for="userName"
      >
        <b-form-input
          id="userName"
          v-model="formDefault.userName"
        ></b-form-input>
      </b-form-group>
    </b-form>
    <b-form>
      <b-form-group
        class="col-12"
        label-cols="2"
        content-cols="2"
        label="密碼"
        label-for="password"
      >
        <b-form-input
          id="password"
          v-model="formDefault.password"
        ></b-form-input>
      </b-form-group>
    </b-form>
    <b-form>
      <b-form-group
        class="col-12"
        label-cols="2"
        content-cols="2"
        label="主機名稱"
        label-for="hostName"
      >
        <b-form-input
          id="hostName"
          v-model="formDefault.hostName"
        ></b-form-input>
      </b-form-group>
    </b-form>
    <b-form>
      <b-form-group
        class="col-12"
        label-cols="2"
        content-cols="2"
        label="Port號"
        label-for="port"
      >
        <b-form-input id="port" v-model="formDefault.port"></b-form-input>
      </b-form-group>
    </b-form>

    <!-- <b-button-toolbar class="text-center">
      <b-button class="mr-1" type="search">儲存</b-button>
      <b-button class="mr-1" type="x-circle">清除</b-button>
      <b-button class="mr-1" type="file-earmark-plus">
        <router-link class="list-group-item" active-class="active" to="/log"
          >返回</router-link
        >
      </b-button>
    </b-button-toolbar> -->
    <div class="text-center">
      <b-button class="ml-2" style="background-color: #1aa4b7" @click="toSave"
        >儲存</b-button
      >
      <b-button class="mr-10" style="background-color: #1aa4b7">清除</b-button>
      <b-button class="pl-12" style="background-color: #1aa4b7" @click="toLog"
        >返回
      </b-button>
    </div>
  </div>
</template>

<script lang="ts">
import axios from "axios";
import { ref, computed, reactive, onMounted } from "vue";
import { BButton, BFormInput, BButtonToolbar } from "bootstrap-vue-3";
import router from "@/router";

// import appHeader from "./header/app-header.vue";

export default {
  name: "log",
  components: { BButton, BFormInput, BButtonToolbar },
  setup() {
    // const types = ["password", "userName", "hostName", "port", "fargeFileName"];

    const formDefault = ref({
      password: "",
      userName: "",
      hostName: "",
      port: "",
      fargeFileName: "",
    });

    // 表單物件驗證規則
    const rules = ref({
      password: {},
      userName: {},
      hostName: {},
      port: {},
      fargeFileName: {},
    });

    const toSave = () => {
      const criteria = {
        password: "123456",
        username: "syuan",
        hostname: "01",
        port: "22",
      };
      axios
        .post("/create/iwgHosts", criteria)
        .then((response: any) => {
          console.log(response);
        })
        .catch((error) => {
          console.log(error);
        });
    };
    const toLog = () => {
      router.push({ path: "/log" });
    };

    return {
      // types,
      formDefault,
      toLog,
      toSave,
    };
  },
};
</script>

<style scoped>
</style>