import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";

const Home = () => import('@/component/home.vue');
const TestRouter = () => import('@/component/testRouter.vue');
const Log = () => import('@/component/log.vue');

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
  {
    path: "/log",
    name: "log",
    component: Log,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
