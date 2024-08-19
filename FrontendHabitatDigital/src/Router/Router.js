import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "HomePage",
      component: () => import("../home/views/Home.vue"),
    },
    {
      path: "/user",
      name: "UserInfo",
      component: () => import("../account/views/AccountInfo.vue"),
    },
    {
      path: "/property",
      name: "PropertyInfo",
      component: () => import("../property/views/PropertyInfo.vue"),
    }
  ],
  linkActiveClass: "active-link",
});

export default router;
