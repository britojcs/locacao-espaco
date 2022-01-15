import Api from '@/services/api';
import sumBy from 'lodash/sumBy';
import groupBy from 'lodash/groupBy';
import map from 'lodash/map';
import orderBy from 'lodash/orderBy';

import { LOAD_USERS, CREATE_USER, EDIT_USER, REMOVE_USER, UPDATE_PASSWORD_USER, ADD_ALERT } from '@/store/_actiontypes';
import { SET_USERS, ADD_USER, UPDATE_USER, DELETE_USER } from '@/store/_mutationtypes';

const state = {
  users: [],
  totalElements: 0,
  totalPages: 0,
};

const actions = {
  [LOAD_USERS]({ commit }, { page, itemsPerPage, sort, sortDesc }) {
    let sortFormatted = sort + ',' + (sortDesc ? 'DESC' : 'ASC');
    return Api.get(`/users?page=${page}&size=${itemsPerPage}&sort=${sortFormatted}`)
      .then(response => {
        let data = response.data;
        commit(SET_USERS, data);
        return data;
      })
  },
  [CREATE_USER]({ commit, dispatch }, { user, page, itemsPerPage, sort, sortDesc }) {
    return Api.post('/users', user)
      .then(response => {
        let user = response.data;
        commit(ADD_USER, user);
        dispatch(`users/${LOAD_USERS}`, { page, itemsPerPage, sort, sortDesc }, { root: true });
        dispatch(`alert/${ADD_ALERT}`, { message: response.data.message || 'Usuário criado com sucesso', color: 'success' }, { root: true });
      })
  },
  [EDIT_USER]({ commit, dispatch }, { user }) {
    return Api.put(`/users/${user.id}`, user)
      .then(response => {
        let user = response.data;
        commit(UPDATE_USER, user);
        dispatch(`alert/${ADD_ALERT}`, { message: response.data.message || 'Usuário alterado com sucesso', color: 'success' }, { root: true });
      })
  },
  [REMOVE_USER]({ commit, dispatch }, { user, page, itemsPerPage, sort, sortDesc }) {
    return Api.delete(`/users/${user.id}`)
      .then(response => {
        commit(DELETE_USER, user.id);
        dispatch(`users/${LOAD_USERS}`, { page, itemsPerPage, sort, sortDesc }, { root: true });
        dispatch(`alert/${ADD_ALERT}`, { message: response.data.message || 'Usuário excluído com sucesso', color: 'success' }, { root: true });
      })
  },
  [UPDATE_PASSWORD_USER]({ dispatch }, userId) {
    return Api.put(`/users/${userId}/reset-password`)
      .then(response => {
        dispatch(`alert/${ADD_ALERT}`, { message: response.data.message || 'Senha alterada com sucesso', color: 'success' }, { root: true });
      })
  },
}

const mutations = {
  [SET_USERS](state, data) {
    state.users = data.content;
    state.totalElements = data.totalElements;
    state.totalPages = data.totalPages;
  },
  [ADD_USER](state, user) {
    state.users.push(user);
  },
  [UPDATE_USER](state, user) {
    let userUpdated = state.users.find(item => item.id == user.id);
    userUpdated.roles = user.roles;
    userUpdated.username = user.username;
    userUpdated.firstname = user.firstname;
    userUpdated.lastname = user.lastname;
    userUpdated.enabled = user.enabled;
    userUpdated.lastLoginAt = user.lastLoginAt;
  },
  [DELETE_USER](state, id) {
    const removeIndex = state.users.findIndex(item => item.id === id);
    state.users.splice(removeIndex, 1);
  },
}

const getters = {
  overallSpent: (state, getters, rootState) => {
    var overallSpent = new Intl.NumberFormat(window.navigator.language).format(sumBy(state.users, "value").toFixed(2))
    return `${rootState.account.user.displayCurrency} ${overallSpent}`
  },
  mostSpentBy: state => {
    return state.users.length <= 0 ? 'N/A' : orderBy(
      map(
        groupBy(state.users, (e) => {
          return e.type
        }), (type, id) => ({
          type: id,
          value: sumBy(type, 'value')
        })), ['value'], ['desc'])[0].type;
  },
  mostSpentOn: state => {
    return state.users.length <= 0 ? 'N/A' : orderBy(
      map(
        groupBy(state.users, (e) => {
          return e.category
        }), (category, id) => ({
          category: id,
          value: sumBy(category, 'value')
        })), ['value'], ['desc'])[0].category;
  },
  spentThisYear: (state, getters, rootState) => {
    var currentYear = new Date().getFullYear();
    var spentThisYear = new Intl.NumberFormat(window.navigator.language).format(sumBy(state.users.filter((o) => {
      return new Date(o.date).getFullYear() == currentYear;
    }), "value").toFixed(2))
    return `${rootState.account.user.displayCurrency} ${spentThisYear}`
  }
}

export const users = {
  namespaced: true,
  state,
  actions,
  mutations,
  getters
};