import Api from '@/services/api';

import { LOAD_ADDITIONALS, CREATE_ADDITIONAL, EDIT_ADDITIONAL, REMOVE_ADDITIONAL, ADD_ALERT } from '@/store/_actiontypes';
import { SET_ADDITIONALS, ADD_ADDITIONAL, UPDATE_ADDITIONAL, DELETE_ADDITIONAL } from '@/store/_mutationtypes';

const state = {
  additionals: [],
  totalElements: 0,
  totalPages: 0,
};

const actions = {
  [LOAD_ADDITIONALS]({ commit }, { page, itemsPerPage, sort, sortDesc }) {
    let sortFormatted = sort + ',' + (sortDesc ? 'DESC' : 'ASC');
    return Api.get(`/additionals?page=${page}&size=${itemsPerPage}&sort=${sortFormatted}`)
      .then(response => {
        let data = response.data;
        commit(SET_ADDITIONALS, data);
        return data;
      })
  },
  [CREATE_ADDITIONAL]({ commit, dispatch }, { additional, page, itemsPerPage, sort, sortDesc }) {
    return Api.post('/additionals', additional)
      .then(response => {
        let additional = response.data;
        commit(ADD_ADDITIONAL, additional);
        dispatch(`additionals/${LOAD_ADDITIONALS}`, { page, itemsPerPage, sort, sortDesc }, { root: true });
        dispatch(`alert/${ADD_ALERT}`, { message: response.data.message || 'Adicional criado com sucesso', color: 'success' }, { root: true });
      })
  },
  [EDIT_ADDITIONAL]({ commit, dispatch }, { additional }) {
    return Api.put('/additionals', additional)
      .then(response => {
        let additional = response.data;
        commit(UPDATE_ADDITIONAL, additional);
        dispatch(`alert/${ADD_ALERT}`, { message: response.data.message || 'Adicional alterado com sucesso', color: 'success' }, { root: true });
      })
  },
  [REMOVE_ADDITIONAL]({ commit, dispatch }, { additional, page, itemsPerPage, sort, sortDesc }) {
    return Api.delete(`/additionals/${additional.id}`)
      .then(response => {
        commit(DELETE_ADDITIONAL, additional.id);
        dispatch(`additionals/${LOAD_ADDITIONALS}`, { page, itemsPerPage, sort, sortDesc }, { root: true });
        dispatch(`alert/${ADD_ALERT}`, { message: response.data.message || 'Adicional excluído com sucesso', color: 'success' }, { root: true });
      })
  },
}

const mutations = {
  [SET_ADDITIONALS](state, data) {
    state.additionals = data.content;
    state.totalElements = data.totalElements;
    state.totalPages = data.totalPages;
  },
  [ADD_ADDITIONAL](state, additional) {
    state.additionals.push(additional);
  },
  [UPDATE_ADDITIONAL](state, additional) {
    let additionalUpdated = state.additionals.find(item => item.id == additional.id);
    additionalUpdated.name = additional.name;
    additionalUpdated.value = additional.value;
    additionalUpdated.requiredAnyLease = additional.requiredAnyLease;
    additionalUpdated.requiredMainLease = additional.requiredMainLease;
    additionalUpdated.enabled = additional.enabled;
  },
  [DELETE_ADDITIONAL](state, id) {
    const removeIndex = state.additionals.findIndex(item => item.id === id);
    state.additionals.splice(removeIndex, 1);
  },
}

const getters = {
}

export const additionals = {
  namespaced: true,
  state,
  actions,
  mutations,
  getters
};