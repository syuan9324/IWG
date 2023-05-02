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

    <b-form class="pb-2">
      <b-form-group
        class="col-12"
        label-cols="2"
        content-cols="2"
        label="帳號"
        label-for="username"
      >
        <b-form-input
          id="username"
          v-model="formDefault.username"
        ></b-form-input>
      </b-form-group>
    </b-form>
    <b-form class="pb-2">
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
    <b-form class="pb-2">
      <b-form-group
        class="col-12"
        label-cols="2"
        content-cols="2"
        label="主機名稱"
        label-for="hostname"
      >
        <b-form-input
          id="hostname"
          v-model="formDefault.hostname"
        ></b-form-input>
      </b-form-group>
    </b-form>
    <b-form class="pb-2">
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
    <b-form class="pb-2">
      <b-form-group
        class="col-12"
        label-cols="2"
        content-cols="2"
        label="信件收件者"
        label-for="mail"
      >
        <b-form-input id="mail" v-model="formDefault.mail"></b-form-input>
      </b-form-group>
    </b-form>
    <b-form class="pb-2">
      <b-form-group
        class="col-12"
        label-cols="2"
        content-cols="2"
        label="SMS收件者"
        label-for="sms"
      >
        <b-form-input id="sms" v-model="formDefault.sms"></b-form-input>
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
      <b-button class="mr-10" style="background-color: #1aa4b7" @click="reset"
        >清除</b-button
      >
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
  name: "addServer",
  components: { BButton, BFormInput, BButtonToolbar },
  setup() {
    // const types = ["password", "userName", "hostName", "port", "fargeFileName"];

    let formDefault = ref({
      password: "123456",
      username: "syuan",
      hostname: "01",
      port: 22,
      mail: "testReceiver@test.com",
      sms: "0921531997",
      // fargeFilename: "pcic",
    });

    // 表單物件驗證規則
    const rules = ref({
      password: {},
      username: {},
      hostname: {},
      port: {},
      mail: {},
      sms: {},
      // fargeFilename: {},
    });
    const form = reactive(Object.assign({}, formDefault));

    const reset = () => {
      formDefault.value = form.value;
      console.log("66form", form);
      console.log("66formDefault", formDefault);
    };

    const toSave = () => {
      axios
        .post("/create/iwgHosts", formDefault.value)
        .then((response: any) => {
          alert("新增成功");
          console.log("then", response);
        })
        .catch((error) => {
          console.log("catch", error);
        });
    };
    const toLog = () => {
      router.push({ path: "/searchLog" });
    };

    return {
      // types,
      formDefault,
      toLog,
      toSave,
      reset,
    };
  },
};
</script>

<style scoped>
</style>