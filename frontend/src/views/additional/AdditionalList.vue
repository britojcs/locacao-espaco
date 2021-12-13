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
              <v-dialog v-model="dialogAdditionalPackage" max-width="400px">
                <v-card>
                  <v-card-title>
                    <span class="text-h5">{{ editedAdditional.name }}</span>
                  </v-card-title>
                  <v-card-text v-if="editedAdditional.additionalPackage">
                    <div
                      v-for="(
                        text, index
                      ) in editedAdditional.additionalPackage.split('\n')"
                      :key="index"
                    >
                      {{ text }}
                    </div>
                  </v-card-text>
                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn
                      outlined
                      small
                      class="primary--text font-weight-bold"
                      @click="dialogAdditionalPackage = false"
                    >
                      Fechar
                    </v-btn>
                  </v-card-actions>
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
              <IconTip
                icon="mdi-text-box-plus-outline"
                tooltip="Adicional múltiplo"
                eventname="editRecord"
                @editRecord="editAdditional(item, true)"
              />
              <IconTip
                icon="edit"
                tooltip="Editar"
                eventname="editRecord"
                @editRecord="editAdditional(item, false)"
              />
              <IconTip
                icon="delete"
                tooltip="Excluir"
                eventname="deleteRecord"
                @deleteRecord="deleteAdditional(item, false)"
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
  LOAD_ADDITIONALS,
  CREATE_ADDITIONAL,
  EDIT_ADDITIONAL,
  REMOVE_ADDITIONAL,
} from "@/store/_actiontypes";
import AdditionalForm from "@/views/additional/AdditionalForm";
import ConfirmDialog from "@/components/dialog/ConfirmDialog";
import IconTip from "@/components/tooltip/IconTip";

export default {
  components: { AdditionalForm, ConfirmDialog, IconTip },
  data: () => ({
    loading: false,
    dialog: false,
    dialogAdditionalPackage: false,
    page: 1,
    itemsPerPage: 10,
    options: {},
    headers: [
      { text: "Código", value: "id", align: " d-none" },
      { text: "Descrição", value: "name" },
      { text: "Valor", value: "value", width: 140 },
      { text: "Obrigatoriedade", value: "requiredType.description" },
      { text: "Ativo", value: "enabled", width: 80 },
      { text: "Ações", value: "action", sortable: false, width: 100 },
    ],
    editedAdditional: {
      id: 0,
      name: "",
      requiredType: { name: "NOT_REQUIRED", description: "Não é obrigatório" },
      additionalPackage: "",
      value: 0,
      enabled: true,
    },
    defaultAdditional: {
      id: 0,
      name: "",
      requiredType: { name: "NOT_REQUIRED", description: "Não é obrigatório" },
      additionalPackage: "",
      value: 0,
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
    dialogAdditionalPackage(val) {
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
    editAdditional(item, showDialogAdditionalPackage) {
      this.editedAdditional = Object.assign({}, item);
      if (showDialogAdditionalPackage) this.dialogAdditionalPackage = true;
      else this.dialog = true;
    },
    async deleteAdditional(item) {
      if (
        await this.$refs.confirm.open({
          message: "Tem certeza de que deseja excluir este Adicional?",
        })
      ) {
        this.REMOVE_ADDITIONAL(this.getPageOptions(item));
      }
    },
    close() {
      this.dialog = false;
      this.dialogAdditionalPackage = false;
      this.editedAdditional = Object.assign({}, this.defaultAdditional);
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