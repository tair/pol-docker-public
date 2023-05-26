import * as types from "../types_login";
import axios from "axios/index";

const ROLE = ["Admin", "Sales", "Librarian", "User"];

const state = {
  logininfo: {
    isLoading: false,
    isError: false,
    user: {
      username: null,
      accessToken: null,
      firstName: "John",
      lastName: "Smith",
      email: "john@gmail.com",
      role: "User",
      associatedPartyIds: "",
      addedPartyIds: "",
    },
  },
};

const getters = {
  [types.USG_GET_USER]: (state) => {
    // console.log(state.logininfo.user);
    return state.logininfo.user;
  },
  [types.USG_GET_REGINFO]: (state) => {
    return state.registerInfo;
  },
};

const actions = {
  [types.USG_ACTION_LOGIN]: (context, payload) => {
    // console.log({ payload });
    return new Promise((resolve, reject) => {
      axios({
        method: "POST",
        url: "/api/auth/signin",
        data: payload,
        headers: { "Content-Type": "application/json; charset=utf-8" },
      })
        .then((res) => {
          // console.log(res.data.user);
          context.state.logininfo.user = {};
          context.state.logininfo.user.username = res.data.user.username;
          context.state.logininfo.user.accessToken = res.data.accessToken;
          context.state.logininfo.user.firstName = res.data.user.firstName;
          context.state.logininfo.user.lastName = res.data.user.lastName;
          context.state.logininfo.user.role = res.data.user.role;
          context.state.logininfo.user.associatedPartyIds =
            res.data.user.associatedPartyIds;
          context.state.logininfo.user.addedPartyIds =
            res.data.user.addedPartyIds;
          localStorage.setItem(
            "user",
            JSON.stringify(context.state.logininfo.user)
          );
          resolve(res.data);
        })
        .catch((error) => {
          if (error.response) {
            // console.log(error.response.data);
            if (!error.response.data.message) {
              reject("Unknown Error");
            }
            reject(error.response.data.message);
          }
          reject("Unknown Error");
        });
    });
  },
  [types.USG_ACTION_USER_UPDATE]: (context, payload) => {
    // console.log({ payload });
    context.state.logininfo.user.firstName = payload.firstName;
    context.state.logininfo.user.lastName = payload.lastName;
    context.state.logininfo.user.associatedPartyIds =
      payload.associatedPartyIds;
    context.state.logininfo.user.addedPartyIds = payload.addedPartyIds;
    localStorage.setItem("user", JSON.stringify(context.state.logininfo.user));
  },
  [types.USG_ACTION_REGISTER]: (context, payload) => {
    payload.username = payload.email;
    payload.associatedPartyIds = payload.institution;
    return new Promise((resolve, reject) => {
      axios({
        method: "POST",
        url: "/api/auth/signup",
        data: payload,
        headers: { "Content-Type": "application/json; charset=utf-8" },
      })
        .then((res) => {
          resolve(res.data);
        })
        .catch((error) => {
          if (error.response) {
            // console.log(error.response.data);
            reject(error.response.data.message);
          }
          reject("Unknown Error");
        });
    });
  },
  [types.USG_ACTION_ACTIVATE]: (context, payload) => {
    payload.email = payload.email;
    return new Promise((resolve, reject) => {
      axios({
        method: "POST",
        url: "/api/auth/activate",
        data: payload,
        headers: { "Content-Type": "application/json; charset=utf-8" },
      })
        .then((res) => {
          resolve(res.data);
        })
        .catch((error) => {
          if (error.response) {
            // console.log(error.response.data);
            reject(error.response.data.message);
          }
          reject("Unknown Error");
        });
    });
  },
  [types.USG_ACTION_RESET]: (context, payload) => {
    payload.email = payload.email;
    return new Promise((resolve, reject) => {
      axios({
        method: "POST",
        url: "/api/auth/reset",
        data: payload,
        headers: { "Content-Type": "application/json; charset=utf-8" },
      })
        .then((res) => {
          resolve(res.data);
        })
        .catch((error) => {
          if (error.response) {
            // console.log(error.response.data);
            reject(error.response.data.message);
          }
          reject("Unknown Error");
        });
    });
  },
  [types.USG_ACTION_RECOVER]: (context, payload) => {
    payload.email = payload.email;
    return new Promise((resolve, reject) => {
      axios({
        method: "POST",
        url: "/api/auth/recover",
        data: payload,
        headers: { "Content-Type": "application/json; charset=utf-8" },
      })
        .then((res) => {
          resolve(res.data);
        })
        .catch((error) => {
          if (error.response) {
            // console.log(error.response.data);
            reject(error.response.data.message);
          }
          reject("Unknown Error");
        });
    });
  },
  [types.USG_ACTION_LOGOUT]: (context, payload) => {
    // console.log("USG_ACTION_LOGOUT");
    context.state.logininfo.user = {};
  },
  [types.USG_ACTION_SESSION]: (context, payload) => {
    context.state.logininfo.user = {};
    context.state.logininfo.user.username = payload.username;
    context.state.logininfo.user.accessToken = payload.accessToken;
    context.state.logininfo.user.firstName = payload.firstName;
    context.state.logininfo.user.lastName = payload.lastName;
    context.state.logininfo.user.role = payload.role;
    context.state.logininfo.user.associatedPartyIds =
      payload.associatedPartyIds;
    context.state.logininfo.user.addedPartyIds = payload.addedPartyIds;
    // console.log(ROLE);
    localStorage.setItem("user", JSON.stringify(context.state.logininfo.user));
  },
};

export default {
  state,
  getters,
  actions,
};
