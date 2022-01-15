import Api from '@/services/api';

import { LOAD_CONTRACTS, CREATE_CONTRACT, EDIT_CONTRACT, REMOVE_CONTRACT, ADD_ALERT } from '@/store/_actiontypes';
import { SET_CONTRACTS, ADD_CONTRACT, UPDATE_CONTRACT, DELETE_CONTRACT } from '@/store/_mutationtypes';

const state = {
  contracts: [],
  totalElements: 0,
  totalPages: 0,
};

const actions = {
  [LOAD_CONTRACTS]({ commit }, { page, itemsPerPage, sort, sortDesc }) {
    let sortFormatted = sort + ',' + (sortDesc ? 'DESC' : 'ASC');
    return Api.get(`/contracts?page=${page}&size=${itemsPerPage}&sort=${sortFormatted}`)
      .then(response => {
        let data = response.data;
        commit(SET_CONTRACTS, data);
        return data;
      })
  },
  [CREATE_CONTRACT]({ commit, dispatch }, { contract, page, itemsPerPage, sort, sortDesc }) {
    return Api.post('/contracts', contract)
      .then(response => {
        let contract = response.data;
        commit(ADD_CONTRACT, contract);
        dispatch(`contracts/${LOAD_CONTRACTS}`, { page, itemsPerPage, sort, sortDesc }, { root: true });
        dispatch(`alert/${ADD_ALERT}`, { message: response.data.message || 'Contrato criado com sucesso', color: 'success' }, { root: true });
      })
  },
  [EDIT_CONTRACT]({ commit, dispatch }, { contract }) {
    return Api.put('/contracts', contract)
      .then(response => {
        let contract = response.data;
        commit(UPDATE_CONTRACT, contract);
        dispatch(`alert/${ADD_ALERT}`, { message: response.data.message || 'Contrato alterado com sucesso', color: 'success' }, { root: true });
      })
  },
  [REMOVE_CONTRACT]({ commit, dispatch }, { contract, page, itemsPerPage, sort, sortDesc }) {
    return Api.delete(`/contracts/${contract.id}`)
      .then(response => {
        commit(DELETE_CONTRACT, contract.id);
        dispatch(`contracts/${LOAD_CONTRACTS}`, { page, itemsPerPage, sort, sortDesc }, { root: true });
        dispatch(`alert/${ADD_ALERT}`, { message: response.data.message || 'Contrato excluído com sucesso', color: 'success' }, { root: true });
      })
  },
}

const mutations = {
  [SET_CONTRACTS](state, data) {
    state.contracts = data.content;
    state.totalElements = data.totalElements;
    state.totalPages = data.totalPages;
  },
  [ADD_CONTRACT](state, contract) {
    state.contracts.push(contract);
  },
  [UPDATE_CONTRACT](state, contract) {
    let contractUpdated = state.contracts.find(item => item.id == contract.id);
    contractUpdated.name = contract.name;
    contractUpdated.description = contract.description;
    contractUpdated.content = contract.content;
    contractUpdated.enabled = contract.enabled;
    contractUpdated.updatedAt = contract.updatedAt;
  },
  [DELETE_CONTRACT](state, id) {
    const removeIndex = state.contracts.findIndex(item => item.id === id);
    state.contracts.splice(removeIndex, 1);
  },
}

const getters = {
}

export const contracts = {
  namespaced: true,
  state,
  actions,
  mutations,
  getters
};