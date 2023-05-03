import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";

const Home = () => import('@/component/home.vue');
const Login = () => import('@/account/login.vue');
const TestRouter = () => import('@/component/testRouter.vue');
const TestRouter2 = () => import('@/component/testRouter2.vue');
const searchLog = () => import('@/component/search-log.vue');
const AddServer = () => import('@/component/add-server.vue');

const routes: Array<RouteRecordRaw> = [
  {
    path: "/login",
    name: "login",
    component: Login,
    props: true,
  },
  {
    path: "/",
    name: "home",
    component: Home,
    props : true,
    children: [
      {
        path: "/testRouter",
        name: "testRouter",
        component: TestRouter,
        props : true,
      },
      {
        path: "/testRouter2",
        name: "testRouter2",
        component: TestRouter2,
        props: true,
      },
       {
          path: "/searchLog",
          name: "searchLog",
          component: searchLog,
          props: true,
       },
       {
          path: "/addServer",
          name: "addServer",
          component: AddServer,
          props: true,
        },
    ]
  },
  // {
  //   path: "/log",
  //   name: "log",
  //   component: Log,
  // },
  // {
  //   path: "/add",
  //   name: "add",
  //   component: Add,
  // },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
