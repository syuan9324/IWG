<template>
  <div class="text-center">
    <h3 class="text-center pb-5">編輯主機資料</h3>
    <div class="editServer">
      <b-form class="pb-2">
        <b-form-group
          label-cols="4"
          content-cols="8"
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
          label-cols="4"
          content-cols="8"
          label="Port號"
          label-for="port"
        >
          <b-form-input id="port" v-model="formDefault.port"></b-form-input>
        </b-form-group>
      </b-form>
      <b-form class="pb-2">
        <b-form-group
          label-cols="4"
          content-cols="8"
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
          label-cols="4"
          content-cols="8"
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
          label-cols="4"
          content-cols="8"
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
    </div>
    <div class="text-center pt-5">
      <b-button class="ml-2" style="background-color: #1aa4b7" @click="toSave"
        >儲存</b-button
      >
      <b-button class="ml-2" style="background-color: #1aa4b7" @click="toBack"
        >返回</b-button
      >
    </div>
  </div>
</template>

<script lang="ts">
import axios from "axios";
import { ref, computed, reactive, onMounted, Ref, toRef, toRaw } from "vue";
import router from "@/router";
import NotificationService from "@/shared/notification-service";
import { routerKey, useRoute } from "vue-router";

// import appHeader from "./header/app-header.vue";

export default {
  name: "editServer",
  props: {
    id: {
      required: false,
    },
  },

  setup(props: any) {
    const route = useRoute();
    const elea = toRaw(props.id);
    console.log("elea", props.id);
    console.log("route", route.params);
    // console.log("Prop", router);
    // console.log("Prop", props);
    // console.log("elea", props.elea);
    // const types = ["password", "userName", "hostName", "port", "fargeFileName"];
    const notificationService = new NotificationService();

    let formDefault = ref({
      password: "",
      username: "",
      hostname: "",
      port: "",
      mailReceiver: "",
      smsReceiver: "",
      active: "",
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
    };

    const toSave = () => {
      axios
        .post("/create/iwgHosts", formDefault.value)
        .then((response: any) => {
          notificationService.info("修改成功");
          console.log("then", response);
        })
        .catch((error) => {
          console.log("catch", error);
        });
    };
    const toBack = () => {
      router.push({ path: "/searchServer" });
    };

    return {
      // types,
      formDefault,
      toBack,
      toSave,
      reset,
    };
  },
};
</script>

<style scoped>
.editServer {
  max-width: 700px;
  margin-left: auto;
  margin-right: auto;
}
.ml-2 {
  margin-right: 20px;
}
</style>