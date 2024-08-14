import { createApp } from "vue";
import "./style.css";
import App from "./App.vue";
import router from "./Router/Router.js";
import store from "./Store/Store.js";

createApp(App).use(router).use(store).mount("#app");
