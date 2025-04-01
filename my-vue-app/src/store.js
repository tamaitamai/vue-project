import { createStore } from 'vuex';

const store = createStore({
    state: {
        responseData: null,
        userData: null,
        itemListData: null
    },
    mutations: {
        setResponseData(state, data) {
            state.responseData = data;
        },
        setUserData(state,data){
            state.userData = data
        },
        setItemListData(state,data){
            state.itemListData = data;
        }
    },
    actions: {
        updateResponseData({ commit }, data) {
            commit('setResponseData', data);
        },
        updateUserData({ commit }, data) {
            commit('setUserData', data);
        },
        updateItemListData({ commit },data){
            commit('setItemListData',data);
        }
    },
    getters: {
        getResponseData: (state) => state.responseData,
        getUserData: (state) => state.userData,
        getItemListData: (state) => state.itemListData
    },
});

export default store;