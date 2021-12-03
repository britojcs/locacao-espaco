import Api from '@/services/api';

import { LOAD_CUSTOMER, LOAD_CUSTOMERS, CREATE_CUSTOMER, EDIT_CUSTOMER, REMOVE_CUSTOMER, ADD_ALERT } from '@/store/_actiontypes';
import { SET_CUSTOMER, SET_CUSTOMERS, ADD_CUSTOMER, UPDATE_CUSTOMER, DELETE_CUSTOMER } from '@/store/_mutationtypes';

const state = {
  customer: {},
  customers: [],
  totalElements: 0,
  totalPages: 0,
};

const actions = {
  [LOAD_CUSTOMER]({ commit }, { id }) {
    return Api.get(`/customers/${id}`)
      .then(response => {
        let data = response.data;
        commit(SET_CUSTOMER, data);
        return data;
      })
  },
  [LOAD_CUSTOMERS]({ commit }, { page, itemsPerPage, sort, sortDesc }) {
    let sortFormatted = sort + ',' + (sortDesc ? 'DESC' : 'ASC');
    return Api.get(`/customers?page=${page}&size=${itemsPerPage}&sort=${sortFormatted}`)
      .then(response => {
        let data = response.data;
        commit(SET_CUSTOMERS, data);
        return data;
      })
  },
  [CREATE_CUSTOMER]({ commit, dispatch }, { customer, page, itemsPerPage, sort, sortDesc }) {
    return Api.post('/customers', customer)
      .then(response => {
        let customer = response.data;
        commit(ADD_CUSTOMER, customer);
        dispatch(`customers/${LOAD_CUSTOMERS}`, { page, itemsPerPage, sort, sortDesc }, { root: true });
        dispatch(`alert/${ADD_ALERT}`, { message: response.data.message || 'Cliente criado com sucesso', color: 'success' }, { root: true });
      })
  },
  [EDIT_CUSTOMER]({ commit, dispatch }, { customer }) {
    return Api.put('/customers', customer)
      .then(response => {
        let customer = response.data;
        commit(UPDATE_CUSTOMER, customer);
        dispatch(`alert/${ADD_ALERT}`, { message: response.data.message || 'Cliente alterado com sucesso', color: 'success' }, { root: true });
      })
  },
  [REMOVE_CUSTOMER]({ commit, dispatch }, { customer, page, itemsPerPage, sort, sortDesc }) {
    return Api.delete(`/customers/${customer.id}`)
      .then(response => {
        commit(DELETE_CUSTOMER, customer.id);
        dispatch(`customers/${LOAD_CUSTOMERS}`, { page, itemsPerPage, sort, sortDesc }, { root: true });
        dispatch(`alert/${ADD_ALERT}`, { message: response.data.message || 'Cliente excluído com sucesso', color: 'success' }, { root: true });
      })
  },
}

const mutations = {
  [SET_CUSTOMER](state, customer) {
    state.customer = customer;
  },
  [SET_CUSTOMERS](state, data) {
    state.customers = data.content;
    state.totalElements = data.totalElements;
    state.totalPages = data.totalPages;
  },
  [ADD_CUSTOMER](state, customer) {
    state.customers.push(customer);
  },
  [UPDATE_CUSTOMER](state, customer) {
    let customerUpdated = state.customers.find(item => item.id == customer.id);
    customerUpdated.fullname = customer.fullname;
    customerUpdated.cpfCnpj = customer.cpfCnpj;
    customerUpdated.cep = customer.cep;
    customerUpdated.address = customer.address;
    customerUpdated.addressComplement = customer.addressComplement;
    customerUpdated.district = customer.district;
    customerUpdated.city = customer.city;
    customerUpdated.state = customer.state;
    customerUpdated.enabled = customer.enabled;
  },
  [DELETE_CUSTOMER](state, id) {
    const removeIndex = state.customers.findIndex(item => item.id === id);
    state.customers.splice(removeIndex, 1);
  },
}

const getters = {
}

export const customers = {
  namespaced: true,
  state,
  actions,
  mutations,
  getters
};