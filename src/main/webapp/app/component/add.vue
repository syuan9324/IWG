<template>
  <div>
    <h1 class="text-center">新增批次服務服務</h1>
    <b-container fluid>
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
    </b-container>

    <!-- <b-form-row> -->
    <!-- <i-form-group-check :label="$t('label.batchServiceCategory') + '：'" :item="$v.batchServiceCategory"> -->
    <!-- <b-form-input></b-form-input> -->
    <!-- </i-form-group-check> -->
    <!-- <i-form-group-check :label="$t('label.batchServiceNo') + '：'" :item="$v.batchServiceNo"> -->
    <!-- <b-form-select :options="queryOptions.ServiceNoOpt" v-model="$v.batchServiceNo.$model"></b-form-select> -->
    <!-- </i-form-group-check> -->
    <!-- </b-form-row> -->

    <b-button-toolbar class="text-center">
      <b-button class="mr-1" type="search">儲存</b-button>
      <b-button class="mr-1" type="x-circle">清除</b-button>
      <b-button class="mr-1" type="file-earmark-plus">
        <router-link class="list-group-item" active-class="active" to="/log"
          >返回</router-link
        >
      </b-button>
    </b-button-toolbar>
  </div>
</template>

<script lang="ts">
import { ref, computed, reactive, onMounted } from "vue";
import { BButton, BFormInput, BButtonToolbar } from "bootstrap-vue-3";

// import appHeader from "./header/app-header.vue";

export default {
  name: "log",
  components: { BButton, BFormInput, BButtonToolbar },
  setup() {
    const types = ["password", "userName", "hostName", "port", "fargeFileName"];

    return {
      types,
    };
  },
};
</script>

<style scoped>
</style>