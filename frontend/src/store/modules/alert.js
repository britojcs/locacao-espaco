import { ADD_ALERT } from '@/store/_actiontypes';
import { CREATE_ALERT } from '@/store/_mutationtypes';

const state = {
  alert: {}
};

const actions = {
  [ADD_ALERT]({ commit }, { color, message }) {
    alert.show = true;
    alert.color = color;
    alert.message = message;
    commit(CREATE_ALERT, alert);
  }
};

const mutations = {
  [CREATE_ALERT](state, alert) {
    state.alert = alert;
  }
};

export const alert = {
  namespaced: true,
  state,
  actions,
  mutations
};