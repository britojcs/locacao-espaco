import Api from '@/services/api';

import { LOAD_ROLES } from '@/store/_actiontypes';
import { SET_ROLES } from '@/store/_mutationtypes';

const state = {
  roles: []
};

const actions = {
  [LOAD_ROLES]({ commit }) {
    Api.get('/enums/roles')
      .then(response => {
        let roles = response.data;
        commit(SET_ROLES, roles);
      })
  },
}

const mutations = {
  [SET_ROLES](state, roles) {
    state.roles = roles;
  },
}

export const roles = {
  namespaced: true,
  state,
  actions,
  mutations
};