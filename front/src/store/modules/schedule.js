// schedule.js
import * as types from "../types_schedule";
import axios from "axios";
import authHeader from "../../authHeader";

const state = {
    // schedule: {
    //   email: null,
    //   schedulePeriod: null,
    //   partyType: null,
    //   selectedParty: null,
    //   ipRange: null,
    // },
    schedules: {
        isLoading: false,
        isError: false,
        data: {},
    }
  };

const getters = {
    [types.SCH_GET_DATA]: (state) => {
        return state.schedules.data;
      },
};
  

const actions = {
  [types.SCH_ACTION_SUBMIT_SCHEDULE]: (context, payload) => {
    return new Promise((resolve, reject) => {
        let headers = authHeader();
        // console.log("Headers:", headers);
        axios({
        method: "POST",
        url: "/schedule/addschedule", // Update the URL to your API endpoint
        data: payload,
        headers: {
            ...headers,
            "Content-Type": "application/json"
          },
      })
        .then((res) => {
          // Update the state or handle the response if necessary
          // console.log(res);
          resolve(res.data);
        })
        .catch((error) => {
          if (error.response) {
            reject(error.response.data.message);
          }
          reject("Unknown Error");
        });
    });
  },
  [types.SCH_SET_SCHEDULE]: (context, payload) => {
    return new Promise((resolve, reject) => {
        axios({
        method: "GET",
        url: "/schedule/getschedules",
        headers: authHeader(),
        })
        .then((res) => {
            // console.log(res.data);
            context.state.schedules.data = res.data;
            resolve("Success");
        })
        .catch((error) => {
            context.state.schedules.isLoading = false;
            context.state.schedules.isError = true;
            reject("Unknown Error");
        });
    });
  },
};

export default {
  state,
  getters,
  actions,
};
