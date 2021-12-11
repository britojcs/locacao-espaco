<template>
  <div>
    <v-container>
      <v-layout row justify-space-around>
        <v-flex xs12 sm8>
          <v-card class="pa-2 mr-2 elevation-1" flat height="100%">
            <div
              class="primary--text px-2 py-1 text-capitalize font-weight-medium"
            >
              Perfil
            </div>
            <v-divider></v-divider>
            <v-form class="xs12 my-1">
              <v-container>
                <v-text-field
                  label="Usuário/Login"
                  class="my-1 pa-0 form-label"
                  v-model="profile.username"
                  dense
                  readonly
                  disabled
                ></v-text-field>
                <v-text-field
                  label="Nome"
                  required
                  dense
                  class="my-1 pa-0 form-label"
                  v-model="profile.firstname"
                  :rules="[required('Nome')]"
                  @keyup.enter.native="handleSubmit()"
                  :disabled="user.id == 1"
                ></v-text-field>
                <v-text-field
                  label="Sobrenome"
                  dense
                  required
                  class="mt-1 mb-2 pa-0 form-label"
                  v-model="profile.lastname"
                  @keyup.enter.native="handleSubmit()"
                  :disabled="user.id == 1"
                ></v-text-field>
                <v-row class="justify-end">
                  <v-btn
                    small
                    outlined
                    class="primary--text font-weight-bold"
                    @click="handleSubmit"
                    :loading="loading"
                    :disabled="user.id == 1"
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
import { mapState, mapActions } from "vuex";
import validations from "@/helpers/validations";
import { EDIT_USER_PROFILE } from "@/store/_actiontypes";

export default {
  data: () => ({
    loading: false,
    profile: { firstname: "", lastname: "", username: "" },
    ...validations,
  }),
  computed: {
    ...mapState({
      user: (state) => state.account.user,
    }),
  },
  mounted() {
    this.profile = {
      firstname: this.user.firstname,
      lastname: this.user.lastname,
      username: this.user.username,
    };
  },
  methods: {
    ...mapActions("account", [EDIT_USER_PROFILE]),
    handleSubmit() {
      this.loading = true;
      this.EDIT_USER_PROFILE(this.profile).finally(() => {
        this.loading = false;
      });
    },
  },
};
</script>

<style>
</style> 