import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";

const Home = () => import('@/component/home.vue');
const Login = () => import('@/account/login.vue');
const TestRouter = () => import('@/component/testRouter.vue');
const TestRouter2 = () => import('@/component/testRouter2.vue');
const Log = () => import('@/component/log.vue');

const routes: Array<RouteRecordRaw> = [
  {
    path: "/login",
    name: "login",
    component: Login,
  },
  {
    path: "/",
    name: "home",
    component: Home,
    children: [
      {
        path: "/testRouter",
        name: "testRouter",
        component: TestRouter,
      },
      {
        path: "/testRouter2",
        name: "testRouter2",
        component: TestRouter2,
      },
       {
          path: "/log",
          name: "log",
          component: Log,
       },
    ]
  },

];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
