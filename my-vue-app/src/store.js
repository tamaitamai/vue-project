import { createStore } from 'vuex';

const store = createStore({
  state: {
    responseData: null,
  },
  mutations: {
    setResponseData(state, data) {
      state.responseData = data;
    },
  },
  actions: {
    updateResponseData({ commit }, data) {
      commit('setResponseData', data);
    },
  },
  getters: {
    getResponseData: (state) => state.responseData,
  },
});

export default store;