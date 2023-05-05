<template>
  <div class="text-center">
    <h3 class="text-center pb-5">主機查詢</h3>
    <div class="searchServer">
      <b-form>
        <b-form-group
          class="col-12"
          label-cols="4"
          content-cols="8"
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
    </div>
    <div class="text-center pt-5">
      <b-button class="ml-2" style="background-color: #1aa4b7" @click="toQuery"
        >查詢</b-button
      >
      <b-button class="ml-2" style="background-color: #1aa4b7">清除</b-button>
    </div>
  </div>

  <section class="pt-5" v-if="stepVisible">
    <div>
      <b-table
        striped
        hover
        :items="paginatedItems"
        :fields="table.fields"
        :editable="true"
        :edit-mode="'inline'"
      >
        <template #cell(index)="row">
          {{ row.index + 1 }}
        </template>
        <!-- <template #cell(result)="row">
          {{ row.item.result }}
        </template> -->
        <template #cell(action)="row">
          <b-button
            class="ml-2"
            style="background-color: #1aa4b7"
            @click="toEdit(row.item)"
            >編輯</b-button
          >
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
import router from "@/router";
import axios from "axios";

export default {
  name: "searchServer",
  setup() {
    const stepVisible = ref(false);
    const page = ref(1); //當前頁面
    const perPage = ref(20); //每頁顯示的資料
    const edit = ref(false);

    // onMounted(() => {
    //   toQuery();
    // });

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
          key: "active",
          label: "是否啟用",
          sortable: false,
          thStyle: "width:20%",
          thClass: "text-center",
          tdClass: "text-center align-middle",
        },
        {
          key: "action",
          label: "功能",
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
        .post("/find/iwgHosts", formDefault.value)
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
    const toEdit = (ele: any) => {
      console.log(ele);
      edit.value = true;
      router.push({ path: "/editServer", query: { id: ele.hostname } });
    };

    return {
      // types,
      toAdd,
      formDefault,
      stepVisible,
      toQuery,
      toEdit,
      table,
      paginatedItems,
      totalPages,
    };
  },
};
</script>

<style scoped>
.searchServer {
  max-width: 700px;
  margin-left: auto;
  margin-right: auto;
}
.ml-2 {
  margin-right: 20px;
}
</style>