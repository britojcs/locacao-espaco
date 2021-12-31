<template>
  <v-app>
    <v-main>
      <v-container fill-height>
        <v-layout align-center justify-center>
          <v-flex xs12 sm8 md4>
            <v-card tile>
              <v-toolbar flat color="primary" dark>
                <v-toolbar-title>Castelo Villa Imperial</v-toolbar-title>
              </v-toolbar>
              <v-card-text>
                <v-form ref="loginForm">
                  <v-text-field
                    v-model="loginForm.username"
                    @input="loginForm.username = uppercase(loginForm.username)"
                    placeholder="Usuário"
                    prepend-icon="person"
                    :rules="[required('Usuário')]"
                    dense
                  ></v-text-field>
                  <v-text-field
                    v-model="loginForm.password"
                    placeholder="Senha"
                    prepend-icon="lock"
                    :type="showPassword ? 'text' : 'password'"
                    :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
                    @click:append="showPassword = !showPassword"
                    :rules="[required('Senha')]"
                    @keyup.enter.native="handleLoginSubmit()"
                    dense
                  ></v-text-field>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                  small
                  outlined
                  class="primary--text"
                  @click="handleLoginSubmit"
                  :loading="loading"
                  >Entrar</v-btn
                >
              </v-card-actions>
            </v-card>
          </v-flex>
        </v-layout>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import { mapState, mapActions } from "vuex";
import { LOGIN, LOGOUT } from "@/store/_actiontypes";
import validations from "@/helpers/validations";
import { uppercase } from "@/helpers/string-util";

export default {
  data() {
    return {
      loginForm: {
        username: "",
        password: "",
      },
      showPassword: false,
      ...validations,
      uppercase,
    };
  },
  computed: {
    ...mapState({
      loading: (state) => state.loader.loading,
    }),
  },
  created() {
    //reset theme
    this.$vuetify.theme.dark = 0;
    // reset login status
    this.LOGOUT();
  },
  methods: {
    ...mapActions("account", [LOGIN, LOGOUT]),
    handleLoginSubmit() {
      if (!this.$refs.loginForm.validate()) return;
      const { username, password } = this.loginForm;
      if (username && password) {
        this.LOGIN({ username, password });
      }
    },
  },
};
</script>

<style>
</style>