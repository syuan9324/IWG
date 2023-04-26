<template>
  <div>
    <h1>test router</h1>
    <h1>home</h1>
    <button @click="testToken()">testToken</button>
    <div>
      <b-table striped hover :items="items"></b-table>
    </div>
    <BButton @click.stop="showModal = !showModal">Open Modal</BButton>
    {{ showModal }}
    <b-modal id='aaa' v-model="showModal">
      <p>This is a test modal.</p>
    </b-modal>
    <i class="bi bi-clipboard"></i>
  </div>
</template>
<script lang="ts">
import { ref, getCurrentInstance } from "vue";
import axios from "axios";
import { BModal } from "bootstrap-vue-next";
export default {
  name: "testRouter",
  components: {
    BModal,
  },
  setup() {
    const vue = getCurrentInstance();   
    // globals2.show('aaa');
    // const notificationService: NotificationService  = inject<NotificationService>('notificationService')!;
    // notificationService.info('test123');
    const items = ref([
      { age: 40, first_name: "Dickerson", last_name: "Macdonald" },
      { age: 21, first_name: "Larsen", last_name: "Shaw" },
      { age: 89, first_name: "Geneva", last_name: "Wilson" },
      { age: 38, first_name: "Jami", last_name: "Carney" },
    ]);
    const result = ref("");
    const showModal = ref(false);
    const testToken: any = () => {
      axios
        .get("testResource")
        .then((response: any) => {
          console.log(response);
          result.value = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    };
    return {
      testToken,
      items,
      showModal,
    };
  },
};
</script>