import Vue from "vue";
import App from "./App.vue";
import VueRouter from "vue-router";
import { routes } from "./routes";
import axios from "axios";
import Vue2Filters from "vue2-filters";
import ReadOnlyField from "./components/common/ReadOnlyField";
import DetailHeader from "./components/common/DetailHeader";
import UnderConstruction from "./components/common/UnderConstruction";
import TableEx from "./components/common/TableEx";
import NoDataMessage from "./components/common/NoDataMessage";
import { BootstrapVue, IconsPlugin } from "bootstrap-vue";
import VueCookies from "vue-cookies";
import { store } from "./store/store";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
import "babel-polyfill";

axios.defaults.baseURL = process.env.VUE_APP_SERVER_URL;

Vue.use(VueRouter);
Vue.use(Vue2Filters);
// Install BootstrapVue
Vue.use(BootstrapVue);
// Optionally install the BootstrapVue icon components plugin
Vue.use(IconsPlugin);
Vue.use(VueCookies);

Vue.component("app-readonly-field", ReadOnlyField);
Vue.component("app-detail-header", DetailHeader);
Vue.component("app-under-construction", UnderConstruction);
Vue.component("app-table-ex", TableEx);
Vue.component("app-no-data", NoDataMessage);

const router = new VueRouter({
  mode: "history",
  routes,
});

new Vue({
  el: "#app",
  store,
  router,
  render: (h) => h(App),
});
