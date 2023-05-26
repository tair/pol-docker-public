import * as types from "../types_partner";
import axios from "axios/index";
import authHeader from "../../authHeader";

const state = {
  partner: {
    isLoading: false,
    isError: false,
    data: [],
  },
};

const getters = {
  [types.PRTR_IS_ERROR]: (state) => {
    return state.partner.isError;
  },
  [types.PRTR_IS_LOADING]: (state) => {
    return state.partner.isLoading;
  },
  [types.PRTR_GET_DATA]: (state) => {
    return state.partner.data;
  },
};

const mutations = {};

const actions = {
  [types.PRTR_ACTION_GET_DATA]: (context) => {
    context.state.partner.isLoading = true;
    let headers = authHeader();
    axios({
      method: "GET",
      url: "/partner",
      headers: headers,
    })
      .then((res) => {
        context.state.partner.data = res.data;
        context.state.partner.isLoading = false;
      })
      .catch((error) => {
        context.state.partner.isLoading = false;
        context.state.partner.isError = true;

        console.log("Error while reading data (E8273): " + error);
      });
  },
};

export default {
  state,
  getters,
  actions,
  mutations,
};
