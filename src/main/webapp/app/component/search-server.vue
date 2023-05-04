<template>
  <div class="text-center">
    <h1 class="text-center">批次服務查詢</h1>
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

    <!-- <b-form-row> -->
    <!-- <i-form-group-check :label="$t('label.batchServiceCategory') + '：'" :item="$v.batchServiceCategory"> -->
    <!-- <b-form-input></b-form-input> -->
    <!-- </i-form-group-check> -->
    <!-- <i-form-group-check :label="$t('label.batchServiceNo') + '：'" :item="$v.batchServiceNo"> -->
    <!-- <b-form-select :options="queryOptions.ServiceNoOpt" v-model="$v.batchServiceNo.$model"></b-form-select> -->
    <!-- </i-form-group-check> -->
    <!-- </b-form-row> -->
    <b-form>
      <b-form-group
        class="col-12"
        label-cols="2"
        content-cols="2"
        id="fieldset-1"
        label="主機名稱"
        label-for="hostname"
      >
        <b-form-input
          id="hostname"
          v-model="formDefault.hostname"
        ></b-form-input>
      </b-form-group>
    </b-form>
    <!-- <b-form>
      <b-form-group
        class="col-12"
        label-cols="2"
        content-cols="2"
        id="fieldset-1"
        label="檔案路徑"
        label-for="targeFilename"
      >
        <b-form-input
          id="targeFilename"
          v-model="formDefault.targeFilename"
        ></b-form-input>
      </b-form-group>
    </b-form> -->
    <b-form>
      <b-form-group
        class="col-12"
        label-cols="2"
        content-cols="2"
        id="fieldset-1"
        label="檔案是否異動"
        label-for="result"
      >
        <b-form-radio-group id="result" v-model="formDefault.result">
          <b-form-radio value="Y">是</b-form-radio>
          <b-form-radio value="N">否</b-form-radio>
        </b-form-radio-group>
      </b-form-group>
    </b-form>
    <div class="text-center">
      <b-button class="ml-2" style="background-color: #1aa4b7" @click="toQuery"
        >查詢</b-button
      >
      <b-button class="mr-10" style="background-color: #1aa4b7">清除</b-button>
      <b-button class="pl-12" style="background-color: #1aa4b7" @click="toAdd"
        >新增
        <!-- <router-link class="list-group-item" active-class="active" to="/add"
          >新增</router-link
        > -->
      </b-button>
    </div>
  </div>

  <section class="mt-2" v-if="stepVisible">
    <div>
      <b-table striped hover :items="paginatedItems" :fields="table.fields">
        <template #cell(result)="row">
          {{ row.item.result }}
        </template>
        <template #cell(index)="row">
          {{ row.index + 1 }}
        </template>
      </b-table>
      <b-pagination
        v-model="page"
        :total-rows="table.data.length"
        :per-page="perPage"
        align="center"
      />
    </div>
  </section>
</template>

<script lang="ts">
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
import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import router from "@/router";
import axios from "axios";

export default {
  name: "searchServer",
  components: {
    BButton,
    BFormInput,
    BButtonToolbar,
    BFormGroup,
    BForm,
    BFormRadio,
    BFormRadioGroup,
  },
  setup() {
    const stepVisible = ref(false);

    const page = ref(1); //當前頁面
    const perPage = ref(20); //每頁顯示的資料

    // 根據當前頁碼和每頁顯示的資料數，計算當前頁面顯示的資料
    const paginatedItems = computed(() => {
      const start = (page.value - 1) * perPage.value;
      const end = start + perPage.value;
      return table.data.slice(start, end);
    });

    // 計算總頁數
    const totalPages = computed(() => {
      return Math.ceil(table.data.length / perPage.value);
    });

    const formDefault = ref({
      hostname: "",
      result: "N",
    });

    // 表單物件驗證規則
    const rules = ref({
      hostname: {},
      result: {},
    });

    const table = reactive({
      fields: [
        {
          key: "index",
          label: "序號",
          sortable: false,
          thStyle: "width:10%",
          thClass: "text-center",
          tdClass: "text-center align-middle",
        },
        {
          key: "hostname",
          label: "主機",
          sortable: false,
          thStyle: "width:10%",
          thClass: "text-center",
          tdClass: "text-center align-middle",
        },
        {
          key: "port",
          label: "port號",
          sortable: false,
          thStyle: "width:20%",
          thClass: "text-center",
          tdClass: "text-center align-middle",
        },
        {
          key: "triggerTime",
          label: "啟動時間",
          sortable: false,
          thStyle: "width:20%",
          thClass: "text-center",
          tdClass: "text-center align-middle",
        },
        {
          key: "finishTime",
          label: "結束時間",
          sortable: false,
          thStyle: "width:20%",
          thClass: "text-center",
          tdClass: "text-center align-middle",
        },
        {
          key: "result",
          label: "結果",
          sortable: false,
          thStyle: "width:20%",
          thClass: "text-center",
          tdClass: "text-center align-middle",
        },
        {
          key: "targetFilename",
          label: "偵測檔案",
          sortable: false,
          thStyle: "width:20%",
          thClass: "text-center",
          tdClass: "text-center align-middle",
        },
      ],
      data: [],
      totalItems: 0,
    });

    const toAdd = () => {
      router.push({ path: "/add" });
    };

    const toQuery = () => {
      stepVisible.value = true;
      axios
        .post("/find/iwgHostsLogs", formDefault.value)
        .then((data) => {
          // ele.forEach((e) => {});
          table.data = data.data;
          console.log("table.data", table.data);
        })
        .catch((error) => {
          console.log("catch", error);
        });
      // table.data = [];
      // table.totalItems = 1;
      // table.data.splice(0, table.data.length, ...mockdata);
    };

    return {
      // types,
      toAdd,
      formDefault,
      stepVisible,
      toQuery,
      table,
      paginatedItems,
      totalPages,
    };
  },
};
</script>

<style scoped>
</style>