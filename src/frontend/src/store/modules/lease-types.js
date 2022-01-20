import Api from '@/services/api';

import { LOAD_LEASE_TYPES } from '@/store/_actiontypes';
import { SET_LEASE_TYPES } from '@/store/_mutationtypes';

const state = {
  leaseTypes: []
};

const actions = {
  [LOAD_LEASE_TYPES]({ commit }) {
    Api.get('/enums/lease-types')
      .then(response => {
        let leaseTypes = response.data;
        commit(SET_LEASE_TYPES, leaseTypes);
      })
  },
}

const mutations = {
  [SET_LEASE_TYPES](state, leaseTypes) {
    state.leaseTypes = leaseTypes;
  },
}

export const leaseTypes = {
  namespaced: true,
  state,
  actions,
  mutations
};