import * as types from '../types_subscription';
import axios from "axios/index";
import authHeader from '../../authHeader';

const state = {
    subscription: {
        isLoading: false,
        isError: false,
        data: null,
        transactions: null,
        filter: {
            endDate_From: '',
            endDate_To: '',
            startDate_From: '',
            startDate_To: '',
            partyEmail: '',
            partyName: '',
            partyType: [],
            partner: [],
            transactionType: []
        }
    }
};

const getters = {
    [types.SUB_IS_ERROR]: state => {
        return state.subscription.isError;
    },
    [types.SUB_IS_LOADING]: state => {
        return state.subscription.isLoading;
    },
    [types.SUB_GET_DATA]: state => {
        //console.log(state.subscription.data);
        return state.subscription.data;
    },
    [types.SUB_GET_TRANSACTIONS]: state => {
        //console.log(state.subscription.transactions);
        return state.subscription.transactions;
    },
    [types.SUB_GET_FILTER]: state => {
        return state.subscription.filter;
    }
};

const mutations = {

    [types.SUB_MUTATION_CLEAR_FILTER]: (state, payload) => {
        state.subscription.filter.endDate_From = '';
        state.subscription.filter.endDate_To = '';
        state.subscription.filter.startDate_From = '';
        state.subscription.filter.startDate_To = '';
        state.subscription.filter.partyEmail = '';
        state.subscription.filter.partyName = '';
        state.subscription.filter.partyType = [];
        state.subscription.filter.partner = [];
    },

    [types.SUB_MUTATION_FILTER_EMAIL]: (state, email) => {
        state.subscription.filter.partyEmail = email;
    },

    [types.SUB_MUTATION_CLEAR_TRANSACTIONS]: (state, payload) => {
        state.subscription.transactions = null;
    }
};

const actions = {

    // [types.SUB_ACTION_DATA]: ({commit}, payload) => {
    //   commit('subscriptionData', payload);
    // },

    [types.SUB_ACTION_FILTER_EMAIL]: ({commit}, payload) => {
        commit(types.SUB_MUTATION_CLEAR_FILTER, payload);
        commit(types.SUB_MUTATION_FILTER_EMAIL, payload);
    },

    [types.SUB_ACTION_CLEAR_FILTER]: ({commit}, payload) => {
        commit(types.SUB_MUTATION_CLEAR_FILTER, payload);
    },

    [types.SUB_ACTION_CLEAR_TRANSACTIONS]: ({commit}, payload) => {
        commit(types.SUB_MUTATION_CLEAR_TRANSACTIONS, payload);
    },

    [types.SUB_ACTION_FILTER]: (context, payload) => {
        let headers = authHeader();
        context.state.subscription.filter = payload;
        context.state.subscription.isLoading = true;

        axios({
            method: 'POST',
            url: '/subscription/filter',
            data: context.state.subscription.filter,
            headers: {
                ...headers,
                "Content-Type": "application/json"
              },
        })
            .then(res => {
                context.state.subscription.data = res.data;
                context.state.subscription.isLoading = false;
            })
            .catch(error => {
                context.state.subscription.isLoading = false;
                context.state.subscription.isError = true;

                console.log('Error while reading data (E8273): ' + JSON.stringify(error));
            })
    },

    [types.SUB_ACTION_GET_TRANSACTIONS]: (context, data) => {

        //context.state.subscription.filter = payload;
        context.state.subscription.isLoading = true;
        axios({
            method: 'GET',
            baseURL: 'https://h3adb6tcod.execute-api.us-west-2.amazonaws.com',
            url: '/subscription/transactions',
            params: {"partyId": data},
            headers: {"Content-Type": "application/json; charset=utf-8"}
        })
            .then(res => {
                context.state.subscription.transactions = res.data;
                context.state.subscription.isLoading = false;
            })
            .catch(error => {
                context.state.subscription.isLoading = false;
                context.state.subscription.isError = true;

                console.log('Error while reading data (E8273): ' + JSON.stringify(error));
            })
    }
};

export default {
    state,
    getters,
    actions,
    mutations
}
