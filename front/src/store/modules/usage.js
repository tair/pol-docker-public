import * as types from "../types_usage";
import axios from "axios/index";
import authHeader from "../../authHeader";

const state = {
  tableRef: null,
  manyRecordsAlert: false,
  usage: {
    isLoading: false,
    isError: false,
    data: null,
    filter: {
      dateFrom: null,
      dateTo: null,
      partyId: null,
      partyName: null,
      pageHitMin: 0,
      pageHitMax: 0,
      partyType: "organization", // do not delete this
      partner: "biocyc", // do not delete this
      ipRange: null,
      paidType: [],
    },
  },
};

const getters = {
  [types.USG_IS_ERROR]: (state) => {
    return state.usage.isError;
  },
  [types.USG_IS_LOADING]: (state) => {
    return state.usage.isLoading;
  },
  [types.USG_GET_DATA]: (state) => {
    return state.usage.data;
  },
  [types.USG_GET_FILTER]: (state) => {
    // console.log(state.usage.filter);
    return state.usage.filter;
  },
  [types.USG_GET_MANY_RECORDS_ALERT]: (state) => {
    return state.manyRecordsAlert;
  },
};

const actions = {
  [types.USG_ACTION_MANY_RECORDS_ALERT]: (context, payload) => {
    context.state.manyRecordsAlert = payload;
  },
  [types.USG_ACTION_TABLE_REF]: (context, tableRef) => {
    context.state.tableRef = tableRef;
  },
  [types.USG_ACTION_FILTER]: (context, payload) => {
    console.log({ payload });
    context.state.usage.filter = payload;
    context.state.usage.isLoading = true;
    context.state.usage.isError = false;
    axios({
      method: "POST",
      url: "/pageview_sessions/filter",
      data: context.state.usage.filter,
      headers: authHeader(),
    })
      .then((res) => {
        res.data = res.data.map((col) => {
          // console.log(col);
          col["partnerId"] = payload.partner;
          return col;
        });
        context.state.usage.data = res.data;
        // console.log(payload, res.data);

        if (res.data.length >= 100000) {
          context.state.manyRecordsAlert = true;
        }

        context.state.tableRef.clear().draw();

        context.state.tableRef.rows.add(res.data).draw();

        context.state.usage.isLoading = false;
      })
      .catch((error) => {
        context.state.usage.isLoading = false;
        context.state.usage.isError = true;

        console.log(
          "Error while reading data (E8273): " + JSON.stringify(error)
        );
      });
  },
  [types.USG_TWA_ACTION_FILTER]: (context, payload) => {
    context.state.usage.filter = payload;
    context.state.usage.isLoading = true;
    // console.log({ payload });
    axios({
      method: "POST",
      url: "/turnaways/filter",
      data: context.state.usage.filter,
      headers: { "Content-Type": "application/json; charset=utf-8" },
    })
      .then((res) => {
        console.log({ res });
        let tableCols = [
          {
            data: "ip",
            title: "IP Address",
            type: "string",
            render: null,
          },
        ];
        context.state.tableRef.clear().draw();
        context.state.tableRef.aoColumns = tableCols;
        context.state.usage.data = res.data;
        context.state.tableRef.rows.add(res.data).draw();
        context.state.usage.isLoading = false;
      })
      .catch((error) => {
        context.state.usage.isLoading = false;
        context.state.usage.isError = true;

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
};
