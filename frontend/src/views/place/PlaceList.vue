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
                <span class="primary--text font-weight-medium">Espaços</span>
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
                      <PlaceForm
                        :place="editedPlace"
                        :showCloseButton="true"
                        :onCloseClick="close"
                        :onSubmitClick="savePlace"
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
              <div class="d-flex justify-end">
                <v-icon small class="mr-2" @click="editPlace(item)"
                  >edit</v-icon
                >
                <v-icon small @click="deletePlace(item)">delete</v-icon>
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
  LOAD_PLACES,
  CREATE_PLACE,
  EDIT_PLACE,
  REMOVE_PLACE,
} from "@/store/_actiontypes";
import PlaceForm from "@/views/place/PlaceForm";

export default {
  components: { PlaceForm },
  data: () => ({
    loading: false,
    dialog: false,
    page: 1,
    itemsPerPage: 10,
    options: {},
    headers: [
      { text: "Código", value: "id", width: 90 /*, align: " d-none"*/ },
      { text: "Nome", value: "name" },
      { text: "Tipo de locação", value: "leaseType.description" },
      { text: "Valor", value: "value", width: 140 },
      { text: "Ativo", value: "enabled", width: 80 },
      { text: "Ações", value: "action", sortable: false, width: 100 },
    ],
    editedPlace: {
      id: 0,
      name: "",
      leaseType: {
        name: "INDEPENDENT",
        description: "Locação independente (Espaço principal)",
      },
      value: 0,
      enabled: true,
    },
    defaultPlace: {
      id: 0,
      name: "",
      leaseType: {
        name: "INDEPENDENT",
        description: "Locação independente (Espaço principal)",
      },
      value: 0,
      enabled: true,
    },
  }),
  computed: {
    ...mapState({
      items: (state) => state.places.places,
      totalElements: (state) => state.places.totalElements,
      totalPages: (state) => state.places.totalPages,
    }),
    formTitle() {
      return this.editedPlace.id === 0 ? "Novo Espaço" : "Editar Espaço";
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
    ...mapActions("places", [
      LOAD_PLACES,
      CREATE_PLACE,
      EDIT_PLACE,
      REMOVE_PLACE,
    ]),
    async loadData() {
      this.loading = true;
      await this.LOAD_PLACES(this.getPageOptions());
      this.loading = false;
    },
    editPlace(item) {
      this.editedPlace = Object.assign({}, item);
      this.dialog = true;
    },
    deletePlace(item) {
      confirm("Tem certeza de que deseja excluir este Espaço?") &&
        this.REMOVE_PLACE(this.getPageOptions(item));
    },
    close() {
      this.dialog = false;
      this.editedPlace = Object.assign({}, this.defaultPlace);
      this.$refs.form.reset();
    },
    savePlace() {
      this.loading = true;
      if (this.editedPlace.id == 0) {
        this.CREATE_PLACE(this.getPageOptions(this.editedPlace))
          .then(() => {
            this.close();
          })
          .finally(() => {
            this.loading = false;
          });
      } else {
        this.EDIT_PLACE({
          place: this.editedPlace,
        })
          .then(() => {
            this.close();
          })
          .finally(() => {
            this.loading = false;
          });
      }
    },
    getPageOptions(place) {
      const { page, itemsPerPage, sortBy, sortDesc } = this.options;
      let pageOptions = {
        page: page - 1,
        itemsPerPage: itemsPerPage,
        sort: sortBy[0] || "name",
        sortDesc: sortDesc[0] || false,
      };
      if (place) pageOptions["place"] = place;
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