<template>
  <div class="text-center">
    <h1 class="text-center">新增批次服務</h1>
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

    <b-form-row class="pb-2">
      <b-form-group
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
    </b-form-row>
    <b-form class="pb-2">
      <b-form-group
        label-cols="2"
        content-cols="2"
        label="密碼"
        label-for="password"
      >
        <b-form-input
          id="password"
          type="password"
          v-model="formDefault.password"
        ></b-form-input>
      </b-form-group>
    </b-form>
    <b-form class="pb-2">
      <b-form-group
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
        label-cols="2"
        content-cols="2"
        label="信件收件者"
        label-for="mailReceiver"
      >
        <b-form-input
          id="mailReceiver"
          v-model="formDefault.mailReceiver"
        ></b-form-input>
      </b-form-group>
    </b-form>
    <b-form class="pb-2">
      <b-form-group
        label-cols="2"
        content-cols="2"
        label="SMS收件者"
        label-for="smsReceiver"
      >
        <b-form-input
          id="smsReceiver"
          v-model="formDefault.smsReceiver"
        ></b-form-input>
      </b-form-group>
    </b-form>
    <b-form>
      <b-form-group
        label-cols="2"
        content-cols="2"
        id="active"
        label="是否啟用"
        label-for="active"
      >
        <b-form-radio-group id="active" v-model="formDefault.active">
          <b-form-radio value="Y">是</b-form-radio>
          <b-form-radio value="N">否</b-form-radio>
        </b-form-radio-group>
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
      <!-- <b-button class="pl-12" style="background-color: #1aa4b7" @click="toLog"
        >返回
      </b-button> -->
    </div>
  </div>
</template>

<script lang="ts">
import axios from "axios";
import { ref, computed, reactive, onMounted } from "vue";
import {
  BButton,
  BFormInput,
  BButtonToolbar,
  BFormGroup,
  BForm,
  BFormRadioGroup,
  BFormRadio,
} from "bootstrap-vue-3";
import router from "@/router";
import NotificationService from "@/shared/notification-service";

// import appHeader from "./header/app-header.vue";

export default {
  name: "addServer",
  components: {
    BButton,
    BFormInput,
    BButtonToolbar,
    BFormGroup,
    BFormRadioGroup,
    BFormRadio,
    BForm,
  },
  setup() {
    // const types = ["password", "userName", "hostName", "port", "fargeFileName"];
    const notificationService = new NotificationService();

    let formDefault = ref({
      password: "123456",
      username: "syuan",
      hostname: "01",
      port: 22,
      mailReceiver: "testReceiver@test.com",
      smsReceiver: "0921531997",
      active: "Y",
    });

    // 表單物件驗證規則
    const rules = ref({
      password: {},
      username: {},
      hostname: {},
      port: {},
      mailReceiver: {},
      smsReceiver: {},
      active: {},
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
          notificationService.info("新增成功");
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
.b-form-group .form-control {
  max-width: 400px;
  margin-left: auto;
  margin-right: auto;
}

.text-center {
  text-align: center;
}
</style>