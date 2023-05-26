import * as types from '../types_sessions';
import axios from "axios/index";

const state = {
  tableRef: null,
  manyRecordsAlert: false,
  sessions: {
    isLoading: false,
    isError: false,
    data: null,
    filter: {
      dateFrom: null,
      dateTo: null,
      partyId: null,
      pageHitMin: 0,
      pageHitMax: 0,
      partyType: 'organization',  // do not delete this
      partner: 'biocyc',  // do not delete this
      ipRange: null
    }
  }
};

const getters = {
  [types.SESS_IS_ERROR]: state => {
    return state.sessions.isError;
  },
  [types.SESS_IS_LOADING]: state => {
    return state.sessions.isLoading;
  },
  [types.SESS_GET_DATA]: state => {
    return state.sessions.data;
  },
  [types.SESS_GET_FILTER]: state => {
    return state.sessions.filter;
  },
  [types.SESS_GET_MANY_RECORDS_ALERT]: state => {
    return state.manyRecordsAlert;
  }
};

const actions = {

  [types.SESS_ACTION_MANY_RECORDS_ALERT]: (context, payload) => {
    context.state.manyRecordsAlert = payload;
  },
  [types.SESS_ACTION_TABLE_REF]: (context, tableRef) => {
    context.state.tableRef = tableRef;
  },
  [types.SESS_ACTION_FILTER]: (context, payload) => {
    context.state.sessions.filter = payload;
    context.state.sessions.isLoading = true;

    axios({
      method: 'POST',
      url: '/sessions/filter',
      data: context.state.sessions.filter,
      headers: { 'Content-Type': 'application/json; charset=utf-8' }
    })
      .then(res => {
        context.state.sessions.data = res.data;

        if (res.data.length >= 100000) {
          context.state.manyRecordsAlert = true;
        }

        context.state.tableRef.clear().draw();
        context.state.tableRef.rows.add(res.data).draw();

        context.state.sessions.isLoading = false;
      })
      .catch(error => {
        context.state.sessions.isLoading = false;
        context.state.sessions.isError = true;

        console.log('Error while reading data: ' + JSON.stringify(error));
      })
  }
};

export default {
  state,
  getters,
  actions,
}
