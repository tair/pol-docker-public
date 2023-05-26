import * as types from "../types_user";
import axios from "axios/index";
import authHeader from "../../authHeader";

const state = {
  users: {
    isLoading: false,
    isError: false,
    data: [],
    filter: {},
  },
};

const getters = {
  [types.USR_IS_ERROR]: (state) => {
    return state.users.isError;
  },
  [types.USR_IS_LOADING]: (state) => {
    return state.users.isLoading;
  },
  [types.USR_GET_DATA]: (state) => {
    return state.users.data;
  },
  [types.USR_GET_FILTER]: (state) => {
    return state.users.filter;
  },
};

const mutations = {
  [types.USR_MUTATION_CLEAR_FILTER]: (state, payload) => {
    state.users.filter = {};
  },
};

const actions = {
  [types.USR_ACTION_CLEAR_FILTER]: ({ commit }, payload) => {
    commit(types.USR_MUTATION_CLEAR_FILTER, payload);
  },

  [types.USR_ACTION_FILTER]: (context, payload) => {
    context.state.users.filter = payload;
    context.state.users.isLoading = true;
    context.state.users.data = [{}];
  },

  [types.USR_ACTION_REFRESH]: (context) => {
    context.state.users.isLoading = true;
  },
  [types.USR_ACTION_ADD_ASSOC]: (context, payload) => {
    // context.state.users.isLoading = true;
    return new Promise((resolve, reject) => {
      // console.log("Add Association: ", payload);
      return axios({
        method: "POST",
        url: "api/user/update/partyIds/add",
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
  [types.USR_ACTION_RMV_ASSOC]: (context, payload) => {
    // context.state.users.isLoading = true;
    return new Promise((resolve, reject) => {
      // let inpStr = payload.username + "_" + payload.partyId;
      // console.log("Remove Association: ", payload);
      return axios({
        method: "POST",
        url: "api/user/update/partyIds/remove",
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
  [types.USR_ACTION_DELETE]: (context, payload) => {
    return new Promise((resolve, reject) => {
      // console.log("Delete user: ", payload);
      return axios({
        method: "DELETE",
        url: "api/user/delete",
        data: payload.username,
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
  [types.USR_ACTION_GETALL]: (context) => {
    context.state.users.isLoading = true;
    return new Promise((resolve, reject) => {
      return axios({
        method: "GET",
        url: "/api/user/all",
        headers: authHeader(),
      })
        .then((res) => {
          context.state.users.isLoading = true;
          resolve(res.data);
        })
        .catch((error) => {
          context.state.users.isLoading = false;
          context.state.users.isError = true;
          reject(JSON.stringify(error));
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
