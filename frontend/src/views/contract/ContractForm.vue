<template>
  <div>
    <v-alert text outlined dense color="grey">
      <v-container>
        <v-row>
          <v-col cols="12" class="ma-0"> Variáveis disponíveis </v-col>
        </v-row>
        <v-row>
          <v-col cols="12" md="4" class="py-0 ma-0">
            <b>[TESTE]</b> Prefix for dollar currency
          </v-col>
          <v-col cols="12" md="4" class="py-0 ma-0">
            <b>[TESTE]</b> Prefix for dollar currency
          </v-col>
          <v-col cols="12" md="4" class="py-0 ma-0">
            <b>[TESTE]</b> Prefix for dollar currency
          </v-col>
        </v-row>
        <v-row>
          <v-col cols="12" md="4" class="py-0 ma-0">
            <b>[TESTE]</b> Prefix for dollar currency
          </v-col>
          <v-col cols="12" md="4" class="py-0 ma-0">
            <b>[TESTE]</b> Prefix for dollar currency
          </v-col>
          <v-col cols="12" md="4" class="py-0 ma-0">
            <b>[TESTE]</b> Prefix for dollar currency
          </v-col>
        </v-row>
        <v-row>
          <v-col cols="12" md="4" class="py-0 ma-0">
            <b>[TESTE]</b> Prefix for dollar currency
          </v-col>
          <v-col cols="12" md="4" class="py-0 ma-0">
            <b>[TESTE]</b> Prefix for dollar currency
          </v-col>
          <v-col cols="12" md="4" class="py-0 ma-0">
            <b>[TESTE]</b> Prefix for dollar currency
          </v-col>
        </v-row>
      </v-container>
    </v-alert>
    <v-form class="mt-1" ref="contractform">
      <v-container>
        <v-row>
          <v-col cols="12" md="1" class="py-0 ma-0 my-1">
            <input type="hidden" :value="contract.id" />
            <v-checkbox
              class="ma-0 pa-0 form-label"
              v-model="model.enabled"
              label="Ativo"
              type="checkbox"
            />
          </v-col>
          <v-col cols="12" md="3" class="py-0 ma-0 my-1">
            <v-text-field
              v-model="model.name"
              label="Nome"
              class="ma-0 pa-0 form-label"
              dense
              required
              counter="50"
              :rules="[
                required('Nome'),
                minLength('Nome', 2),
                maxLength('Nome', 50),
              ]"
            />
          </v-col>
          <v-col cols="12" md="8" class="py-0 ma-0 my-1">
            <v-text-field
              v-model="model.description"
              label="Descrição"
              class="ma-0 pa-0 form-label"
              dense
              counter="250"
            />
          </v-col>
        </v-row>
        <v-row>
          <v-col class="py-0 ma-0 mt-1 mb-3">
            Conteúdo do contrato
            <VueEditor
              v-model="model.content"
              :editor-toolbar="customToolbar"
              required
              :rules="[required('Conteúdo do contrato')]"
            />
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
                if (!this.$refs.contractform.validate()) return;
                onSubmitClick();
              }
            "
            >Salvar</v-btn
          >
        </v-row>
      </v-container>
    </v-form>
  </div>
</template>

<script>
import validations from "@/helpers/validations";
import { VueEditor } from "vue2-editor";

export default {
  props: {
    contract: {
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
  components: { VueEditor },
  data() {
    return {
      ...validations,
      content: "",
      customToolbar: [
        [{ header: [false, 1, 2, 3, 4, 5, 6] }],
        ["bold", "italic", "underline"],
        [{ list: "ordered" }, { list: "bullet" }],
        [
          { align: "" },
          { align: "center" },
          { align: "right" },
          { align: "justify" },
        ],
        [{ color: [] }],
        ["clean"],
      ],
    };
  },
  computed: {
    model: {
      get() {
        return this.contract;
      },
      set(contract) {
        this.$emit("input", contract);
      },
    },
  },
  methods: {
    reset() {
      this.$refs.contractform.reset();
    },
  },
};
</script>

<style>
</style>