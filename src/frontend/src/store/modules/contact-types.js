import Api from '@/services/api';

import { LOAD_CONTACT_TYPES } from '@/store/_actiontypes';
import { SET_CONTACT_TYPES } from '@/store/_mutationtypes';

const state = {
  contactTypes: []
};

const actions = {
  [LOAD_CONTACT_TYPES]({ commit }) {
    Api.get('/enums/contact-types')
      .then(response => {
        let contactTypes = response.data;
        commit(SET_CONTACT_TYPES, contactTypes);
      })
  },
}

const mutations = {
  [SET_CONTACT_TYPES](state, contactTypes) {
    state.contactTypes = contactTypes;
  },
}

export const contactTypes = {
  namespaced: true,
  state,
  actions,
  mutations
};