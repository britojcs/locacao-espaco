<template>
  <v-form class="mt-1" ref="userform">
    <v-container>
      <v-row>
        <v-col cols="12" md="12" class="py-0 ma-0 my-1">
          <v-alert dense text outlined type="warning">
            O nome de usuário não deve ter espaços ou caracteres especiais
          </v-alert>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="12" md="4" class="py-0 ma-0 my-1">
          <input type="hidden" :value="user.id" />
          <v-checkbox
            class="ma-0 pa-0 form-label"
            v-model="model.enabled"
            :disabled="model.id == 1"
            label="Ativo"
            type="checkbox"
          ></v-checkbox>
        </v-col>
        <v-col cols="12" md="8" class="py-0 ma-0 my-1">
          <v-text-field
            v-model="model.username"
            :disabled="model.id == 1"
            label="Usuário/login"
            class="ma-0 pa-0 form-label"
            dense
            required
            counter="30"
            :rules="[
              required('Usuário/Login'),
              minLength('Usuário/Login', 5),
              maxLength('Usuário/Login', 30),
            ]"
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="12" md="4" class="py-0 ma-0">
          <v-text-field
            v-model="model.firstname"
            label="Nome"
            class="ma-0 pa-0 form-label"
            dense
            required
            counter="30"
            :rules="[
              required('Nome'),
              minLength('Nome', 2),
              maxLength('Nome', 30),
            ]"
          ></v-text-field>
        </v-col>
        <v-col cols="12" md="8" class="py-0 ma-0">
          <v-text-field
            v-model="model.lastname"
            label="Sobrenome"
            class="ma-0 pa-0 form-label"
            dense
            counter="50"
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="12" class="py-0 ma-0">
          <v-select
            v-model="model.roles"
            :items="roles"
            :item-text="'description'"
            return-object
            small-chips
            multiple
            label="Permissões"
            required
            :rules="[required('Permissão')]"
            :disabled="model.id == 1"
          >
            <template v-slot:prepend-item>
              <v-list-item ripple @click="toggleSelectAllRoles">
                <v-list-item-action>
                  <v-icon :color="model.roles.length > 0 ? 'darken-4' : ''">
                    {{ iconSelectRole }}
                  </v-icon>
                </v-list-item-action>
                <v-list-item-content>
                  <v-list-item-title> Admin </v-list-item-title>
                </v-list-item-content>
              </v-list-item>
              <v-divider class="mt-2"></v-divider>
            </template>
          </v-select>
        </v-col>
      </v-row>
      <v-row class="justify-end">
        <v-btn
          v-if="showCloseButton"
          outlined
          small
          class="mr-2 red--text font-weight-bold"
          @click="onCloseClick"
          >Cancelar</v-btn
        >
        <v-btn
          outlined
          small
          class="primary--text font-weight-bold"
          :loading="loading"
          @click="
            () => {
              if (!this.$refs.userform.validate()) return;
              onSubmitClick();
            }
          "
          >Salvar</v-btn
        >
      </v-row>
    </v-container>
  </v-form>
</template>

<script>
import { mapState } from "vuex";
import validations from "@/helpers/validations";
import { LOAD_ROLES } from "@/store/_actiontypes";

export default {
  props: {
    user: {
      type: Object,
    },
    showCloseButton: {
      type: Boolean,
      default: false,
    },
    onSubmitClick: {
      type: Function,
    },
    onCloseClick: {
      type: Function,
    },
    loading: {
      type: Boolean,
    },
  },
  data() {
    return {
      ...validations,
    };
  },
  mounted() {
    this.$store.dispatch(`roles/${LOAD_ROLES}`);
  },
  computed: {
    ...mapState({
      roles: (state) =>
        state.roles.roles.filter((role) => role.name != "ROLE_ADMIN"),
    }),
    model: {
      get() {
        return this.user;
      },
      set(user) {
        this.$emit("input", user);
      },
    },
    likesAllRoles() {
      return this.user.roles.length === this.roles.length;
    },
    likesSomeRole() {
      return this.user.roles.length > 0 && !this.likesAllRoles;
    },
    iconSelectRole() {
      if (this.likesAllRoles) return "mdi-close-box";
      if (this.likesSomeRole) return "mdi-minus-box";
      return "mdi-checkbox-blank-outline";
    },
  },
  methods: {
    reset() {
      this.$refs.userform.reset();
    },
    toggleSelectAllRoles() {
      this.$nextTick(() => {
        if (this.likesAllRoles) this.model.roles = [];
        else this.model.roles = this.roles.slice();
      });
    },
  },
};
</script>

<style>
</style>