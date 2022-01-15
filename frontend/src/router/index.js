import Vue from 'vue';
import Router from 'vue-router';
import store from '@/store/index.js';

import LoginPage from '@/views/LoginPage.vue';
import HomePage from '@/views/HomePage.vue';
import UpdateProfile from '@/views/account/UpdateProfile.vue';
import UpdatePassword from '@/views/account/UpdatePassword.vue';
import UserList from '@/views/user/UserList.vue';
import CustomerList from '@/views/customer/CustomerList.vue';
import ContactList from '@/views/contact/ContactList.vue';
import ContractList from '@/views/contract/ContractList.vue';
import PlaceList from '@/views/place/PlaceList.vue';
import AdditionalList from '@/views/additional/AdditionalList.vue';

Vue.use(Router);

const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/', component: HomePage,
      children: [
        // { path: '/dashboard', component: Dashboard },
        { path: '/usuarios', component: UserList },
        { path: '/perfil', component: UpdateProfile },
        { path: '/alterar-senha', component: UpdatePassword },
        { path: '/clientes', component: CustomerList },
        { path: '/:owner/:id/contatos', component: ContactList },
        { path: '/contratos', component: ContractList },
        { path: '/espacos', component: PlaceList },
        { path: '/adicionais', component: AdditionalList },
        // { path: '/', component: Dashboard },
      ]
    },
    { path: '/login', component: LoginPage },
    { path: '*', redirect: '/' }
  ],
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition)
      return savedPosition;

    return { x: 0, y: 0 };
  }
});

router.beforeEach(async (to, _, next) => {
  // redirect to login page if not logged in and trying to access a restricted page
  const publicPages = ['/login'];
  const authRequired = !publicPages.includes(to.path);
  const loggedIn = store.state.account.user != undefined && store.state.account.user != null;

  if (authRequired && !loggedIn)
    return next('/login');

  next();
})

export default router;