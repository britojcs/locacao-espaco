<template>
  <div>
    <v-container>
      <v-layout row>
        <v-flex xs12>
          <v-data-table
            :loading="loading"
            :headers="headers"
            :items="items"
            :page="page"
            :page-count="totalPages"
            :options.sync="options"
            :items-per-page="itemsPerPage"
            :footer-props="{
              'items-per-page-options': [5, 10, 20, 30, 40, 50],
            }"
            :server-items-length="totalElements"
            class="elevation-1"
          >
            <template v-slot:top>
              <div class="d-flex align-center pa-4">
                <span class="primary--text font-weight-medium">Usuários</span>
                <v-divider
                  class="mx-2 my-1"
                  inset
                  vertical
                  style="height: 20px"
                ></v-divider>
                <v-spacer></v-spacer>
                <v-dialog v-model="dialog" max-width="650px">
                  <template v-slot:activator="{ on }">
                    <v-btn
                      outlined
                      small
                      class="primary--text font-weight-bold"
                      v-on="on"
                    >
                      <v-icon left> mdi-plus-thick </v-icon>
                      Novo</v-btn
                    >
                  </template>
                  <v-card>
                    <v-card-title>
                      <span class="text-h5">{{ formTitle }}</span>
                    </v-card-title>

                    <v-card-text>
                      <UserForm
                        :user="editedUser"
                        :showCloseButton="true"
                        :onCloseClick="close"
                        :onSubmitClick="saveUser"
                        :loading="loading"
                        ref="form"
                      />
                    </v-card-text>
                  </v-card>
                </v-dialog>
              </div>
            </template>
            <template v-slot:[`item.roles`]="{ item }">
              <v-chip
                v-for="role in item.roles"
                :key="role.name"
                class="mr-1 font-weight-bold"
                x-small
                :color="getColorRole(role.name)"
                outlined
              >
                {{ role.description }}
              </v-chip>
            </template>
            <template v-slot:[`item.enabled`]="{ item }">
              <v-chip
                :color="getColorEnabled(item.enabled)"
                style="padding: 0px; height: 12px; width: 12px"
                flat
                small
                class="ml-1 mb-1"
              ></v-chip>
            </template>
            <template v-slot:[`item.action`]="{ item }">
              <div class="d-flex justify-end">
                <v-icon small class="mr-2" @click="editUser(item)">edit</v-icon>
                <v-icon v-if="item.id != 1" small @click="deleteUser(item)"
                  >delete</v-icon
                >
              </div>
            </template>
          </v-data-table>
        </v-flex>
      </v-layout>
    </v-container>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
import {
  LOAD_USERS,
  CREATE_USER,
  EDIT_USER,
  REMOVE_USER,
} from "@/store/_actiontypes";
import UserForm from "@/views/user/UserForm";

export default {
  components: { UserForm },
  data: () => ({
    loading: false,
    dialog: false,
    page: 1,
    itemsPerPage: 10,
    options: {},
    headers: [
      { text: "Código", value: "id", width: 90 /*, align: " d-none"*/ },
      { text: "Usuário/Login", value: "username" },
      { text: "Permissão", value: "roles" },
      { text: "Nome", value: "firstname" },
      { text: "Sobrenome", value: "lastname" },
      { text: "Ativo", value: "enabled", width: 80 },
      { text: "Último login", value: "lastLoginAt" },
      { text: "Ações", value: "action", sortable: false, width: 75 },
    ],
    editedUser: {
      id: 0,
      roles: [],
      username: "",
      firstname: "",
      lastname: "",
      enabled: true,
      lastLoginAt: "",
    },
    defaultUser: {
      id: 0,
      roles: [],
      username: "",
      firstname: "",
      lastname: "",
      enabled: true,
      lastLoginAt: "",
    },
  }),
  computed: {
    ...mapState({
      items: (state) => state.users.users,
      totalElements: (state) => state.users.totalElements,
      totalPages: (state) => state.users.totalPages,
    }),
    formTitle() {
      return this.editedUser.id === 0 ? "Novo Usuário" : "Editar Usuário";
    },
  },
  watch: {
    dialog(val) {
      val || this.close();
    },
    options: {
      handler() {
        this.loadData();
      },
    },
    deep: true,
  },
  methods: {
    ...mapActions("users", [LOAD_USERS, CREATE_USER, EDIT_USER, REMOVE_USER]),
    async loadData() {
      this.loading = true;
      await this.LOAD_USERS(this.getPageOptions());
      this.loading = false;
    },
    editUser(item) {
      this.editedUser = Object.assign({}, item);
      this.dialog = true;
    },
    deleteUser(item) {
      confirm("Tem certeza de que deseja excluir este Usuário?") &&
        this.REMOVE_USER(this.getPageOptions(item));
    },
    close() {
      this.dialog = false;
      this.editedUser = Object.assign({}, this.defaultUser);
      this.$refs.form.reset();
    },
    saveUser() {
      this.loading = true;
      if (this.editedUser.id == 0) {
        this.CREATE_USER(this.getPageOptions(this.editedUser))
          .then(() => {
            this.close();
          })
          .finally(() => {
            this.loading = false;
          });
      } else {
        this.EDIT_USER({
          user: this.editedUser,
        })
          .then(() => {
            this.close();
          })
          .finally(() => {
            this.loading = false;
          });
      }
    },
    getPageOptions(user) {
      const { page, itemsPerPage, sortBy, sortDesc } = this.options;
      let pageOptions = {
        page: page - 1,
        itemsPerPage: itemsPerPage,
        sort: sortBy[0] || "username",
        sortDesc: sortDesc[0] || false,
      };
      if (user) pageOptions["user"] = user;
      return pageOptions;
    },
    getColorRole(role) {
      if (role == "ROLE_ADMIN") return "red";
      return "default";
    },
    getColorEnabled(enabled) {
      return enabled ? "green" : "red";
    },
  },
};
</script>

<style>
</style>