import Api from '@/services/api';

import { LOAD_ADDITIONAL_REQUIRED_TYPES } from '@/store/_actiontypes';
import { SET_ADDITIONAL_REQUIRED_TYPES } from '@/store/_mutationtypes';

const state = {
  additionalRequiredTypes: []
};

const actions = {
  [LOAD_ADDITIONAL_REQUIRED_TYPES]({ commit }) {
    Api.get('/enums/additional-required-types')
      .then(response => {
        let additionalRequiredTypes = response.data;
        commit(SET_ADDITIONAL_REQUIRED_TYPES, additionalRequiredTypes);
      })
  },
}

const mutations = {
  [SET_ADDITIONAL_REQUIRED_TYPES](state, additionalRequiredTypes) {
    state.additionalRequiredTypes = additionalRequiredTypes;
  },
}

export const additionalRequiredTypes = {
  namespaced: true,
  state,
  actions,
  mutations
};