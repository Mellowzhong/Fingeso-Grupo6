import { createStore } from "vuex";
import VuexPersist from "vuex-persist";

const vuexPersist = new VuexPersist({
  key: "my-app",
  storage: window.localStorage,
});

export default createStore({
  state: {
    usuario: null,
    property: null,
  },
  mutations: {
    setUsuario(state, usuario) {
      state.usuario = usuario;
    },
    clearUsuario(state) {
      state.usuario = null;
    },
    setProperty(state, property) {
      state.property = property;
    },
    clearProperty(state) {
      state.property = null;
    },
  },
  actions: {
    setUsuario({ commit }, usuario) {
      commit("setUsuario", usuario);
    },
    clearUsuario({ commit }) {
      commit("clearUsuario");
    },
    logoutUser({ commit }) {
      commit('clearUsuario');
    },
    selectProperty({ commit }, property) {
      commit("setProperty", property);
    },
    clearProperty({ commit }) {
      commit("clearProperty");
    },
  },
  getters: {
    getUsuario: (state) => state.usuario,
    getProperty: (state) => state.property,
  },
  plugins: [vuexPersist.plugin],
});
