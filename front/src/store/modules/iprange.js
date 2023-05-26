import * as types from "../types_iprange";
import axios from "axios/index";
import authHeader from "../../authHeader";

const state = {
  iprange: {
    isLoading: false,
    isError: false,
    data: "",
    filter: {
      partyName: "",
      username: "",
      email: "",
      institution: "",
      partners: [],
      partyTypes: [],
      userIdentifier: "",
      firstName: "",
      lastName: "",
    },
  },
};

const getters = {
  [types.IPR_IS_ERROR]: (state) => {
    return state.iprange.isError;
  },
  [types.IPR_IS_LOADING]: (state) => {
    return state.iprange.isLoading;
  },
  [types.IPR_GET_DATA]: (state) => {
    return state.iprange.data;
  },
  [types.IPR_GET_FILTER]: (state) => {
    return state.iprange.filter;
  },
};

const mutations = {
  // [types.IPR_MUTATION_CLEAR_FILTER]: (state, payload) => {
  //   state.iprange.filter.partyTypes = [];
  //   state.iprange.filter.partyName = "";
  //   state.iprange.filter.username = "";
  //   state.iprange.filter.email = "";
  //   state.iprange.filter.institution = "";
  //   state.iprange.filter.partners = [];
  //   state.iprange.filter.userIdentifier = "";
  //   state.iprange.filter.firstName = "";
  //   state.iprange.filter.lastName = "";
  // },
  // [types.IPR_MUTATION_FILTER_EMAIL]: (state, email) => {
  //   state.iprange.filter.partyEmail = email;
  // },
};

const actions = {
  // [types.IPR_ACTION_FILTER_EMAIL]: ({ commit }, payload) => {
  //   commit(types.IPR_MUTATION_CLEAR_FILTER, payload);
  //   commit(types.IPR_MUTATION_FILTER_EMAIL, payload);
  // },

  // [types.IPR_ACTION_CLEAR_FILTER]: ({ commit }, payload) => {
  //   commit(types.IPR_MUTATION_CLEAR_FILTER, payload);
  // },

  [types.IPR_ACTION_FILTER]: (context, payload) => {
    context.state.iprange.filter = payload;
    context.state.iprange.isLoading = true;
    let headers = authHeader();
    axios({
      method: "POST",
      url: "/iprange/filter",
      data: context.state.iprange.filter,
      headers: headers,
    })
      .then((res) => {
        let finalData = res.data;
        finalData = finalData.filter((party) => {
          return party.name != "";
        });
        context.state.iprange.data = finalData;
        context.state.iprange.isLoading = false;
        context.state.iprange.isError = false;
      })
      .catch((error) => {
        context.state.iprange.isLoading = false;
        context.state.iprange.isError = true;
      });
  },
};

export default {
  state,
  getters,
  actions,
  mutations,
};
