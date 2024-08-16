import { createStore } from "vuex";
import VuexPersist from "vuex-persist";

const vuexPersist = new VuexPersist({
  key: "my-app",
  storage: window.localStorage,
});

export default createStore({
  state: {
    usuario: null,
  },
  mutations: {
    setUsuario(state, usuario) {
      state.usuario = usuario;
    },
    clearUsuario(state) {
      state.usuario = null;
    },
  },
  actions: {
    setUsuario({ commit }, usuario) {
      commit("setUsuario", usuario);
    },
    clearUsuario({ commit }) {
      commit("clearUsuario");
    },
  },
  getters: {
    getUsuario: (state) => state.usuario,
  },
  plugins: [vuexPersist.plugin],
});
