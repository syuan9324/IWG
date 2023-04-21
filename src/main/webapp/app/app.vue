<template>
  <div>
    <div>
      <router-link class="list-group-item" active-class="active" to="/log"
        >紀錄查詢</router-link
      >
    </div>
    <div>
      <router-link class="list-group-item" active-class="active" to="/home"
        >home</router-link
      >
    </div>
    <div>
      <router-view></router-view>
    </div>
  </div>
</template>

<script lang="ts">
import Home from "@/component/home.vue";
export default {
  name: "app",
  components: { Home },
  setup() {
    const testFunction = () => {
      console.log("測試用");
    };
    function testTsAdd(a: number, b: number): number {
      return a + b;
    }

    testFunction();
    console.log(testTsAdd(1, 2));
  },
};
</script>

<style scoped>
.text-red {
  color: red;
}
</style>