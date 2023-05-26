import * as types from "../types_activation";
import qs from "qs";
import axios from "axios/index";
import authHeader from "../../authHeader";

const secretKey = "%2FqN3fOKu1af3YUHzjZ0096SENYk%3D";
const credentialId = 33197;
const baseUrl = "https://pwapi-tair.arabidopsis.org";

const state = {
  activation: {
    isLoading: false,
    isError: false,
    data: null,
    filter: {
      purchaseDate_From: "",
      code: "",
      purchaseDate_To: "",
      partyEmail: "",
      partyName: "",
      partyType: [],
      partner: [],
      transactionType: [],
    },
    activationId: -1,
  },
};

const getters = {
  [types.ACT_IS_ERROR]: (state) => {
    return state.activation.isError;
  },
  [types.ACT_IS_LOADING]: (state) => {
    return state.activation.isLoading;
  },
  [types.ACT_GET_DATA]: (state) => {
    return state.activation.data;
  },
  [types.ACT_GET_FILTER]: (state) => {
    return state.activation.filter;
  },
};

const mutations = {
  [types.ACT_MUTATION_CLEAR_FILTER]: (state, payload) => {
    state.activation.filter.purchaseDate_From = "";
    state.activation.filter.code = "";
    state.activation.filter.purchaseDate_To = "";
    state.activation.filter.partyEmail = "";
    state.activation.filter.partyName = "";
    state.activation.filter.partyType = [];
    state.activation.filter.partner = [];
    state.activation.filter.transactionType = [];
  },

  [types.ACT_MUTATION_FILTER_EMAIL]: (state, email) => {
    state.activation.filter.partyEmail = email;
  },
};

const actions = {
  // [types.ACT_ACTION_DATA]: ({commit}, payload) => {
  //   commit('activationData', payload);
  // },

  [types.ACT_ACTION_FILTER_EMAIL]: ({ commit }, payload) => {
    commit(types.ACT_MUTATION_CLEAR_FILTER, payload);
    commit(types.ACT_MUTATION_FILTER_EMAIL, payload);
  },

  [types.ACT_ACTION_CLEAR_FILTER]: ({ commit }, payload) => {
    commit(types.ACT_MUTATION_CLEAR_FILTER, payload);
  },

  [types.ACT_ACTION_FILTER]: (context, payload) => {
    context.state.activation.filter = payload;
    context.state.activation.isLoading = true;

    axios({
      method: "POST",
      url: "/activationCode/filter",
      data: context.state.activation.filter,
      headers: authHeader(),
    })
      .then((res) => {
        context.state.activation.data = res.data;
        context.state.activation.isLoading = false;
      })
      .catch((error) => {
        context.state.activation.isLoading = false;
        context.state.activation.isError = true;

        console.log(
          "Error while reading data (E8273): " + JSON.stringify(error)
        );
      });
  },

  [types.ACT_ACTION_REFRESH]: (context) => {
    context.state.activation.isLoading = true;

    axios({
      method: "POST",
      url: "/activationCode/filter",
      data: context.state.activation.filter,
      headers: authHeader(),
    })
      .then((res) => {
        context.state.activation.data = res.data;
        context.state.activation.isLoading = false;
      })
      .catch((error) => {
        context.state.activation.isLoading = false;
        context.state.activation.isError = true;

        console.log(
          "Error while reading data (E8273): " + JSON.stringify(error)
        );
      });
  },

  [types.ACT_ACTION_CREATE]: (context, payload) => {
    // console.log({ payload });
    return new Promise((resolve, reject) => {
      axios({
        method: "POST",
        url: "/activationCode/create",
        data: payload,
        headers: authHeader(),
      })
        .then((res) => {
          resolve(res);
        })
        .catch((error) => {
          reject(error);
        });
    });
  },
  [types.ACT_ACTION_DELETE_CODE]: (context, activationId) => {
    // console.log({ activationId });
    return new Promise((resolve, reject) => {
      //context.state.subscription.filter = payload;
      context.state.activation.isLoading = true;
      axios({
        method: "POST",
        url: "/activationCode/delete",
        data: { activationCodeId: activationId },
        headers: authHeader(),
      })
        .then((res) => {
          // console.log(res.data);
          // context.state.activation.data = res.data;
          context.state.activation.isLoading = false;
          resolve(res.data);
        })
        .catch((error) => {
          context.state.activation.isLoading = false;
          context.state.activation.isError = true;

          if (!error.response.data.message) {
            reject("Unknown Error");
          }
          reject(error.response.data.message);
        });
    });
  },
};

export default {
  state,
  getters,
  actions,
  mutations,
};
