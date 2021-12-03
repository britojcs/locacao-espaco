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
                <span class="primary--text font-weight-medium">Contratos</span>
                <v-divider
                  class="mx-2 my-1"
                  inset
                  vertical
                  style="height: 20px"
                ></v-divider>
                <v-spacer></v-spacer>
                <v-dialog v-model="dialog" max-width="1115px">
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
                      <ContractForm
                        :contract="editedContract"
                        :showCloseButton="true"
                        :onCloseClick="close"
                        :onSubmitClick="saveContract"
                        :loading="loading"
                        ref="form"
                      />
                    </v-card-text>
                  </v-card>
                </v-dialog>
              </div>
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
              <v-icon small class="mr-2" @click="editContract(item)"
                >edit</v-icon
              >
              <v-icon small @click="deleteContract(item)">delete</v-icon>
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
  LOAD_CONTRACTS,
  CREATE_CONTRACT,
  EDIT_CONTRACT,
  REMOVE_CONTRACT,
} from "@/store/_actiontypes";
import ContractForm from "@/views/contract/ContractForm";

export default {
  components: { ContractForm },
  data: () => ({
    loading: false,
    dialog: false,
    page: 1,
    itemsPerPage: 10,
    options: {},
    headers: [
      { text: "Código", value: "id", width: 90 /*, align: " d-none"*/ },
      { text: "Nome", value: "name" },
      { text: "Descrição", value: "description" },
      { text: "Última atualização", value: "updatedAt", width: 180 },
      { text: "Ativo", value: "enabled", width: 80 },
      { text: "Ações", value: "action", sortable: false, width: 75 },
    ],
    editedContract: {
      id: 0,
      name: "",
      description: "",
      enabled: false,
    },
    defaultContract: {
      id: 0,
      name: "",
      description: "",
      enabled: false,
    },
  }),
  computed: {
    ...mapState({
      items: (state) => state.contracts.contracts,
      totalElements: (state) => state.contracts.totalElements,
      totalPages: (state) => state.contracts.totalPages,
    }),
    formTitle() {
      return this.editedContract.id === 0 ? "Novo Contrato" : "Editar Contrato";
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
    ...mapActions("contracts", [
      LOAD_CONTRACTS,
      CREATE_CONTRACT,
      EDIT_CONTRACT,
      REMOVE_CONTRACT,
    ]),
    async loadData() {
      this.loading = true;
      await this.LOAD_CONTRACTS(this.getPageOptions());
      this.loading = false;
    },
    editContract(item) {
      this.editedContract = Object.assign({}, item);
      this.dialog = true;
    },
    deleteContract(item) {
      confirm("Tem certeza de que deseja excluir este Contrato?") &&
        this.REMOVE_CONTRACT(this.getPageOptions(item));
    },
    close() {
      this.dialog = false;
      this.editedContract = Object.assign({}, this.defaultContract);
      this.$refs.form.reset();
    },
    saveContract() {
      this.loading = true;
      if (this.editedContract.id == 0) {
        this.CREATE_CONTRACT(this.getPageOptions(this.editedContract))
          .then(() => {
            this.close();
          })
          .finally(() => {
            this.loading = false;
          });
      } else {
        this.EDIT_CONTRACT({
          contract: this.editedContract,
        })
          .then(() => {
            this.close();
          })
          .finally(() => {
            this.loading = false;
          });
      }
    },
    getPageOptions(contract) {
      const { page, itemsPerPage, sortBy, sortDesc } = this.options;
      let pageOptions = {
        page: page - 1,
        itemsPerPage: itemsPerPage,
        sort: sortBy[0] || "name",
        sortDesc: sortDesc[0] || false,
      };
      if (contract) pageOptions["contract"] = contract;
      return pageOptions;
    },
    getColorEnabled(enabled) {
      return enabled ? "green" : "red";
    },
  },
};
</script>

<style>
</style>