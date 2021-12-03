import Vue from 'vue';
import Vuex from 'vuex';
import createPersistedState from 'vuex-persistedstate';
import SecureLS from 'secure-ls';
import { alert } from '@/store/modules/alert';
import { loader } from '@/store/modules/loader';
import { account } from '@/store/modules/account';
import { users } from '@/store/modules/users';
import { roles } from '@/store/modules/roles';
import { states } from '@/store/modules/states';
import { contactTypes } from '@/store/modules/contact-types';
import { contacts } from '@/store/modules/contacts';
import { customers } from '@/store/modules/customers';
import { contracts } from '@/store/modules/contracts';

const ls = new SecureLS({ isCompression: false });

Vue.use(Vuex);

const store = new Vuex.Store({
  modules: {
    alert,
    loader,
    account,
    users,
    roles,
    states,
    contactTypes,
    contacts,
    customers,
    contracts,
  },
  plugins: [
    createPersistedState({
      storage: {
        getItem: key => ls.get(key),
        setItem: (key, value) => ls.set(key, value),
        removeItem: key => ls.remove(key)
      }
    })
  ],
});

export default store;