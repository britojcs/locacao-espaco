<template>
  <div>
    <v-container>
      <v-layout row justify-space-around>
        <v-flex xs12 sm8>
          <v-card class="pa-2 mr-2 elevation-1" flat height="100%">
            <div
              class="primary--text px-2 py-1 text-capitalize font-weight-medium"
            >
              Alterar senha
            </div>
            <v-divider></v-divider>
            <v-alert
              v-if="defaultPassword"
              dense
              text
              outlined
              color="warning"
              class="mt-3"
            >
              Olá <b>{{ username }}</b
              >, você está usando uma senha padrão e, por questões de segurança,
              <b>altere sua senha</b>!
            </v-alert>
            <v-form ref="userPassword" class="xs12 my-1">
              <v-container>
                <v-text-field
                  label="Senha atual"
                  required
                  dense
                  outlined
                  class="pa-0 form-label"
                  v-model="userPassword.oldPassword"
                  :type="showOldPassword ? 'text' : 'password'"
                  :append-icon="showOldPassword ? 'mdi-eye' : 'mdi-eye-off'"
                  @click:append="showOldPassword = !showOldPassword"
                  :rules="[required('Senha atual')]"
                />
                <v-text-field
                  label="Nova senha"
                  required
                  dense
                  outlined
                  class="mb-2 pa-0 form-label"
                  v-model="userPassword.newPassword"
                  :type="showNewPassword ? 'text' : 'password'"
                  :append-icon="showNewPassword ? 'mdi-eye' : 'mdi-eye-off'"
                  @click:append="showNewPassword = !showNewPassword"
                  :rules="[required('Nova senha'), minLength('Nova senha', 5)]"
                  @keyup.enter.native="handleSubmit()"
                />
                <v-row class="justify-end">
                  <v-btn
                    small
                    outlined
                    class="primary--text"
                    @click="handleSubmit"
                    :loading="loading"
                    >Salvar</v-btn
                  >
                </v-row>
              </v-container>
            </v-form>
          </v-card>
        </v-flex>
      </v-layout>
    </v-container>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import store from "@/store/index.js";
import validations from "@/helpers/validations";
import { EDIT_USER_PASSWORD } from "@/store/_actiontypes";

export default {
  data: () => ({
    loading: false,
    showOldPassword: false,
    showNewPassword: false,
    userPassword: {
      oldPassword: "",
      newPassword: "",
    },
    username: store.state.account.user.username,
    defaultPassword: store.state.account.user.defaultPassword == true,
    ...validations,
  }),
  methods: {
    ...mapActions("account", [EDIT_USER_PASSWORD]),
    handleSubmit() {
      if (!this.$refs.userPassword.validate()) return;
      this.loading = true;
      this.EDIT_USER_PASSWORD(this.userPassword).finally(() => {
        this.loading = false;
      });
    },
  },
};
</script>

<style>
</style> 