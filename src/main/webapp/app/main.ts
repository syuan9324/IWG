import { createApp } from "vue";
import App from "./app.vue";
import router from "./router";
import store from "./store";
import { setupAxiosInterceptors, initAxios } from "@/config/axios-intercepter";



initAxios();
setupAxiosInterceptors(
  //處理401 error,onUnauthenticatedError
  (error: any) => {
    const url = error.response?.config?.url;
    const status = error.status || error.response.status;
    console.log("Unauthorized!");
    return Promise.reject(error);
  },
  //處理500  error,onServerError 500
  (error: any) => {
    console.log("Server error!");
    return Promise.reject(error);
  }
);

createApp(App).use(store).use(router).mount("#app");
