import Api from '@/services/api';
import router from '@/router/index';

import { LOGIN, LOGOUT, REFRESHTOKEN, EDIT_USER_DETAILS, EDIT_USER_SETTINGS, EDIT_USER_PROFILE, EDIT_USER_PASSWORD, ADD_ALERT } from '@/store/_actiontypes';
import { LOGIN_SUCCESS, LOGIN_FAILURE, LOGOUT_USER, UPDATE_USER_DETAILS, UPDATE_USER_SETTINGS, UPDATE_USER_PROFILE } from '@/store/_mutationtypes';

const state = {
  user: null,
  currencies: []
};

const actions = {
  [LOGIN]({ commit }, { username, password }) {
    Api.post('/auth/signin', {
      username,
      password
    })
      .then(response => {
        let user = response.data;
        commit(LOGIN_SUCCESS, user);
        router.push('/dashboard');
      })
      .catch(() => {
        commit(LOGIN_FAILURE);
      });
  },
  [REFRESHTOKEN]({ commit }, { refreshtoken, token }) {
    return Api.post('/refreshtoken', {
      token,
      refreshtoken
    })
      .then(response => {
        let user = response.data;
        commit(LOGIN_SUCCESS, user);
      })
      .catch(() => {
        commit(LOGIN_FAILURE);
      });
  },
  [LOGOUT]({ commit }) {
    commit(LOGOUT_USER);
  },
  [EDIT_USER_DETAILS]({ commit }) {
    commit(UPDATE_USER_DETAILS);
  },
  [EDIT_USER_SETTINGS]({ commit, dispatch }, { systemName, currencyRegionName, useDarkMode }) {
    return Api.put('/settings', {
      systemName,
      currencyRegionName,
      useDarkMode
    })
      .then(response => {
        commit(UPDATE_USER_SETTINGS, response.data);
        dispatch(`alert/${ADD_ALERT}`, { message: 'Settings updaded successfully', color: 'success' }, { root: true });
      })
  },
  [EDIT_USER_PROFILE]({ commit, dispatch }, { firstname, lastname }) {
    const userProfile = { firstname, lastname };
    return Api.put('/account/profile', userProfile)
      .then(response => {
        commit(UPDATE_USER_PROFILE, userProfile);
        dispatch(`alert/${ADD_ALERT}`, { message: response.data.message, color: 'success' }, { root: true });
      })
  },
  [EDIT_USER_PASSWORD]({ dispatch }, { oldPassword, newPassword }) {
    return Api.post('/account/password', { oldPassword, newPassword })
      .then(response => {
        dispatch(`alert/${ADD_ALERT}`, { message: response.data.message, color: 'success' }, { root: true });
      })
  },
};

const mutations = {
  [LOGIN_SUCCESS](state, user) {
    state.user = user;
  },
  [LOGIN_FAILURE](state) {
    state.user = null;
  },
  [LOGOUT_USER](state) {
    state.user = null;
  },
  [UPDATE_USER_DETAILS](state) {
    state.user.useDarkMode = !state.user.useDarkMode;
  },
  [UPDATE_USER_SETTINGS](state, { systemName, currencyRegionName, useDarkMode, theme, displayCurrency }) {
    state.user.systemName = systemName;
    state.user.useDarkMode = useDarkMode;
    state.user.theme = theme;
    state.user.currencyRegionName = currencyRegionName;
    state.user.displayCurrency = displayCurrency;
  },
  [UPDATE_USER_PROFILE](state, { firstname, lastname }) {
    state.user.firstname = firstname;
    state.user.lastname = lastname;
  },
};

const getters = {
  nameInitials: (state) => {
    const fullName = state.user.firstname + ' ' + state.user.lastname;
    var initials = fullName.match(/\b\w/g) || [];
    return ((initials.shift() || "") + (initials.pop() || "")).toUpperCase();
  }
}

export const account = {
  namespaced: true,
  state,
  actions,
  mutations,
  getters
};