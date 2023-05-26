import * as types from "../types_party";
import axios from "axios/index";
import authHeader from "../../authHeader";

const state = {
  party: {
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
  [types.PRT_IS_ERROR]: (state) => {
    return state.party.isError;
  },
  [types.PRT_IS_LOADING]: (state) => {
    return state.party.isLoading;
  },
  [types.PRT_GET_DATA]: (state) => {
    return state.party.data;
  },
  [types.PRT_GET_FILTER]: (state) => {
    return state.party.filter;
  },
};

const mutations = {
  [types.PRT_MUTATION_CLEAR_FILTER]: (state, payload) => {
    state.party.filter.partyTypes = [];
    state.party.filter.partyName = "";
    state.party.filter.username = "";
    state.party.filter.email = "";
    state.party.filter.institution = "";
    state.party.filter.partners = [];
    state.party.filter.userIdentifier = "";
    state.party.filter.firstName = "";
    state.party.filter.lastName = "";
  },

  [types.PRT_MUTATION_FILTER_EMAIL]: (state, email) => {
    state.party.filter.partyEmail = email;
  },
};

const actions = {
  [types.PRT_ACTION_FILTER_EMAIL]: ({ commit }, payload) => {
    commit(types.PRT_MUTATION_CLEAR_FILTER, payload);
    commit(types.PRT_MUTATION_FILTER_EMAIL, payload);
  },

  [types.PRT_ACTION_CLEAR_FILTER]: ({ commit }, payload) => {
    commit(types.PRT_MUTATION_CLEAR_FILTER, payload);
  },

  [types.PRT_ACTION_GET_ORGS]: (context, payload) => {
    return new Promise((resolve, reject) => {
      let headers = authHeader();
      // console.log({ headers });
      axios({
        method: "GET",
        url: "/party/orgs",
        headers: headers,
      })
        .then((res) => {
          resolve(res.data);
        })
        .catch((error) => {
          reject(error);
        });
    });
  },
  [types.PRT_ACTION_FILTER]: (context, payload) => {
    context.state.party.filter = payload;
    context.state.party.isLoading = true;
    let headers = authHeader();
    // console.log({ headers });
    axios({
      method: "POST",
      url: "/party/filter",
      data: context.state.party.filter,
      headers: headers,
    })
      .then((res) => {
        let finalData = res.data;
        console.log({ finalData });
        finalData = finalData.filter((party) => {
          return party.name != "";
        });
        context.state.party.data = finalData;
        context.state.party.isLoading = false;
      })
      .catch((error) => {
        context.state.party.isLoading = false;
        context.state.party.isError = true;

        console.log(
          "Error while reading data (E8273): " + JSON.stringify(error)
        );
      });
  },
};

export default {
  state,
  getters,
  actions,
  mutations,
};
