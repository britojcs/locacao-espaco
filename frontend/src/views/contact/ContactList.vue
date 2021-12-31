<template>
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
            <div class="d-flex align-center pa-4 primary lighten-5">
              <span class="primary--text font-weight-medium"
                >Contatos de <b>{{ contactOwnerName }}</b></span
              >
              <v-divider
                class="mx-2 my-1"
                inset
                vertical
                style="height: 20px"
              ></v-divider>
              <v-spacer></v-spacer>
              <v-btn
                outlined
                small
                class="primary--text font-weight-bold mr-2"
                @click="$router.push('/clientes')"
              >
                <v-icon left> mdi-arrow-left-thick </v-icon>
                Voltar</v-btn
              >
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
                    <ContactForm
                      :contact="editedContact"
                      :showCloseButton="true"
                      :onCloseClick="close"
                      :onSubmitClick="saveContact"
                      :loading="loading"
                      ref="form"
                    />
                  </v-card-text>
                </v-card>
              </v-dialog>
            </div>
          </template>
          <template v-slot:[`item.action`]="{ item }">
            <div class="d-flex justify-end">
              <IconTip
                icon="edit"
                tooltip="Editar"
                eventname="editRecord"
                @editRecord="editContact(item)"
              />
              <IconTip
                icon="delete"
                tooltip="Excluir"
                eventname="deleteRecord"
                @deleteRecord="deleteContact(item)"
              />
            </div>
          </template>
        </v-data-table>
      </v-flex>
    </v-layout>
    <ConfirmDialog ref="confirm" />
  </v-container>
</template>

<script>
import { mapState, mapActions } from "vuex";
import {
  LOAD_CUSTOMER,
  LOAD_CUSTOMER_CONTACTS,
  CREATE_CONTACT,
  EDIT_CONTACT,
  REMOVE_CONTACT,
} from "@/store/_actiontypes";
import ContactForm from "@/views/contact/ContactForm";
import ConfirmDialog from "@/components/dialog/ConfirmDialog";
import IconTip from "@/components/tooltip/IconTip";

export default {
  components: { ContactForm, ConfirmDialog, IconTip },
  data: () => ({
    loading: false,
    dialog: false,
    page: 1,
    itemsPerPage: 10,
    options: {},
    contactOwnerId: 0,
    contactOwnerType: "",
    contactOwnerName: "-",
    headers: [
      { text: "Código", value: "id", align: " d-none" },
      { text: "Tipo", value: "contactType.description", width: 150 },
      { text: "Contato", value: "name" },
      { text: "Descrição/Obervação", value: "description" },
      { text: "Ações", value: "action", sortable: false, width: 100 },
    ],
    editedContact: {
      id: 0,
      contactType: { name: "PERSONAL_PHONE", description: "TELEFONE PESSOAL" },
      name: "",
      description: "",
      customerId: 0,
    },
    defaultContact: {
      id: 0,
      contactType: { name: "PERSONAL_PHONE", description: "TELEFONE PESSOAL" },
      name: "",
      description: "",
      customerId: 0,
    },
  }),
  async mounted() {
    this.contactOwnerId = this.$route.params.id;
    this.contactOwnerType = this.$route.params.owner;
    this.loading = true;
    this.$store
      .dispatch(`customers/${LOAD_CUSTOMER}`, {
        id: this.contactOwnerId,
      })
      .then((data) => {
        this.contactOwnerName = data.fullname;
      })
      .finally(() => {
        this.loading = false;
      });
  },
  computed: {
    ...mapState({
      items: (state) => state.contacts.contacts,
      totalElements: (state) => state.contacts.totalElements,
      totalPages: (state) => state.contacts.totalPages,
    }),
    formTitle() {
      return this.editedContact.id === 0 ? "Novo Contato" : "Editar Contato";
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
    ...mapActions("contacts", [
      LOAD_CUSTOMER_CONTACTS,
      CREATE_CONTACT,
      EDIT_CONTACT,
      REMOVE_CONTACT,
    ]),
    async loadData() {
      this.loading = true;
      await this.LOAD_CUSTOMER_CONTACTS(this.getPageOptions());
      this.loading = false;
    },
    editContact(item) {
      this.editedContact = Object.assign({}, item);
      this.dialog = true;
    },
    async deleteContact(item) {
      if (
        await this.$refs.confirm.open({
          message: "Tem certeza de que deseja excluir este Contato?",
        })
      ) {
        this.REMOVE_CONTACT(this.getPageOptions(item));
      }
    },
    close() {
      this.dialog = false;
      this.editedContact = Object.assign({}, this.defaultContact);
      this.$refs.form.reset();
    },
    saveContact() {
      this.loading = true;
      if (this.editedContact.id == 0) {
        this.CREATE_CONTACT(this.getPageOptions(this.editedContact))
          .then(() => {
            this.close();
          })
          .finally(() => {
            this.loading = false;
          });
      } else {
        this.EDIT_CONTACT({
          contact: this.editedContact,
        })
          .then(() => {
            this.close();
          })
          .finally(() => {
            this.loading = false;
          });
      }
    },
    getPageOptions(contact) {
      const { page, itemsPerPage, sortBy, sortDesc } = this.options;
      let pageOptions = {
        contactOwnerId: this.contactOwnerId,
        contactOwnerType: this.contactOwnerType,
        page: page - 1,
        itemsPerPage: itemsPerPage,
        sort: sortBy[0] || "contactType",
        sortDesc: sortDesc[0] || false,
      };
      if (contact) {
        if (this.contactOwnerType == "clientes")
          contact["customerId"] = this.contactOwnerId;
        pageOptions["contact"] = contact;
      }
      return pageOptions;
    },
  },
};
</script>

<style>
</style>