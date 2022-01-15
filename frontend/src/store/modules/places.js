import Api from '@/services/api';

import { LOAD_PLACES, CREATE_PLACE, EDIT_PLACE, REMOVE_PLACE, ADD_ALERT } from '@/store/_actiontypes';
import { SET_PLACES, ADD_PLACE, UPDATE_PLACE, DELETE_PLACE } from '@/store/_mutationtypes';

const state = {
  places: [],
  totalElements: 0,
  totalPages: 0,
};

const actions = {
  [LOAD_PLACES]({ commit }, { page, itemsPerPage, sort, sortDesc }) {
    let sortFormatted = sort + ',' + (sortDesc ? 'DESC' : 'ASC');
    return Api.get(`/places?page=${page}&size=${itemsPerPage}&sort=${sortFormatted}`)
      .then(response => {
        let data = response.data;
        commit(SET_PLACES, data);
        return data;
      })
  },
  [CREATE_PLACE]({ commit, dispatch }, { place, page, itemsPerPage, sort, sortDesc }) {
    return Api.post('/places', place)
      .then(response => {
        let place = response.data;
        commit(ADD_PLACE, place);
        dispatch(`places/${LOAD_PLACES}`, { page, itemsPerPage, sort, sortDesc }, { root: true });
        dispatch(`alert/${ADD_ALERT}`, { message: response.data.message || 'Espaço criado com sucesso', color: 'success' }, { root: true });
      })
  },
  [EDIT_PLACE]({ commit, dispatch }, { place }) {
    return Api.put('/places', place)
      .then(response => {
        let place = response.data;
        commit(UPDATE_PLACE, place);
        dispatch(`alert/${ADD_ALERT}`, { message: response.data.message || 'Espaço alterado com sucesso', color: 'success' }, { root: true });
      })
  },
  [REMOVE_PLACE]({ commit, dispatch }, { place, page, itemsPerPage, sort, sortDesc }) {
    return Api.delete(`/places/${place.id}`)
      .then(response => {
        commit(DELETE_PLACE, place.id);
        dispatch(`places/${LOAD_PLACES}`, { page, itemsPerPage, sort, sortDesc }, { root: true });
        dispatch(`alert/${ADD_ALERT}`, { message: response.data.message || 'Espaço excluído com sucesso', color: 'success' }, { root: true });
      })
  },
}

const mutations = {
  [SET_PLACES](state, data) {
    state.places = data.content;
    state.totalElements = data.totalElements;
    state.totalPages = data.totalPages;
  },
  [ADD_PLACE](state, place) {
    state.places.push(place);
  },
  [UPDATE_PLACE](state, place) {
    let placeUpdated = state.places.find(item => item.id == place.id);
    placeUpdated.name = place.name;
    placeUpdated.leaseType = place.leaseType;
    placeUpdated.value = place.value;
    placeUpdated.enabled = place.enabled;
  },
  [DELETE_PLACE](state, id) {
    const removeIndex = state.places.findIndex(item => item.id === id);
    state.places.splice(removeIndex, 1);
  },
}

const getters = {
}

export const places = {
  namespaced: true,
  state,
  actions,
  mutations,
  getters
};