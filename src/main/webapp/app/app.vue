<template>
  <div>
    <home></home>
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