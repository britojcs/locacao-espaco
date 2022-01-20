import Api from '@/services/api';

import { LOAD_CUSTOMER_CONTACTS, CREATE_CONTACT, EDIT_CONTACT, REMOVE_CONTACT, ADD_ALERT } from '@/store/_actiontypes';
import { SET_CONTACTS, ADD_CONTACT, UPDATE_CONTACT, DELETE_CONTACT } from '@/store/_mutationtypes';

const state = {
  contacts: [],
  totalElements: 0,
  totalPages: 0,
};

const actions = {
  [LOAD_CUSTOMER_CONTACTS]({ commit }, { contactOwnerId, contactOwnerType, page, itemsPerPage, sort, sortDesc }) {
    let sortFormatted = sort + ',' + (sortDesc ? 'DESC' : 'ASC');
    if (contactOwnerType == 'clientes') contactOwnerType = 'customers';
    return Api.get(`/${contactOwnerType}/${contactOwnerId}/contacts?page=${page}&size=${itemsPerPage}&sort=${sortFormatted}`)
      .then(response => {
        let data = response.data;
        commit(SET_CONTACTS, data);
        return data;
      })
  },
  [CREATE_CONTACT]({ commit, dispatch }, { contact, contactOwnerId, contactOwnerType, page, itemsPerPage, sort, sortDesc }) {
    return Api.post('/contacts', contact)
      .then(response => {
        let contact = response.data;
        commit(ADD_CONTACT, contact);
        dispatch(`contacts/${LOAD_CUSTOMER_CONTACTS}`, { contactOwnerId, contactOwnerType, page, itemsPerPage, sort, sortDesc }, { root: true });
        dispatch(`alert/${ADD_ALERT}`, { message: response.data.message || 'Contato criado com sucesso', color: 'success' }, { root: true });
      })
  },
  [EDIT_CONTACT]({ commit, dispatch }, { contact }) {
    return Api.put('/contacts', contact)
      .then(response => {
        let contact = response.data;
        commit(UPDATE_CONTACT, contact);
        dispatch(`alert/${ADD_ALERT}`, { message: response.data.message || 'Contato alterado com sucesso', color: 'success' }, { root: true });
      })
  },
  [REMOVE_CONTACT]({ commit, dispatch }, { contact, contactOwnerId, contactOwnerType, page, itemsPerPage, sort, sortDesc }) {
    return Api.delete(`/contacts/${contact.id}`)
      .then(response => {
        commit(DELETE_CONTACT, contact.id);
        dispatch(`contacts/${LOAD_CUSTOMER_CONTACTS}`, { contactOwnerId, contactOwnerType, page, itemsPerPage, sort, sortDesc }, { root: true });
        dispatch(`alert/${ADD_ALERT}`, { message: response.data.message || 'Contato excluído com sucesso', color: 'success' }, { root: true });
      })
  },
}

const mutations = {
  [SET_CONTACTS](state, data) {
    state.contacts = data.content;
    state.totalElements = data.totalElements;
    state.totalPages = data.totalPages;
  },
  [ADD_CONTACT](state, contact) {
    state.contacts.push(contact);
  },
  [UPDATE_CONTACT](state, contact) {
    let contactUpdated = state.contacts.find(item => item.id == contact.id);
    contactUpdated.contactType = contact.contactType;
    contactUpdated.name = contact.name;
    contactUpdated.description = contact.description;
    contactUpdated.customerId = contact.customerId;
  },
  [DELETE_CONTACT](state, id) {
    const removeIndex = state.contacts.findIndex(item => item.id === id);
    state.contacts.splice(removeIndex, 1);
  },
}

const getters = {
}

export const contacts = {
  namespaced: true,
  state,
  actions,
  mutations,
  getters
};