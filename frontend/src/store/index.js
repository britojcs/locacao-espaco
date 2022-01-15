import Vue from 'vue';
import Vuex from 'vuex';
import createPersistedState from 'vuex-persistedstate';
import SecureLS from 'secure-ls';

import { alert } from '@/store/modules/alert';
import { loader } from '@/store/modules/loader';

import { roles } from '@/store/modules/roles';
import { states } from '@/store/modules/states';
import { contactTypes } from '@/store/modules/contact-types';
import { leaseTypes } from '@/store/modules/lease-types';
import { additionalRequiredTypes } from '@/store/modules/additional-required-types';

import { account } from '@/store/modules/account';
import { users } from '@/store/modules/users';
import { contacts } from '@/store/modules/contacts';
import { customers } from '@/store/modules/customers';
import { contracts } from '@/store/modules/contracts';
import { places } from '@/store/modules/places';
import { additionals } from '@/store/modules/additionals';

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
    leaseTypes,
    additionalRequiredTypes,
    contacts,
    customers,
    contracts,
    places,
    additionals,
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