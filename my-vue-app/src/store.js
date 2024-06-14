import { createStore } from 'vuex';

const store = createStore({
    state: {
        responseData: null,
        userData: null
    },
    mutations: {
        setResponseData(state, data) {
            state.responseData = data;
        },
        setUserData(state,data){
            state.userData = data
        }
    },
    actions: {
        updateResponseData({ commit }, data) {
            commit('setResponseData', data);
        },
        updateUserData({ commit }, data) {
            commit('setUserData', data);
        },
    },
    getters: {
        getResponseData: (state) => state.responseData,
        getUserData: (state) => state.userData
    },
});

export default store;