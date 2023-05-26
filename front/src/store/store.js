import Vue from "vue";
import Vuex from "vuex";

import subscription from "./modules/subscription";
import activation from "./modules/activation";
import usage from "./modules/usage";
import sessions from "./modules/sessions";
import party from "./modules/party";
import partner from "./modules/partner";
import summary from "./modules/summary";
import login from "./modules/login";
import users from "./modules/users";
import iprange from "./modules/iprange";
import schedule from "./modules/schedule"

Vue.use(Vuex);

export const store = new Vuex.Store({
  modules: {
    subscription,
    activation,
    usage,
    users,
    login,
    sessions,
    party,
    partner,
    summary,
    iprange,
    schedule,
  },
});
