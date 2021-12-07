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
                <span class="primary--text font-weight-medium">Clientes</span>
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
                      <CustomerForm
                        :customer="editedCustomer"
                        :showCloseButton="true"
                        :onCloseClick="close"
                        :onSubmitClick="saveCustomer"
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
              <div class="d-flex justify-end">
                <v-icon small class="mr-2" @click="showContacts(item)"
                  >mdi-account-box</v-icon
                >
                <v-icon small class="mr-2" @click="editCustomer(item)"
                  >edit</v-icon
                >
                <v-icon small @click="deleteCustomer(item)">delete</v-icon>
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
  LOAD_CUSTOMERS,
  CREATE_CUSTOMER,
  EDIT_CUSTOMER,
  REMOVE_CUSTOMER,
} from "@/store/_actiontypes";
import CustomerForm from "@/views/customer/CustomerForm";

export default {
  components: { CustomerForm },
  data: () => ({
    loading: false,
    dialog: false,
    page: 1,
    itemsPerPage: 10,
    options: {},
    headers: [
      { text: "Código", value: "id", width: 90 /*, align: " d-none"*/ },
      { text: "Nome completo", value: "fullname" },
      { text: "CPF/CNPJ", value: "cpfCnpj" },
      { text: "CEP", value: "cep" },
      { text: "Cidade", value: "city" },
      { text: "Estado", value: "state.name", width: 90 },
      { text: "Ações", value: "action", sortable: false, width: 100 },
    ],
    editedCustomer: {
      id: 0,
      fullname: "",
      cpfCnpj: "",
      cep: "",
      address: "",
      addressComplement: "",
      district: "",
      city: "",
      state: {},
      enabled: true,
    },
    defaultCustomer: {
      id: 0,
      fullname: "",
      cpfCnpj: "",
      cep: "",
      address: "",
      addressComplement: "",
      district: "",
      city: "",
      state: {},
      enabled: true,
    },
  }),
  computed: {
    ...mapState({
      items: (state) => state.customers.customers,
      totalElements: (state) => state.customers.totalElements,
      totalPages: (state) => state.customers.totalPages,
    }),
    formTitle() {
      return this.editedCustomer.id === 0 ? "Novo Cliente" : "Editar Cliente";
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
    ...mapActions("customers", [
      LOAD_CUSTOMERS,
      CREATE_CUSTOMER,
      EDIT_CUSTOMER,
      REMOVE_CUSTOMER,
    ]),
    async loadData() {
      this.loading = true;
      await this.LOAD_CUSTOMERS(this.getPageOptions());
      this.loading = false;
    },
    showContacts(item) {
      this.$router.push(`/clientes/${item.id}/contatos`);
    },
    editCustomer(item) {
      this.editedCustomer = Object.assign({}, item);
      this.dialog = true;
    },
    deleteCustomer(item) {
      confirm("Tem certeza de que deseja excluir este Cliente?") &&
        this.REMOVE_CUSTOMER(this.getPageOptions(item));
    },
    close() {
      this.dialog = false;
      this.editedCustomer = Object.assign({}, this.defaultCustomer);
      this.$refs.form.reset();
    },
    saveCustomer() {
      this.loading = true;
      if (this.editedCustomer.id == 0) {
        this.CREATE_CUSTOMER(this.getPageOptions(this.editedCustomer))
          .then(() => {
            this.close();
          })
          .finally(() => {
            this.loading = false;
          });
      } else {
        this.EDIT_CUSTOMER({
          customer: this.editedCustomer,
        })
          .then(() => {
            this.close();
          })
          .finally(() => {
            this.loading = false;
          });
      }
    },
    getPageOptions(customer) {
      const { page, itemsPerPage, sortBy, sortDesc } = this.options;
      let pageOptions = {
        page: page - 1,
        itemsPerPage: itemsPerPage,
        sort: sortBy[0] || "fullname",
        sortDesc: sortDesc[0] || false,
      };
      if (customer) pageOptions["customer"] = customer;
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