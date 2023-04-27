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
    <b-form>
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
    </b-form>
    <b-form>
      <b-form-group
        class="col-12"
        label-cols="2"
        content-cols="2"
        id="fieldset-1"
        label="檔案是否異動"
        label-for="fileMove"
      >
        <b-form-radio-group id="fileMove" v-model="formDefault.fileMove">
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

  <!-- <section class="mt-2">
    <div v-if="stepVisible" class="container">
      <i-table
        ref="iTable"
        title="通報案件查處結果公告一覽表"
        class="table-sm"
        :itemsUndefinedBehavior="'loading'"
        :items="table.data"
        :fields="table.fields"
        :totalItems="table.totalItems"
        :is-server-side-paging="true"
        @changePagination="handlePaginationChanged($event)"
      >
      </i-table>
    </div>
  </section> -->
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

// import iTable from "./shared/i-table/i-table.vue";

export default {
  name: "log",
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
    // const types = ["hostName", "targeFileName"];
    const iTable = ref(null);

    const stepVisible = ref(false);

    const formDefault = ref({
      hostName: "",
      targeFileName: "",
      fileMove: "N",
    });

    // 表單物件驗證規則
    const rules = ref({
      hostName: {},
      targeFileName: {},
      fileMove: {},
    });

    const toAdd = () => {
      router.push({ path: "/add" });
    };

    const toQuery = () => {
      stepVisible.value = true;
      axios
        .post("/find/iwgHosts", formDefault.value)
        .then((data) => {
          // ele.forEach((e) => {});
          console.log("資料", data);
        })
        .catch((error) => {
          console.log("catch", error);
        });
      // table.data = [];
      // table.totalItems = 1;
      // table.data.splice(0, table.data.length, ...mockdata);
    };

    const table = reactive({
      fields: [
        {
          //發生日期
          key: "sdate",
          label: "發生日期",
          thClass: "text-center",
          tdClass: "text-center align-middle",
          thStyle: "width:8%",
          // formatter: (value: string) => formatToString(value, '/', '-'),
        },
        {
          //承攬廠商
          key: "prmTitle",
          label: "承攬廠商",
          thClass: "text-center",
          tdClass: "text-left align-middle",
          thStyle: "width:40%",
        },
        {
          //違反之法令
          key: "violateFact",
          label: "違反之法令",
          thClass: "text-center",
          tdClass: "text-left align-middle",
          thStyle: "width:20%",
        },
        {
          key: "action",
          label: "功能",
          thClass: "text-center",
          tdClass: "text-center align-middle",
          thStyle: "width:20%",
        },
      ],
      data: undefined,
      totalItems: 0,
    });

    const mockdata = [
      {
        sdate: "110/03/13",
        prmTitle: "XX營造",
        violateFact: "中華民國刑法第X條",
      },
    ];

    return {
      // types,
      toAdd,
      formDefault,
      stepVisible,
      toQuery,
      table,
      iTable,
    };
  },
};
</script>

<style scoped>
</style>