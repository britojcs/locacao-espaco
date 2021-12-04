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
                <span class="primary--text font-weight-medium">Adicionais</span>
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
                      <AdditionalForm
                        :additional="editedAdditional"
                        :showCloseButton="true"
                        :onCloseClick="close"
                        :onSubmitClick="saveAdditional"
                        :loading="loading"
                        ref="form"
                      />
                    </v-card-text>
                  </v-card>
                </v-dialog>
              </div>
            </template>
            <template v-slot:[`item.value`]="{ item }">
              {{ getCurrencyFormatted(item.value) }}
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
              <v-icon small class="mr-2" @click="editAdditional(item)"
                >edit</v-icon
              >
              <v-icon small @click="deleteAdditional(item)">delete</v-icon>
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
  LOAD_ADDITIONALS,
  CREATE_ADDITIONAL,
  EDIT_ADDITIONAL,
  REMOVE_ADDITIONAL,
} from "@/store/_actiontypes";
import AdditionalForm from "@/views/additional/AdditionalForm";

export default {
  components: { AdditionalForm },
  data: () => ({
    loading: false,
    dialog: false,
    page: 1,
    itemsPerPage: 10,
    options: {},
    headers: [
      { text: "Código", value: "id", width: 90 /*, align: " d-none"*/ },
      { text: "Descrição", value: "name" },
      { text: "Valor", value: "value", width: 140 },
      { text: "Obrigatório", value: "required", width: 120 },
      { text: "Ativo", value: "enabled", width: 80 },
      { text: "Ações", value: "action", sortable: false, width: 100 },
    ],
    editedAdditional: {
      id: 0,
      name: "",
      additionalPackage: {
        description: "",
      },
      value: 0,
      required: false,
      enabled: true,
    },
    defaultAdditional: {
      id: 0,
      name: "",
      additionalPackage: {
        description: "",
      },
      value: 0,
      required: false,
      enabled: true,
    },
  }),
  computed: {
    ...mapState({
      items: (state) => state.additionals.additionals,
      totalElements: (state) => state.additionals.totalElements,
      totalPages: (state) => state.additionals.totalPages,
    }),
    formTitle() {
      return this.editedAdditional.id === 0
        ? "Novo Adicional"
        : "Editar Adicional";
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
    ...mapActions("additionals", [
      LOAD_ADDITIONALS,
      CREATE_ADDITIONAL,
      EDIT_ADDITIONAL,
      REMOVE_ADDITIONAL,
    ]),
    async loadData() {
      this.loading = true;
      await this.LOAD_ADDITIONALS(this.getPageOptions());
      this.loading = false;
    },
    editAdditional(item) {
      this.editedAdditional = Object.assign({}, item);
      this.dialog = true;
    },
    deleteAdditional(item) {
      confirm("Tem certeza de que deseja excluir este Adicional?") &&
        this.REMOVE_ADDITIONAL(this.getPageOptions(item));
    },
    close() {
      this.dialog = false;
      this.editedAdditional = Object.assign({}, this.defaultAdditional);
      this.$refs.form.reset();
    },
    saveAdditional() {
      this.loading = true;
      if (this.editedAdditional.id == 0) {
        this.CREATE_ADDITIONAL(this.getPageOptions(this.editedAdditional))
          .then(() => {
            this.close();
          })
          .finally(() => {
            this.loading = false;
          });
      } else {
        this.EDIT_ADDITIONAL({
          additional: this.editedAdditional,
        })
          .then(() => {
            this.close();
          })
          .finally(() => {
            this.loading = false;
          });
      }
    },
    getPageOptions(additional) {
      const { page, itemsPerPage, sortBy, sortDesc } = this.options;
      let pageOptions = {
        page: page - 1,
        itemsPerPage: itemsPerPage,
        sort: sortBy[0] || "name",
        sortDesc: sortDesc[0] || false,
      };
      if (additional) pageOptions["additional"] = additional;
      return pageOptions;
    },
    getCurrencyFormatted(value) {
      if (isNaN(value)) value = 0;
      return Number(value).toLocaleString("pt-BR", {
        maximumFractionDigits: 2,
        minimumFractionDigits: 2,
      });
    },
    getColorEnabled(enabled) {
      return enabled ? "green" : "red";
    },
  },
};
</script>

<style>
</style>