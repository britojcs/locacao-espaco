import Api from '@/services/api';

import { LOAD_STATES } from '@/store/_actiontypes';
import { SET_STATES } from '@/store/_mutationtypes';

const state = {
  states: []
};

const actions = {
  [LOAD_STATES]({ commit }) {
    Api.get('/enums/states')
      .then(response => {
        let states = response.data;
        commit(SET_STATES, states);
      })
  },
}

const mutations = {
  [SET_STATES](state, states) {
    state.states = states;
  },
}

export const states = {
  namespaced: true,
  state,
  actions,
  mutations
};