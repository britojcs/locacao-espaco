<template>
  <div>
    <v-navigation-drawer
      v-model="sidebar"
      app
      left
      offset-y
      style="margin-top: 50px"
      class="hidden-md-and-up"
    >
      <v-list-item
        v-for="item in navItems"
        :key="item.text"
        :to="item.route"
        active-class="primary--text"
      >
        <v-list-item-icon class="mr-2">
          <v-icon small>{{ item.icon }}</v-icon>
        </v-list-item-icon>
        <v-list-item-title>{{ item.text }}</v-list-item-title>
      </v-list-item>
    </v-navigation-drawer>

    <v-app-bar dense dark fixed color="primary">
      <span class="hidden-md-and-up">
        <v-app-bar-nav-icon @click="sidebar = !sidebar"></v-app-bar-nav-icon>
      </span>
      <v-spacer class="hidden-md-and-up"></v-spacer>
      <v-toolbar-title class="text-uppercase">
        <router-link
          to="/dashboard"
          tag="span"
          style="cursor: pointer"
          class="text-h5"
        >
          <!-- <span class="grey--text text--lighten-1">Vue</span> -->
          <span class="font-weight-bold">{{ systemName }}</span>
        </router-link>
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <!-- menu -->
      <v-menu offset-y>
        <template v-slot:activator="{ on }">
          <div class="ml-4">
            <div v-on="on" class="d-flex align-center">
              <v-avatar
                size="30px"
                color="primary lighten-2"
                class="ml-2"
                style="cursor: pointer"
              >
                <span class="white--text">{{ nameInitials }}</span>
              </v-avatar>
              <div class="hidden-sm-and-down">
                <v-btn text class="pa-1">
                  <span class="text-capitalize">{{ username }}</span>
                  <v-icon>expand_more</v-icon>
                </v-btn>
              </div>
            </div>
          </div>
        </template>
        <v-list>
          <v-list-item
            v-for="item in menuItems"
            :key="item.text"
            router
            dense
            :to="item.route"
          >
            <v-list-item-icon class="mr-2">
              <v-icon small>{{ item.icon }}</v-icon>
            </v-list-item-icon>
            <v-list-item-title>{{ item.text }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </v-app-bar>
    <v-app-bar
      dense
      class="hidden-sm-and-down secondary-toolbar mt-12"
      :elevate-on-scroll="true"
      fixed
    >
      <v-toolbar-items>
        <v-btn
          text
          v-for="item in navItems"
          :key="item.text"
          :to="item.route"
          :ripple="false"
          :disabled="item.route == '/dashboard' || item.route == '/locacoes'"
        >
          <span>
            <v-icon small class="primary--text mr-1">{{ item.icon }}</v-icon>
          </span>
          <span class="mt-1 text-subtitle-2 text-capitalize">{{
            item.text
          }}</span>
        </v-btn>
      </v-toolbar-items>
    </v-app-bar>
    <ProgressBar />
  </div>
</template>

<script>
import ProgressBar from "@/components/ProgressBarApp";
import { mapState, mapGetters } from "vuex";

export default {
  components: {
    ProgressBar,
  },
  computed: {
    ...mapGetters("account", ["nameInitials"]),
    ...mapState({
      username: (state) =>
        state.account.user ? state.account.user.firstname : "",
      systemName: (state) =>
        state.account.user ? state.account.user.systemName : "",
      navItems: (state) => {
        let items = [
          { icon: "mdi-view-dashboard", text: "Painel", route: "/dashboard" },
        ];
        const userRoles = state.account.user ? state.account.user.roles : [];
        if (
          userRoles.includes("ROLE_ADMIN") ||
          userRoles.includes("ROLE_CUSTOMER")
        )
          items.push({
            icon: "mdi-account-multiple",
            text: "Clientes",
            route: "/clientes",
          });
        if (
          userRoles.includes("ROLE_ADMIN") ||
          userRoles.includes("ROLE_PLACE")
        )
          items.push({
            icon: "mdi-home-group",
            text: "Espaços",
            route: "/espacos",
          });
        if (
          userRoles.includes("ROLE_ADMIN") ||
          userRoles.includes("ROLE_ADDITIONAL")
        )
          items.push({
            icon: "mdi-plus-box",
            text: "Adicionais",
            route: "/adicionais",
          });
        if (
          userRoles.includes("ROLE_ADMIN") ||
          userRoles.includes("ROLE_LEASE")
        )
          items.push({
            icon: "mdi-home-account",
            text: "Locações",
            route: "/locacoes",
          });
        if (userRoles.includes("ROLE_ADMIN") || userRoles.includes("ROLE_USER"))
          items.push({
            icon: "mdi-card-account-details",
            text: "Usuários",
            route: "/usuarios",
          });
        if (
          userRoles.includes("ROLE_ADMIN") ||
          userRoles.includes("ROLE_CONTRACT")
        )
          items.push({
            icon: "mdi-text-box",
            text: "Contratos",
            route: "/contratos",
          });
        return items;
      },
    }),
  },
  data() {
    return {
      sidebar: false,
      menuItems: [
        { icon: "perm_identity", text: "Perfil", route: "/perfil" },
        {
          icon: "mdi-key-change",
          text: "Alterar senha",
          route: "/alterar-senha",
        },
        { icon: "exit_to_app", text: "Sair", route: "/login" },
      ],
    };
  },
};
</script>

<style scoped lang="scss">
// ::v-deep .v-toolbar__content {
//   max-width: 1185px;
//   margin: auto;
// }
::v-deep .v-toolbar__items > a.v-btn--active {
  .v-btn__content span {
    font-weight: bold;
  }
}
::v-deep .v-toolbar__items > a.v-btn--active:after {
  content: "";
  position: absolute;
  left: 18px;
  bottom: 0;
  height: 2px;
  width: 75%;
  border-bottom: 2px solid #5c5153;
}
::v-deep .secondary-toolbar .v-toolbar__content {
  padding: 0;
}
::v-deep .v-btn:before {
  background-color: transparent !important;
}
</style>