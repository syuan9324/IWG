import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";

const Home = () => import('@/component/home.vue');
const TestRouter = () => import('@/component/testRouter.vue');

const routes: Array<RouteRecordRaw> = [
  {
    path: "/home",
    name: "home",
    component: Home,
  },
  {
    path: "/testRouter",
    name: "testRouter",
    component: TestRouter,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
