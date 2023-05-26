import * as types from '../types_summary';
import axios from "axios/index";

const state = {
    tableRef: null,
    manyRecordsAlert: false,
    summary: {
        isLoading: false,
        isError: false,
        data: null,
        filter: {
            type: 'sess_count',
            partner: 'tair',  // do not delete this
            year: '2022'
        }
    }
};

const getters = {
    [types.SUMM_IS_ERROR]: state => {
        return state.summary.isError;
    },
    [types.SUMM_IS_LOADING]: state => {
        return state.summary.isLoading;
    },
    [types.SUMM_GET_DATA]: state => {
        return state.summary.data;
    },
    [types.SUMM_GET_FILTER]: state => {
        return state.summary.filter;
    },
    [types.SUMM_GET_MANY_RECORDS_ALERT]: state => {
        return state.manyRecordsAlert;
    }
};

const actions = {
    [types.SUMM_ACTION_MANY_RECORDS_ALERT]: (context, payload) => {
        context.state.manyRecordsAlert = payload;
    },
    [types.SUMM_ACTION_TABLE_REF]: (context, tableRef) => {
        context.state.tableRef = tableRef;
    },
    [types.SUMM_ACTION_FILTER]: (context, payload) => {
        context.state.summary.filter = payload;
        context.state.summary.isLoading = true;

        axios({
            method: 'POST',
            url: '/summary/filter',
            data: context.state.summary.filter,
            headers: { 'Content-Type': 'application/json; charset=utf-8' }
        })
            .then(res => {
                // console.log(res.data);
                context.state.summary.data = res.data;

                if (res.data.length >= 100000) {
                    context.state.manyRecordsAlert = true;
                }

                // context.state.tableRef.clear().draw();
                // context.state.tableRef.rows.add(res.data).draw();

                context.state.summary.isLoading = false;
            })
            .catch(error => {
                context.state.summary.isLoading = false;
                context.state.summary.isError = true;

                console.log('Error while reading data: ' + JSON.stringify(error));
            })
    }
};

export default {
    state,
    getters,
    actions,
}
