<template>
  <div class="text-center">
    <h3 class="text-center pb-5">編輯主機資料</h3>
    <div class="editServer">
      <!-- <b-form class="pb-2">
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
      </b-form> -->
      <!-- <b-form class="pb-2">
        <b-form-group
          label-cols="4"
          content-cols="8"
          label="Port號"
          label-for="port"
        >
          <b-form-input id="port" v-model="formDefault.port"></b-form-input>
        </b-form-group>
      </b-form> -->
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

      <!-- <b-form-row class="pb-2">
        <b-form-group
          label-cols="4"
          content-cols="8"
          label="檔案名稱"
          label-for="fileName"
        >
          <b-form-input
            id="fileName"
            v-model="formDefault.fileName"
            placeholder="pwc-web.war"
          ></b-form-input>
        </b-form-group>
      </b-form-row>
      <b-form-row class="pb-2">
        <b-form-group
          label-cols="4"
          content-cols="8"
          label="IWG主機中參考檔案的位置"
          label-for="originFileLocation"
        >
          <b-form-input
            id="originFileLocation"
            v-model="formDefault.originFileLocation"
            placeholder="/root/"
          ></b-form-input>
        </b-form-group>
      </b-form-row>
      <b-form-row class="pb-2">
        <b-form-group
          label-cols="4"
          content-cols="8"
          label="IWG要監控的host中的檔案位置"
          label-for="targetFileLocation"
        >
          <b-form-input
            id="targetFileLocation"
            v-model="formDefault.targetFileLocation"
            placeholder="/home/tailinh/"
          ></b-form-input>
        </b-form-group>
      </b-form-row>
      <b-form-row class="pb-2">
        <b-form-group
          label-cols="4"
          content-cols="8"
          label="IWG主機中參考的目錄位置"
          label-for="originFolder"
        >
          <b-form-input
            id="originFolder"
            v-model="formDefault.originFolder"
            placeholder="C:Users/2106017welcome-content"
          ></b-form-input>
        </b-form-group>
      </b-form-row>
      <b-form-row class="pb-2">
        <b-form-group
          label-cols="4"
          content-cols="8"
          label="IWG要監控的host中的目錄位置"
          label-for="targerFolder"
        >
          <b-form-input
            id="targerFolder"
            v-model="formDefault.targerFolder"
            placeholder="/opt/wildfly-4/welcome-content/"
          ></b-form-input>
        </b-form-group>
      </b-form-row> -->
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
import { ref, watch, reactive, onMounted, Ref, toRef, toRaw } from "vue";
import router from "@/router";
import NotificationService from "@/shared/notification-service";
import { routerKey, useRoute } from "vue-router";
export default {
  name: "editServer",
  props: ["searchCon"],
  setup(props: any) {
    const notificationService = new NotificationService();
    const searchConProp = toRef(props, "searchCon");
    console.log("searchConProp", toRaw(searchConProp.value));

    onMounted(() => {
      formDefault.value.id = searchConProp.value.id;
      formDefault.value.targetFilenanme = searchConProp.value.targetFilenanme;
      formDefault.value.hostname = searchConProp.value.hostname;
      formDefault.value.port = searchConProp.value.port;
      formDefault.value.mailReceiver = searchConProp.value.mailReceiver;
      formDefault.value.smsReceiver = searchConProp.value.smsReceiver;
      formDefault.value.active = searchConProp.value.active;
    });

    let formDefault = ref({
      id: "",
      targetFilenanme: "",
      //
      password: "",
      username: "",
      hostname: "",
      port: "",
      mailReceiver: "",
      smsReceiver: "",
      active: "",
      //iwghoststarget
      fileName: "",
      originFileLocation: "",
      targetFileLocation: "",
      originFolder: "",
      targerFolder: "",
    });

    // 表單物件驗證規則
    const rules = ref({
      id: {},
      targetFilenanme: {},
      //iwgjosts
      password: {},
      username: {},
      hostname: {},
      port: {},
      mailReceiver: {},
      smsReceiver: {},
      active: {},
      //iwghoststarget
      fileName: {},
      originFileLocation: {},
      targetFileLocation: {},
      originFolder: {},
      targerFolder: {},
    });
    const form = reactive(Object.assign({}, formDefault));

    const reset = () => {
      formDefault.value = form.value;
    };

    const toSave = () => {
      console.log("then", formDefault.value);
      axios
        .post("/update/iwgHosts", formDefault.value)
        .then((response: any) => {
          notificationService.info("修改成功");
          console.log("then", response);
          router.push({ path: "/searchServer" });
        })
        .catch((error) => {
          console.log("catch", error);
        });
    };
    const toBack = () => {
      router.push({ path: "/searchServer" });
    };

    watch(
      searchConProp,
      (aa) => {
        formDefault.value.password = aa.id;
      },

      { immediate: true }
    );

    return {
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